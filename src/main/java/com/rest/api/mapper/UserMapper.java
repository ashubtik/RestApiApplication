package com.rest.api.mapper;

import com.rest.api.dto.UserDTO;
import com.rest.api.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserDTO convertUserToDto(User user) {
        return new UserDTO(user.getUserId(), user.getUsername());
    }

    public static List<UserDTO> convertUsersToDtoList(List<User> users) {
        return users.stream()
                .map(UserMapper::convertUserToDto)
                .collect(Collectors.toList());
    }

//    public static User convertToUser(UserDTO dto) {
//        return userService.getUserFromDB(dto.getUserId());
//    }
}
