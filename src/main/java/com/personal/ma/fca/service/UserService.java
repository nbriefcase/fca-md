package com.personal.ma.fca.service;

import com.personal.ma.fca.security.user.User;

public interface UserService {
    User getByUsername(String username);
}
