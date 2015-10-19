package com.pgs.taxidriver.exception;

/**
 * Created by kklonowski on 2015-09-03.
 */
public class UserNotFound extends Exception {

    public UserNotFound() {
        super("Unknown Error");
    }

    public UserNotFound(String message) {
        super(message);
    }

    /**
     *
     */
    private static final long serialVersionUID = 1L;
}
