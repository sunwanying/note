package cn.sunguolei.note.controller;

import cn.sunguolei.note.config.WebSecurityConfig;
import cn.sunguolei.note.domain.User;
import cn.sunguolei.note.service.UserService;
import cn.sunguolei.note.utils.UserUtil;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    /**
     * 获取用户相关信息
     *
     * @param request http 请求
     * @return 返回用户是否登录及用户相关信息的 map
     */
    @GetMapping("/getUserIdentity")
    @ResponseBody
    public Map<String, String> getUserIdentity(HttpServletRequest request) {
        return UserUtil.getUserIdentity(request);
    }

    @GetMapping("/findUserByUsername")
    public User findUserByUsername(String username) {
        return userService.findUserByUsername(username);
    }

    @GetMapping("/index")
    public String index(HttpServletRequest request, Model model) {
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
    public String create(User user,Model model) {
        LocalDateTime createTime = LocalDateTime.now();
        user.setCreateTime(createTime);
        String password = new BCryptPasswordEncoder(11).encode(user.getPassword());
        user.setPassword(password);
        userService.create(user);
        model.addAttribute("msg", "注册成功");
        return "login";
    }
}
