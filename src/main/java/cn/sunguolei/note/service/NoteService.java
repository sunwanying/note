package cn.sunguolei.note.service;

import cn.sunguolei.note.domain.Note;

import java.util.List;

public interface NoteService {
    List<Note> index(int userId);

    int create(Note note);

    Note findNoteById(int id);

    int update(Note note);
}
