package com.employee.EMPLOYEE.service;

import com.employee.EMPLOYEE.model.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    EmployeeDto saveEmployee(EmployeeDto employeeDto);
    EmployeeDto updateEmployee(Long id,EmployeeDto employeeDto);
    EmployeeDto getSingleemplopee(Long id);
    void deleteEmployee(Long id);
    List<EmployeeDto> getAllEmployee();

    EmployeeDto getEmployeeByEmpcodeAndCompanyName(String empcode, String companyName);
}
