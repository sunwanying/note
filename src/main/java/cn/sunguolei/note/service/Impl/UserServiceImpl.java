package cn.sunguolei.note.service.Impl;

import cn.sunguolei.note.domain.User;
import cn.sunguolei.note.mapper.UserMapper;
import cn.sunguolei.note.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User findUserByUsername(String username) {
        return userMapper.findUserByUsername(username);
    }
}
