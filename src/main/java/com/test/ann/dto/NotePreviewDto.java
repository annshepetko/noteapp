package com.test.ann.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;


/**
 * Data Transfer Object for previewing notes in lists.
 *
 * @param id        the unique identifier of the note
 * @param title     the title of the note
 * @param createdAt the timestamp when the note was created, formatted as "yyyy-MM-dd HH:mm:ss" in Europe/Kyiv timezone
 */
public record NotePreviewDto(
        String id,
        String title,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Kyiv")
        LocalDateTime createdAt
) {
}
