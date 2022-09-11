package esm.repository.tag;

import esm.domain.Tag;
import esm.exception.DataAlreadyExistException;
import esm.exception.DataNotFoundException;
import esm.exception.UnknownDataBaseException;
import esm.util.PageRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TagRepositoryImpl implements TagRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public Tag create(Tag tag) {
        Optional<Tag> optional = findByName(tag.getName());
        if (optional.isPresent()){
            throw new DataAlreadyExistException("Tag with name:("+tag.getName()+") already exist");
        }
        entityManager.persist(tag);
        if (tag.getId() != null) {
            return tag;
        }
        throw new UnknownDataBaseException("Tag Creatiaton Failed: Something wrong with database!");
    }

    @Override
    public List<Tag> getAll(PageRequest pageRequest) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> query = builder.createQuery(Tag.class);
        Root<Tag> root = query.from(Tag.class);
        query.select(root);

        List<Tag> tags = entityManager.createQuery(query)
                .setMaxResults(pageRequest.getSize())
                .setFirstResult((pageRequest.getPage() - 1) * pageRequest.getSize()) // to be checked
                .getResultList();
        if (tags.isEmpty()){
            throw new DataNotFoundException("no matching tags found");
        }
        return tags;
    }

    @Override
    public Tag getOne(UUID id) {
        Tag tag = entityManager.find(Tag.class, id);
        if (tag != null) {
            return tag;
        }
        throw new DataNotFoundException("Tag with id:(" + id + ") not found");
    }

    @Override
    public Optional<Tag> findById(UUID id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public Optional<Tag> findByName(String name) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> query = builder.createQuery(Tag.class);
        Root<Tag> root = query.from(Tag.class);
        query.select(root).where(builder.equal(root.get("name"), name));

        try {
            return Optional.of(entityManager.createQuery(query)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Transactional
    @Override
    public int delete(UUID id) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaDelete<Tag> queryDelete =
                builder.createCriteriaDelete(Tag.class);
        Root<Tag> root = queryDelete.from(Tag.class);
        queryDelete.where(builder.equal(root.get("id"), id));

        return entityManager
                .createQuery(queryDelete)
                .executeUpdate();
    }

    @Override
    public List<Tag> getMostUsedTag(UUID userId) {
         List<Tag> tags = entityManager.createNativeQuery(GET_MOST_CERTIFICATE_USED_TAG, Tag.class)
                .setParameter(1, userId)
                .getResultList();

        if (!tags.isEmpty()) {
            return tags;
        }
        throw new DataNotFoundException("Tags with user id = (" + userId + ") not found");
    }

}
