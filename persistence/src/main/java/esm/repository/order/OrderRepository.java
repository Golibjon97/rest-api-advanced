package esm.repository.order;

import esm.domain.Order;
import esm.repository.BaseRepository;
import esm.util.PageRequest;

import java.util.List;
import java.util.UUID;


public interface OrderRepository extends BaseRepository<Order> {

    String GET_ORDERS_OF_USER = "select * from orders t where t.user_id = ?1";
    Order getTheMostExpensive();

    int delete(UUID id);

    List<Order> getByUserId(UUID userId, PageRequest pageRequest);
}
