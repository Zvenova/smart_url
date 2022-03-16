package com.zvenova.like_my.api.users;

import com.zvenova.like_my.api.exception.request.FieldInRequestCannotBeEmpty;
import com.zvenova.like_my.api.exception.user.UserDoesNotExistsException;
import com.zvenova.like_my.mapping.UserMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.zvenova.like_my.api.users.request.CreateUserRequest;
import com.zvenova.like_my.api.users.response.CreateUserResponse;
import com.zvenova.like_my.domain.entity.User;
import com.zvenova.like_my.api.exception.user.UserIsAlreadyPresentException;
import com.zvenova.like_my.service.UserService;

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
