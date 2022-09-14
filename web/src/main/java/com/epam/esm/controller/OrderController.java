package com.epam.esm.controller;

import com.epam.esm.dto.BaseResponse;
import com.epam.esm.dto.request.OrderRequestDto;
import com.epam.esm.dto.response.OrderResponseDto;
import com.epam.esm.exception.InvalidInputException;
import com.epam.esm.service.order.OrderService;
import com.epam.esm.util.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequestMapping("/api/orders")
@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Creates an Order with existing user id and certificate id
     * @param orderPostRequest Request DTO typed order object with validations for not null check
     * @param bindingResult General interface that represents binding results. Used to check for the validations
     * @return return the response entity type order response dto object
     */
    @PostMapping()
    public ResponseEntity<?> create(
            @Valid @RequestBody OrderRequestDto orderPostRequest,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException(bindingResult);
        }
        OrderResponseDto response = orderService.create(orderPostRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Gets the order object by given UUID
     * @param orderId UUID of order to search for the object
     * @return returns response entity typed order response dto object
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") UUID orderId) {
        OrderResponseDto responseDto = orderService.get(orderId);
        return ResponseEntity.ok(responseDto);
    }

    /**
     * Returns all the orders available
     * @param page page number to start the result from
     * @param size number of order objects in each page
     * @return returns all available orders
     */
    @GetMapping()
    public BaseResponse<List<OrderResponseDto>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        List<OrderResponseDto> orders = orderService.getAll(new PageRequest(page, size));
        BaseResponse<List<OrderResponseDto>> response
                = new BaseResponse<>(HttpStatus.OK.value(), "order list", orders);
        if (!orders.isEmpty())
            response.add(linkTo(methodOn(OrderController.class)
                    .getAll(page + 1, size))
                    .withRel("next page"));

        if (page > 1) {
            response.add(linkTo(methodOn(OrderController.class)
                    .getAll(page - 1, size))
                    .withRel("previous page"));
        }
        return response;
    }

    /**
     * Deletes the order as per the given UUID
     * @param orderId UUID to find the order to be deleted
     * @return Returns response entity status message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") UUID orderId) {
        int delete = orderService.delete(orderId);
        if (delete == 1) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
