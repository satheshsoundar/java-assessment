package com.java.assessment.customer.service;

import com.java.assessment.customer.beans.Address;
import com.java.assessment.customer.dto.AddressDto;
import com.java.assessment.customer.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressService {

    @Autowired
    private final AddressRepository addressRepository;


    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<Address> getAddress() {
        return addressRepository.findAll();
    }

    public AddressDto getAddressById(Long id) {
        Optional<Address> address = addressRepository.findById(id);
        if(address.isPresent()) {
            return mapAddressToAddressDto(address.get());
        }
        return null;
    }

    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    private AddressDto mapAddressToAddressDto(Address address) {
        AddressDto addressDto = new AddressDto();
        addressDto.setCountry(address.getCountry());
        addressDto.setName(address.getName());
        addressDto.setPostalCode(address.getPostalCode());
        addressDto.setStreetName(address.getStreetName());
        addressDto.setStreetNumber(address.getStreetNumber());
        return addressDto;
    }

    private List<AddressDto> mapAddressListToAddressDtoList(List<Address> addressList){
        return addressList.stream()
                .map(address -> mapAddressToAddressDto( address)).collect(Collectors.toList());
    }
}
