package com.personal.ma.fca.controller;

import com.personal.ma.fca.exception.NotFoundException;
import com.personal.ma.fca.security.user.Role;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/roles")
public class RolController {

    @GetMapping
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("GET:OK");
    }

    @GetMapping(value = "/{roleName}")
    public ResponseEntity getByName(@PathVariable String roleName) {
        Objects.requireNonNull(roleName);
        Role.valueOf(roleName);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/role-access")
    public ResponseEntity<RoleAccess> validateAccess(@RequestParam String role, @RequestParam String menu) {
        Objects.requireNonNull(role);
        Objects.requireNonNull(menu);
        List<RoleAccess> roleAccesses = Optional.ofNullable(mapMenu.get(role.toUpperCase()))
                .filter(list -> list.size() > 0)
                .filter(m -> m.get(0).menu.equals(menu))
                .stream().findFirst()
                .orElseThrow(NotFoundException::new);
        return ResponseEntity.ok(roleAccesses.get(0));
    }

    private final Map<String, List<RoleAccess>> mapMenu = new HashMap<>() {
        {
            put(Role.ADMIN.toString(), List.of(RoleAccess.builder()
                            .role(Role.ADMIN.toString())
                            .menu("user")
                            .haveadd(true)
                            .haveedit(true)
                            .havedelete(true)
                            .build(),
                    RoleAccess.builder()
                            .role(Role.ADMIN.toString())
                            .menu("customer")
                            .haveadd(true)
                            .haveedit(true)
                            .havedelete(true)
                            .build()));
            put(Role.MANAGER.toString(), List.of(RoleAccess.builder()
                    .role(Role.ADMIN.toString())
                    .menu("user")
                    .haveadd(true)
                    .build()));
            put("TECH", List.of(RoleAccess.builder()
                    .role(Role.ADMIN.toString())
                    .menu("user")
                    .haveadd(true)
                    .build()));
            put(Role.USER.toString(), List.of(RoleAccess.builder()
                            .role(Role.USER.toString())
                            .menu("user")
                            .build(),
                    RoleAccess.builder()
                            .role(Role.USER.toString())
                            .menu("customer")
                            .haveadd(true)
                            .haveedit(true)
                            .build()));
        }
    };

    @Data
    @Builder
    static class RoleAccess {
        private String role;
        private String menu;
        private boolean haveadd;
        private boolean haveedit;
        private boolean havedelete;
    }
}
