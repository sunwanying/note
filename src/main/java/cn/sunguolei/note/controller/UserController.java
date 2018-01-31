package cn.sunguolei.note.controller;

import cn.sunguolei.note.domain.User;
import cn.sunguolei.note.service.UserService;
import cn.sunguolei.note.utils.UserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
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
}
