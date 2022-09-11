package service.util;

import esm.domain.Order;
import esm.domain.User;
import esm.dto.response.UserResponseDto;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserUtil {

    private List<Order> orders;
    public static UUID uuid(String text) {
        return UUID.fromString("00000000-0000-" + text + "-0000-000000000000");
    }

    public static User getUser(){
        return new User(
                uuid("0001"),
                LocalDateTime.of(2022, Month.AUGUST, 01, 9, 20),
                LocalDateTime.now(),
                "test name",
                "test password",
                false,
                null
        );
    }

    public static List<User> getUserList(){
        List<User> list = new ArrayList<>();
        list.add(getUser());
        return list;
    }

    public static UserResponseDto getUserResponse(){
        return new UserResponseDto(
                uuid("0001"),
                "test name",
                "test username",
                false,
                LocalDateTime.of(2022, Month.AUGUST, 01, 9, 20),
                LocalDateTime.now()
        );
    }

    public static List<UserResponseDto> getUserResponseList(){
        List<UserResponseDto> list = new ArrayList<>();
        list.add(getUserResponse());
        return list;
    }

}

