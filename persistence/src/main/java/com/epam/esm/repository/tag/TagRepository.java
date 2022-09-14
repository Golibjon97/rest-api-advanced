package com.epam.esm.repository.tag;

import com.epam.esm.domain.Tag;
import com.epam.esm.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TagRepository extends BaseRepository<Tag> {

    String GET_MOST_CERTIFICATE_USED_TAG = "select t.id,t.name,t.updated_date,t.created_date\n" +
            "            from tags t\n" +
            "                 inner join certificates_tags ct on t.id = ct.tags_id\n" +
            "                 inner join certificates c on c.id = ct.certificate_id\n" +
            "                 inner join orders o on c.id = o.certificate_id\n" +
            "                 inner join users u2 on o.user_id = u2.id\n" +
            "            where o.user_id = ?1\n" +
            "            group by t.id\n" +
            "            having count(t.id) = (SELECT MAX(sub.tagCount)\n" +
            "                                  FROM (select count(t.id) tagCount\n" +
            "                                        from orders\n" +
            "                                             inner join certificates c on c.id = orders.certificate_id\n" +
            "                                             inner join users u on orders.user_id = u.id\n" +
            "                                             inner join certificates_tags ct on c.id = ct.certificate_id\n" +
            "                                             inner join tags t on ct.tags_id = t.id\n" +
            "                                        where user_id = ?1\n" +
            "                                        group by t.id) sub);";

    Optional<Tag> findById(UUID id);

    Optional<Tag> findByName(String name);

    int delete(UUID id);

    List<Tag> getMostUsedTag(UUID userid);
}
