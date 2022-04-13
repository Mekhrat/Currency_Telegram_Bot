package com.example.telebotspring.services.impl;

import com.example.telebotspring.entities.Users;
import com.example.telebotspring.repositories.UsersRepository;
import com.example.telebotspring.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public List<Users> getUsers() {
        return usersRepository.findAll();
    }

    @Override
    public Users addUser(Users user) {
        return usersRepository.save(user);
    }

    @Override
    public Users getUser(String chatId) {
        return usersRepository.findUsersByChatId(chatId);
    }
}
