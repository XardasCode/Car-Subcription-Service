package com.csub.controller;

import com.csub.entity.User;
import com.csub.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserRestController {
    private final UserService userService;

    private static final String SUCCESS = "success";

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("Getting all users");
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id) {
        log.info("Getting user with id {}", id);
        return ResponseEntity.ok(userService.getUser(id));
    }

    @GetMapping(value = "/{email}/{password}")
    public ResponseEntity<User> checkUserCredzentials(@PathVariable String email, @PathVariable String password) {
        log.info("Checking user credentials");
        return ResponseEntity.ok(userService.checkUserCredentials(email, password));
    }

    @GetMapping(value = "/search")
    public ResponseEntity<List<User>> findUsers(@RequestParam(value = "partOfName",required = false) String partOfName,
                                                @RequestParam(value = "partOfSurname",required = false) String partOfSurname,
                                                @RequestParam(value = "isSortByName",required = false) boolean isSortByName,
                                                @RequestParam(value = "sortType",required = false) String sortType) {
        log.info("Searching users");
        return ResponseEntity.ok(userService.findUsers(partOfName, partOfSurname, isSortByName, sortType));
    }

    @PostMapping
    public ResponseEntity<JSONInfo> addUser(@RequestBody @Valid User user) {
        log.info("Adding user: {}", user);
        long id = userService.addUser(user);
        log.info("User added with id {}", id);
        return new ResponseEntity<>(new JSONInfo(SUCCESS, "New user with id " + id + " created"), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<JSONInfo> updateUser(@RequestBody User user, @PathVariable long id) {
        log.info("Updating user: {}", user);
        userService.updateUser(user, id);
        log.info("User updated");
        return new ResponseEntity<>(new JSONInfo(SUCCESS, "User with id " + user.getId() + " updated"), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<JSONInfo> deleteUser(@PathVariable long id) {
        log.info("Deleting user with id {}", id);
        userService.deleteUser(id);
        log.info("User deleted");
        return new ResponseEntity<>(new JSONInfo(SUCCESS, "User with id " + id + " deleted"), HttpStatus.OK);
    }

}
