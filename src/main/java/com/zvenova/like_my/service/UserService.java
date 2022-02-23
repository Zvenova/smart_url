package com.zvenova.like_my.service;

import javax.transaction.Transactional;

import com.zvenova.like_my.exception.user.UserDoesNotExistsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zvenova.like_my.domain.entity.User;
import com.zvenova.like_my.exception.user.UserIsAlreadyPresentException;
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

        if (isUserPresentByUsername(user.getUsername())) {
            throw new UserIsAlreadyPresentException(user.getUsername());
        }

       return userRepository.save(user);
    }

    public void deleteUser(User user) throws UserDoesNotExistsException {

        if (!isUserPresentById(user.getId())) {
            throw new UserDoesNotExistsException(user.getId());
        }

        userRepository.deleteById(user.getId());
    }

    public User findById(Long id) throws UserDoesNotExistsException {

        return userRepository.findById(id).orElseThrow(() -> new UserDoesNotExistsException(id));
    }

    private boolean isUserPresentByUsername(String username) {

        return userRepository.existsByUsername(username);
    }

    private boolean isUserPresentById(Long id) {

        return userRepository.existsById(id);
    }
}
