package controller.factory;

import model.http.proxy.ProxySetter;

public class ProxyFactory {
	public static ProxySetter createProxy() {
		return new ProxySetter("xtfix4:start123", "149.211.177.96", 8080);
	}
}
