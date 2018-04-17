package cn.sunguolei.note.mapper;

import cn.sunguolei.note.domain.Note;
import cn.sunguolei.note.domain.NoteWithUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoteMapper {
    List<Note> index(int userId);

    List<NoteWithUser> homeNoteList();

    int create(Note note);

    Note findNoteById(int id);

    int update(Note note);
}
