package com.innovative.coder.aqua.Repository;

import com.innovative.coder.aqua.Model.Pond;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PondRepository extends JpaRepository<Pond, UUID> {
}
