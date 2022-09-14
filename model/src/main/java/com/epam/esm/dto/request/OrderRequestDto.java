package com.epam.esm.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {


    @NotNull(message = "certificate id cannot be null")
    private UUID certificateId;

    @NotNull(message = "user id cannot be null")
    private UUID userId;

}
