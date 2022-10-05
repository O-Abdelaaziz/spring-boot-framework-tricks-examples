package com.datatransferobjectmanual.mapper;

import com.datatransferobjectmanual.entity.Address;
import com.datatransferobjectmanual.entity.User;

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
public class UserMapper {

    public static User toUser(UserDto userDto) {
        if (Objects.isNull(userDto)) {
            return null;
        }
        User user = new User();
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAddressList(userDto.getAddressList());
        return user;
    }

    public static UserDto toUserDto(User user) {
        if (Objects.isNull(user)) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setAddressList(user.getAddressList());
        return userDto;
    }

    public static List<User> toUsers(List<UserDto> userDtos) {
        return userDtos.stream().map(a -> toUser(a)).collect(Collectors.toList());
    }

    public static List<UserDto> toUsersDtos(List<User> Useres) {
        return Useres.stream().map(a -> toUserDto(a)).collect(Collectors.toList());
    }

    public static User toUpdateUser(User user, User updatedUser) {
        user.setFirstName(updatedUser.getFirstName() == null ? user.getFirstName() : updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName() == null ? user.getLastName() : updatedUser.getLastName());
        user.setUsername(updatedUser.getUsername() == null ? user.getUsername() : updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail() == null ? user.getEmail() : updatedUser.getEmail());
        user.setPassword(updatedUser.getPassword() == null ? user.getPassword() : updatedUser.getPassword());

        return user;
    }
}
