package com.tms.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @BeforeAll
    static void beforeAll() {
        System.out.println("Before All");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("After All");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("Before Each");
    }

    @AfterEach
    void afterEach() {
        System.out.println("After Each");
    }

    @Tag(value = "idtag")
    @DisplayName("Метод поиска пользователя")
    @Test
    void getUserById_Success() throws Exception {
        //1. настраиваем тест - что и как будет происходить

        //2. запуск кода, получаем результат.
        mockMvc.perform(get("/user/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.getFirstName", Matchers.is("Adam")));
        //3. сравниваем результат из 2 шага и эталонный результат.
    }

    @Tag(value = "idtag")
    @Test
    void getUserById_NotFound() {

    }
}
//Mock
//Write test

/*      Assertions.assertTrue(true);
        Assertions.assertFalse(false);
        Assertions.assertEquals(5 + 3, 8);
        Assertions.assertNotEquals(5, 8);
        Assertions.assertNull(null);
        Assertions.assertNotNull(1);
        Assertions.assertThrows(NullPointerException.class, () -> {
        throw new NullPointerException();
        });
        Assertions.assertTimeout(Duration.ofMillis(10), () -> {
        Thread.sleep(1);
        });
        Assertions.assertArrayEquals(new int[]{1, 2, 3}, new int[]{1, 2, 3});*/
