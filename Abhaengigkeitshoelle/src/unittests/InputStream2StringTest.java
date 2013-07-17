package unittests;

import static org.junit.Assert.*;


import model.http.inputstreamconverter.InputStream2String;
import model.http.inputstreamconverter.InputStreamConverter;

import org.junit.Test;

public class InputStream2StringTest {
	private static final String UTF_8 = "UTF-8";

	@Test
	public final void testBasic() {
		String test = "Dies ist mein InputStream2String testString.";
		testText(test);
	}
	
	@Test
	public final void testBasicWithLines() {
		String test = "Dies ist mein InputStream2String testString.\nSecondline.\n\rThirdline.";
		testText(test);
	}
	@Test
	public final void testBasicWithLinesAndSpecialChars() {
		String test = "Dies ist mein InputStream2String testString.äöüÖÜÄß\nSecondline.äöüÖÜÄß\n\rThirdline.äöüÖÜÄß";
		testText(test);
	}

	private void testText(String test) {
		InputStreamConverter converter = new InputStream2String(new InputStreamMock(test, UTF_8), UTF_8);
		assertEquals("Failure: converter did not convert the text right.", test.replace("\n", " ").replace("\r", " "), converter.convert());
	}
}
