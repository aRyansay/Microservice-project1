package com.address.Address.service;

import com.address.Address.model.dto.AddressDto;
import com.address.Address.model.dto.AddressRequest;
import java.util.List;


public interface AddressService {
    List<AddressDto>  saveAddress(AddressRequest addressRequest);
    List<AddressDto> updateAddress(AddressRequest addressRequest);

    AddressDto getSingleAddress(Long id);

    List<AddressDto> getAllAddress();

    List<AddressDto> getAddressByEmpId(Long empId);

    void deleteaddress(Long id);

}
