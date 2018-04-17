package cn.sunguolei.note.controller;

import cn.sunguolei.note.domain.NoteWithUser;
import cn.sunguolei.note.service.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private NoteService noteService;

    public HomeController(NoteService noteService) {
        this.noteService = noteService;
    }

    /**
     * home 主页的笔记列表，只拉取公共笔记
     *
     * @param model 存放前端数据的 model
     * @return 笔记列表页或者登录页面
     */
    @GetMapping("/")
    public String homeNoteList(Model model) {

        List<NoteWithUser> noteWithUserList = noteService.homeNoteList();

        model.addAttribute("noteWithUserList", noteWithUserList);

        return "index";
    }
}
