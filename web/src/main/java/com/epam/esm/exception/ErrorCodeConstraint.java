package com.epam.esm.exception;


public final class ErrorCodeConstraint {

    /**
     * The constant NOT_FOUND.
     */
    public static final ErrorCodeConstraint NOT_FOUND
            = new ErrorCodeConstraint(40401);
    /**
     * The constant NOT_ACCEPTABLE.
     */
    public static final ErrorCodeConstraint NOT_ACCEPTABLE
            = new ErrorCodeConstraint(40601);
    /**
     * The constant BAD_REQUEST.
     */
    public static final ErrorCodeConstraint BAD_REQUEST_INPUT
            = new ErrorCodeConstraint(40001);

    public static final ErrorCodeConstraint BAD_REQUEST_NUMBER_FORMAT
            = new ErrorCodeConstraint(40002);

    public static final ErrorCodeConstraint BAD_REQUEST_SORT_FORMAT
            = new ErrorCodeConstraint(40003);

    /**
     * The constant CONFLICT.
     */
    public static final ErrorCodeConstraint CONFLICT
            = new ErrorCodeConstraint(40901);

    /**
     * The constant INTERNAL_SERVER_ERROR.
     */
    public static final ErrorCodeConstraint INTERNAL_CLASS_NOT_FOUND
            = new ErrorCodeConstraint(50001);

    public static final ErrorCodeConstraint INTERNAL_DATABASE_ERROR
            = new ErrorCodeConstraint(50002);

    public int code;

    private ErrorCodeConstraint(int code) {
        this.code = code;
    }
}
