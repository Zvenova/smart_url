package com.zvenova.smart_url.service;

import javax.transaction.Transactional;

import com.zvenova.smart_url.api.exception.user.UserDoesNotExistsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zvenova.smart_url.domain.entity.User;
import com.zvenova.smart_url.api.exception.user.UserIsAlreadyPresentException;
import com.zvenova.smart_url.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findByUsername(String username) {

        return userRepository.findByUsername(username)
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

    public User updateUser(User userToUpdate) throws UserIsAlreadyPresentException {


        if (isUserPresentByUsername(userToUpdate.getUsername())) {

            throw new UserIsAlreadyPresentException(userToUpdate.getUsername());
        }

        return userRepository.save(userToUpdate);
    }

    public User findById(Long id) throws UserDoesNotExistsException {

        return userRepository.findById(id).orElseThrow(() -> new UserDoesNotExistsException(id));
    }

    private boolean isUserPresentByUsername(String username) {

        return userRepository.findByUsername(username).isPresent();
    }

    private boolean isUserPresentById(Long id) {

        return userRepository.existsById(id);
    }
}
