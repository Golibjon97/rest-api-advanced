package esm.repository.user;

import esm.domain.User;
import esm.exception.DataNotFoundException;
import esm.exception.UnknownDataBaseException;
import esm.util.PageRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.UUID;


@Repository
public class UserRepositoryImpl implements UserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User create(User userEntity) {
        entityManager.persist(userEntity);
        if (userEntity.getId() != null)
            return userEntity;
        throw new UnknownDataBaseException("User Creatiaton Failed: Something wrong with database!");
    }

    @Override
    public List<User> getAll(PageRequest pageRequest) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root);

        List<User> users = entityManager.createQuery(query)
                .setMaxResults(pageRequest.getSize())
                .setFirstResult((pageRequest.getPage() - 1) * pageRequest.getSize())
                .getResultList();
        if (users.isEmpty()){
            throw new DataNotFoundException("no matching users found");
        }
        return users;
    }

    @Override
    public User getOne(UUID id) {
        User user = entityManager.find(User.class, id);
        if (user != null)
            return user;
        throw new DataNotFoundException("User with id:("+id+") not found");
    }
}
