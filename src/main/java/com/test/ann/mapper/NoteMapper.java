package com.test.ann.mapper;

import com.test.ann.documents.Note;
import com.test.ann.dto.NoteDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class NoteMapper {

    public Note toNote(NoteDto.CreateNoteBody noteDto){
        Note note = new Note(
                noteDto.title(),
                noteDto.content(),
                noteDto.createdAt(),
                noteDto.tags()
        );

        if (note.getCreatedAt() == null) {
            note.setCreatedAt(LocalDateTime.now());
        }

        return note;
    }


    public NoteDto toNoteDto(Note note){
        return new NoteDto(note.getId(), note.getTitle(), note.getContent(), note.getCreatedAt(), note.getTags());
    }

}
