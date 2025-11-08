package com.test.ann.controller;

import com.test.ann.documents.Tag;
import com.test.ann.dto.FilterCredentials;
import com.test.ann.dto.NotePreviewDto;
import com.test.ann.facade.NoteFindFacade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for fetching notes for the dashboard.
 * Supports pagination, sorting, and optional filtering by tag.
 */
@RestController
@RequestMapping("/api/v1/dashboard")
public class NoteDashboardController {

    private final NoteFindFacade noteService;

    public NoteDashboardController(NoteFindFacade noteService) {
        this.noteService = noteService;
    }

    /**
     * Retrieves a paginated list of note previews.
     *
     * @param page          the page number (default 0)
     * @param size          the page size (default 10)
     * @param tag           optional tag to filter notes
     * @param sortBy        the field to sort by (default "createdAt")
     * @param sortDirection the sort direction, either ASC or DESC (default "DESC")
     * @return a paginated list of note previews
     */
    @GetMapping("/notes")
    public ResponseEntity<Page<NotePreviewDto>> getNotes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Tag tag,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection
    ) {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase());
        Sort sort = Sort.by(direction, sortBy);

        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(
                noteService.findNotes(pageable, new FilterCredentials(tag))
        );
    }
}
