package org.example.bootrestapi.model.repository;

import org.example.bootrestapi.model.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food,Long> {
}
