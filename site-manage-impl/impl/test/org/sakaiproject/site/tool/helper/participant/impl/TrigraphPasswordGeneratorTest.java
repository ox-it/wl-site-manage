package org.sakaiproject.site.tool.helper.participant.impl;

import org.sakaiproject.sitemanage.impl.TrigraphPasswordGenerator;

import junit.framework.TestCase;

public class TrigraphPasswordGeneratorTest extends TestCase {

    public void testPassword() {
	TrigraphPasswordGenerator pwgen = new TrigraphPasswordGenerator();
	pwgen.setLength(8);
	String password = pwgen.generate();
	assertEquals(8, password.length());
    }
    
    public void testMultiplePasswords() {
	TrigraphPasswordGenerator pwgen = new TrigraphPasswordGenerator();
	pwgen.setLength(15);
	String password, last;
	last = pwgen.generate();
	// This might fail but very unlikely.
	for (int i = 0; i< 100; i++) { 
	    password = pwgen.generate();
	    assertNotSame(last, password);
	    last = password;
	}
    }
}
