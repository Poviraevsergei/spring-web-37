package com.tms.service;

import com.tms.exception.UpdateException;
import com.tms.exception.UserCreateException;
import com.tms.exception.UserNotFoundException;
import com.tms.model.User;
import com.tms.model.dto.UserCreateDto;
import com.tms.model.dto.UserUpdateDto;
import com.tms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
       return userRepository.findAll();
    }

    public User save(UserCreateDto dto) throws UserCreateException {
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setAge(dto.getAge());
        user.setCreated(Instant.now());
        user.setUpdated(Instant.now());

        return userRepository.save(user);
    }

    public void delete(Integer id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }
        userRepository.deleteById(id);
    }

    public User update(UserUpdateDto updateDto) throws UserNotFoundException, UpdateException {
        Optional<User> userOptional = userRepository.findById(updateDto.getId());
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException();
        }
        User user = userOptional.get();
        user.setFirstName(updateDto.getFirstName());
        user.setLastName(updateDto.getLastName());
        user.setEmail(updateDto.getEmail());
        user.setAge(updateDto.getAge());
        user.setUpdated(Instant.now());
        return userRepository.saveAndFlush(user);
    }
}
