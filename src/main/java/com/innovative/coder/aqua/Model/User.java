package com.innovative.coder.aqua.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UuidGenerator;

import java.lang.annotation.ElementType;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity
{
    @Id
    @GeneratedValue(generator = "uuid")
    @UuidGenerator
    @Column(columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(Types.VARCHAR)
    private UUID id;
    private String employeeId;
    private String firstName;
    private String lastName;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String email;
    private LocalDateTime failedLoginAttempt;
    private Integer loginAttemptCount=0;
    @Column(columnDefinition = "VARCHAR(15)")
    private String mobile;
    @Column(columnDefinition = "VARCHAR(15)")
    private String secondaryPhoneNo;
    @Column(columnDefinition = "VARCHAR(15)")
    private String emergencyPhoneNo;
    private String martialStatus;
    private String bloodGroup;
    private Boolean isPhysicallyHandicap;
    private String password;
    private Boolean isPasswordUpdated;
    private String personalEmail;
    //    private String conformationCode;
    @Column(columnDefinition = "VARCHAR(15)")
    private String gender;
    private LocalDate dateOfBirth;
    private LocalDate dateOfJoining;
    private String profilePicUrl;
    @Column(columnDefinition = "VARCHAR(36)")
//    @Type(type = "uuid-char")
    private UUID userId;
    private String profession;
    private String language;
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Company customer;
    private String userRole;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
    @JsonIgnore
    private List<Login> logins;
    private String addressList;
}
