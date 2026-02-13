package com.employee.EMPLOYEE.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDto {
    private Long id;
    private String empName;
    private String empEmail;
    private String empcode;
    private String companyName;
    private List<AddressDto> addressDtoList;
}

