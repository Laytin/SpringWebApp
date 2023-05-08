package com.laytin.SpringWebApp.repositories;

import com.laytin.SpringWebApp.models.OrdAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdAddressRepository  extends JpaRepository<OrdAddress, Integer> {
}
