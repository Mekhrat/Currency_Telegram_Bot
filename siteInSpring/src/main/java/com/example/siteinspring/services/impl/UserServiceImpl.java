package com.example.siteinspring.services.impl;

import com.example.siteinspring.entities.Roles;
import com.example.siteinspring.entities.Users;
import com.example.siteinspring.repositories.RolesRepository;
import com.example.siteinspring.repositories.UsersRepository;
import com.example.siteinspring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Override
    public Users getUserByEmail(String email) {
        return usersRepository.findUsersByEmail(email);
    }

    @Override
    public Users createUser(Users user) {
        Users checkUser = usersRepository.findUsersByEmail(user.getEmail());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (checkUser == null){
            Roles role = rolesRepository.findByRole("ROLE_ADMIN");
            if (role != null){
                List<Roles> roles = new ArrayList<>();
                roles.add(role);
                user.setRoles(roles);
                user.setPassword(encoder.encode(user.getPassword()));
                return usersRepository.save(user);
            }
        }
        return null;
    }

    @Override
    public void deleteUser(Users user) {
        usersRepository.delete(user);
    }

    @Override
    public List<Users> getUsers() {
        return usersRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepository.findUsersByEmail(username);
        if (user != null){
            User secUser = new User(user.getEmail(), user.getPassword(), user.getRoles());
            return  secUser;
        }
        throw new UsernameNotFoundException("User not found");
    }
}
