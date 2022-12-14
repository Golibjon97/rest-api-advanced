package com.epam.esm.repository.order;

import com.epam.esm.domain.Order;
import com.epam.esm.exception.UnknownDataBaseException;
import com.epam.esm.util.PageRequest;
import com.epam.esm.exception.DataNotFoundException;
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
    public Order create(Order order) {
        entityManager.persist(order);
        if (order.getId() != null) {
            return order;
        }
        throw new UnknownDataBaseException("Order creation failed: Something wrong with database!");
    }

    @Override
    public List<Order> getAll(PageRequest pageRequest) {
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
    public Order getOne(UUID id) {
        Order order = entityManager.find(Order.class, id);
        if (order != null) {
            return order;
        }
        throw new DataNotFoundException("Order with id:(" + id + ") not found");
    }

    @Override
    public Order getTheMostExpensive() {
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
            return resultList.get(0);
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
    public List<Order> getByUserId(UUID userId, PageRequest pageRequest) {

        return entityManager.createNativeQuery(GET_ORDERS_OF_USER, Order.class)
                .setParameter(1, userId)
                .getResultList();
    }


}
