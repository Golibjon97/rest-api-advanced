package esm.repository.order;

import esm.domain.Order;
import esm.exception.DataNotFoundException;
import esm.exception.UnknownDataBaseException;
import esm.util.PageRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

import java.util.List;
import java.util.UUID;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public esm.domain.Order create(esm.domain.Order order) {
        entityManager.persist(order);
        if (order.getId() != null) {
            return order;
        }
        throw new UnknownDataBaseException("Order Creatiaton Failed: Something wrong with database!");
    }

    @Override
    public List<esm.domain.Order> getAll(PageRequest pageRequest) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> query = builder.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);
        query.select(root);

        List<Order> orders = entityManager.createQuery(query)
                .setMaxResults(pageRequest.getSize())
                .setFirstResult((pageRequest.getPage() - 1) * pageRequest.getSize())
                .getResultList();
        if (orders.isEmpty()){
            throw new DataNotFoundException("no matching orders found");
        }
        return orders;
    }

    @Override
    public esm.domain.Order getOne(UUID id) {
        Order order = entityManager.find(Order.class, id);
        if (order != null) {
            return (esm.domain.Order) order;
        }
        throw new DataNotFoundException("Order with id:(" + id + ") not found");
    }

    @Override
    public esm.domain.Order getTheMostExpensive() {
        //select * from orders where price = (select max(price) from orders)
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> query = cb.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);

        Subquery<Long> sub = query.subquery(Long.class);
        Root<Order> subRoot = sub.from(Order.class);
        sub.select(cb.max(subRoot.get("price")));

        query.where(cb.equal(sub, root.get("price")));

        TypedQuery<Order> typedQuery = entityManager.createQuery(query);
        List<Order> resultList = typedQuery.getResultList();
        if (!resultList.isEmpty()) {
            return (esm.domain.Order) resultList.get(0);
        }
        throw new DataNotFoundException("Not Found Any Order Records");
    }

    @Override
    public int delete(UUID id) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaDelete<Order> queryDelete =
                builder.createCriteriaDelete(Order.class);
        Root<Order> root = queryDelete.from(Order.class);
        queryDelete.where(builder.equal(root.get("id"), id));

        return entityManager
                .createQuery(queryDelete)
                .executeUpdate();
    }

    @Override
    public List<esm.domain.Order> getByUserId(UUID userId, PageRequest pageRequest) {

        return entityManager.createNativeQuery(GET_ORDERS_OF_USER, Order.class)
                .setParameter(1, userId)
                .getResultList();
    }


}
