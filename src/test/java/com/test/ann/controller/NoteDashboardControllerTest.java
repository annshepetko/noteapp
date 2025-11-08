package com.test.ann.controller;

import com.test.ann.dto.FilterCredentials;
import com.test.ann.dto.NotePreviewDto;
import com.test.ann.facade.NoteFindFacade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(NoteDashboardController.class)
class NoteDashboardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteFindFacade noteFindFacade;

    @Test
    void shouldReturnNotesWithPaginationAndFilter() throws Exception {

        NotePreviewDto noteDto = new NotePreviewDto("123", "Test title", LocalDateTime.now());
        Page<NotePreviewDto> page = new PageImpl<>(List.of(noteDto));

        when(noteFindFacade.findNotes(any(Pageable.class), any(FilterCredentials.class)))
                .thenReturn(page);

        mockMvc.perform(get("/api/v1/dashboard/notes")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "createdAt")
                        .param("sortDirection", "DESC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value("123"))
                .andExpect(jsonPath("$.content[0].title").value("Test title"));

        verify(noteFindFacade)
                .findNotes(any(Pageable.class), any(FilterCredentials.class));
    }
}
