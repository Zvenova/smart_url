package com.zvenova.like_my.service.mapper;

import com.zvenova.like_my.api.users.request.CreateUserRequest;
import com.zvenova.like_my.api.users.response.CreateUserResponse;
import com.zvenova.like_my.domain.entity.User;
import com.zvenova.like_my.domain.security.Role;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
public class UserMapper {

    public User getUserFromCreateUserRequest(CreateUserRequest createUserRequest) {

        User userForCreation = new User();

        userForCreation.setUsername(createUserRequest.getUsername());
        userForCreation.setPassword(createUserRequest.getPassword());
        userForCreation.setRoles(Set.of(Role.USER));
        userForCreation.setActive(true);
        return userForCreation;
    }

    public CreateUserResponse getCreateUserResponse(User createdUser) {

        return new CreateUserResponse(createdUser.getId(), createdUser.getUsername());
    }
}
