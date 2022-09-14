package com.epam.esm.service;

import com.epam.esm.util.PageRequest;

import java.util.List;
import java.util.UUID;

public interface BaseService<T,R> {

    R create(T t);

    R get(UUID id);

    List<R> getAll(PageRequest pageRequest);

    int delete(UUID id);
}
