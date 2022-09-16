package com.epam.esm;

import com.epam.esm.dto.request.CertificateRequestDto;
import com.epam.esm.util.CertificateUtil;
import com.epam.esm.validation.CertificateValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CertificateValidatorTest {

    @InjectMocks
    private CertificateValidator certificateValidator;

    private CertificateRequestDto certificateRequestDto;
    private CertificateRequestDto certificateRequestDtoMinus;
    private CertificateRequestDto certificateRequestDtoNull;

    @BeforeEach
    void setUp() {
        certificateRequestDto = CertificateUtil.getCertificateRequestDto();
        certificateRequestDtoNull = CertificateUtil.getCertificateRequestDtoNull();
        certificateRequestDtoMinus = CertificateUtil.getCertificateRequestDtoMinus();
    }

    @Test
    void checkNotNull() {
        List<ValidationResult> validationResultList = certificateValidator.checkNotNull(certificateRequestDtoNull);
        assertNotNull(validationResultList);
    }

    @Test
    void checkUpdate() {
        ValidationResult validationResult = certificateValidator.checkUpdate(certificateRequestDto);
        assertEquals(new ValidationResult(0), validationResult);
    }

    @Test
    void validate() {
        List<ValidationResult> validationResultList = certificateValidator.validate(certificateRequestDtoNull);
        assertNotNull(validationResultList);
    }

    @Test
    void validateNegativePriceAndNegativeDuration(){
        List<ValidationResult> validationResultList = certificateValidator.validate(certificateRequestDtoMinus);
        assertNotNull(validationResultList);
    }
}