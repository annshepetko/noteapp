package com.test.ann.facade;

import com.test.ann.dto.FilterCredentials;
import com.test.ann.dto.NotePreviewDto;
import com.test.ann.service.NoteQueryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Facade layer for retrieving notes with optional filtering and pagination.
 * <p>
 * This class acts as a bridge between the controller and the lower-level
 * {@link NoteQueryService}, mapping {@link com.test.ann.documents.Note} entities
 * to {@link NotePreviewDto} objects for presentation in the API.
 */
@Service
public class NoteFindFacade {

    private final NoteQueryService noteQueryService;


    public NoteFindFacade(NoteQueryService noteQueryService) {
        this.noteQueryService = noteQueryService;
    }

    /**
     * Retrieves a page of notes, optionally filtered, and maps them to {@link NotePreviewDto}.
     *
     * @param pageable pagination and sorting information
     * @param filter   optional filter criteria (e.g., by tag)
     * @return a page of {@link NotePreviewDto} objects matching the filter criteria
     */
    public Page<NotePreviewDto> findNotes(Pageable pageable, FilterCredentials filter) {
        return noteQueryService.findNotes(filter, pageable)
                .map(note -> new NotePreviewDto(
                        note.getId(),
                        note.getTitle(),
                        note.getCreatedAt()
                ));
    }
}
