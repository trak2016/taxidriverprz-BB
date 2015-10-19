package com.pgs.taxidriver.exception;

/**
 * Created by kklonowski on 2015-09-03.
 */
public class CompanyNotFound extends Exception {

    public CompanyNotFound() {
        super("Unknown Error");
    }

    public CompanyNotFound(String message) {
        super(message);
    }

    /**
     *
     */
    private static final long serialVersionUID = 1L;
}
