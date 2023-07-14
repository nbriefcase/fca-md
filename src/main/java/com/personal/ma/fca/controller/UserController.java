package com.personal.ma.fca.controller;

import com.personal.ma.fca.controller.model.DtoUser;
import com.personal.ma.fca.security.user.User;
import com.personal.ma.fca.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("GET:OK");
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<DtoUser> getByUsername(@PathVariable String username) {
        Objects.requireNonNull(username);
        User user = service.getByUsername(username);
        return ResponseEntity.ok(user.toDto());
    }
}
