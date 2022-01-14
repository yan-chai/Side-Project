package com.example.sideproject.service;

import com.example.sideproject.pojo.User;

public interface UserService {

    public User queryUserByEmail(String email);

    public void addUser(String email, String name, String pwd);

    public void updateName(int id, String name);

    public void updatePwd(int id, String pwd);
}
