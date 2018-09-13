package cn.sunguolei.note.service.Impl;

import cn.sunguolei.note.domain.Note;
import cn.sunguolei.note.domain.NoteWithUser;
import cn.sunguolei.note.mapper.NoteMapper;
import cn.sunguolei.note.service.NoteService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
    public PageInfo<NoteWithUser> homeNoteList(int pageNum, int pageSize) {
        PageInfo<NoteWithUser> pageInfo = PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(() -> noteMapper.homeNoteList());
        return pageInfo;
    }

    @Override
    public int create(Note note) {
        return noteMapper.create(note);
    }

    @Override
    public Note findNoteById(int id) {
        return noteMapper.findNoteById(id);
    }

    @Override
    public int update(Note note) {
        return noteMapper.update(note);
    }
}
