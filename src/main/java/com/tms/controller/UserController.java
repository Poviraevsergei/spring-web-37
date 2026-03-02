package com.tms.controller;

import com.tms.exception.UpdateException;
import com.tms.exception.UserCreateException;
import com.tms.exception.UserNotFoundException;
import com.tms.model.User;
import com.tms.model.dto.UserCreateDto;
import com.tms.model.dto.UserUpdateDto;
import com.tms.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Slf4j
@Tag(name = "UserController", description = "Контроллер для пользователей")
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Поиск пользователей",
            description = "Система ищет пользователя в БД по id который передан в пути.")
    @ApiResponses(value = {
            @ApiResponse(description = "Данного юзера не существует в системе", responseCode = "404"),
            @ApiResponse(description = "Успешный возврат пользователя", responseCode = "200")
    })
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") @Parameter(description = "Id пользователя", example = "1") Integer id) {
        log.info("IN: getUserById(). Params: id = " + id);

        Optional<User> userOptional = userService.getUserById(id);
        if (userOptional.isPresent()) {
            log.info("OUT: getUserById(). Result: " + userOptional.get());
            return ResponseEntity.ok(userOptional.get());
        }
        log.info("OUT: getUserById(). Result: null");
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> userPage = userService.getAllUsers();
        return ResponseEntity.ok(userPage);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid UserCreateDto userDto,
                                           BindingResult bindingResult) throws UserCreateException {
        if (bindingResult.hasErrors()) {
            log.warn(bindingResult.getAllErrors().toString());
            return ResponseEntity.badRequest().build();
        }
        User user = userService.save(userDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(location).body(user);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody @Valid UserUpdateDto userDto,
                                                 BindingResult bindingResult) throws UserNotFoundException, UpdateException {
        if (bindingResult.hasErrors()) {
            log.warn(bindingResult.getAllErrors().toString());
            return ResponseEntity.badRequest().build();
        }
        User user = userService.update(userDto);
        return ResponseEntity.ok(user);
    }

    @Tag(name = "Удаление", description = "Эндпоинты связанные с удалением!!!")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Integer id) throws UserNotFoundException {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}




















