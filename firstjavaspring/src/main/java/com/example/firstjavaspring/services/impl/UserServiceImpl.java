package com.example.firstjavaspring.services.impl;

import com.example.firstjavaspring.entities.Roles;
import com.example.firstjavaspring.entities.Users;
import com.example.firstjavaspring.repositories.RolesRepository;
import com.example.firstjavaspring.repositories.UsersRepository;
import com.example.firstjavaspring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class
UserServiceImpl implements UserService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RolesRepository rolesRepository;

//    @Autowired
//    private  BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users myUser = usersRepository.findByEmail(username);
        if (myUser != null) {
            User secUser = new User(myUser.getEmail(), myUser.getPassword(), myUser.getRoles());
            return secUser;
        }
        throw new UsernameNotFoundException("User not found");
    }

    @Override
    public Users getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    @Override
    public Users createUser(Users user) {
        Users checkUser = usersRepository.findByEmail(user.getEmail());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if ( checkUser == null){
            Roles role = rolesRepository.findByRole("ROLE_USER");
            if (role!=null){
                ArrayList<Roles> roles = new ArrayList<>();
                roles.add(role);
                user.setRoles(roles);
                user.setPassword(encoder.encode(user.getPassword()));
                return usersRepository.save(user);
            }
        }
        return null;
    }
}

