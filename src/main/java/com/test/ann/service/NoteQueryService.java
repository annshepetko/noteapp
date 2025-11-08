package com.test.ann.service;

import com.test.ann.documents.Note;
import com.test.ann.dto.FilterCredentials;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for querying notes from MongoDB with optional filtering and pagination.
 */
@Service
public class NoteQueryService {

    private final MongoTemplate mongoTemplate;

    /**
     * Constructs a NoteQueryService with the provided MongoTemplate.
     *
     * @param mongoTemplate the MongoTemplate used to query the database
     */
    public NoteQueryService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * Finds notes based on the given filter and pageable parameters.
     *
     * <p>If a {@link FilterCredentials} object is provided with a tag, only notes containing
     * that tag will be returned. The results are paginated according to the provided {@link Pageable}.</p>
     *
     * @param filter   optional filter criteria, e.g., a tag
     * @param pageable pagination and sorting information
     * @return a {@link Page} of {@link Note} matching the filter and pagination
     */
    public Page<Note> findNotes(FilterCredentials filter, Pageable pageable) {
        Query query = new Query().with(pageable);

        if (filter != null && filter.tag() != null) {
            query.addCriteria(Criteria.where("tags").is(filter.tag()));
        }

        long count = mongoTemplate.count(query, Note.class);
        var notes = mongoTemplate.find(query, Note.class);

        return new PageImpl<>(notes, pageable, count);
    }

}
