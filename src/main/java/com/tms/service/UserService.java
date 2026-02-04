package com.tms.service;

import com.tms.model.User;
import com.tms.model.dto.UserCreateDto;
import com.tms.model.dto.UserUpdateDto;
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
    public Optional<User> getUserById(Integer id) {
        User user = new User();
        user.setId(id);
        return Optional.ofNullable(null);
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

    public User save(UserCreateDto dto){
        User user = new User();
        user.setId(new Random().nextInt(10,100));
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setAge(dto.getAge());
        user.setCreated(Instant.now());
        user.setUpdated(Instant.now());
        return user;
    }

    public void delete(Integer id) {
        // Есть ли юзер?
        // Проверяем можно ли удалить
        // Удаляем
        // Перепроверяем
    }

    public void update(UserUpdateDto updateDto) {
        // Есть ли юзер?
        // Проверяем можно ли обновить
        // Обновляем
    }
}
