package cn.sunguolei.note.service;

import cn.sunguolei.note.domain.Note;
import cn.sunguolei.note.domain.NoteWithUser;

import java.util.List;

public interface NoteService {
    List<Note> index(int userId);

    List<NoteWithUser> homeNoteList();

    int create(Note note);

    Note findNoteById(int id);

    int update(Note note);
}
