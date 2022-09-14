package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> extends RepresentationModel<BaseResponse<T>> {

    private int status;
    private String message;
    private T data;

}
