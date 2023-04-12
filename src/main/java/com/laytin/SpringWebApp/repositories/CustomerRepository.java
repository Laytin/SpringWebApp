package com.laytin.SpringWebApp.repositories;

import com.laytin.SpringWebApp.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByEmailOrUsername(String emailOrUsername1,String emailOrUsername);

    Optional<Customer> findByUsername(String username);
}
