package com.address.Address.service.impl;

import com.address.Address.client.EmployeeClient;
import com.address.Address.exception.ResourceNotFoundException;
import com.address.Address.model.dto.AddressDto;
import com.address.Address.model.dto.AddressRequest;
import com.address.Address.model.dto.AddressRequestDto;
import com.address.Address.model.dto.EmployeeDto;
import com.address.Address.model.entity.Address;
import com.address.Address.repository.AddressRepository;
import com.address.Address.service.AddressService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


@Service
public class AddressserviceImpl  implements AddressService {
    Logger log = LoggerFactory.getLogger(AddressserviceImpl.class);
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;
    private  final EmployeeClient employeeClient;
    public AddressserviceImpl(AddressRepository addressRepository, ModelMapper modelMapper, EmployeeClient employeeClient) {
        this.addressRepository = addressRepository;
        this.modelMapper = modelMapper;
        this.employeeClient = employeeClient;
    }

    @Override
    public List<AddressDto> saveAddress(AddressRequest addressRequest) {
        EmployeeDto employeeDto = employeeClient.getsingleEmployees(addressRequest.getEmpId());
//
        List<Address> listToSave = saveOrUpdateAddressRequest(addressRequest);
        List<Address> saveList = addressRepository.saveAll(listToSave);
        return saveList.stream().map(address -> modelMapper.map(address,AddressDto.class)).toList();
    }

    @Override
    @Transactional
    public List<AddressDto> updateAddress(AddressRequest addressRequest) {
        List<Address> addressByEmpid = addressRepository.findAllByEmpId(addressRequest.getEmpId());
       if(addressByEmpid.isEmpty()){
           log.info("No address found for employee id {}", addressRequest.getEmpId());
       }
        Map<Long, Address> existingById = new HashMap<>();
        for (Address existing : addressByEmpid) {
            if (existing.getId() != null) {
                existingById.put(existing.getId(), existing);
            }
        }

        List<Address> listToUpdate = new ArrayList<>();
        Set<Long> incomingIds = new HashSet<>();
        for (AddressRequestDto addressRequestDto : addressRequest.getAddressRequestDtoList()) {
            Address address;
            if (addressRequestDto.getId() != null) {
                address = existingById.get(addressRequestDto.getId());
                if (address == null) {
                    throw new ResourceNotFoundException(
                            "Address id " + addressRequestDto.getId() + " not found for empId " + addressRequest.getEmpId());
                }
                incomingIds.add(addressRequestDto.getId());
            } else {
                address = new Address();
            }
            address.setStreet(addressRequestDto.getStreet());
            address.setCity(addressRequestDto.getCity());
            address.setCountry(addressRequestDto.getCountry());
            address.setPincode(addressRequestDto.getPincode());
            address.setAddresssType(addressRequestDto.getAddresssType());
            address.setEmpId(addressRequest.getEmpId());
            listToUpdate.add(address);
        }

        List<Long> idstoDelete = addressByEmpid.stream()
                .map(Address::getId)
                .filter(Objects::nonNull)
                .filter(id -> !incomingIds.contains(id))
                .toList();
        if(!idstoDelete.isEmpty()){
             addressRepository.deleteAllById(idstoDelete);
        }
        List<Address> updatedAddress = addressRepository.saveAll(listToUpdate);
        return updatedAddress.stream().map(address -> modelMapper.map(address, AddressDto.class)).toList();

    }

    @Override
    public AddressDto getSingleAddress(Long id) {
        Address address = addressRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Address not found with id : " + id));

        return modelMapper.map(address, AddressDto.class);
    }

    @Override
    public List<AddressDto> getAllAddress() {
//        try {
//            Thread.sleep(6000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        List<Address> all = addressRepository.findAll();
        if(all.isEmpty()){
            throw new ResourceNotFoundException("No address found");
        }
        return all.stream().map(address -> modelMapper.map(address, AddressDto.class)).toList();
    }

    @Override
    public List<AddressDto> getAddressByEmpId(Long empId) {
        List<Address> addressByEmpId = addressRepository.findAllByEmpId(empId);
        if(addressByEmpId.isEmpty()){
            throw new ResourceNotFoundException("No address found for employee id: " + empId);
        }
        return addressByEmpId.stream().map(address -> modelMapper.map(address, AddressDto.class)).toList();
    }

    @Override
    public void deleteaddress(Long id) {
        if (!addressRepository.existsById(id)) {
            throw new ResourceNotFoundException("Address not found with id : " + id);
        }
        addressRepository.deleteById(id);
    }
    private List<Address> saveOrUpdateAddressRequest(AddressRequest addressRequest){
        List<Address> listToSave  = new ArrayList<>();
        for(AddressRequestDto addressRequestDto : addressRequest.getAddressRequestDtoList()){
            Address address = new Address();
            address.setId(addressRequestDto.getId() != null ? addressRequestDto.getId() : null);
            address.setStreet(addressRequestDto.getStreet());
            address.setCity(addressRequestDto.getCity());
            address.setCountry(addressRequestDto.getCountry());
            address.setPincode(addressRequestDto.getPincode());
            address.setAddresssType(addressRequestDto.getAddresssType());
            address.setEmpId(addressRequest.getEmpId());
            listToSave.add(address);

        }
        return listToSave;
    }
}
