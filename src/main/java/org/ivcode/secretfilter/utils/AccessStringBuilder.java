package org.ivcode.secretfilter.utils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class AccessStringBuilder {
	
	private List<String> authorities = new LinkedList<>();
	
	public AccessStringBuilder hasAuthority(String authority) {
		authorities.add(authority);
		return this;
	}
	
	public String build() {
		StringBuilder sb = new StringBuilder();
		
		Iterator<String> it = authorities.iterator();
		if(!it.hasNext()) {
			return "";
		}
		
		sb.append("hasAuthority('").append(it.next()).append("')");
		while(it.hasNext()) {
			sb.append(" and hasAuthority('").append(it.next()).append("')");
		}
		
		return sb.toString();
	}
}
