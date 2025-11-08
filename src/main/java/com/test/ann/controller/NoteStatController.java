package com.test.ann.controller;

import com.test.ann.dto.NoteStatDto;
import com.test.ann.facade.NoteStatFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for retrieving statistics about notes.
 * Provides word frequency statistics for a specific note.
 */
@RestController
@RequestMapping("/api/v1/notes")
public class NoteStatController {

    private final NoteStatFacade noteStatFacade;

    public NoteStatController(NoteStatFacade noteStatFacade) {
        this.noteStatFacade = noteStatFacade;
    }

    /**
     * Retrieves word frequency statistics for the note with the given ID.
     *
     * @param id the ID of the note
     * @return a NoteStatDto containing the note ID and word frequency map
     */
    @GetMapping("/{id}/stats")
    public ResponseEntity<NoteStatDto> getNoteStats(@PathVariable String id) {
        return ResponseEntity.ok(noteStatFacade.getStatisticForNote(id));
    }

}
