package com.epam.esm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "certificates")
public class Certificate extends BaseEntity {

    @Column(unique = true)
    private String name;

    private String description;
    private BigDecimal price;
    private Integer duration;

    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.DETACH,
            CascadeType.MERGE,CascadeType.REFRESH})
     private List<Tag> tags;

    public Certificate(UUID id, LocalDateTime createdDate, LocalDateTime updatedDate, String name, String description, BigDecimal price, Integer duration, List<Tag> tags) {
        super(id, createdDate, updatedDate);
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.tags = tags;
    }

}
