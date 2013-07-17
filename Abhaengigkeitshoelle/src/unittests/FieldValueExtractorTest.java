package unittests;

import static org.junit.Assert.*;

import model.http.valueextractor.FieldValueExtractor;
import model.http.valueextractor.HttpValueExtractor;

import org.junit.Test;

public class FieldValueExtractorTest {
	private static final String DELIMITER = ";";
	private static final String SEPERATOR = "=";
	private static final String TESTPREFIX = "testprefix";
	@Test
	public final void testFieldValueExtractor() {
		HttpValueExtractor valueExtractor = new FieldValueExtractor(TESTPREFIX);
		assertEquals(TESTPREFIX, valueExtractor.getPrefix());
	}
	@Test
	public final void testGetCharset() {
		HttpValueExtractor valueExtractor = new FieldValueExtractor(TESTPREFIX);
		String value = "value";
		assertEquals(value, valueExtractor.getValue(
				"someotherprefix2" + SEPERATOR + "b" + DELIMITER +
				TESTPREFIX + SEPERATOR + value + DELIMITER +
				"someotherprefix" + SEPERATOR + "a" + DELIMITER
		));
	}
}
