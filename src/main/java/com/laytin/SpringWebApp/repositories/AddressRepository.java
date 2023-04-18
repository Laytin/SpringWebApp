package com.laytin.SpringWebApp.repositories;

import com.laytin.SpringWebApp.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address,Integer> {
    List<Address> findByCustomerId(int customerId);
}
