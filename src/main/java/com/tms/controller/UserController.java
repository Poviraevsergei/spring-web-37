package com.tms.controller;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
import java.util.Optional;

@Slf4j
@Tag(name = "UserController" ,description = "Контроллер для пользователей")
@RestController
@RequestMapping("/user")
public class UserController {
    //private final Logger log = LoggerFactory.getLogger(UserController.class);

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
            log.info("OUT: getUserById(). Result: null" + userOptional.get());
            return ResponseEntity.ok(userOptional.get());
        }
        log.info("OUT: getUserById(). Result: null");
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<Page<User>> getAllUsers(@PageableDefault(
            size = 20,
            sort = "id",
            direction = Sort.Direction.DESC) Pageable pageable) {
        Page<User> userPage = userService.getAllUsers(pageable);
        return ResponseEntity.ok(userPage);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid UserCreateDto userDto,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.warn(bindingResult.getAllErrors().toString());
            return ResponseEntity.badRequest().body(null);
        }
        User user = userService.save(userDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(location).body(user);
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateUser(@RequestBody @Valid UserUpdateDto userDto,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.warn(bindingResult.getAllErrors().toString());
            return ResponseEntity.badRequest().body(null);
        }
        userService.update(userDto);
        return ResponseEntity.noContent().build();
    }

    @Tag(name = "Удаление", description = "Эндпоинты связанные с удалением!!!")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Integer id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}




















