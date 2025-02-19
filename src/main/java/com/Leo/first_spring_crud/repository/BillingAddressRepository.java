package com.Leo.first_spring_crud.repository;

import com.Leo.first_spring_crud.entity.BillingAddress;
import com.Leo.first_spring_crud.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BillingAddressRepository extends JpaRepository<BillingAddress, UUID> {

}
