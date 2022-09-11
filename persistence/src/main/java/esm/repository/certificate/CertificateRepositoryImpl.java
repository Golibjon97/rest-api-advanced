package esm.repository.certificate;


import esm.domain.Certificate;
import esm.domain.Tag;
import esm.exception.DataAlreadyExistException;
import esm.exception.DataNotFoundException;
import esm.exception.UnknownDataBaseException;
import esm.util.PageRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CertificateRepositoryImpl implements CertificateRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    @Override
    public Certificate create(Certificate certificate) {
        Optional<Certificate> optional = findByName(certificate.getName());
        if (optional.isPresent()){
        throw new DataAlreadyExistException("Certificate with name:("+certificate.getName()+") already exsist");
        }
        entityManager.persist(certificate);
        if (certificate.getId() != null) {
            return certificate;
        }
        throw new UnknownDataBaseException("Gift Certificate Creatiaton Failed: Something wrong with database!");
    }

    @Override
    public List<Certificate> getAll(PageRequest pageRequest) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Certificate> query = builder.createQuery(Certificate.class);
        Root<Certificate> root = query.from(Certificate.class);
        query.select(root);

        if (pageRequest.getSort() != null) {
            query.orderBy(sort(builder, root, pageRequest.getSort()));
        }
        List<Certificate> certificates = entityManager.createQuery(query)
                .setMaxResults(pageRequest.getSize())
                .setFirstResult((pageRequest.getPage() - 1) * pageRequest.getSize())
                .getResultList();
        if (certificates.isEmpty()){
            throw new DataNotFoundException("no matching gift certificate found");
        }
        return certificates;
    }

    @Override
    public Certificate getOne(UUID id) {
        Certificate certificate = entityManager.find(Certificate.class, id);
        if (certificate != null) {
            return certificate;
        }
        throw new DataNotFoundException("Gift Certificate with id:(" + id + ") not found");
    }

    @Override
    public Optional<Certificate> findById(UUID id) {
        return Optional.ofNullable(entityManager.find(Certificate.class, id));
    }

    @Transactional
    @Override
    public Certificate update(Certificate updatedCertificate) {
        return entityManager.merge(updatedCertificate);
    }

    @Override
    public int delete(UUID id) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaDelete<Certificate> queryDelete =
                builder.createCriteriaDelete(Certificate.class);
        Root<Certificate> root = queryDelete.from(Certificate.class);
        queryDelete.where(builder.equal(root.get("id"), id));

        return entityManager
                .createQuery(queryDelete)
                .executeUpdate();
    }

    @Override
    public List<Certificate> getByMultipleTags(PageRequest pageRequest, Tag... tags) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Certificate> query = builder.createQuery(Certificate.class);
        Root<Certificate> giftRoot = query.from(Certificate.class);
        Join<Certificate, Tag> tagJoin = giftRoot.join("tags");

        CriteriaBuilder.In<String> inTagName = builder.in(tagJoin.get("name"));
        for (Tag tag : tags) {
            inTagName.value(tag.getName());
        }
        query.select(giftRoot).where(inTagName);
        query.groupBy(giftRoot.get("id"));
        query.having(builder.equal(builder.count(giftRoot), tags.length));

        List<Certificate> certificates = entityManager.createQuery(query)
                .setMaxResults(pageRequest.getSize())
                .setFirstResult((pageRequest.getPage() - 1) * pageRequest.getSize())
                .getResultList();
        if (certificates.isEmpty()){
            throw new DataNotFoundException("no matching gift certificate found");
        }
        return certificates;
    }

    @Override
    public List<Certificate> getBy(String searchWord, UUID tagId, PageRequest pageRequest) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Certificate> cq = builder.createQuery(Certificate.class);
        Root<Certificate> root = cq.from(Certificate.class);
        Join<Certificate, Tag> tags = root.join("tags");

        List<Predicate> filterList = new ArrayList<>();
        if (tagId != null) {
            filterList.add(builder.and(builder.equal(tags.get("id"), tagId)));
        }
        if (searchWord != null) {
            filterList.add(builder.like(builder.lower(root.get("name")), "%" + searchWord.toLowerCase() + "%"));
        }

        if (!filterList.isEmpty()) {
            cq.where(filterList.toArray(new Predicate[0])).distinct(true);
        }

        if (pageRequest.getSort() != null) {
            cq.orderBy(sort(builder, root, pageRequest.getSort())).distinct(true);
        }

        List<Certificate> certificates = entityManager.createQuery(cq)
                .setMaxResults(pageRequest.getSize())
                .setFirstResult((pageRequest.getPage() - 1) * pageRequest.getSize())
                .getResultList();

        if (certificates.isEmpty()){
            throw new DataNotFoundException("no matching gift certificate found");
        }
        return certificates;
    }

    @Override
    public Optional<Certificate> findByName(String name) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Certificate> query = builder.createQuery(Certificate.class);
        Root<Certificate> root = query.from(Certificate.class);
        query.select(root).where(builder.equal(root.get("name"), name));

        try {
            return Optional.of(entityManager.createQuery(query)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

}
