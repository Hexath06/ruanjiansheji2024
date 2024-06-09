package DAO;

import Entity.User;
import org.apache.ibatis.annotations.*;

public interface UserMapper {
    @Select("SELECT * FROM user WHERE username = #{username}")
    public User getUserByUsername(@Param("username") String username);
    @Select("SELECT * FROM user WHERE id = #{userid}")
    public User getUserById(@Param("userid") int userid);
    @Insert("INSERT INTO user (username, password, email) VALUES(#{username}, #{password}, #{email})")
    public void insertUser(User user);
}