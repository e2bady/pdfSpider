package controller.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.http.proxy.ProxySetter;

public class ProxyFactory {
	private static final Logger log = (Logger) LoggerFactory.getLogger(ProxyFactory.class);
	public static ProxySetter createProxy() {
		log.error("Creating Proxy Object.");
		return new ProxySetter("xtfix4:start123", "149.211.177.96", 8080);
	}
}
