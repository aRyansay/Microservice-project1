package com.employee.EMPLOYEE.service.impl;

import com.employee.EMPLOYEE.client.AddressClient;
import com.employee.EMPLOYEE.exception.BadRequestException;
import com.employee.EMPLOYEE.exception.ResourceNotFoundException;
import com.employee.EMPLOYEE.model.dto.AddressDto;
import com.employee.EMPLOYEE.model.dto.EmployeeDto;
import com.employee.EMPLOYEE.model.entity.Employee;
import com.employee.EMPLOYEE.repository.EmployeeRepository;
import com.employee.EMPLOYEE.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
public class EmployeeserviceImpl implements EmployeeService {
    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private  final AddressClient addressClient;
    public EmployeeserviceImpl(EmployeeRepository employeeRepository,
                               ModelMapper modelMapper, AddressClient addressClient) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
        this.addressClient = addressClient;
    }


    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        if(employeeDto.getId()!=null){
            throw new RuntimeException("Employee already exist");
        }
       Employee entity = modelMapper.map(employeeDto, Employee.class);
        Employee saveemployee = employeeRepository.save(entity);

        return modelMapper.map(saveemployee,EmployeeDto.class);
    }

    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        if(id==null||employeeDto.getId()==null){
            throw new BadRequestException("please provide Emp id");
        }
        if(!Objects.equals(id,employeeDto.getId())){
            throw new BadRequestException("Id mismatch");
        }
        employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee not found with id " + id));
        Employee entity = modelMapper.map(employeeDto,Employee.class);
        Employee saveentity = employeeRepository.save(entity);
        return modelMapper.map(saveentity,EmployeeDto.class);

    }

    @Override
    public EmployeeDto getSingleemplopee(Long id) {
        Employee emp = employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee not found with id "+id));
        List<AddressDto> addressDtos = new ArrayList<>();
        EmployeeDto employeeDto= modelMapper.map(emp , EmployeeDto.class);
        try{
            log.info("Calling AddressClient for empId: {}", emp.getId());
            addressDtos = addressClient.getAddressByEmpId(emp.getId());
            employeeDto.setAddressDtoList(addressDtos);
            log.info("Successfully retrieved {} addresses for empId: {}", addressDtos.size(), emp.getId());
        }catch(Exception e){
            log.error("Failed to fetch addresses for empid: {} - Error: {}", emp.getId(), e.getMessage(), e);
        }

        return employeeDto;
    }

    @Override
    public void deleteEmployee(Long id) {
       Employee emp = employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee not found"+id));
        employeeRepository.delete(emp);

    }

    @Override
    public List<EmployeeDto> getAllEmployee() {
        List<Employee> emps = employeeRepository.findAll();
        List<EmployeeDto> response = new ArrayList<>();
        List<EmployeeDto> employeeDtosList = emps.stream().map(emp-> modelMapper.map(emp,EmployeeDto.class)).toList();
        for(EmployeeDto employeeDto : employeeDtosList){
            List<AddressDto> addressDtos = new ArrayList<>();
            try{
                log.info("Calling AddressClient for empId: {}", employeeDto.getId());
                addressDtos = addressClient.getAddressByEmpId(employeeDto.getId());
                employeeDto.setAddressDtoList(addressDtos);
                log.info("Successfully retrieved {} addresses for empId: {}", addressDtos.size(), employeeDto.getId());
            }catch(Exception e){
                log.error("Failed to fetch addresses for empid: {} - Error: {}", employeeDto.getId(), e.getMessage(), e);
            }
             response.add(employeeDto);

        }
        return response;
    }

    @Override
    public EmployeeDto getEmployeeByEmpcodeAndCompanyName(String empcode, String companyName) {
       Employee entity = employeeRepository.findByEmpcodeAndCompanyName(empcode,companyName).orElseThrow(()-> new ResourceNotFoundException("Employee not found with "+ empcode +" and "+companyName));
       return modelMapper.map(entity,EmployeeDto.class);
    }

}
