package com.epam.esm;


import com.epam.esm.domain.Order;
import com.epam.esm.domain.User;
import com.epam.esm.dto.response.OrderResponseDto;
import com.epam.esm.dto.response.UserResponseDto;
import com.epam.esm.repository.order.OrderRepository;
import com.epam.esm.repository.user.UserRepository;
import com.epam.esm.user.UserServiceImpl;
import com.epam.esm.util.PageRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private List<Order> orderList;
    private List<OrderResponseDto> orderResponseDtoList;
    private List<User> userList;
    private UserResponseDto userResponse;
    private List<UserResponseDto> userResponseList;
    private PageRequest pageRequest;

    @BeforeEach
    void setUp() {
        user = UserUtil.getUser();
        userList = UserUtil.getUserList();
        userResponse = UserUtil.getUserResponse();
        pageRequest = CertificateUtil.getPageRequest();
        userResponseList = UserUtil.getUserResponseList();
        orderList = OrderUtil.getOrders();
        orderResponseDtoList = OrderUtil.getListOrderResponse();
    }

    @Test
    void get() {
        when(userRepository.getOne(user.getId())).thenReturn(user);
        when(modelMapper.map(user, UserResponseDto.class)).thenReturn(userResponse);
        UserResponseDto result = userService.get(user.getId());
        assertEquals(userResponse, result);
    }

    @Test
    void getAll() {
        when(userRepository.getAll(pageRequest)).thenReturn(userList);
        when(modelMapper.map(userList, new TypeToken<List<UserResponseDto>>() {}.getType()))
                .thenReturn(userResponseList);
        List<UserResponseDto> result = userService.getAll(pageRequest);
        assertEquals(userResponseList, result);
    }

    @Test
    void getOrders() {
        when(orderRepository.getByUserId(user.getId(), pageRequest)).thenReturn(orderList);
        when(modelMapper.map(orderList, new TypeToken<List<OrderResponseDto>>(){}.getType()))
                .thenReturn(orderResponseDtoList);
        List<OrderResponseDto> result = userService.getOrders(user.getId(), pageRequest);
        assertEquals(orderResponseDtoList, result);
    }

}