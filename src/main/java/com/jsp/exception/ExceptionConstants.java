package com.jsp.exception;
/**
 * Class containing core constants for the Epsilon application.
 *
 */
public class ExceptionConstants {


    private ExceptionConstants() {
    }
    public static final String ERROR_CODE_UNPROCESSABLE = "C2001";
    public static final String ERROR_CODE_APPLICATION = "C5001";
    public static final String ERROR_CODE_UNAUTHORIZED = "C6001";

    //Gateway Constants
    public static final String DEVICE_URI ="/mercury-api/json/mercury/integration/";
    public static final String GENERAL_ERROR_CODE = "E002C04";
}
