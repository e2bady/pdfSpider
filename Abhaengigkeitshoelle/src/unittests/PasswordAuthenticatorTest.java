package unittests;

import static org.junit.Assert.*;

import java.net.PasswordAuthentication;

import model.http.proxy.PasswordAuthenticator;

import org.junit.Test;

public class PasswordAuthenticatorTest {
	@Test
	public final void testGetPasswordAuthenticationBasic() {
		check("username", "password");
	}
	@Test
	public final void testGetPasswordAuthenticationWithDouble() {
		check("username", "pass:word");
	}
	@Test
	public final void testGetPasswordAuthenticationWithSpecialChars() {
		check("user_name", "pässwörd");
	}

	private void check(String username, String password) {
		String seperator = ":";
		PasswordAuthenticator auth = new PasswordAuthenticator(username+seperator+password);
		PasswordAuthentication passauth = auth.getPasswordAuthentication();
		assertEquals("username not correct",username, passauth.getUserName());
		assertEquals("password not correct",password, new String(passauth.getPassword()));
	}
}
