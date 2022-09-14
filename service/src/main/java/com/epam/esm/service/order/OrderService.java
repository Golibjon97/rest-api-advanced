package com.epam.esm.service.order;

import com.epam.esm.dto.request.OrderRequestDto;
import com.epam.esm.dto.response.OrderResponseDto;
import com.epam.esm.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public interface OrderService extends BaseService<OrderRequestDto, OrderResponseDto> {


}
