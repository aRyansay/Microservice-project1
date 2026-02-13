package com.address.Address.repository;

import com.address.Address.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface AddressRepository extends JpaRepository<Address,Long> {

    List<Address> findAllByEmpId(Long empid);
}
