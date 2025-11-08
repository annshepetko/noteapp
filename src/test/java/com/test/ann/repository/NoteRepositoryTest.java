package com.test.ann.repository;

import com.test.ann.documents.Note;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@Testcontainers
class NoteRepositoryTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.11");

    @BeforeAll
    static void setUp() {
        System.setProperty("spring.data.mongodb.uri", mongoDBContainer.getReplicaSetUrl());
    }

    @Autowired
    private NoteRepository noteRepository;

    @Test
    void shouldSaveAndRetrieveNote() {
        // given
        Note note = new Note();
        note.setId("1");
        note.setTitle("Test Note");
        note.setContent("Hello Testcontainers");
        note.setCreatedAt(LocalDateTime.now());

        noteRepository.save(note);

        // when
        Page<Note> page = noteRepository.findAll(PageRequest.of(0, 10));

        // then
        assertThat(page.getTotalElements()).isEqualTo(1);
        Note retrieved = page.getContent().get(0);
        assertThat(retrieved.getId()).isEqualTo("1");
        assertThat(retrieved.getTitle()).isEqualTo("Test Note");
        assertThat(retrieved.getContent()).isEqualTo("Hello Testcontainers");
    }
}
