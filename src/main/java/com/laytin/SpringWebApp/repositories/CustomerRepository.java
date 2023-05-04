package com.laytin.SpringWebApp.repositories;

import com.laytin.SpringWebApp.models.Customer;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByUsername(String username);

    @Query(value = "SELECT c FROM Customer c WHERE UPPER(c.fullname)  LIKE UPPER(concat('%', ?1,'%')) " +
            "OR UPPER(c.email) LIKE UPPER(concat('%', ?1,'%'))" +
            " OR UPPER(c.username)  LIKE UPPER(concat('%', ?1,'%'))")
    List<Customer> findAllByAll(String search, PageRequest of);
}
