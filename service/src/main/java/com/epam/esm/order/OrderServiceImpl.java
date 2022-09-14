package com.epam.esm.order;

import com.epam.esm.domain.Certificate;
import com.epam.esm.domain.Order;
import com.epam.esm.dto.request.OrderRequestDto;
import com.epam.esm.dto.response.OrderResponseDto;
import com.epam.esm.repository.certificate.CertificateRepository;
import com.epam.esm.repository.order.OrderRepository;
import com.epam.esm.repository.user.UserRepository;
import com.epam.esm.util.PageRequest;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CertificateRepository certificateRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public OrderServiceImpl(OrderRepository orderRepository, CertificateRepository certificateRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.certificateRepository = certificateRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    @Transactional
    @Override
    public OrderResponseDto create(OrderRequestDto orderRequestDto) {
        Certificate certificate = certificateRepository
                .getOne(orderRequestDto.getCertificateId());
        Order newOrder = new Order(
                certificate.getPrice(),
                certificate,
                userRepository.getOne(orderRequestDto.getUserId()));

        Order order = orderRepository.create(newOrder);
        return modelMapper.map(order, OrderResponseDto.class);
    }

    @Override
    public OrderResponseDto get(UUID id) {
        Order order = orderRepository.getOne(id);
        return modelMapper.map(order, OrderResponseDto.class);
    }

    @Override
    public List<OrderResponseDto> getAll(PageRequest pageRequest) {
        List<Order> orders = orderRepository.getAll(pageRequest);
        return modelMapper.map(orders, new TypeToken<List<OrderResponseDto>>() {
        }.getType());
    }

    @Transactional
    @Override
    public int delete(UUID id) {
        return orderRepository.delete(id);
    }
}
