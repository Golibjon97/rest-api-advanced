package com.epam.esm.validation;

import com.epam.esm.ValidationResult;
import java.util.List;

public abstract class BaseValidator<T>{

   public abstract List<ValidationResult> validate(T t);
}
