package com.epam.esm.validation;

import com.epam.esm.dto.request.CertificateRequestDto;
import com.epam.esm.exception.InvalidCertificateException;
import com.epam.esm.validation.constraints.NotNullable;
import com.epam.esm.validation.constraints.Updatable;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static org.apache.commons.lang3.StringUtils.startsWith;

@Service
public class CertificateValidator extends BaseValidator<CertificateRequestDto>
        implements Updatable<CertificateRequestDto>, NotNullable<CertificateRequestDto> {

    @Override
    public void checkNotNull(CertificateRequestDto requestDto) {
        StringBuilder exceptionMessage = new StringBuilder();
        boolean isValid = true;
        if (requestDto.getName() == null) {
            exceptionMessage.append("name");
            isValid = false;
        }
        if (requestDto.getPrice() == null) {
            exceptionMessage.append("price");
            isValid = false;
        }

        if (requestDto.getDuration() == null) {
            exceptionMessage.append("duration");
            isValid = false;
        }
        if (!isValid) {
            int l = exceptionMessage.length();
            exceptionMessage.delete(l - 2, l);
            exceptionMessage.append(" cannot be null");
            throw new InvalidCertificateException(exceptionMessage.toString());
        }

        validate(requestDto);
    }

    @Override
    public void checkUpdate(CertificateRequestDto requestDto) {
        boolean isUpdated = requestDto.getName() != null ||
                requestDto.getDescription() != null ||
                requestDto.getPrice() != null ||
                requestDto.getDuration() != null ||
                requestDto.getTags() != null;
        if (!isUpdated) {
            throw new InvalidCertificateException("nothing to update");
        }
    }

    @Override
    public void validate(CertificateRequestDto requestDto) {
        StringBuilder exceptionMessage = new StringBuilder();
        boolean isValid = true;
        Boolean validatePrice = validatePrice(requestDto.getPrice());
        Boolean validateDuration = validateDuration(requestDto.getDuration());

        if (!validatePrice) {
            exceptionMessage.append("price should not be less than 0");
            isValid = false;
        }
        if (!isValid) {
            exceptionMessage.append(", ");
        }

        if (!validateDuration) {
            exceptionMessage.append("duration should be greater than 0");
            isValid = false;
        }

        if (!isValid) {
            throw new InvalidCertificateException(exceptionMessage.toString());
        }
    }

    private Boolean validateDuration(String duration) {
        try {
            Integer.valueOf(duration);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("duration should be only numeric");
        }
        return (!startsWith(duration, "-"));
    }

    private Boolean validatePrice(String price) {
        try {
            new BigDecimal(price);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("price should be only numeric");
        }
        return (!price.startsWith("-"));
    }

}
