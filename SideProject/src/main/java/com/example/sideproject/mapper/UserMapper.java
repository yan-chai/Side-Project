package com.example.sideproject.mapper;

import com.example.sideproject.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    public User queryUserByEmail(String email);

    public void addUser(String email, String username, String pwd);

    public void updateDays(int id);

    public void updateName(int id, String name);

    public void updatePwd(int id, String pwd);

}
