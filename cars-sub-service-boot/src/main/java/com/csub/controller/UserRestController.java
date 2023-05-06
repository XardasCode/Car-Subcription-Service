package com.csub.controller;

import com.csub.controller.request.UserRequestDTO;
import com.csub.controller.util.JSONInfo;
import com.csub.dto.UserDTO;
import com.csub.service.UserService;
import com.csub.util.UserSearchInfo;
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
@CrossOrigin
public class UserRestController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        log.info("Getting users with page {} and size {}", page, size);
        return ResponseEntity.ok(userService.getUsers(
                UserSearchInfo.builder()
                        .page(page)
                        .size(size)
                        .build()
        ));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable long id) {
        log.info("Getting user with id {}", id);
        return ResponseEntity.ok(userService.getUser(id));
    }

    @GetMapping(value = "/{email}/{password}")
    public ResponseEntity<UserDTO> checkUserCredentials(@PathVariable String email, @PathVariable String password) {
        log.info("Checking user credentials");
        return ResponseEntity.ok(userService.checkUserCredentials(email, password));
    }

    @GetMapping(value = "/search")
    public ResponseEntity<List<UserDTO>> findUsers(@RequestParam(value = "partOfName", required = false) String partOfName,
                                                   @RequestParam(value = "partOfSurname", required = false) String partOfSurname,
                                                   @RequestParam(value = "isSortByName", required = false) boolean isSortByName,
                                                   @RequestParam(value = "sortType", required = false) String sortType,
                                                   @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                                   @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        log.info("Searching users");
        UserSearchInfo info = UserSearchInfo.builder()
                .page(page)
                .size(size)
                .build();
        return ResponseEntity.ok(userService.findUsers(partOfName, partOfSurname, isSortByName, sortType, info));
    }

    @PostMapping
    public ResponseEntity<JSONInfo> addUser(@RequestBody @Valid UserRequestDTO user) {
        log.info("Adding user: {}", user);
        long id = userService.addUser(user);
        log.info("User added with id {}", id);
        return new ResponseEntity<>(JSONInfo.builder().message(String.valueOf(id)).build(), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<JSONInfo> updateUser(@RequestBody UserRequestDTO user, @PathVariable long id) {
        log.info("Updating user: {}", user);
        userService.updateUser(user, id);
        log.info("User updated");
        return new ResponseEntity<>(JSONInfo.builder().message(String.valueOf(id)).build(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<JSONInfo> deleteUser(@PathVariable long id) {
        log.info("Deleting user with id {}", id);
        userService.deleteUser(id);
        log.info("User deleted");
        return new ResponseEntity<>(JSONInfo.builder().message(String.valueOf(id)).build(), HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}/block")
    public ResponseEntity<JSONInfo> blockUser(@PathVariable long id) {
        log.info("Blocking user with id {}", id);
        userService.blockUser(id);
        log.info("User blocked");
        return new ResponseEntity<>(JSONInfo.builder().message(String.valueOf(id)).build(), HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}/unblock")
    public ResponseEntity<JSONInfo> unblockUser(@PathVariable long id) {
        log.info("Unblocking user with id {}", id);
        userService.unblockUser(id);
        log.info("User unblocked");
        return new ResponseEntity<>(JSONInfo.builder().message(String.valueOf(id)).build(), HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}/generate-verification-code")
    public ResponseEntity<JSONInfo> verifyEmail(@PathVariable long id) {
        log.info("Generating verification code for user with id {}", id);
        userService.generateVerificationCode(id);
        log.info("Verification code generated");
        return new ResponseEntity<>(JSONInfo.builder().message(String.valueOf(id)).build(), HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}/verify-email")
    public ResponseEntity<JSONInfo> verifyEmail(@PathVariable long id, @RequestParam String code) {
        log.info("Verifying email for user with id {}", id);
        userService.verifyEmail(id, code);
        log.info("Email verified");
        return new ResponseEntity<>(JSONInfo.builder().message(String.valueOf(id)).build(), HttpStatus.OK);
    }
}
