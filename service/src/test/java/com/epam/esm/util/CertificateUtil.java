package com.epam.esm.util;



import com.epam.esm.ValidationResult;
import com.epam.esm.domain.Certificate;
import com.epam.esm.dto.request.CertificateRequestDto;
import com.epam.esm.dto.response.CertificateResponseDto;
import com.epam.esm.util.enums.SortField;
import com.epam.esm.util.enums.SortType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class CertificateUtil {

    public static UUID uuid(String text) {
        return UUID.fromString("00000000-0000-" + text + "-0000-000000000000");
    }


    public static Certificate getCertificate() {
        return new Certificate(
                uuid("0001"),
                LocalDateTime.of(2022, Month.AUGUST, 01, 9, 20),
                LocalDateTime.now(),
                "Test",
                "Test description",
                new BigDecimal("1.0"),
                1,
                TagUtil.getTags()
                );
    }

    public static List<Certificate> getCertificateList(){
        List<Certificate> list = new ArrayList<>();
        list.add(getCertificate());
        return list;
    }

    public static CertificateResponseDto getCertificateResponseDto() {
        return new CertificateResponseDto(
                uuid("0001"),
                "Data",
                "this is for test",
                new BigDecimal(1),
                1,
                LocalDateTime.now(),
                LocalDateTime.now(),
                new ArrayList<>()
        );
    }

    public static List<CertificateResponseDto> getCertificiateResponseList(){
        List<CertificateResponseDto> list = new ArrayList<>();
        list.add(getCertificateResponseDto());
        return list;
    }

    public static CertificateRequestDto getCertificateRequestDto() {
        return new CertificateRequestDto(
                "store",
                "this for test",
                "2",
                "1",
                TagUtil.getTags()
        );
    }
    public static CertificateRequestDto getCertificateRequestDtoNull() {
        return new CertificateRequestDto(
                "",
                "",
                "",
                "",
                TagUtil.getTags()
        );
    }

    public static CertificateRequestDto getCertificateRequestDtoMinus() {
        return new CertificateRequestDto(
                null,
                null,
                "-1",
                "-1",
                null
        );
    }

    public static List<CertificateRequestDto> getCertificateRequestList(){
        List<CertificateRequestDto> list = new ArrayList<>();
        list.add(getCertificateRequestDto());
        return list;
    }

    public static PageRequest getPageRequest(){
        return new PageRequest(
          1,
          20,
            getSort()
        );
    }

    public static Sort getSort(){
        return new Sort(
                getSortField(),
                getSortType()
        );
    }

    public static List<SortField> getSortField(){
        ArrayList list = new ArrayList();
        list.add(SortField.NOT_SORT);
        return list;
    }

    public static SortType getSortType(){
        return SortType.ASC;
    }

    public static List<ValidationResult> getValidationResultEmpty(){
        List<ValidationResult> validationResults = new ArrayList<>();
        validationResults.add(new ValidationResult());
        return validationResults;
    }
}
