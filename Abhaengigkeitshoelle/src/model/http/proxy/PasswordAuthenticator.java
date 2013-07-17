package model.http.proxy;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

public class PasswordAuthenticator extends Authenticator {
	private String login;
	public PasswordAuthenticator(String login) {
		this.login = login;
	}
	@Override
	public PasswordAuthentication getPasswordAuthentication() {
		String[] strs = this.login.split(":", 2);
		if (strs == null || strs.length < 2)
			throw new UnsupportedOperationException(
					"Not able to decode Username:Password for Proxy.");
		return (new PasswordAuthentication(strs[0], strs[1].toCharArray()));
	}
}
