package com.datatransferobjectmanual.service;

import com.datatransferobjectmanual.entity.Address;

import java.util.List;

/**
 * @Created 02/10/2022 - 15:02
 * @Package com.datatransferobjectmanual.service
 * @Project Data-Transfer-Object-Manual
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
public interface IAddressService {
    public List<Address> findAll();

    public Address findById(Long id);

    List<Address> findByUserId(Long userId);

    public Address save(Address address);

    public Address update(Long id, Address address);

    public void delete(long id);
}
