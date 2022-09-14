package com.epam.esm;

import com.epam.esm.dto.request.CertificateRequestDto;
import com.epam.esm.exception.InvalidCertificateException;
import com.epam.esm.util.CertificateUtil;
import com.epam.esm.validation.CertificateValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CertificateValidatorTest {

    @InjectMocks
    private CertificateValidator certificateValidator;

    private CertificateRequestDto certificateRequestDtoNull;
    private CertificateRequestDto certificateRequestDtoMinus;

    @BeforeEach
    void setUp() {
          certificateRequestDtoNull = CertificateUtil.getCertificateRequestDtoNull();
          certificateRequestDtoMinus = CertificateUtil.getCertificateRequestDtoMinus();
    }

    @Test
    void checkNotNull() {
        Assertions.assertThrows(
                InvalidCertificateException.class, () ->{
                    certificateValidator.checkNotNull(certificateRequestDtoNull);
                }
        );
    }

    @Test
    void checkUpdate() {
        Assertions.assertThrows(
                InvalidCertificateException.class, () ->{
                    certificateValidator.checkUpdate(certificateRequestDtoNull);
                }
        );
    }

    @Test
    void validate() {
        Assertions.assertThrows(
                InvalidCertificateException.class, () ->{
                    certificateValidator.validate(certificateRequestDtoMinus);
                }
        );
    }
}