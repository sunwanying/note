package cn.sunguolei.note.mapper;

import cn.sunguolei.note.domain.Note;
import cn.sunguolei.note.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    User findUserByUsername(String username);
    List<User> index();
    int create(User user);
}
