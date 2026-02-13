package com.address.Address.client;

import com.address.Address.model.dto.EmployeeDto;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "EMPLOYEE")
public interface EmployeeClient {
    @GetMapping("/employees/{id}")
    EmployeeDto getsingleEmployees(@PathVariable Long id);
}
