package cn.sunguolei.note.service;

import cn.sunguolei.note.domain.User;

public interface UserService {

    User findUserByUsername(String username);

}