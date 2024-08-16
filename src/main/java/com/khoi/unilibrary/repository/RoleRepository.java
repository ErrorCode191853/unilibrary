package com.khoi.unilibrary.repository;

import com.khoi.unilibrary.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);

    List<Role> findAll();

}
