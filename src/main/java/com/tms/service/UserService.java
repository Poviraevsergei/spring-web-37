package com.tms.service;

import com.tms.exception.UpdateException;
import com.tms.exception.UserCreateException;
import com.tms.exception.UserNotFoundException;
import com.tms.model.User;
import com.tms.model.dto.UserCreateDto;
import com.tms.model.dto.UserUpdateDto;
import com.tms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserById(Integer id) {
        return Optional.ofNullable(userRepository.findUserById(id));

    }

    public Page<User> getAllUsers(Pageable pageable) {
        User user = new User();
        user.setId(1);
        return new Page<User>() {
            @Override
            public int getTotalPages() {
                return 0;
            }

            @Override
            public long getTotalElements() {
                return 0;
            }

            @Override
            public <U> Page<U> map(Function<? super User, ? extends U> converter) {
                return null;
            }

            @Override
            public int getNumber() {
                return 0;
            }

            @Override
            public int getSize() {
                return 0;
            }

            @Override
            public int getNumberOfElements() {
                return 0;
            }

            @Override
            public List<User> getContent() {
                return List.of();
            }

            @Override
            public boolean hasContent() {
                return false;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @Override
            public Iterator<User> iterator() {
                return null;
            }
        };
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
