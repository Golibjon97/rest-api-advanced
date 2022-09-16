package com.epam.esm;

import com.epam.esm.service.certificate.CertificateServiceImpl;
import com.epam.esm.domain.Certificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.dto.request.CertificateRequestDto;
import com.epam.esm.dto.response.CertificateResponseDto;
import com.epam.esm.repository.certificate.CertificateRepository;
import com.epam.esm.repository.tag.TagRepository;
import com.epam.esm.util.PageRequest;
import com.epam.esm.validation.CertificateValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.internal.InheritingConfiguration;
import com.epam.esm.util.CertificateUtil;
import com.epam.esm.util.TagUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CertificateServiceImplTest {

    @Mock
    private CertificateRepository certificateRepository;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private CertificateValidator validator;
    @InjectMocks
    private CertificateServiceImpl certificateService;

    private Certificate certificate;
    private List<Certificate> certificateList;
    private CertificateRequestDto requestDto;
    private List<CertificateRequestDto> certificateRequestList;
    private CertificateResponseDto responseDto;
    private List<CertificateResponseDto> certificateResponseList;
    private Tag tag;
    private List<ValidationResult> validationResultEmpty;
    private PageRequest pageRequest;


    @BeforeEach
    void setUp() {
        certificate = CertificateUtil.getCertificate();
        certificateList = CertificateUtil.getCertificateList();
        requestDto = CertificateUtil.getCertificateRequestDto();
        certificateRequestList = CertificateUtil.getCertificateRequestList();
        responseDto = CertificateUtil.getCertificateResponseDto();
        certificateResponseList = CertificateUtil.getCertificiateResponseList();
        pageRequest = CertificateUtil.getPageRequest();
        tag = TagUtil.getTag();
        validationResultEmpty = CertificateUtil.getValidationResultEmpty();
    }

    @Test
    void canCreateNewCertificate() {
        when(modelMapper.map(requestDto,Certificate.class)).thenReturn(certificate);
        when(modelMapper.map(certificate,CertificateResponseDto.class)).thenReturn(responseDto);
        when(certificateRepository.create(certificate)).thenReturn(certificate);
        CertificateResponseDto result = certificateService.create(requestDto);
        assertEquals(result, responseDto);
    }

    @Test
    void canGet() {
        when(certificateRepository.getOne(certificate.getId())).thenReturn(certificate);
        when(modelMapper.map(certificate, CertificateResponseDto.class)).thenReturn(responseDto);
        CertificateResponseDto result = certificateService.get(certificate.getId());
        assertEquals(result, responseDto);
    }

    @Test
    void canGetByMultipleTags() {
        ArrayList<String> tags = new ArrayList();
        tags.add(tag.toString());

        when(tagRepository.findByName(String.valueOf(tag))).thenReturn(Optional.ofNullable(tag));
        when(certificateRepository.getByMultipleTags(pageRequest, tag))
                .thenReturn(certificateList);
        when(modelMapper.map(certificateList, new TypeToken<List<CertificateResponseDto>>() {
        }.getType())).thenReturn(certificateResponseList);
        List<CertificateResponseDto> result
                = certificateService.getByMultipleTags(tags, pageRequest);
        assertEquals(certificateResponseList, result);

    }

    @Test
    void canGetAll() {
        when(certificateRepository.getAll(pageRequest)).thenReturn(certificateList);
        when(modelMapper.map(certificateList, new TypeToken<List<CertificateResponseDto>>(){
        }.getType())).thenReturn(certificateResponseList);
        List<CertificateResponseDto> result = certificateService.getAll(pageRequest);
        assertEquals(certificateResponseList, result);
    }

    @Test
    void canGetBySearchWordOrTagName() {
        when(tagRepository.findByName(tag.getName())).thenReturn(Optional.ofNullable(tag));
        when(certificateRepository.getBy("Test", tag.getId(),pageRequest))
                .thenReturn(certificateList);
        when(modelMapper.map(certificateList, new TypeToken<List<CertificateResponseDto>>() {}.getType()))
                .thenReturn(certificateResponseList);
        List<CertificateResponseDto> result
                = certificateService.get("Test", tag.getName(),pageRequest);
        assertEquals(certificateResponseList, result);
    }
    @Test
    void canUpdate() {
        when(validator.checkUpdate(requestDto)).thenReturn(new ValidationResult(0));
        when(certificateRepository.getOne(certificate.getId())).thenReturn(certificate);
        when(modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());
        doNothing().when(modelMapper).map(requestDto, certificate);
        when(certificateRepository.update(certificate)).thenReturn(certificate);
        when(modelMapper.map(certificate, CertificateRequestDto.class)).thenReturn(requestDto);
        when(modelMapper.map(certificate, CertificateResponseDto.class)).thenReturn(responseDto);

        CertificateResponseDto result = certificateService.update(requestDto, certificate.getId());
        assertEquals(responseDto, result);
    }

    @Test
    void canDelete() {
        when(certificateRepository.delete(certificate.getId())).thenReturn(1);
        int result = certificateService.delete(certificate.getId());
        assertEquals(1, result);
    }

    @Test
    void canGetBySearchWordOrTagNameWithNull(){
        when(certificateRepository.getAll(pageRequest)).thenReturn(certificateList);
        when(modelMapper.map(certificateList, new TypeToken<List<CertificateResponseDto>>(){
        }.getType())).thenReturn(certificateResponseList);
        List<CertificateResponseDto> result = certificateService.get(
                null, null,pageRequest
        );
        assertEquals(certificateResponseList, result);
    }

}