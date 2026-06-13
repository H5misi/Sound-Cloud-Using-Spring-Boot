package com.soundcloud.SoundCloudUsingSpringBoot.common.constants;

/**
 * Stores application-wide constants that are shared
 * across multiple modules of the application.
 *
 * final class -> means  no other class can inherit or extended from it.
 */
public final class AppConstants {

    // Private constructor -> Prevents instantiation of this utility class.
    private AppConstants() {
        throw new IllegalStateException("Utility class");
    }


    /**
     * public -> everyone can access it
     * static -> Belongs to the class itself, not to an object instance (access through the class)
     * final  -> value cannot change / be reassigned.
     */

    
    /**
     * Default number of records returned per page
     * when pagination parameters are not provided.
     */
    public static final int DEFAULT_PAGE_SIZE = 20;

    /**
     * Maximum number of records allowed per page
     * to prevent excessively large requests.
     */
    public static final int MAX_PAGE_SIZE = 100;

}
