package com.test.ann.dto;

import com.test.ann.documents.Tag;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Transfer Object representing a Note.
 *
 * @param noteId    the unique identifier of the note, must not be null
 * @param title     the title of the note, must not be null or empty
 * @param content   the content of the note, must not be null or empty
 * @param createdAt timestamp of note creation
 * @param tags      list of tags associated with the note
 */
public record NoteDto(

        @NotNull(message = "Note ID must not be null.")
        String noteId,

        @NotNull(message = "Title must not be null.")
        @NotEmpty(message = "Title cannot be empty.")
        String title,

        @NotNull(message = "Content must not be null.")
        @NotEmpty(message = "Content cannot be empty.")
        String content,

        LocalDateTime createdAt,
        List<Tag> tags

) {

    /**
     * Converts this DTO to a {@link CreateNoteBody} for creating a note.
     */
    public CreateNoteBody createNoteBody() {
        return new CreateNoteBody(this.title, this.content, this.createdAt, tags);
    }

    /**
     * Converts this DTO to an {@link UpdateNoteBody} for updating a note.
     */
    public UpdateNoteBody updateNoteBody() {
        return new UpdateNoteBody(this.title, this.content, this.createdAt, tags);
    }

    /**
     * DTO used for creating a new note.
     */
    public static record CreateNoteBody(

            @NotNull(message = "Title must not be null.")
            @NotEmpty(message = "Title cannot be empty.")
            String title,

            @NotNull(message = "Content must not be null.")
            @NotEmpty(message = "Content cannot be empty.")
            String content,

            LocalDateTime createdAt,
            List<Tag> tags
    ) {
    }

    /**
     * DTO used for updating an existing note.
     */
    public static record UpdateNoteBody(

            @NotNull(message = "Title must not be null.")
            @NotEmpty(message = "Title cannot be empty.")
            String title,

            @NotNull(message = "Content must not be null.")
            @NotEmpty(message = "Content cannot be empty.")
            String content,

            LocalDateTime createdAt,
            List<Tag> tags
    ) {
    }
}
