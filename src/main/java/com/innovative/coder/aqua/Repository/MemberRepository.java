package com.innovative.coder.aqua.Repository;

import com.innovative.coder.aqua.Model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, UUID> {
    Member findByEmailAndIsDeleted(String email, Boolean aFalse);

    Member findByRoleAndIsDeleted(String aquaAdmin, Boolean aFalse);

}
