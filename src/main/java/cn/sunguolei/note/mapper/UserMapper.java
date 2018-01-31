package cn.sunguolei.note.mapper;

import cn.sunguolei.note.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User findUserByUsername(String username);

}
