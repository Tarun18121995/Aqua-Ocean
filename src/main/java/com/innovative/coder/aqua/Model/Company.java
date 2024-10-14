package com.innovative.coder.aqua.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Types;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Company extends BaseEntity
{
    @Id
    @GeneratedValue(generator = "uuid")
    @UuidGenerator
    @Column(columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(Types.VARCHAR)
    private UUID id;
    @Column(name = "customer_name")
    private String companyName;
    @Column(columnDefinition = "VARCHAR(50)")
    private String email;
    @Column(columnDefinition = "VARCHAR(15)")
    private String mobile;
    @Column(name = "address_line_1",columnDefinition = "TEXT")
    private String address1;
    @Column(name = "address_line_2",columnDefinition = "TEXT")
    private String address2;
    @Column(name = "city_name",columnDefinition = "VARCHAR(50)")
    private String city;
    @Column(name = "zip_code",columnDefinition = "VARCHAR(15)")
    private String zip;
    @Column(name = "state_name",columnDefinition = "VARCHAR(50)")
    private String state;
    @Column(name = "country_name",columnDefinition = "VARCHAR(50)")
    private String country;
    private String companyImageUrl;
    private String slackToken;
    private String slackChannelName;

    //    private LocalDateTime mailSentOn;
//    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "customer")
    private List<User> Users;

//    @OneToMany(mappedBy="customer")
//    private List<Location> locations;
    private String industryType;
}
