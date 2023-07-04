package com.personal.ma.fca;

import com.personal.ma.fca.security.auth.AuthenticationService;
import com.personal.ma.fca.security.auth.RegisterRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.personal.ma.fca.security.user.Role.ADMIN;
import static com.personal.ma.fca.security.user.Role.MANAGER;

@SpringBootApplication
//@SpringBootApplication(exclude = { SecurityAutoConfiguration.class }) // Disabling the Auto-Configuration
public class FcaApplication {

    public static void main(String[] args) {
        SpringApplication.run(FcaApplication.class, args);
    }


    @Bean
    public CommandLineRunner commandLineRunner(AuthenticationService service) {
        return args -> {
            var admin = RegisterRequest.builder()
                    .firstname("Admin")
                    .lastname("Admin")
                    .email("admin@mail.com")
                    .password("password")
                    .role(ADMIN)
                    .build();
            System.out.println("Admin token: " + service.register(admin).getAccessToken());

            var manager = RegisterRequest.builder()
                    .firstname("Admin")
                    .lastname("Admin")
                    .email("manager@mail.com")
                    .password("password")
                    .role(MANAGER)
                    .build();
            System.out.println("Manager token: " + service.register(manager).getAccessToken());

        };
    }
}
