package com.datatransferobjectmanual.mapper;

import lombok.*;

/**
 * @Created 03/10/2022 - 08:25
 * @Package com.datatransferobjectmanual.mapper
 * @Project Data-Transfer-Object-Manual
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDto {
    private Long id;
    private String country;
    private String city;
    private String address1;
    private String address2;
    private String address3;
    private UserDto userDto;
    private Long userId;
}
