package com.epam.esm.dto.request;


import com.epam.esm.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificateRequestDto {

    private String name;
    private String description;
    private String price;
    private String duration;
    private List<Tag> tags;

}
