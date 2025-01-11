package model.mapper;

import model.UserModel;
import org.apache.ibatis.annotations.*;
import java.util.List;

public interface UserMapper {
    @Select("SELECT * FROM user_manajemen WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "namaLengkap", column = "nama_lengkap"),
            @Result(property = "email", column = "email"),
            @Result(property = "lastLogin", column = "last_login"),
            @Result(property = "createdAt", column = "created_at")
    })
    UserModel getById(Integer id);

    @Select("SELECT * FROM user_manajemen WHERE username = #{username}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "namaLengkap", column = "nama_lengkap"),
            @Result(property = "email", column = "email"),
            @Result(property = "lastLogin", column = "last_login"),
            @Result(property = "createdAt", column = "created_at")
    })
    UserModel findByUsername(String username);

    @Select("SELECT * FROM user_manajemen")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "namaLengkap", column = "nama_lengkap"),
            @Result(property = "email", column = "email"),
            @Result(property = "lastLogin", column = "last_login"),
            @Result(property = "createdAt", column = "created_at")
    })
    List<UserModel> findAll();

    @Insert("INSERT INTO user_manajemen (username, password, nama_lengkap, email) " +
            "VALUES (#{username}, #{password}, #{namaLengkap}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int register(UserModel user);

    @Update("UPDATE user_manajemen SET username = #{username}, password = #{password}, " +
            "nama_lengkap = #{namaLengkap}, email = #{email} WHERE id = #{id}")
    int update(UserModel user);

    @Update("UPDATE user_manajemen SET last_login = CURRENT_TIMESTAMP WHERE id = #{id}")
    int updateLastLogin(Integer id);

    @Delete("DELETE FROM user_manajemen WHERE id = #{id}")
    int delete(Integer id);

    @Select("SELECT * FROM user_manajemen WHERE username = #{username} AND password = #{password}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "namaLengkap", column = "nama_lengkap"),
            @Result(property = "email", column = "email"),
            @Result(property = "lastLogin", column = "last_login"),
            @Result(property = "createdAt", column = "created_at")
    })
    UserModel login(@Param("username") String username, @Param("password") String password);
}
