package com.epam.esm.validation;

import com.epam.esm.ValidationResult;
import com.epam.esm.dto.request.CertificateRequestDto;
import com.epam.esm.validation.constraints.NotNullable;
import com.epam.esm.validation.constraints.Updatable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.startsWith;

@Service
public class CertificateValidator extends BaseValidator<CertificateRequestDto>
        implements Updatable<CertificateRequestDto>, NotNullable<CertificateRequestDto> {

    @Override
    public List<ValidationResult> checkNotNull(CertificateRequestDto requestDto) {
        List<ValidationResult> validationResultList = validate(requestDto);

        StringBuilder exceptionMessage = new StringBuilder();
        boolean isValid = true;
        if (requestDto.getName() == null || requestDto.getName() == "") {
            exceptionMessage.append("name");
            isValid = false;
        }
        if (requestDto.getPrice() == null || requestDto.getPrice() == "") {
            exceptionMessage.append("price");
            isValid = false;
        }
        if (requestDto.getDuration() == null || requestDto.getDuration() == "") {
            exceptionMessage.append("duration");
            isValid = false;
        }
        if (!isValid) {
            int l = exceptionMessage.length();
            exceptionMessage.delete(l - 2, l);
            exceptionMessage.append(" cannot be null");
            validationResultList.add(new ValidationResult(5, exceptionMessage.toString()));
        }

        return validationResultList;
    }

    @Override
    public ValidationResult checkUpdate(CertificateRequestDto requestDto) {
        boolean isUpdated = requestDto.getName() != null ||
                requestDto.getDescription() != null ||
                requestDto.getPrice() != null ||
                requestDto.getDuration() != null ||
                requestDto.getTags() != null;
        if (!isUpdated) {
            return new ValidationResult(6, "nothing to update");
        }
        return new ValidationResult(0);
    }

    @Override
    public List<ValidationResult> validate(CertificateRequestDto certificateRequestDto) {
        List<ValidationResult> validationResultsList = new ArrayList<>();

        try {
            Integer.valueOf(certificateRequestDto.getDuration());
        } catch (NumberFormatException e) {
            validationResultsList.add(new ValidationResult(3, "duration should be only numeric"));
        }
        if (startsWith(certificateRequestDto.getDuration(), "-")) {
            validationResultsList.add(new ValidationResult(4, "duration should be greater than 0"));
        }

        try {
            new BigDecimal(certificateRequestDto.getPrice());
        } catch (NumberFormatException e) {
            validationResultsList.add(new ValidationResult(1, "price should be only numeric"));
        }
        if (certificateRequestDto.getPrice().startsWith("-")) {
            validationResultsList.add(new ValidationResult(2, "price should not be less than 0"));
        }
        return validationResultsList;
    }


}