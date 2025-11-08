package com.test.ann.service;

import com.test.ann.documents.Note;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service class responsible for analyzing the content of notes
 * and generating word frequency statistics.
 */
@Service
public class NoteStatService {

    /**
     * Generates a word frequency map for the given note.
     * <p>
     * The method normalizes the text to lowercase, splits it by common delimiters,
     * counts occurrences of each word, and sorts the result in descending order
     * of frequency.
     *
     * @param note the note whose content will be analyzed
     * @return a map where keys are words and values are their respective counts,
     *         sorted in descending order by frequency; returns an empty map if
     *         the note content is null or blank
     */
    public Map<String, Long> getNoteWordStats(Note note) {
        String text = note.getContent();
        if (text == null || text.isBlank()) {
            return Map.of();
        }

        Map<String, Long> wordCount = filterNoteText(text);
        return sortResultMap(wordCount);
    }

    /**
     * Splits the text into words, filters out blank entries, and counts occurrences.
     *
     * @param text the text to process
     * @return a map of words to their respective counts
     */
    private Map<String, Long> filterNoteText(String text) {
        return Arrays.stream(text.toLowerCase()
                        .split("[\\s.,!?;:()\"]+"))
                .filter(word -> !word.isBlank())
                .collect(Collectors.groupingBy(
                        word -> word,
                        Collectors.counting()
                ));
    }

    /**
     * Sorts a word count map by values in descending order, preserving insertion order.
     *
     * @param wordCount the unsorted word count map
     * @return a {@link LinkedHashMap} sorted by frequency in descending order
     */
    private static LinkedHashMap<String, Long> sortResultMap(Map<String, Long> wordCount) {
        return wordCount.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
    }
}
