package com.personal.ma.fca.controller.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DtoUser {

    private long id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String role;
    private boolean isActive;
}
