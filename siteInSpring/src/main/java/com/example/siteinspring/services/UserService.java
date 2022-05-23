package com.example.siteinspring.services;

import com.example.siteinspring.entities.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    Users getUserByEmail(String email);
    Users createUser(Users user);
    void deleteUser(Users user);
    List<Users> getUsers();
}
