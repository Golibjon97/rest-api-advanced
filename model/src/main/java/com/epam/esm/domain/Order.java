package com.epam.esm.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne
    private Certificate certificate;

    @ManyToOne
    private User user;

    public Order(UUID id, LocalDateTime createdDate, LocalDateTime updatedDate, BigDecimal price, Certificate certificate, User user) {
        super(id, createdDate, updatedDate);
        this.price = price;
        this.certificate = certificate;
        this.user = user;
    }

}
