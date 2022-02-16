package com.zvenova.like_my.service;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zvenova.like_my.domain.entity.User;
import com.zvenova.like_my.exception.UserIsAlreadyPresentException;
import com.zvenova.like_my.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User loadUserByUsername(String username) {

        return userRepository.findByUsernameEquals(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User with name '%s' not found", username)));
    }

    public User saveUser(User user) throws UserIsAlreadyPresentException {

        if (isUserPresent(user)) {
            throw new UserIsAlreadyPresentException(user.getUsername());
        }

       return userRepository.save(user);
    }

    private boolean isUserPresent(User user) {

        return userRepository.findByUsernameEquals(user.getUsername()).isPresent();
    }

}
