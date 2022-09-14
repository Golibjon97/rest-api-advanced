package com.epam.esm.exception;

public class InvalidSortException extends RuntimeException{
    public InvalidSortException() {
        super("Invalid sort type");
    }
}
