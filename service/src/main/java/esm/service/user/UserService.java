package esm.service.user;

import esm.dto.response.OrderResponseDto;
import esm.dto.response.UserResponseDto;
import esm.util.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface UserService {

    UserResponseDto get(UUID id);

    List<UserResponseDto> getAll(PageRequest pageRequest);

    List<OrderResponseDto> getOrders(UUID userId, PageRequest pageRequest);
}
