package org.sakaiproject.sitemanage.impl;

import java.util.Random;

import org.sakaiproject.sitemanage.api.PasswordGenerator;

/**
 * Wraps an existing password and changes a few of the characters.
 * @author buckett
 *
 */
public class SubCharsPasswordGenerator implements PasswordGenerator {

    private PasswordGenerator source;
    private boolean includeCap;
    private boolean includeNumber;
    private Random random;
    
    public SubCharsPasswordGenerator() {
	this.random = new Random();
    }
    
    public String generate() {
	StringBuffer password = new StringBuffer(source.generate());
	if (includeCap) {
	    int capPos = random.nextInt(password.length());
	    password.setCharAt(capPos, Character.toUpperCase(password
		.charAt(capPos)));
	}
	if (includeNumber) {
	    int numPos = random.nextInt(password.length());
	    password.setCharAt(numPos, Character.forDigit(random.nextInt(10), 10));
	}
	return password.toString();
    }

    public void setSource(PasswordGenerator source) {
        this.source = source;
    }

    public void setIncludeCap(boolean includeCap) {
        this.includeCap = includeCap;
    }

    public void setIncludeNumber(boolean includeNumber) {
        this.includeNumber = includeNumber;
    }

}
