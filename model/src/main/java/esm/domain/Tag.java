package esm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tags")
public class Tag extends BaseEntity {

    @Column(unique = true)
    private String name;

    public Tag(UUID id, LocalDateTime createdDate, LocalDateTime updatedDate, String name) {
        super(id, createdDate, updatedDate);
        this.name = name;
    }

    public Tag(String name) {
        this.name = name;
    }

    public Tag() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
