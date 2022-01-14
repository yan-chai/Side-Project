package com.example.sideproject.service;

import com.example.sideproject.mapper.UserMapper;
import com.example.sideproject.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserMapper mapper;

    @Override
    public User queryUserByEmail(String email) {
        return mapper.queryUserByEmail(email);
    }

    @Override
    public void addUser(String email, String username, String pwd) {
        mapper.addUser(email, username, pwd);
    }

    @Override
    public void updateName(int id, String name) {
        mapper.updateName(id, name);
    }

    @Override
    public void updatePwd(int id, String pwd) {
        mapper.updatePwd(id, pwd);
    }

}
