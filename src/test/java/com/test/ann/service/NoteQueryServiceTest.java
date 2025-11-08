package com.test.ann.service;


import com.test.ann.documents.Note;
import com.test.ann.dto.FilterCredentials;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NoteQueryServiceTest {

    private final MongoTemplate mongoTemplate = mock(MongoTemplate.class);
    private final NoteQueryService noteQueryService = new NoteQueryService(mongoTemplate);

    @Test
    void shouldReturnPageOfNotes() {
        // given
        Note note = new Note();
        note.setId("1");
        note.setTitle("Test Note");

        when(mongoTemplate.count(any(Query.class), any(Class.class))).thenReturn(1L);
        when(mongoTemplate.find(any(Query.class), any(Class.class))).thenReturn(List.of(note));

        // when
        Page<Note> result = noteQueryService.findNotes(new FilterCredentials(null), PageRequest.of(0, 10));

        // then
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getId()).isEqualTo("1");
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("Test Note");
    }
}
