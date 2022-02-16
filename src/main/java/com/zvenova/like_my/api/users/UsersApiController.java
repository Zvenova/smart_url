package com.zvenova.like_my.api.users;

import java.util.Set;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zvenova.like_my.api.users.request.CreateUserRequest;
import com.zvenova.like_my.api.users.response.CreateUserResponse;
import com.zvenova.like_my.domain.entity.User;
import com.zvenova.like_my.domain.security.Role;
import com.zvenova.like_my.exception.UserIsAlreadyPresentException;
import com.zvenova.like_my.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController("/api/user")
@RequiredArgsConstructor
public class UsersApiController {

    private final UserService userService;

    @PostMapping
    public CreateUserResponse createUser(CreateUserRequest request)
            throws UserIsAlreadyPresentException {

        User createdUser = userService.saveUser(getUserFromCreateUserRequest(request));
        return getCreateUserResponse(createdUser);

    }

    // TODO to mapper service
    private CreateUserResponse getCreateUserResponse(User createdUser) {

        return new CreateUserResponse(createdUser.getId(), createdUser.getUsername());
    }

    private User getUserFromCreateUserRequest(CreateUserRequest request) {

        User userForCreation = new User();

        userForCreation.setUsername(request.getUsername());
        userForCreation.setPassword(request.getPassword());
        userForCreation.setRoles(Set.of(Role.USER));
        userForCreation.setActive(true);
        return userForCreation;
    }

}
