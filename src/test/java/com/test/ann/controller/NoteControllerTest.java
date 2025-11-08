package com.test.ann.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.ann.dto.NoteDto;
import com.test.ann.facade.NoteServiceFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NoteController.class)
class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NoteServiceFacade noteServiceFacade;

    private NoteDto noteDto;

    @BeforeEach
    void setUp() {
        this.noteDto = new NoteDto(
                "1",
                "title",
                "Updated title",
                LocalDateTime.now(),
                List.of()
        );
    }

    @Test
    void shouldCreateNote() throws Exception {
        NoteDto.CreateNoteBody body = noteDto.createNoteBody();

        mockMvc.perform(post("/api/v1/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isCreated());

        verify(noteServiceFacade).createNote(any());
    }

    @Test
    void shouldUpdateNote() throws Exception {

        mockMvc.perform(put("/api/v1/notes/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(noteDto.updateNoteBody())))
                .andExpect(status().isNoContent());

        verify(noteServiceFacade).updateNote(eq("123"), any());
    }

    @Test
    void shouldGetNoteById() throws Exception {
        Mockito.when(noteServiceFacade.getNoteDetailsByNoteId("123"))
                .thenReturn(noteDto);

        mockMvc.perform(get("/api/v1/notes/123"))
                .andExpect(status().isOk());

        verify(noteServiceFacade).getNoteDetailsByNoteId("123");
    }

    @Test
    void shouldDeleteNote() throws Exception {
        mockMvc.perform(delete("/api/v1/notes/123"))
                .andExpect(status().isNoContent());

        verify(noteServiceFacade).deleteNoteById("123");
    }
}
