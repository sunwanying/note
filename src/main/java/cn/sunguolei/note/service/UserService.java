package cn.sunguolei.note.service;

import cn.sunguolei.note.domain.User;
import sun.nio.cs.US_ASCII;

import java.util.List;

public interface UserService {
    User findUserByUsername(String username);

    List<User> index();

    User checkEmail(String email);

    int create(User user);
}