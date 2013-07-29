package unittests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ExtractLinksTest.class, FieldValueExtractorTest.class,
		HttpGetImplTest.class, InputStream2StringTest.class, 
		HttpURLConnectionConfigurationTest.class,
		ProxySetterTest.class, InputStream2StringTest.class,
		PasswordAuthenticatorTest.class,
		CrawlBufferTest.class, CrawlerTest.class, URLConnectorImplTest.class,SimpleResultParserTest.class})
public class AllTests {
}
