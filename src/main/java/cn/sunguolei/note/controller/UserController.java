package cn.sunguolei.note.controller;

import cn.sunguolei.note.domain.User;
import cn.sunguolei.note.service.EmailService;
import cn.sunguolei.note.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private EmailService emailService;

    public UserController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping("/findUserByUsername")
    public User findUserByUsername(String username) {
        return userService.findUserByUsername(username);
    }

    @GetMapping("/index")
    public String index(Model model) {
        //查找用户列表
        List<User> userList = userService.index();
        // 将用户列表存放到 model 中，返回给前端页面
        model.addAttribute("userList", userList);
        return "user/index";
    }

    @GetMapping("/add")
    public String register() {
        return "user/add";
    }

    @PostMapping("/create")
    public String create(User user, Model model) {
        LocalDateTime createTime = LocalDateTime.now();
        user.setCreateTime(createTime);
        String password = new BCryptPasswordEncoder(11).encode(user.getPassword());
        user.setPassword(password);
        User resultCheckUsername = userService.findUserByUsername(user.getUsername());
        User resultCheckEmail = userService.checkEmail(user.getEmail());
        if (resultCheckUsername != null) {

            model.addAttribute("msg", "注册失败,用户名重复");
            return "user/add";
        } else if (resultCheckEmail != null) {
            model.addAttribute("msg", "注册失败,邮箱重复");
            return "user/add";
        } else {
            userService.create(user);
            try {
                emailService.sendSimpleRegisterMail(user.getEmail(), Locale.CHINA, user);
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            model.addAttribute("msg", "注册成功");
        }
        return "login";
    }
}