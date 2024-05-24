package com.rest.api.service;

import com.rest.api.dto.UserDTO;
import com.rest.api.entity.User;
import com.rest.api.exeptions.BadRequestException;
import com.rest.api.exeptions.ResourceNotFoundException;
import com.rest.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static com.rest.api.constants.Constants.*;
import static com.rest.api.mapper.UserMapper.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserDTO getUserById(Integer userId) {
        return convertUserToDto(getUserFromDB(userId));
    }

    public List<UserDTO> getUsers(Map<String, String> params) {
        return switch (params.size()) {
            case 0 -> convertUsersToDtoList(this.userRepository.findAll());
            case 1 -> convertUsersToDtoList(getUsersByOneParam(params));
            case 2 -> convertUsersToDtoList(getUsersByTwoParams(params));
            default -> throw new BadRequestException("Maximum 2 parameters allowed");
        };
    }

    public UserDTO createUser(User user) {
        return convertUserToDto(this.userRepository.save(user));
    }

    public UserDTO updateUser(Integer userId, User user) {
        var oldUser = getUserFromDB(userId);
        oldUser.setUsername(user.getUsername());
        oldUser.setEmail(user.getEmail());
        oldUser.setCity(user.getCity());
        oldUser.setCompany(user.getCompany());

        return convertUserToDto(this.userRepository.save(oldUser));
    }

    public void partiallyUpdateUser(Integer userId, Map<String, Object> updates) {
        var user = getUserFromDB(userId);
        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(User.class, key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, user, value);
        });
        this.userRepository.save(user);
    }

    public void deleteUser(Integer userId) {
        this.userRepository.deleteById(userId);
    }

    User getUserFromDB(Integer userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id: " + userId + " doesn't exist"));
    }

    private List<User> getUsersByOneParam(Map<String, String> params) {
        return switch (params.keySet().iterator().next().toLowerCase()) {
            case COMPANY -> this.userRepository.findByCompany(params.values().iterator().next().toLowerCase());
            case CITY -> this.userRepository.findByCity(params.values().iterator().next().toLowerCase());
            default -> throw new BadRequestException("Invalid query parameter " + params.keySet().iterator().next());
        };
    }

    private List<User> getUsersByTwoParams(Map<String, String> params) {
        String city = null; String company = null;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            switch (entry.getKey()) {
                case CITY -> city = entry.getValue().toLowerCase();
                case COMPANY -> company = entry.getValue().toLowerCase();
                default -> throw new BadRequestException("Invalid query parameter " + entry.getKey());
            }
        }
        return this.userRepository.findByCityAndCompany(company, city);
    }
}
