package com.bookstore.demo.user;

import com.bookstore.demo.user.model.ERole;
import com.bookstore.demo.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole role);
}
