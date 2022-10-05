package com.datatransferobjectmanual.mapper;

import com.datatransferobjectmanual.entity.Address;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Created 03/10/2022 - 08:25
 * @Package com.datatransferobjectmanual.mapper
 * @Project Data-Transfer-Object-Manual
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
public class AddressMapper {

    public static Address toAddress(AddressDto addressDto) {

        if (Objects.isNull(addressDto)) {
            return null;
        }

        return Address.builder()
                .id(addressDto.getId())
                .country(addressDto.getCountry())
                .city(addressDto.getCity())
                .address1(addressDto.getAddress1())
                .address2(addressDto.getAddress2())
                .address3(addressDto.getAddress3())
                .user(UserMapper.toUser(addressDto.getUserDto()))
                .userId(addressDto.getUserId())
                .build();
    }

    public static AddressDto toAddressDto(Address address) {
        if (Objects.isNull(address)) {
            return null;
        }

        return AddressDto.builder()
                .id(address.getId())
                .country(address.getCountry())
                .city(address.getCity())
                .address1(address.getAddress1())
                .address2(address.getAddress2())
                .address3(address.getAddress3())
                .userId(address.getUser().getId())
                .userDto(UserMapper.toUserDto(address.getUser()))
                .build();
    }

    public static List<Address> toAddresses(List<AddressDto> addressDTOS) {
        return addressDTOS.stream().map(a -> toAddress(a)).collect(Collectors.toList());
    }

    public static List<AddressDto> toAddressDtos(List<Address> addresses) {
        return addresses.stream().map(a -> toAddressDto(a)).collect(Collectors.toList());
    }

    public static Address toUpdateAddress(Address address, Address updatedAddress) {
        address.setCountry(updatedAddress.getCountry() == null ? address.getCountry() : updatedAddress.getCountry());
        address.setCountry(updatedAddress.getCity() == null ? address.getCountry() : updatedAddress.getCountry());
        address.setCountry(updatedAddress.getAddress1() == null ? address.getCountry() : updatedAddress.getCountry());
        address.setCountry(updatedAddress.getAddress2() == null ? address.getCountry() : updatedAddress.getCountry());
        address.setCountry(updatedAddress.getAddress3() == null ? address.getCountry() : updatedAddress.getCountry());
        address.setUserId(updatedAddress.getUserId() == null ? address.getUserId() : updatedAddress.getUserId());
        return address;
    }
}
