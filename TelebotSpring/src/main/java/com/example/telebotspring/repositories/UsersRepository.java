package com.example.telebotspring.repositories;

import com.example.telebotspring.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {
    Users findUsersByChatId(String chatId);
}
