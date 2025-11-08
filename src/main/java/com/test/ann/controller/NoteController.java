package com.test.ann.controller;

import com.test.ann.dto.NoteDto;
import com.test.ann.facade.NoteServiceFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing notes.
 * Provides endpoints for creating, updating, deleting, and retrieving notes by ID.
 */
@RestController
@RequestMapping("/api/v1/notes")
public class NoteController {

    private final NoteServiceFacade noteServiceFacade;

    public NoteController(NoteServiceFacade noteServiceFacade) {
        this.noteServiceFacade = noteServiceFacade;
    }

    /**
     * Creates a new note.
     *
     * @param body the note data to create
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createNote(@RequestBody @Validated NoteDto.CreateNoteBody body) {
        noteServiceFacade.createNote(body);
    }

    /**
     * Updates an existing note by its ID.
     *
     * @param id   the ID of the note to update
     * @param body the updated note data
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateNote(
            @PathVariable String id,
            @RequestBody @Validated NoteDto.UpdateNoteBody body
    ) {
        noteServiceFacade.updateNote(id, body);
    }

    /**
     * Deletes a note by its ID.
     *
     * @param id the ID of the note to delete
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNote(@PathVariable String id) {
        noteServiceFacade.deleteNoteById(id);
    }

    /**
     * Retrieves a note by its ID.
     *
     * @param id the ID of the note to retrieve
     * @return the note data
     */
    @GetMapping("/{id}")
    public ResponseEntity<NoteDto> getNoteById(@PathVariable String id) {
        return ResponseEntity.ok().body(noteServiceFacade.getNoteDetailsByNoteId(id));
    }
}
