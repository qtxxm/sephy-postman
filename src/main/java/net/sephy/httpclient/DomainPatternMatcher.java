package net.sephy.httpclient;

import org.springframework.util.AntPathMatcher;

/**
 * @author Sephy
 * @since: 2015-09-17
 */
public class DomainPatternMatcher {

	private AntPathMatcher antPathMatcher = new AntPathMatcher();

	public boolean isMatch(String pattern, String host) {
		return antPathMatcher.match(pattern, host);
	}
}
