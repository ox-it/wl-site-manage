package org.sakaiproject.site.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.*;
import static org.sakaiproject.site.util.SiteGroupHelper.*;

/**
 * @author Matthew Buckett
 */
public class SiteGroupHelperTest {

	@Test
	public void testRoundTripSimple() {
		testRoundTrip("A", "B");
	}

	@Test
	public void testRoundTripEmpty() {
		testRoundTrip("", "", "A", "");
	}

	@Test
	public void testRoundTripNothing() {
		testRoundTrip("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRoundTripSeperator() {
		testRoundTrip(SEPARATOR, "A", SEPARATOR);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testRoundTripEmbedded() {
		testRoundTrip("Hello", SEPARATOR +"middle"+ SEPARATOR );
	}

	public void testRoundTrip(String... parts) {
		Collection<String> source = Arrays.asList(parts);
		String packed = pack(source);
		Collection<String> unpacked = unpack(packed);
		assertEquals(source, unpacked);
	}

	@Test
	public void testPackNull() {
		assertNull(pack(null));
	}

	@Test
	public void testUnpackNull() {
		assertEquals(Collections.emptyList(), unpack(null));
	}
}
