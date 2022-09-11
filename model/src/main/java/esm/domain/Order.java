package esm.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

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

    public Order(BigDecimal price, Certificate certificate, User user) {
        this.price = price;
        this.certificate = certificate;
        this.user = user;
    }

    public Order() {
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
