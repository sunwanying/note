package cn.sunguolei.note.service;

import cn.sunguolei.note.domain.User;

import java.util.List;

public interface UserService {
    User findUserByUsername(String username);

    List<User> index();

    int create(User user);
}