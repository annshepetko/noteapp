package com.test.ann.dto;

import com.test.ann.documents.Tag;


import com.test.ann.documents.Tag;

/**
 * Filter criteria for querying notes.
 * <p>
 * Currently supports filtering notes by a specific {@link Tag}.
 *
 * @param tag the tag to filter notes by, can be {@code null} for no tag filtering
 */
public record FilterCredentials(
        Tag tag
) {
}
