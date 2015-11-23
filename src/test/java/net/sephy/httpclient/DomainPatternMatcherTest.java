package net.sephy.httpclient;

import org.junit.Test;
import org.springframework.util.Assert;

/**
 * @author Sephy
 * @since: 2015-09-17
 */
public class DomainPatternMatcherTest {

	private DomainPatternMatcher matcher = new DomainPatternMatcher();

	@Test
	public void testIsMatch() throws Exception {
		Assert.isTrue(matcher.isMatch("*.google.com", "www.google.com"));
		Assert.isTrue(matcher.isMatch("*.google.com", "code.google.com"));
		Assert.isTrue(matcher.isMatch("*.rakuten.co.jp", "search.rakuten.co.jp"));
		Assert.isTrue(!matcher.isMatch("*.rakuten.co.jp", "code.google.com"));
		Assert.isTrue(!matcher.isMatch("*.google.com", "www.go0gle.com"));
	}
}