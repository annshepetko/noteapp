package com.test.ann.facade;

import com.test.ann.documents.Note;
import com.test.ann.dto.NoteDto;
import com.test.ann.exception.NoteNotFoundException;
import com.test.ann.mapper.NoteMapper;
import com.test.ann.repository.NoteRepository;
import org.springframework.stereotype.Service;

/**
 * Service layer for managing notes.
 * <p>
 * Provides operations for creating, updating, deleting, and retrieving note details.
 * All interactions with the database are done through {@link NoteRepository}.
 * Mapping between DTOs and entities is handled by {@link NoteMapper}.
 */
@Service
public class NoteServiceFacade {

    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;

    /**
     * Constructs a new {@code NoteServiceFacade} with the given repository and mapper.
     *
     * @param noteRepository the repository used to access note data
     * @param noteMapper     the mapper used to convert between DTOs and entities
     */
    public NoteServiceFacade(NoteRepository noteRepository, NoteMapper noteMapper) {
        this.noteRepository = noteRepository;
        this.noteMapper = noteMapper;
    }

    /**
     * Creates a new note in the database.
     *
     * @param noteDto the DTO containing note data to be created
     */
    public void createNote(NoteDto.CreateNoteBody noteDto) {
        Note note = noteMapper.toNote(noteDto);
        noteRepository.save(note);
    }

    /**
     * Updates an existing note with the given ID.
     *
     * @param id      the ID of the note to update
     * @param noteDto the DTO containing updated note fields
     * @throws NoteNotFoundException if no note with the given ID exists
     */
    public void updateNote(String id, NoteDto.UpdateNoteBody noteDto) {
        Note existing = findNote(id);
        updateNote(existing, noteDto);
        noteRepository.save(existing);
    }

    /**
     * Deletes a note by its ID.
     *
     * @param id the ID of the note to delete
     */
    public void deleteNoteById(String id) {
        noteRepository.deleteById(id);
    }

    /**
     * Retrieves the details of a note as a DTO.
     *
     * @param id the ID of the note
     * @return a {@link NoteDto} containing the note details
     * @throws NoteNotFoundException if no note with the given ID exists
     */
    public NoteDto getNoteDetailsByNoteId(String id) {
        Note note = findNote(id);
        return noteMapper.toNoteDto(note);
    }

    /**
     * Finds a note by its ID.
     *
     * @param id the ID of the note
     * @return the {@link Note} entity
     * @throws NoteNotFoundException if no note with the given ID exists
     */
    private Note findNote(String id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException("Note not found"));
    }

    /**
     * Updates the fields of an existing note with values from the DTO.
     *
     * @param note the existing note entity to update
     * @param dto  the DTO containing updated fields
     */
    private void updateNote(Note note, NoteDto.UpdateNoteBody dto) {
        if (dto.title() != null) {
            note.setTitle(dto.title());
        }
        if (dto.content() != null) {
            note.setContent(dto.content());
        }
        if (dto.tags() != null) {
            note.setTags(dto.tags());
        }
        if (dto.createdAt() != null) {
            note.setCreatedAt(dto.createdAt());
        }
    }
}
