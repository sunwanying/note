package cn.sunguolei.note.service;

import cn.sunguolei.note.domain.User;

import java.util.List;

public interface UserService {
    User findUserByUsername(String username);

    List<User> index();

    String duplicateUsername(String username);

    String duplicateEmail(String email);

    int create(User user);
}