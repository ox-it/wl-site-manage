package org.sakaiproject.site.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;
import static org.sakaiproject.site.util.SiteGroupHelper.*;

/**
 * @author Matthew Buckett
 */
public class SiteGroupHelperTest {

	@Test
	public void testPackSimple() {
		testRoundTrip("A", "B");
	}

	@Test
	public void testPackEmpty() {
		testRoundTrip("", "", "A", "");
	}

	@Test
	public void testPackNothing() {
		testRoundTrip("");
	}

	@Test
	public void testPackSeperator() {
		testRoundTrip(SEPARATOR_STR, "A", SEPARATOR_STR);
	}

	@Test
	public void testPackEscape() {
		testRoundTrip(ESCAPE_STR, "A", ESCAPE_STR);
	}

	@Test
	public void testPackEmbedded() {
		testRoundTrip("Hello", SEPARATOR_STR +"middle"+ SEPARATOR_STR, ESCAPE_STR+"middle"+ESCAPE_STR);
	}

	@Test
	public void testPackMess() {
		testRoundTrip(SEPARATOR_STR+ESCAPE_STR+ESCAPE_STR+SEPARATOR_STR);
	}

	public void testRoundTrip(String... parts) {
		Collection<String> source = Arrays.asList(parts);
		String packed = pack(source);
		Collection<String> unpacked = unpack(packed);
		assertEquals("Packed value was : "+ packed +"\n", source, unpacked);
	}
}
