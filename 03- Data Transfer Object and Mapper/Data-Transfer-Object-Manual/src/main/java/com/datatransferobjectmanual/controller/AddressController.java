package com.datatransferobjectmanual.controller;

import com.datatransferobjectmanual.entity.Address;
import com.datatransferobjectmanual.entity.User;
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
    private ResponseEntity<List<Address>> getAddressList() {
        return new ResponseEntity<>(iAddressService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Address> getAddressById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(iAddressService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<Address> saveAddress(@RequestBody @Valid Address address) {
        Address savedAddress = iAddressService.save(address);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedAddress.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedAddress);
    }

    @PutMapping("/{id}")
    private ResponseEntity<Address> updateAddress(@PathVariable(name = "id") Long id, @RequestBody @Valid Address address) {
        Address savedAddress = iAddressService.update(id,
                address);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedAddress.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedAddress);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<String> deleteAddress(@PathVariable(name = "id") Long id) {
        iAddressService.delete(id);
        return new ResponseEntity<>("Deleted Successfully", HttpStatus.NOT_FOUND);
    }
}
