package com.example.firstjavaspring.repositories;

import com.example.firstjavaspring.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UsersRepository extends JpaRepository<Users,Long> {
    Users findByEmail(String email);
}
