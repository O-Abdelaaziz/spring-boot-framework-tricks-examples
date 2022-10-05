package com.datatransferobjectmanual.controller;

import com.datatransferobjectmanual.entity.Address;
import com.datatransferobjectmanual.entity.User;
import com.datatransferobjectmanual.mapper.AddressDto;
import com.datatransferobjectmanual.mapper.AddressMapper;
import com.datatransferobjectmanual.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


/**
 * @Created 02/10/2022 - 15:26
 * @Package com.datatransferobjectmanual.controller
 * @Project Data-Transfer-Object-Manual
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@RestController
@RequestMapping("/api/v1/address")
public class AddressController {

    private IAddressService iAddressService;

    @Autowired
    public AddressController(IAddressService iAddressService) {
        this.iAddressService = iAddressService;
    }

    @GetMapping
    private ResponseEntity<List<AddressDto>> getAddressList() {
        return new ResponseEntity<>(AddressMapper.toAddressDtos(iAddressService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    private ResponseEntity<AddressDto> getAddressById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(AddressMapper.toAddressDto(iAddressService.findById(id)), HttpStatus.OK);
    }

    @GetMapping("/user-id/{userId}")
    private ResponseEntity<List<AddressDto>> getAddressByUserId(@PathVariable(name = "userId") Long userId) {
        return new ResponseEntity<>(AddressMapper.toAddressDtos(iAddressService.findByUserId(userId)), HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<AddressDto> saveAddress(@RequestBody @Valid Address address) {
        Address savedAddress = iAddressService.save(address);
        AddressDto addressDto = AddressMapper.toAddressDto(savedAddress);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(addressDto.getId())
                .toUri();
        return ResponseEntity.created(location).body(addressDto);
    }

    @PutMapping("/{id}")
    private ResponseEntity<AddressDto> updateAddress(@PathVariable(name = "id") Long id, @RequestBody @Valid Address address) {
        Address savedAddress = iAddressService.update(id, address);
        AddressDto addressDto = AddressMapper.toAddressDto(savedAddress);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(addressDto.getId())
                .toUri();
        return ResponseEntity.created(location).body(addressDto);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<String> deleteAddress(@PathVariable(name = "id") Long id) {
        iAddressService.delete(id);
        return new ResponseEntity<>("Deleted Successfully", HttpStatus.NOT_FOUND);
    }
}
