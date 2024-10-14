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
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;

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
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime failedLoginAttempt;
    private Integer loginAttemptCount=0;
    @Column(columnDefinition = "VARCHAR(15)")
    private String mobile;
    private String bloodGroup;
    private String password;
    private Boolean isPasswordUpdated;
    private String gender;
    private LocalDate dateOfBirth;
    private String profilePicUrl;
    @JdbcTypeCode(Types.VARCHAR)
    private UUID createdBy;
    private String language;
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;
    private String role;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
    @JsonIgnore
    private List<Login> logins;
    @Column(name = "user_address_1")
    private String userAddress1;
    @Column(name = "user_address_2")
    private String userAddress2;
    @Column(name = "city")
    private String city;
    @Column(name = "district")
    private String district;
    @Column(name = "state")
    private String state;
    @Column(name = "country")
    private String country;
    @Column(name = "postal_code")
    private String postalCode;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "pondOwner")
    @JsonIgnore
    private List<Pond> pond;
}
