package com.innovative.coder.aqua.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Types;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "Pond")
public class Pond extends BaseEntity{
    @Id
    @GeneratedValue(generator = "uuid")
    @UuidGenerator
    @Column(columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(Types.VARCHAR)
    private UUID id;
    @Column(name = "pondName")
    private String pondName;
    @Column(name = "capacity")
    private double capacity;
    @ManyToOne
    @JsonIgnore
    private User pondOwner;
    @Column(name = "seedType")
    private String seedType;
    @Column(name = "pond_address_1")
    private String pondAddress1;
    @Column(name = "pond_address_2")
    private String pondAddress2;
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
}
