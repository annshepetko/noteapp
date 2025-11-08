package com.test.ann.facade;

import static org.junit.jupiter.api.Assertions.*;

import com.test.ann.documents.Note;
import com.test.ann.dto.NoteDto;
import com.test.ann.mapper.NoteMapper;
import com.test.ann.repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.*;


import static org.mockito.Mockito.*;

class NoteServiceFacadeTest {

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private NoteMapper noteMapper;

    @InjectMocks
    private NoteServiceFacade noteServiceFacade;

    private NoteDto noteDto;
    private Note note;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        this.noteDto = new NoteDto("1", "title", "content", LocalDateTime.now(), Collections.emptyList());
        this.note = new Note("title", "content", LocalDateTime.now(), List.of());
    }

    @Test
    void createNote_ShouldSaveNote() {
        NoteDto.CreateNoteBody dto = this.noteDto.createNoteBody();

        when(noteMapper.toNote(dto)).thenReturn(this.note);

        noteServiceFacade.createNote(dto);

        verify(noteRepository, times(1)).save(note);
    }

    @Test
    void updateNote_ShouldUpdateAndSaveNote() {
        String id = "1";
        Note existing = new Note( "Old", "Old", LocalDateTime.now(), List.of());
        NoteDto.UpdateNoteBody dto = this.noteDto.updateNoteBody();

        when(noteRepository.findById(id)).thenReturn(Optional.of(existing));

        noteServiceFacade.updateNote(id, dto);

        assertEquals("title", existing.getTitle());
        assertEquals("content", existing.getContent());
        verify(noteRepository).save(existing);
    }

    @Test
    void updateNote_NotFound_ShouldThrow() {
        when(noteRepository.findById("999")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> noteServiceFacade.updateNote("999", mock(NoteDto.UpdateNoteBody.class)));
    }

    @Test
    void deleteNoteById_ShouldCallRepository() {
        noteServiceFacade.deleteNoteById("77");
        verify(noteRepository).deleteById("77");
    }

    @Test
    void getNoteDetailsById_ShouldReturnDto() {
        String id = "111";
        Note note = new Note("Test", "Content", LocalDateTime.now(), List.of());
        NoteDto expectedDto = new NoteDto(id, "Test", "Content", LocalDateTime.now(), List.of());

        when(noteRepository.findById(id)).thenReturn(Optional.of(note));
        when(noteMapper.toNoteDto(note)).thenReturn(expectedDto);

        NoteDto result = noteServiceFacade.getNoteDetailsByNoteId(id);

        assertEquals(expectedDto, result);
    }
}
