package com.tms;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
        title = "This is c37 lesson app",
                description = "This is c37 lesson app description",
                contact = @Contact(
                        name = "Sergey Poviraev",
                        email = "poviraevsergei@gmail.com",
                        url = "https://teachmeskills.by"
                )
        )
)
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
