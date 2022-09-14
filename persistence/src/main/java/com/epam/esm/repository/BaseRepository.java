package com.epam.esm.repository;

import com.epam.esm.util.PageRequest;

import java.util.List;
import java.util.UUID;


public interface BaseRepository<T> {

    T create(T t);


    List<T> getAll(PageRequest pageRequest);


    T getOne(UUID id);


}
