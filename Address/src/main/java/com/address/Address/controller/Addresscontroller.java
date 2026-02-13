package com.address.Address.controller;

import com.address.Address.client.EmployeeClient;
import com.address.Address.model.dto.AddressDto;
import com.address.Address.model.dto.AddressRequest;
import com.address.Address.model.dto.AddressRequestDto;
import com.address.Address.repository.AddressRepository;
import com.address.Address.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
public class Addresscontroller {
    private final AddressService addressService;
    private final AddressRepository addressRepository;
    private final EmployeeClient employeeClient;

    public Addresscontroller(AddressService addressService, AddressRepository addressRepository, EmployeeClient employeeClient) {
        this.addressService = addressService;
        this.addressRepository = addressRepository;
        this.employeeClient = employeeClient;
    }

    @PostMapping("/save")
    public ResponseEntity<List<AddressDto>> saveAddress(@RequestBody AddressRequest addressDto){
        List<AddressDto> response = addressService.saveAddress(addressDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PutMapping("/updateaddress")
    public ResponseEntity<List<AddressDto>> updateAddress(@RequestBody AddressRequest addressRequest){
        List<AddressDto> addressRequests = addressService.updateAddress(addressRequest);
        return new ResponseEntity<>(addressRequests, HttpStatus.OK);
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<AddressDto> getAddressById(@PathVariable Long addressId){
        AddressDto addressDto = addressService.getSingleAddress(addressId);
        return new ResponseEntity<>(addressDto,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long addressId){
        addressService.deleteaddress(addressId);
        return new ResponseEntity<>("Address deleted successfully with id "+addressId,HttpStatus.OK);
    }

    @GetMapping("/empId/{empId}")
    public ResponseEntity<List<AddressDto>> getAddressByEmpId(@PathVariable Long empId) {
        List<AddressDto> response = addressService.getAddressByEmpId(empId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
