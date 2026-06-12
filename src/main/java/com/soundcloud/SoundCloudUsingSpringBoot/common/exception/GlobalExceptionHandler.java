package com.soundcloud.SoundCloudUsingSpringBoot.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.soundcloud.SoundCloudUsingSpringBoot.common.response.ErrorResponse;


/**
 * Handles application exceptions globally and converts them
 * into standardized JSON error responses.
 */


// @RestControllerAdvice -> Global exception handler for REST APIs 
// (ensure responses return as JSON instead of views like HTML)
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    //ExceptionHandler(ResourceNotFoundException.class)
    // -> if (ResourceNotFoundException) is thrown anywhere, call the method below
    @ExceptionHandler(ResourceNotFoundException.class) 
    // ResponseEntity<ErrorResponse> -> the return type of the handler
    // (ResponseEntity<T>, T represents the type of the HTTP response body.)
    public ResponseEntity<ErrorResponse> handleResourceNotFound (ResourceNotFoundException exception){
        // Creates the JSON body with: status (HttpStatus.~.value()) and the message (exception.getMessage())
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage());

        /**
         * Creates and return the full HTTP response
         * 
         * example: 
         * 
            404 Not Found
            {
                "status": 404,
                "message": "User with identifier [5] was not found!"
            }
         *
         * .status(...) -> controls [ 404 Not Found ]
         * .body(...) -> controls [
         *                          {
         *                          "status":"...",
         *                           "message":"..."
         *                           }
         *                         ]
         * */ 
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }


    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException exception){
        // 400 -> Bad Request 
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(UnauthorizedException exception){
        // 401 -> Unauthorized 
        ErrorResponse response = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), exception.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException exception){
        // 403 -> Forbidden (Access Denied)
        ErrorResponse response = new ErrorResponse(HttpStatus.FORBIDDEN.value(), exception.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }


    /**
     * Fallback handler for all unhandled exceptions.
     *
     * This method catches unexpected errors that are not
     * explicitly handled by other exception handlers,
     * such as:
     * - NullPointerException
     * - IllegalStateException
     * - ArithmeticException
     * - Third-party library failures
     *
     * Returns a generic HTTP 500 response to avoid exposing
     * internal application details to API consumers.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleExceptions(){
        // 500 -> Internal server error
        ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

}
