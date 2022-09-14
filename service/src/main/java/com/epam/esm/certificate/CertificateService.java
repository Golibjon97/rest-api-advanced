package com.epam.esm.certificate;

import com.epam.esm.dto.request.CertificateRequestDto;
import com.epam.esm.dto.response.CertificateResponseDto;
import com.epam.esm.BaseService;
import com.epam.esm.util.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface CertificateService extends BaseService<CertificateRequestDto, CertificateResponseDto> {

    List<CertificateResponseDto> getAll(PageRequest pageRequest);

    List<CertificateResponseDto> getByMultipleTags(List<String> tags, PageRequest pageRequest);

    List<CertificateResponseDto> get(String searchWord, String tagName, PageRequest pageRequest);

    CertificateResponseDto update(CertificateRequestDto requestDto, UUID id);

}
