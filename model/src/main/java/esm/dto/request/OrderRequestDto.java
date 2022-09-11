package esm.dto.request;


import javax.validation.constraints.NotNull;
import java.util.UUID;

public class OrderRequestDto {


    @NotNull(message = "certificate id cannot be null")
    private UUID certificateId;

    @NotNull(message = "user id cannot be null")
    private UUID userId;

    public OrderRequestDto(UUID certificateId, UUID userId) {
        this.certificateId = certificateId;
        this.userId = userId;
    }

    public OrderRequestDto() {
    }

    public UUID getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(UUID certificateId) {
        this.certificateId = certificateId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
