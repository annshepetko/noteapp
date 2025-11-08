package com.test.ann.service;

import com.test.ann.documents.Note;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class NoteStatServiceTest {

    private final NoteStatService noteStatService = new NoteStatService();

    @Test
    void shouldReturnEmptyMapForNullOrBlankContent() {
        Note emptyNote = new Note();
        emptyNote.setContent(null);
        assertThat(noteStatService.getNoteWordStats(emptyNote)).isEmpty();

        Note blankNote = new Note();
        blankNote.setContent("   ");
        assertThat(noteStatService.getNoteWordStats(blankNote)).isEmpty();
    }

    @Test
    void shouldCountWordsCorrectlyAndSortByFrequency() {
        Note note = new Note();
        note.setContent("Hello world! Hello, Java. Hello world?");

        Map<String, Long> stats = noteStatService.getNoteWordStats(note);

        Map<String, Long> expected = new LinkedHashMap<>();
        expected.put("hello", 3L);
        expected.put("world", 2L);
        expected.put("java", 1L);

        assertThat(stats).isEqualTo(expected);
    }

    @Test
    void shouldHandleTextWithVariousSeparators() {
        Note note = new Note();
        note.setContent("One, two; three: four! five? six (seven) \"eight\"");

        Map<String, Long> stats = noteStatService.getNoteWordStats(note);

        Map<String, Long> expected = new LinkedHashMap<>();
        expected.put("one", 1L);
        expected.put("two", 1L);
        expected.put("three", 1L);
        expected.put("four", 1L);
        expected.put("five", 1L);
        expected.put("six", 1L);
        expected.put("seven", 1L);
        expected.put("eight", 1L);

        assertThat(stats).isEqualTo(expected);
    }
}
