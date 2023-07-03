package com.personal.ma.fca.repository;

import com.personal.ma.fca.repository.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
