package com.example.siteinspring.repositories;

import com.example.siteinspring.entities.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ItemsRepository extends JpaRepository<Items, Long> {
    List<Items> findItemsByInTopPageIsTrue();
}
