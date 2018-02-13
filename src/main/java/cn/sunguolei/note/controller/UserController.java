package cn.sunguolei.note.controller;

import cn.sunguolei.note.domain.BaseModel;
import cn.sunguolei.note.domain.User;
import cn.sunguolei.note.service.EmailService;
import cn.sunguolei.note.service.UserService;
import cn.sunguolei.note.utils.DesUtil;
import org.apache.tomcat.jni.Local;
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
    public String index(Model model) throws Exception {
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
            model.addAttribute("msg", "注册成功");
//          emailService.sendSimpleRegisterMail(user.getEmail(), Locale.CHINA, user);
        }
        return "login";
    }

    /**
     * 根据注册后加密参数，检测用户是否通过校验
     * @param sign 加密参数
     * @return 返回校验结果
     */
    public BaseModel checkRegisterCode(String sign){
        BaseModel baseModel = new BaseModel();

        if(sign == null || sign.length() < 1){
            baseModel.setReturnCode(101);
            baseModel.setMessage("参数sign非法");
        }else {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(11);

            //String decode = bCryptPasswordEncoder.

            String[] userArray = sign.split("_");


            if(userArray != null && userArray.length == 2){
                //解析出userid和code
                int userId = Integer.parseInt(userArray[0]);
                String code = userArray[1];

                //根据userid和code，判断是否合法注册用户
                User userParam = new User();

                userParam.setId(userId);
                userParam.setActivateStatus(0);
                userParam.setActivateCode(code);
                int count = userService.getUserCountByIdActivateStatus(userParam);

                if(count > 1){
                    //如果是合法用户，修改校验标识
                    userParam.setActivateStatus(1);
                    userService.SetUserActivateStatus(userParam);
                }else {
                    //如果是合法用户，修改校验标识
                    baseModel.setReturnCode(102);
                    baseModel.setMessage("用户不存在或者已注册成功");
                }
            }else {
                baseModel.setReturnCode(103);
                baseModel.setMessage("加密参数格式错误");
            }
        }

        return  new BaseModel();
    }
}