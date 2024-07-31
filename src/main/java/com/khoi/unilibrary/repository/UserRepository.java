package com.khoi.unilibrary.repository;

import com.khoi.unilibrary.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
