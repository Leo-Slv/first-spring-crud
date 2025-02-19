package com.Leo.first_spring_crud.repository;

import com.Leo.first_spring_crud.entity.Stock;
import com.Leo.first_spring_crud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {

}
