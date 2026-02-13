package com.employee.EMPLOYEE.controller;

import com.employee.EMPLOYEE.exception.MissingParameterException;
import com.employee.EMPLOYEE.model.dto.EmployeeDto;
import com.employee.EMPLOYEE.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.PathMatcher;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final PathMatcher pathMatcher;

    public EmployeeController(EmployeeService employeeService, PathMatcher pathMatcher) {

        this.pathMatcher = pathMatcher;
        this.employeeService = employeeService;
    }
    @PostMapping("/save")
    public ResponseEntity<EmployeeDto> saveEmploye(@RequestBody EmployeeDto employeeDto){
        EmployeeDto response = employeeService.saveEmployee( employeeDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody EmployeeDto employeeDto,
                                                      @PathVariable Long id){
        EmployeeDto response = employeeService.updateEmployee(id,employeeDto);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id){
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>("Employee deleted successfully",HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<EmployeeDto>> getAllEmployees(){
        Iterable<EmployeeDto> response = employeeService.getAllEmployee();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<EmployeeDto> getsingleEmployees(@PathVariable Long id){
        EmployeeDto response = employeeService.getSingleemplopee(id);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/get-by-empcode")
    public ResponseEntity<EmployeeDto> getEmployeeByEmpcodeAndCompanyName(@RequestParam(required = false) String empcode,
                                                                          @RequestParam(required = false)String companyName){

        List<String> missingparameter = new ArrayList<>();
        if(empcode == null|| empcode.trim().isEmpty()){
            missingparameter.add("empCode");
        }

        if(companyName == null|| companyName.trim().isEmpty()){
            missingparameter.add("companyName");
        }
        if(!missingparameter.isEmpty()){
            String finalmessage = missingparameter.stream().collect(Collectors.joining(","));
            throw new MissingParameterException(" Please provide "+finalmessage);
        }
        EmployeeDto response = employeeService.getEmployeeByEmpcodeAndCompanyName(empcode,companyName);
        return new ResponseEntity<>(response,HttpStatus.OK);

    }


}
