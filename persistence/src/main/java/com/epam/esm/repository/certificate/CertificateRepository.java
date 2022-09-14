package com.epam.esm.repository.certificate;

import com.epam.esm.domain.Certificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.repository.BaseRepository;
import com.epam.esm.util.PageRequest;
import com.epam.esm.util.Sort;
import com.epam.esm.util.enums.SortField;
import com.epam.esm.util.enums.SortType;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CertificateRepository extends BaseRepository<Certificate> {

    Optional<Certificate> findById(UUID id);
    Certificate update(Certificate newCertificate);

    int delete(UUID id);

    List<Certificate> getByMultipleTags(PageRequest pageRequest, Tag... tags);

    List<Certificate> getBy(String searchWord, UUID tagId, PageRequest pageRequest);

    Optional<Certificate> findByName(String name);

    default List<javax.persistence.criteria.Order> sort(CriteriaBuilder builder,
                                                        Root<Certificate> root, Sort sort) {
        List<javax.persistence.criteria.Order> orders = new ArrayList<>();
        switch (sort.getSortType()) {
            case ASC: {
                for (SortField field : sort.getFields()) {
                    orders.add(builder.asc(root.get(field.name)));
                }
            }
            break;
            case DESC: {
                for (SortField field : sort.getFields()) {
                    orders.add(builder.desc(root.get(field.name)));
                }
            }
            break;
        }
        return orders;
    }

}
