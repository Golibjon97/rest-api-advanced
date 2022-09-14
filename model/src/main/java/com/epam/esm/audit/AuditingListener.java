package com.epam.esm.audit;


import com.epam.esm.domain.BaseEntity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class AuditingListener {

    @PrePersist
    void prePersist(BaseEntity entity) {
        entity.setCreatedDate(LocalDateTime.now());
        entity.setUpdatedDate(LocalDateTime.now());
    }

    @PreUpdate
    void preUpdate(BaseEntity entity) {
        entity.setUpdatedDate(LocalDateTime.now());
    }
}
