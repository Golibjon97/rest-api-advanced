package com.epam.esm.validation.constraints;

public interface NotNullable<T> {

    void checkNotNull(T t);
}