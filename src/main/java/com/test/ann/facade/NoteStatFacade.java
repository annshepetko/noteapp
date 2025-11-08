package com.test.ann.facade;

import com.test.ann.documents.Note;
import com.test.ann.dto.NoteStatDto;
import com.test.ann.exception.NoteNotFoundException;
import com.test.ann.repository.NoteRepository;
import com.test.ann.service.NoteStatService;
import org.springframework.stereotype.Service;

/**
 * Facade for handling note statistics.
 * <p>
 * This class provides a high-level API to retrieve word statistics for a note.
 * It delegates the computation of statistics to {@link NoteStatService} and
 * fetches notes from {@link NoteRepository}.
 */
@Service
public class NoteStatFacade {

    private final NoteStatService noteStatService;
    private final NoteRepository noteRepository;

    /**
     * Constructs a new {@code NoteStatFacade} with the given service and repository.
     *
     * @param noteStatService the service responsible for computing word statistics
     * @param noteRepository  the repository used to retrieve notes
     */
    public NoteStatFacade(NoteStatService noteStatService, NoteRepository noteRepository) {
        this.noteStatService = noteStatService;
        this.noteRepository = noteRepository;
    }

    /**
     * Retrieves word statistics for the note with the given ID.
     *
     * @param id the ID of the note
     * @return a {@link NoteStatDto} containing the note ID and word frequency statistics
     * @throws NoteNotFoundException if no note with the given ID exists
     */
    public NoteStatDto getStatisticForNote(String id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException("note not found"));

        return new NoteStatDto(note.getId(), noteStatService.getNoteWordStats(note));
    }
}
