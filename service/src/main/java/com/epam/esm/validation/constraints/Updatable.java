package com.epam.esm.validation.constraints;

import com.epam.esm.ValidationResult;

public interface Updatable<T> {

    ValidationResult checkUpdate(T t);
}
