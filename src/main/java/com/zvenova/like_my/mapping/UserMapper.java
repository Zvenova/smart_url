package com.zvenova.like_my.mapping;

import com.zvenova.like_my.api.users.request.CreateUserRequest;
import com.zvenova.like_my.api.users.response.CreateUserResponse;
import com.zvenova.like_my.domain.entity.User;
import com.zvenova.like_my.domain.security.Role;
import com.zvenova.like_my.api.exception.request.FieldInRequestCannotBeEmpty;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
public class UserMapper {

    public User getUserFromCreateUserRequest(CreateUserRequest createUserRequest) throws FieldInRequestCannotBeEmpty {

        if (createUserRequest.getUsername().isBlank() || createUserRequest.getPassword().isBlank()) {

            throw new FieldInRequestCannotBeEmpty("Поле не может быть пустым!");
        }

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

    public User getNewUserFromUpdateUserRequest(User updateUserRequest, User oldUserData) throws FieldInRequestCannotBeEmpty {

        User newUserData = new User();

        if (updateUserRequest.getUsername().isBlank() && updateUserRequest.getPassword().isBlank()) {

            throw new FieldInRequestCannotBeEmpty("Поле не может быть пустым!");
        }

        newUserData.setUsername(updateUserRequest.getUsername().isBlank() ? oldUserData.getUsername() : updateUserRequest.getUsername());
        newUserData.setPassword(updateUserRequest.getPassword().isBlank() ? oldUserData.getPassword() : updateUserRequest.getPassword());
        newUserData.setRoles(oldUserData.getRoles());
        newUserData.setActive(oldUserData.isActive());

        return newUserData;
    }
}
