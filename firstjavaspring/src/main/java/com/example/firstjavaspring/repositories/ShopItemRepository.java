package com.example.firstjavaspring.repositories;

import com.example.firstjavaspring.entities.ShopItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ShopItemRepository extends JpaRepository<ShopItems,Long> {
    List<ShopItems> findAllByAmountGreaterThan(int amount);
}
