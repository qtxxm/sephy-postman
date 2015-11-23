package net.sephy.httpclient;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;

import java.util.Date;
import java.util.List;

/**
 * @author Sephy
 * @since: 2015-09-17
 */
public class PersistenceCookieStore implements CookieStore {


	@Override
	public void addCookie(Cookie cookie) {

	}

	@Override
	public List<Cookie> getCookies() {
		return null;
	}

	@Override
	public boolean clearExpired(Date date) {
		return false;
	}

	@Override
	public void clear() {

	}
}
