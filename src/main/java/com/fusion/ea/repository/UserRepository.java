package com.fusion.ea.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fusion.ea.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUserName(String name);

}
