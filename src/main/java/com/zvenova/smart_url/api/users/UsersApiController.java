package com.zvenova.smart_url.api.users;

import com.zvenova.smart_url.api.exception.request.FieldInRequestCannotBeEmpty;
import com.zvenova.smart_url.api.exception.user.UserDoesNotExistsException;
import com.zvenova.smart_url.mapping.UserMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.zvenova.smart_url.api.users.request.CreateUserRequest;
import com.zvenova.smart_url.api.users.response.CreateUserResponse;
import com.zvenova.smart_url.domain.entity.User;
import com.zvenova.smart_url.api.exception.user.UserIsAlreadyPresentException;
import com.zvenova.smart_url.service.UserService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersApiController {

    private final UserService userService;

    private final UserMapper userMapper;

    @PostMapping
    public CreateUserResponse createUser(
            @RequestBody CreateUserRequest request
    ) throws FieldInRequestCannotBeEmpty, UserIsAlreadyPresentException {

        User createdUser = userService.saveUser(userMapper.getUserFromCreateUserRequest(request));
        return userMapper.getCreateUserResponse(createdUser);
    }

    @PatchMapping
    public ResponseEntity<String> updateUser(
            @RequestBody User updateUserRequest
    ) throws FieldInRequestCannotBeEmpty, UserIsAlreadyPresentException, UserDoesNotExistsException {

        User userToUpdate = userMapper.getNewUserFromUpdateUserRequest(
                updateUserRequest, userService.findById(updateUserRequest.getId()));

        userService.updateUser(userToUpdate);
        return ResponseEntity.ok("User updated");
    }
}
