package com.loginex.logx.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loginex.logx.domain.user.User;

public interface UserRepository extends JpaRepository<User, String> {

	Optional<User> findByEmail(String email);
}
