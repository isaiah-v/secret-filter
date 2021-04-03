package org.ivcode.secretfilter.utils;

import java.util.Collection;
import java.util.Collections;

public class CollectionSafty {
	
	private CollectionSafty() {
		// utility class
	}
	
	public static <T> Collection<T> safe(Collection<T> c) {
		return c==null ? Collections.emptyList() : c;
	}
}
