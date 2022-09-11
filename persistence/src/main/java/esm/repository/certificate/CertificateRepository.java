package esm.repository.certificate;

import esm.domain.Certificate;
import esm.domain.Tag;
import esm.repository.BaseRepository;
import esm.util.PageRequest;
import esm.util.Sort;
import esm.util.enums.SortField;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static esm.util.enums.SortType.ASC;
import static esm.util.enums.SortType.DESC;

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
