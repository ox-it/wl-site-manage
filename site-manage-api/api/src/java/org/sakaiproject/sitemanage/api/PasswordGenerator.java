package org.sakaiproject.sitemanage.api;

/**
 * Simple interface to generate a new password for the user.
 *
 */
public interface PasswordGenerator {

    /**
     * Generate a new password which can be used for a user account.
     */
    public String generate();
    
}
