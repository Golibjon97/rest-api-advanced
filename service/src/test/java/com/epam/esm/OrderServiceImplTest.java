package com.epam.esm;

import com.epam.esm.domain.Certificate;
import com.epam.esm.domain.Order;
import com.epam.esm.domain.User;
import com.epam.esm.dto.request.OrderRequestDto;
import com.epam.esm.dto.response.OrderResponseDto;
import com.epam.esm.service.order.OrderServiceImpl;
import com.epam.esm.repository.order.OrderRepository;
import com.epam.esm.util.PageRequest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import com.epam.esm.util.CertificateUtil;
import com.epam.esm.util.OrderUtil;
import com.epam.esm.util.UserUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private ModelMapper modelMapper;
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order order;
    private Order getOrderWithoutBaseData;
    private List<Order> orders;
    private OrderRequestDto orderRequestDto;
    private OrderResponseDto orderResponseDto;
    private List<OrderResponseDto> orderResponseDtoList;
    private Certificate certificate;
    private User user;
    private PageRequest pageRequest;

    @BeforeEach
    void setUp() {
        order = OrderUtil.getOrder();
        orders = OrderUtil.getOrders();
        getOrderWithoutBaseData = OrderUtil.getOrderWithoutBaseData();
        orderRequestDto = OrderUtil.getOrderRequest();
        orderResponseDto = OrderUtil.getOrderResponse();
        orderResponseDtoList = OrderUtil.getListOrderResponse();
        certificate = CertificateUtil.getCertificate();
        user = UserUtil.getUser();
        pageRequest = CertificateUtil.getPageRequest();
    }

    @Test
    void canGet() {
        when(orderRepository.getOne(order.getId())).thenReturn(order);
        when(modelMapper.map(order, OrderResponseDto.class)).thenReturn(orderResponseDto);
        OrderResponseDto result = orderService.get(order.getId());
        assertEquals(orderResponseDto, result);
    }

    @Test
    void canGetAll() {
        when(orderRepository.getAll(pageRequest)).thenReturn(orders);
        when(modelMapper.map(orders, new TypeToken<List<OrderResponseDto>>() {}.getType()))
                .thenReturn(orderResponseDtoList);
        List<OrderResponseDto> orderResponseDtos = orderService.getAll(pageRequest);
        assertEquals(orderResponseDtoList, orderResponseDtos);
    }

    @Test
    void canDelete() {
        when(orderRepository.delete(order.getId())).thenReturn(1);
        int result = orderService.delete(order.getId());
        assertEquals(1, result);
    }
}