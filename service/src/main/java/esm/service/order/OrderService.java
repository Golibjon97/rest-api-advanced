package esm.service.order;

import esm.dto.request.OrderRequestDto;
import esm.dto.response.OrderResponseDto;
import esm.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public interface OrderService extends BaseService<OrderRequestDto, OrderResponseDto> {


}
