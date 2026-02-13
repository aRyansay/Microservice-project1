package com.employee.EMPLOYEE.client;

import com.employee.EMPLOYEE.model.dto.AddressDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ADDRESS", url = "${address.service.url:http://localhost:8082}")
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface AddressClient {

    @GetMapping("/addresses/empId/{empId}")
    List<AddressDto> getAddressByEmpId(@PathVariable Long empId);


}
