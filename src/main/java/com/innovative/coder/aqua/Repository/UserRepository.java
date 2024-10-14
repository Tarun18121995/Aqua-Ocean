package com.innovative.coder.aqua.Repository;

import com.innovative.coder.aqua.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByEmailAndIsDeleted(String email, Boolean aFalse);

    User findByRoleAndIsDeleted(String aquaAdmin, Boolean aFalse);
}
