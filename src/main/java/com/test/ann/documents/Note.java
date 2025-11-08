package com.test.ann.documents;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "notes")
public class Note {

    @MongoId
    private String id;

    @NotNull
    private String title;

    @NotNull
    private String content;
    private LocalDateTime createdAt;

    private List<Tag> tags;

    public Note() {
    }

    public Note(

            String title,
            String content,
            LocalDateTime createdAt,
            List<Tag> tags
    ) {

        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.tags = tags;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

}
