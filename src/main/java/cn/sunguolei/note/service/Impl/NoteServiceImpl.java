package cn.sunguolei.note.service.Impl;

import cn.sunguolei.note.domain.Note;
import cn.sunguolei.note.mapper.NoteMapper;
import cn.sunguolei.note.service.NoteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    private NoteMapper noteMapper;

    public NoteServiceImpl(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    @Override
    public List<Note> index(int userId) {
        return noteMapper.index(userId);
    }

    @Override
    public int create(Note note) {
        return noteMapper.create(note);
    }
}
