package com.example.telebotspring.services;

import com.example.telebotspring.entities.Users;

import java.util.List;

public interface UsersService {
    List<Users> getUsers();
    Users addUser(Users user);
    Users getUser(String chatId);
}
