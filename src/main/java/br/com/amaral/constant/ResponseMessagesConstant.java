package br.com.amaral.constant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseMessagesConstant {

    public static final ResponseEntity<String> GET_SUCCESS = new ResponseEntity<>("GET request successful", HttpStatus.OK);
    public static final ResponseEntity<String> POST_SUCCESS = new ResponseEntity<>("POST request successful", HttpStatus.CREATED);
    public static final ResponseEntity<String> DELETE_SUCCESS = new ResponseEntity<>("DELETE request successful", HttpStatus.NO_CONTENT);

    public static final ResponseEntity<String> NOT_FOUND = new ResponseEntity<>("Resource not found", HttpStatus.NOT_FOUND);
    public static final ResponseEntity<String> BAD_REQUEST = new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
    public static final ResponseEntity<String> SERVER_ERROR = new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
    
    public static final String NOT_FOUND_ID = "Operation not performed: Not included in the ID database";
    public static final String NOT_FOUND_NAME ="Operation not carried out: Already registered with the name";
    public static final String DELETE_OK = "OK: Deletion completed successfully.";
}
