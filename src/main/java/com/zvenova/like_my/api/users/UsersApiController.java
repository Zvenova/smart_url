package com.zvenova.like_my.api.users;

import com.zvenova.like_my.service.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

import com.zvenova.like_my.api.users.request.CreateUserRequest;
import com.zvenova.like_my.api.users.response.CreateUserResponse;
import com.zvenova.like_my.domain.entity.User;
import com.zvenova.like_my.exception.user.UserIsAlreadyPresentException;
import com.zvenova.like_my.service.UserService;

import lombok.RequiredArgsConstructor;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersApiController {

    private final UserService userService;

    private final UserMapper userMapper;

    @PostMapping
    public CreateUserResponse createUser(
            @RequestBody CreateUserRequest request
    ) throws UserIsAlreadyPresentException {

        User createdUser = userService.saveUser(userMapper.getUserFromCreateUserRequest(request));
        return userMapper.getCreateUserResponse(createdUser);
    }
}
