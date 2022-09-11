package service.util;



import esm.domain.Certificate;
import esm.dto.request.CertificateRequestDto;
import esm.dto.response.CertificateResponseDto;
import esm.util.PageRequest;
import esm.util.Sort;
import esm.util.enums.SortField;
import esm.util.enums.SortType;

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

    public static List<Certificate> getGiftCertificates() {
        Certificate certificateEntity = new Certificate();
        certificateEntity.setId(uuid("0001"));
        certificateEntity.setName("Store");

        Certificate certificateEntity1 = new Certificate();
        certificateEntity1.setId(uuid("0002"));
        certificateEntity1.setName("Kids Clothes");
        certificateEntity1.setTags(TagUtil.getTags());

        Certificate certificateEntity2 = new Certificate();
        certificateEntity2.setId(uuid("0003"));
        certificateEntity2.setName("Men Clothes");

        return Arrays.asList(
                certificateEntity, certificateEntity1, certificateEntity2);
    }

    public static List<CertificateResponseDto> getCertificateResponseDtos() {
        CertificateResponseDto certificateGetResponse = new CertificateResponseDto();
        certificateGetResponse.setId(uuid("0001"));
        certificateGetResponse.setName("Store");

        CertificateResponseDto certificateGetResponse1 = new CertificateResponseDto();
        certificateGetResponse1.setId(uuid("0002"));
        certificateGetResponse1.setName("Kids Clothes");
        //certificateGetResponse1.setTags(TagUtil.getTags());

        CertificateResponseDto certificateGetResponse2 = new CertificateResponseDto();
        certificateGetResponse2.setId(uuid("0003"));
        certificateGetResponse2.setName("Men Clothes");
        return Arrays.asList(
                certificateGetResponse, certificateGetResponse1, certificateGetResponse2);
    }
}
