package cn.sunguolei.note.controller;

import cn.sunguolei.note.domain.BaseModel;
import cn.sunguolei.note.domain.User;
import cn.sunguolei.note.service.UserService;
import cn.sunguolei.note.utils.DesUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by guohuawei on 2018/2/13.
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    private static String KEY_;
    private UserService userService;

    public ApiController(UserService userService) {
        this.userService = userService;
    }

    @Value("${yingnote.key}")
    void setKey(String key) {
        KEY_ = key;
    }

    /**
     * 根据注册后加密参数，检测用户是否通过校验
     *
     * @param sign 加密参数
     * @return 返回校验结果
     */
    @RequestMapping("/activeUser")
    public BaseModel checkRegisterCode(String sign) throws Exception {
        BaseModel baseModel = new BaseModel();

        if (sign == null || sign.length() < 1) {
            baseModel.setReturnCode(101);
            baseModel.setMessage("参数sign非法");
        } else {
            String decode = DesUtil.decrypt(sign, KEY_);

            String[] userArray = decode.split("_");

            if (userArray.length == 2) {
                //解析出userid和code
                String username = userArray[0];
                String code = userArray[1];

                //根据userid和code，判断是否合法注册用户
                User userParam = new User();

                userParam.setUsername(username);
                userParam.setActivateStatus(0);
                userParam.setActivateCode(code);
                int count = userService.getUserCountByNameActivateStatus(userParam);

                if (count > 0) {
                    //如果是合法用户，修改校验标识
                    userParam.setActivateStatus(1);
                    userService.SetUserActivateStatus(userParam);
                } else {
                    //如果是合法用户，修改校验标识
                    baseModel.setReturnCode(102);
                    baseModel.setMessage("用户不存在或者已注册成功");
                }
            } else {
                baseModel.setReturnCode(103);
                baseModel.setMessage("加密参数格式错误");
            }
        }

        return baseModel;
    }
}
