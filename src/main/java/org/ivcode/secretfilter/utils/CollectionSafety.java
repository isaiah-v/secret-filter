package org.ivcode.secretfilter.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * A set of methods that can be used on collections to avoid null pointer
 * exceptions without explicitly checking if the collections are null.
 * 
 * @author isaiah
 *
 */
public class CollectionSafety {

	private CollectionSafety() {
		// utility class
	}

	/**
	 * A {@code null} safety call. The given collection is returned unless the value
	 * is {@code null}, in which case an empty collection is returned.
	 * 
	 * @param <T> The collection's element type
	 * @param c   The collection
	 * @return A non-null collection. The given collection is returned unless the
	 *         value is {@code null}, in which case, an empty collection is
	 *         returned.
	 */
	public static <T> Collection<T> safe(Collection<T> c) {
		return c == null ? Collections.emptyList() : c;
	}
	
	public static <K,V> Map<K,V> safe(Map<K,V> m) {
		return m == null ? Collections.emptyMap() : m;
	}
}
