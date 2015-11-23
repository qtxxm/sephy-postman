package net.sephy.httpclient;

import java.io.FileReader;
import java.io.Reader;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.conn.SchemePortResolver;
import org.apache.http.impl.conn.DefaultRoutePlanner;
import org.apache.http.protocol.HttpContext;

/**
 * @author Sephy
 * @since: 2015-09-17
 */
public class PACRoutePlanner extends DefaultRoutePlanner {

	private ScriptEngine engine;

	private Map<String, HttpHost> cache = new ConcurrentHashMap<String, HttpHost>();

	private Map<String, String> patternMap;

	private Map<String, HttpHost> proxyMap;

	private DomainPatternMatcher hostMatcher = new DomainPatternMatcher();

	public PACRoutePlanner(SchemePortResolver schemePortResolver) {
		super(schemePortResolver);
	}

	public void setPatternMap(Map<String, String> patternMap) {
		this.patternMap = patternMap;
	}

	public void setProxyMap(Map<String, HttpHost> proxyMap) {
		this.proxyMap = proxyMap;
	}

	@Override
	protected HttpHost determineProxy(final HttpHost target,
			final HttpRequest request, final HttpContext context)
			throws HttpException {
		String hostName = target.getHostName();
		HttpHost proxy = cache.get(hostName);
		if (proxy == null) {
			for (String pattern : patternMap.keySet()) {
				if (hostMatcher.isMatch(pattern, hostName)) {
					String proxyName = patternMap.get(pattern);
					proxy = proxyMap.get(proxyName);
					if (proxy != null) {
						cache.put(hostName, proxy);
						break;
					}
				}
			}
		}
		return proxy;
	}

	public static void main(String[] args) throws Exception {
		// 获取 js 执行引擎
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("JavaScript");
		// 绑定 java 执行方法
		Reader reader = new FileReader(
				"/Users/sephy/Documents/OmegaProfile__.pac");
		engine.eval(reader);
		Invocable invocable = (Invocable) engine;
		URL urlGoogle = new URL("https://www.google.com");
		Object result = invocable.invokeFunction("FindProxyForURL",
				urlGoogle.toString(), urlGoogle.getHost());
		System.out.println(result.toString());
		URL urlBaidu = new URL("http://www.baidu.com");
		result = invocable.invokeFunction("FindProxyForURL",
				urlBaidu.toString(), urlBaidu.getHost());
		System.out.println(result.toString());
	}
}
