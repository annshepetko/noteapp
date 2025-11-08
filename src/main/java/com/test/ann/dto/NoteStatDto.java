package com.test.ann.dto;

import java.util.Map;

/**
 * Data Transfer Object for note statistics.
 * Represents the frequency of each word in a note.
 *
 * @param noteId        the unique identifier of the note
 * @param frequencyStat a map where keys are words from the note and values are their corresponding counts
 */
public record NoteStatDto(
        String noteId,
        Map<String, Long> frequencyStat
) {
}
