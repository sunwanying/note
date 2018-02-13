package cn.sunguolei.note.mapper;

import cn.sunguolei.note.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    User findUserByUsername(String username);

    List<User> index();

    User checkEmail(String email);

    int create(User user);

    /**
     * @param user
     * @return
     */
    int getUserCountByNameActivateStatus(User user);

    /**
     * @param user
     * @return
     */
    int SetUserActivateStatus(User user);
}
