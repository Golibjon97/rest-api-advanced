package esm.service.user;

import esm.domain.Order;
import esm.domain.User;
import esm.dto.response.OrderResponseDto;
import esm.dto.response.UserResponseDto;
import esm.exception.DataNotFoundException;
import esm.repository.order.OrderRepository;
import esm.repository.user.UserRepository;
import esm.util.PageRequest;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, OrderRepository orderRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserResponseDto get(UUID id) {
        User user = userRepository.getOne(id);
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    public List<UserResponseDto> getAll(PageRequest pageRequest) {
        List<User> users = userRepository.getAll(pageRequest);
        return modelMapper.map(users, new TypeToken<List<UserResponseDto>>() {
        }.getType());
    }

    @Override
    public List<OrderResponseDto> getOrders(UUID userId, PageRequest pageRequest) {
        //userRepository.getOne(userId);
        List<Order> orders = orderRepository.getByUserId(userId, pageRequest);
        if (orders.isEmpty()){
            throw new DataNotFoundException("no matching orders found");
        }
        
        return modelMapper.map(orders,new TypeToken<List<OrderResponseDto>>(){}.getType());
    }

}
