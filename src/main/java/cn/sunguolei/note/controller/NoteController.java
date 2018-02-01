package cn.sunguolei.note.controller;

import cn.sunguolei.note.domain.Note;
import cn.sunguolei.note.domain.User;
import cn.sunguolei.note.service.NoteService;
import cn.sunguolei.note.service.UserService;
import cn.sunguolei.note.utils.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/note")
public class NoteController {
    private Logger logger = LoggerFactory.getLogger(NoteController.class);

    private NoteService noteService;
    private UserService userService;

    // 将用到的 service 注入进来
    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    /**
     * 笔记的列表页，默认加载登录用户的笔记
     *
     * @param request http 请求
     * @param model   存放前端数据的 model
     * @return 笔记列表页或者登录页面
     */
    @GetMapping("/index")
    public String index(HttpServletRequest request, Model model) {

        // 获取请求 cookie 中的 token
        String token = UserUtil.getTokenFromCookie(request);

        if (null != token) {
            // 获取用户登录信息和用户信息
            Map<String, String> userInfoMap = UserUtil.getUserIdentity(request);
            // 通过用户名查找对应的用户
            User user = userService.findUserByUsername(userInfoMap.get("username"));
            // 通过用户 id 查找对应的用户的笔记
            List<Note> noteList = noteService.index(user.getId());
            // 将笔记列表存放到 model 中，返回给前端页面
            model.addAttribute("noteList", noteList);

            return "note/index";
        }
        // 如果找不到 token，就返回 登录 页面
        return "redirect:/toLoginPage";
    }

    /**
     * 打开写笔记页面
     *
     * @return 返回对应页面
     */
    @GetMapping("/add")
    public String add() {
        return "note/add";
    }

    /**
     * 创建笔记
     *
     * @param note 从表单中获取笔记信息
     * @return 返回笔记列表页
     */
    @PostMapping("/create")
    public String create(HttpServletRequest request, Note note) {

        // 获取请求 cookie 中的 token
        String token = UserUtil.getTokenFromCookie(request);

        if (null != token) {
            // 获取用户登录信息和用户信息
            Map<String, String> userInfoMap = UserUtil.getUserIdentity(request);
            // 通过用户名查找对应的用户
            User user = userService.findUserByUsername(userInfoMap.get("username"));
            note.setUserId(user.getId());
        }
        // 笔记创建时间
        LocalDateTime createTime = LocalDateTime.now();
        note.setCreateTime(createTime);

        // 调用 service 创建笔记
        int number = noteService.create(note);

        // 返回笔记列表页
        return "redirect:/note/index";
    }

}
