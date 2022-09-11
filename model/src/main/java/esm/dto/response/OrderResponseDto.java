package esm.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class OrderResponseDto {

    private UUID id;
    private BigDecimal price;
    private CertificateResponseDto certificate;
    private UserResponseDto user;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedDate;

    public OrderResponseDto(UUID id, BigDecimal price, CertificateResponseDto certificate, UserResponseDto user, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.price = price;
        this.certificate = certificate;
        this.user = user;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public CertificateResponseDto getCertificate() {
        return certificate;
    }

    public void setCertificate(CertificateResponseDto certificate) {
        this.certificate = certificate;
    }

    public UserResponseDto getUser() {
        return user;
    }

    public void setUser(UserResponseDto user) {
        this.user = user;
    }

    public OrderResponseDto() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }
}
