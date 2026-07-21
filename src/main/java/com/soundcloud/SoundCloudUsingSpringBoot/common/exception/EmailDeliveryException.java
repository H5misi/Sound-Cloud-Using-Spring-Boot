package com.soundcloud.SoundCloudUsingSpringBoot.common.exception;


/**
 * Thrown when an email cannot be delivered.
 */
public class EmailDeliveryException extends RuntimeException{
    public EmailDeliveryException(String message, Throwable cause){
        super(message, cause);
    }
}
