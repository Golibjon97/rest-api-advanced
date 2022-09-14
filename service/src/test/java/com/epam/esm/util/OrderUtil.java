package com.epam.esm.util;

import com.epam.esm.domain.Order;
import com.epam.esm.dto.request.OrderRequestDto;
import com.epam.esm.dto.response.OrderResponseDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderUtil {

    private CertificateUtil certificateUtil;

    public static UUID uuid(String text) {
        return UUID.fromString("00000000-0000-" + text + "-0000-000000000000");
    }
    public static List<Order> getOrders(){
        List<Order> orders = new ArrayList<>();
        orders.add(getOrder());
        return orders;
    }

    public static Order getOrder(){
        return new Order(
                uuid("0007"),
                LocalDateTime.of(2022, Month.AUGUST, 01, 9, 20),
                LocalDateTime.now(),
                new BigDecimal("1.0"),
                CertificateUtil.getCertificate(),
                UserUtil.getUser()
        );
    }

    public static Order getOrderWithoutBaseData(){
        return new Order(
                CertificateUtil.getCertificate().getPrice(),
                CertificateUtil.getCertificate(),
                UserUtil.getUser()
        );
    }

    public static OrderRequestDto getOrderRequest(){
        return new OrderRequestDto(
               CertificateUtil.getCertificate().getId(),
               UserUtil.getUser().getId()
        );
    }

    public static OrderResponseDto getOrderResponse(){
        return new OrderResponseDto(
                uuid("0001"),
                new BigDecimal("1.0"),
                CertificateUtil.getCertificateResponseDto(),
                UserUtil.getUserResponse(),
                LocalDateTime.of(2022, Month.AUGUST, 01, 9, 20),
                LocalDateTime.now()
        );
    }

    public static List<OrderResponseDto> getListOrderResponse(){
        ArrayList<OrderResponseDto> orderResponseDtos = new ArrayList<>();
        orderResponseDtos.add(getOrderResponse());
        return orderResponseDtos;
    }
}
