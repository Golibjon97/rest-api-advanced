package com.epam.esm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "tags")
public class Tag extends BaseEntity {

    @Column(unique = true)
    private String name;

    public Tag(UUID id, LocalDateTime createdDate, LocalDateTime updatedDate, String name) {
        super(id, createdDate, updatedDate);
        this.name = name;
    }

}
