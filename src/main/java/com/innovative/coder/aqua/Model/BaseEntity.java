package com.innovative.coder.aqua.Model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@MappedSuperclass
public class BaseEntity
{
    @Column(name = "createdAt")
    private LocalDateTime creationTimestamp;
    @Column(name = "updatedAt")
    private LocalDateTime updateTimestamp;
    private Boolean isActive;
    private Boolean isDeleted = Boolean.FALSE;
    @PreUpdate
    private void updateTimestamp(){
        this.updateTimestamp = LocalDateTime.now();
    }
    @PrePersist
    private void creationTimestamp(){
        this.creationTimestamp = LocalDateTime.now();
        this.updateTimestamp = LocalDateTime.now();
    }
    @Column(columnDefinition = "VARCHAR(36)")
//    @Type(type = "uuid-char")
    private UUID createdBy;
    @Column(columnDefinition = "VARCHAR(36)")
//    @Type(type = "uuid-char")
    private UUID updatedBy;
}

