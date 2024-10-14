package com.innovative.coder.aqua.Model;

import com.innovative.coder.aqua.applicationData.ApplicationEnums;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "LoginData")
public class Login
{
    @Id
    @GeneratedValue(generator = "uuid")
    @UuidGenerator
    @Column(columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(Types.VARCHAR)
    private UUID id;

    private String token;

    private String browserDetails;

    private LocalDateTime loggedInTime;

    private LocalDateTime loggedOutTime;

    @Enumerated(EnumType.STRING)
    private ApplicationEnums.LogInStatusEnum status;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}

