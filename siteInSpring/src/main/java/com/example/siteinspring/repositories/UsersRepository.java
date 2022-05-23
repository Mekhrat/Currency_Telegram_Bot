package com.example.siteinspring.repositories;

import com.example.siteinspring.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UsersRepository extends JpaRepository<Users,Long> {
    Users findUsersByEmail(String email);
}
