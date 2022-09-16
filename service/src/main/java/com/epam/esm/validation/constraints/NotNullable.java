package com.epam.esm.validation.constraints;

import com.epam.esm.ValidationResult;
import java.util.List;

public interface NotNullable<T> {

    List<ValidationResult> checkNotNull(T t);
}
