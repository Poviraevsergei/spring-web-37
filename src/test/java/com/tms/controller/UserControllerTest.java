package com.tms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tms.model.User;
import com.tms.model.dto.UserCreateDto;
import com.tms.model.dto.UserUpdateDto;
import com.tms.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource(properties = {"server.port = 8080"})
@WebMvcTest(controllers = UserController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com\\.tms\\.config\\..*")
        })
@DisplayName("Тесты для UserController")
public class UserControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = new User();
        user1.setId(1);
        user1.setAge(20);
        user1.setFirstName("Adam");
        user1.setLastName("Harry");
        user1.setEmail("adam@gmail.com");

        user2 = new User();
        user2.setId(2);
        user2.setAge(30);
        user2.setFirstName("Eva");
        user2.setLastName("Second");
        user2.setEmail("eva@gmail.com");
    }

    @DisplayName("Получение пользователя по Id - успешный сценарий")
    @Test
    void getUserById_Success() throws Exception {
        when(userService.getUserById(anyInt())).thenReturn(Optional.of(user1));

        mockMvc.perform(get("/user/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", Matchers.is("Adam")));
    }

    @DisplayName("Получение пользователя по Id - не найден")
    @Test
    void getUserById_NotFound() throws Exception {
        when(userService.getUserById(anyInt())).thenReturn(Optional.empty());

        mockMvc.perform(get("/user/10"))
                .andExpect(status().isNotFound());
    }

/*    @DisplayName("Получение всех пользователей с пагинацией")
    @Test
    void getAllUsers_Success() throws Exception {
        Page<User> userPage = new PageImpl<>(List.of(user1, user2));

        when(userService.getAllUsers(any(Pageable.class))).thenReturn(userPage);

        mockMvc.perform(get("/user")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.content[0].firstName", Matchers.is("Adam")))
                .andExpect(jsonPath("$.content[1].id", Matchers.is(2)));
    }*/

    @DisplayName("Создание пользователя - успешный сценарий")
    @Test
    void createUser_Success() throws Exception {
        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setFirstName("Bill");
        userCreateDto.setLastName("Gates");
        userCreateDto.setEmail("bill@gmail.com");
        userCreateDto.setAge(59);

        User savedUser = new User();
        savedUser.setId(3);
        savedUser.setFirstName(userCreateDto.getFirstName());
        savedUser.setLastName(userCreateDto.getLastName());
        savedUser.setEmail(userCreateDto.getEmail());
        savedUser.setAge(userCreateDto.getAge());

        when(userService.save(any(UserCreateDto.class))).thenReturn(savedUser);

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCreateDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Matchers.is(3)))
                .andExpect(jsonPath("$.firstName", Matchers.is(userCreateDto.getFirstName())))
                .andExpect(jsonPath("$.lastName", Matchers.is(userCreateDto.getLastName())))
                .andExpect(jsonPath("$.email", Matchers.is(userCreateDto.getEmail())))
                .andExpect(jsonPath("$.age", Matchers.is(userCreateDto.getAge())))
                .andExpect(header().exists("Location"));
    }

    @DisplayName("Создание пользователя - валидация не прошла")
    @Test
    void createUser_ValidationError() throws Exception {
        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setFirstName("Bill");
        userCreateDto.setLastName("Gates");
        userCreateDto.setEmail("bill@gmail.com");
        userCreateDto.setAge(10);

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCreateDto)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Обновление пользователя - успешный сценарий")
    @Test
    void updateUser_Success() throws Exception {
        UserUpdateDto updateDto = new UserUpdateDto();
        updateDto.setId(1);
        updateDto.setFirstName("Bill");
        updateDto.setLastName("Gates");
        updateDto.setEmail("bill@gmail.com");
        updateDto.setAge(20);

        doNothing().when(userService).update(any(UserUpdateDto.class));

        mockMvc.perform(put("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isNoContent());
    }

    @DisplayName("Обновление пользователя - валидация не прошла")
    @Test
    void updateUser_ValidationFailed() throws Exception {
        UserUpdateDto updateDto = new UserUpdateDto();
        updateDto.setId(null);
        updateDto.setFirstName("Bill");
        updateDto.setLastName("Gates");
        updateDto.setEmail("bill@gmail.com");
        updateDto.setAge(20);

        mockMvc.perform(put("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Удаление пользователя - успешный сценарий")
    @Test
    void deleteUser_Success() throws Exception {
        doNothing().when(userService).delete(anyInt());

        mockMvc.perform(delete("/user/10"))
                .andExpect(status().isNoContent());
    }
}
