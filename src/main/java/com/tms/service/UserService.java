package com.tms.service;

import com.tms.exception.UpdateException;
import com.tms.exception.UserCreateException;
import com.tms.exception.UserNotFoundException;
import com.tms.model.Security;
import com.tms.model.User;
import com.tms.model.dto.UserCreateDto;
import com.tms.model.dto.UserUpdateDto;
import com.tms.repository.UserRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserById(Integer id) {
        User userFromDb = userRepository.findUserById(id);
        return Optional.ofNullable(userFromDb);
    }

    public List<User> getAllUsers() {
       return userRepository.findAllUsers();
    }

    public User save(UserCreateDto dto) throws UserCreateException {
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setAge(dto.getAge());
        user.setCreated(Instant.now());
        user.setUpdated(Instant.now());

        return userRepository.saveUser(user);
    }

    public void delete(Integer id) throws UserNotFoundException {
        User user = userRepository.findUserById(id);
        if (user == null){
            throw new UserNotFoundException();
        }
        userRepository.removeUserById(id);

    }

    public User update(UserUpdateDto updateDto) throws UserNotFoundException, UpdateException {
        User user = userRepository.findUserById(updateDto.getId());
        if (user == null){
            throw new UserNotFoundException();
        }
        user.setFirstName(updateDto.getFirstName());
        user.setLastName(updateDto.getLastName());
        user.setEmail(updateDto.getEmail());
        user.setAge(updateDto.getAge());
        user.setUpdated(Instant.now());
        return userRepository.updateUser(user);
    }
}
