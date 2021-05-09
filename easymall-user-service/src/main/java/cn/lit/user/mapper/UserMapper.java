package cn.lit.user.mapper;

import com.jt.common.pojo.User;

public interface UserMapper {
    int selectUserCountByUserName(String userName);

    void insertUser(User user);

    User selectUserByUserNameAndPassword(User user);
}
