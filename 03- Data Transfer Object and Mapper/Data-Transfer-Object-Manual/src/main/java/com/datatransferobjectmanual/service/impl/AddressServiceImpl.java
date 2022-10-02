package com.datatransferobjectmanual.service.impl;

import com.datatransferobjectmanual.entity.Address;
import com.datatransferobjectmanual.entity.User;
import com.datatransferobjectmanual.repository.AddressRepository;
import com.datatransferobjectmanual.repository.UserRepository;
import com.datatransferobjectmanual.service.IAddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @Created 02/10/2022 - 15:04
 * @Package com.datatransferobjectmanual.service.impl
 * @Project Data-Transfer-Object-Manual
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@Transactional
@Slf4j
@Service
public class AddressServiceImpl implements IAddressService {

    private AddressRepository addressRepository;
    private UserRepository userRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    @Override
    public Address findById(Long id) {
        if (Objects.isNull(id)) {
            throw new RuntimeException("address id can't be null");
        }
        Optional<Address> address = addressRepository.findById(id);
        if (!address.isPresent()) {
            throw new RuntimeException("address can not be found!");
        }
        return address.get();
    }

    @Override
    public Address save(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address update(Long id, Address address) {
        Address getAddress = addressRepository.findById(id).get();


        User getUser = userRepository.findById(address.getUser().getId()).get();
        if (Objects.isNull(getUser)) {
            throw new RuntimeException("user can not be found!");
        }

        getAddress.setCountry(address.getCountry());
        getAddress.setCity(address.getCity());
        getAddress.setAddress1(address.getAddress1());
        getAddress.setAddress2(address.getAddress2());
        getAddress.setAddress3(address.getAddress3());
        getAddress.setUser(getUser);

        Address updatedAddress = addressRepository.save(getAddress);
        return updatedAddress;
    }

    @Override
    public void delete(long id) {
        Address getAddress = addressRepository.findById(id).get();
        if (Objects.isNull(getAddress)) {
            throw new RuntimeException("address can not be found!");
        }
        addressRepository.delete(getAddress);
    }
}
