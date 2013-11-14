package org.sakaiproject.site.util;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Helper for managing groups in a site.
 *
 * @author Matthew Buckett
 */
public class SiteGroupHelper {

	// The separator when packing ids into a string.
	public static final String SEPARATOR = ",";

	// Force this class to be a helper.
	private SiteGroupHelper() {
	}

	/**
	 * This unpacks IDs from a string. It supports empty IDs but not <code>null</code>.
	 * @param ids The packed IDs in a string, can be <code>null</code>.
	 * @return A collection of IDs unpacked from the string.
	 * @see #pack(java.util.Collection)
	 */
	public static Collection<String> unpack(String ids) {
		Collection<String> unpacked = Collections.<String>emptyList();
		if (ids != null) {
			unpacked = Arrays.asList(ids.split(SEPARATOR, -1));
		}
		return unpacked;
	}

	/**
	 * This packs IDs into a string. It supports empty IDs but not <code>null</code>.
	 * @param ids A Collection of IDs to be packed together.
	 * @return The packed string containing all the IDs or <code>null</code> if the original colleciton was
	 * <code>null</code>.
	 * @throws IllegalArgumentException if any of the strings contain the separator.
	 * @see #unpack(String)
	 */
	public static String pack(Collection<String> ids) {
		String packed = null;
		if (ids != null) {
			for(String id: ids) {
				if (id.contains(SEPARATOR)) {
					throw new IllegalArgumentException(id + " contains the seperator and can't be joined.");
				}
			}
			packed = StringUtils.join(ids, SEPARATOR);
		}
		return packed;
	}
}
