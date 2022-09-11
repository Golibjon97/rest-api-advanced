package esm.validation;

import esm.dto.request.CertificateRequestDto;
import esm.exception.InvalidCertificateException;
import esm.validation.constraints.NotNullable;
import esm.validation.constraints.Updatable;
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
        String validatePrice = validatePrice(requestDto.getPrice());
        String validateDuration = validateDuration(requestDto.getDuration());

        if (!validatePrice.isEmpty()) {
            exceptionMessage.append(validatePrice);
            isValid = false;
        }
        if (!isValid) {
            exceptionMessage.append(", ");
        }

        if (!validateDuration.isEmpty()) {
            exceptionMessage.append(validateDuration);
            isValid = false;
        }

        if (!isValid) {
            throw new InvalidCertificateException(exceptionMessage.toString());
        }
    }

    private String validateDuration(String duration) {
        try {
            Integer.valueOf(duration);
        } catch (NumberFormatException e) {
            return "duration should be only numeric";
        }
        if (startsWith(duration, "-")) {
            return "duration should be greater than 0";
        }
        return "";
    }

    private String validatePrice(String price) {
        boolean isValid = true;
        try {
            new BigDecimal(price);
        } catch (NumberFormatException e) {
            return "price should be only numeric";
        }
        if (isValid && price.startsWith("-")) {
            return "price should not be less than 0";
        }
        return "";
    }

}
