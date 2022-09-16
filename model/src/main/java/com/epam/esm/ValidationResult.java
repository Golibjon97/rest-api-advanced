package com.epam.esm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ValidationResult {

    /**
     * CODE MESSAGES
     * 0. Success
     * 1. price should be only numeric
     * 2. price should not be less than 0
     * 3. duration should be only numeric
     * 4. duration should be greater than 0
     * 5. name or price or duration cannot be null
     * 6. nothing to update
     */
    private int code;
    private String message;


    public ValidationResult(int code) {
        this.code = code;
    }
}