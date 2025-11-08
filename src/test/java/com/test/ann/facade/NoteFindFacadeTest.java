package com.test.ann.facade;

import com.test.ann.dto.FilterCredentials;
import com.test.ann.dto.NotePreviewDto;
import com.test.ann.service.NoteQueryService;
import com.test.ann.documents.Note;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NoteFindFacadeTest {

    @Mock
    private NoteQueryService noteQueryService;

    @InjectMocks
    private NoteFindFacade noteFindFacade;

    @Test
    void shouldReturnMappedNotePreviews() {
        // given
        Note note = new Note();
        note.setId("1");
        note.setTitle("Test Note");
        note.setCreatedAt(LocalDateTime.now());

        Page<Note> notePage = new PageImpl<>(List.of(note));

        when(noteQueryService.findNotes(any(FilterCredentials.class), any(PageRequest.class)))
                .thenReturn(notePage);

        // when
        Page<NotePreviewDto> result = noteFindFacade.findNotes(PageRequest.of(0, 10), new FilterCredentials(null));

        // then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).id()).isEqualTo("1");
        assertThat(result.getContent().get(0).title()).isEqualTo("Test Note");
    }
}
