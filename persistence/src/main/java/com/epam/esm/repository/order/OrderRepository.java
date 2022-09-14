package com.epam.esm.repository.order;

import com.epam.esm.domain.Order;
import com.epam.esm.repository.BaseRepository;
import com.epam.esm.util.PageRequest;

import java.util.List;
import java.util.UUID;


public interface OrderRepository extends BaseRepository<Order> {

    String GET_ORDERS_OF_USER = "select t.id, t.created_date, " +
            "                           t.updated_date, t.price, " +
            "                           t.certificate_id, t.user_id " +
            "                   from orders t where t.user_id = ?1";
    Order getTheMostExpensive();

    int delete(UUID id);

    List<Order> getByUserId(UUID userId, PageRequest pageRequest);
}
