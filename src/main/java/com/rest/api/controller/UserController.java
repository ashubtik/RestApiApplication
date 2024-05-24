package com.rest.api.controller;

import com.rest.api.dto.UserDTO;
import com.rest.api.entity.User;
import com.rest.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(this.userService.getUserById(userId));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers(@RequestParam(required = false) Map<String, String> params) {
        return ResponseEntity.ok(this.userService.getUsers(params));
    }

    @PostMapping
    public ResponseEntity<UserDTO> postUser(@RequestBody User user) {
        return new ResponseEntity<>(this.userService.createUser(user), HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> putUser(@PathVariable Integer userId, @RequestBody User user) {
        return ResponseEntity.ok(this.userService.updateUser(userId, user));
    }

    @RequestMapping(value = "/{userId}",
            method = RequestMethod.PATCH,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody void patchUser(@PathVariable Integer userId, @RequestBody Map<String, Object> updates) {
        this.userService.partiallyUpdateUser(userId, updates);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<User> deleteUser(@PathVariable Integer userId) {
        this.userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
