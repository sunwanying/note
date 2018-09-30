package cn.sunguolei.note.service;

import cn.sunguolei.note.domain.Note;
import cn.sunguolei.note.domain.NoteWithUser;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface NoteService {
    List<Note> index(int userId);

    PageInfo<NoteWithUser> homeNoteList(int pageNum, int pageSize);

    int create(Note note);

    Note findNoteById(int id);

    int update(Note note);
}
