package com.innovative.coder.aqua.Repository;

import com.innovative.coder.aqua.Model.Login;
import com.innovative.coder.aqua.applicationData.ApplicationEnums;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LoginRepository extends JpaRepository<Login, UUID> {
    Login findByTokenAndStatus(String authToken, ApplicationEnums.LogInStatusEnum status);
}
