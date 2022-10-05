package com.datatransferobjectmanual.mapper;

import com.datatransferobjectmanual.entity.Address;
import lombok.*;

import java.util.List;

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
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private List<Address> addressList;
}
