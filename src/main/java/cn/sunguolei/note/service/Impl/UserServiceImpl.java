package cn.sunguolei.note.service.Impl;

import cn.sunguolei.note.domain.User;
import cn.sunguolei.note.mapper.UserMapper;
import cn.sunguolei.note.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<User> index() {
        return userMapper.index();
    }

    @Override
    public int create(User user) {
        return userMapper.create(user);
    }
}
