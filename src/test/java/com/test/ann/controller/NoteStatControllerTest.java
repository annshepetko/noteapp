package com.test.ann.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.ann.dto.NoteStatDto;
import com.test.ann.facade.NoteStatFacade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NoteStatController.class)
class NoteStatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteStatFacade noteStatFacade;

    @Test
    void shouldReturnNoteStats() throws Exception {
        // given
        String noteId = "123";
        Map<String, Long> stats = new LinkedHashMap<>();
        stats.put("hello", 3L);
        stats.put("world", 2L);

        NoteStatDto noteStatDto = new NoteStatDto(noteId, stats);

        when(noteStatFacade.getStatisticForNote(noteId)).thenReturn(noteStatDto);

        // when + then
        mockMvc.perform(get("/api/v1/notes/{id}/stats", noteId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.noteId").value(noteId))
                .andExpect(jsonPath("$.frequencyStat.hello").value(3))
                .andExpect(jsonPath("$.frequencyStat.world").value(2));
    }
}