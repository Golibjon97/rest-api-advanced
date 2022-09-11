package esm.controller;

import esm.dto.BaseResponse;
import esm.dto.response.OrderResponseDto;
import esm.dto.response.UserResponseDto;
import esm.service.order.OrderService;
import esm.service.user.UserService;
import esm.util.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequestMapping("/api/users")
@RestController
public class UserController {

    private final UserService userService;
    private final OrderService orderService;

    public UserController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    /**
     * Return the user as per the given UUID
     * @param userId UUID to find the user
     * @return return response entity typed user response dto object
     */
    @GetMapping("/{id}")
    public ResponseEntity getOrders(@PathVariable("id") UUID userId) {
        UserResponseDto responseDto = userService.get(userId);
        return ResponseEntity.ok(responseDto);
    }

    /**
     * Returns all available User objects
     * @param page page number to start the result from
     * @param size number of user objects in each page
     * @return returns base response typed list of users with links for next and previous pages
     */
    @GetMapping()
    public BaseResponse<List<UserResponseDto>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        List<UserResponseDto> users = userService.getAll(new PageRequest(page, size));
        BaseResponse<List<UserResponseDto>> response
                = new BaseResponse<>(HttpStatus.OK.value(), "User list", users);
        if (!users.isEmpty())
            response.add(linkTo(methodOn(UserController.class)
                    .getAll(page + 1, size))
                    .withRel("next page"));

        if (page > 1) {
            response.add(linkTo(methodOn(UserController.class)
                    .getAll(page - 1, size))
                    .withRel("previous page"));
        }
        return response;
    }

    /**
     * Returns all orders of specified user by the UUID
     * @param userId UUID to find the user
     * @param page page number to start the result from
     * @param size number of order objects in each page
     * @return return all orders of the specified user with links for next and previous pages
     */
    @GetMapping("/orders/{id}")
    public BaseResponse<List<OrderResponseDto>> getOrders(
            @PathVariable("id") UUID userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        List<OrderResponseDto> orders = userService.getOrders(userId, new PageRequest(page, size));
        BaseResponse<List<OrderResponseDto>> response
                = new BaseResponse<>(HttpStatus.OK.value(), "Order list", orders);
        if (!orders.isEmpty())
            response.add(linkTo(methodOn(UserController.class)
                    .getOrders(userId,page + 1, size))
                    .withRel("next page"));

        if (page > 1) {
            response.add(linkTo(methodOn(UserController.class)
                    .getOrders(userId,page - 1, size))
                    .withRel("previous page"));
        }
        return response;
    }
}
