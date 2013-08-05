package model.http.inputstreamconverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InputStream2String implements InputStreamConverter {
	private final InputStream response;
	private final String charset;
	
	private static final Logger log = (Logger) LoggerFactory
			.getLogger(InputStream2String.class);
	
	public InputStream2String(InputStream response, String charset) {
		super();
		this.response = response;
		this.charset = charset;
	}
	public String convert() {
		StringBuilder builder = new StringBuilder();
		if (charset != null && this.response != null) {
			
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(response, charset));
				for (String line; (line = reader.readLine()) != null;) {
					builder.append(line);
					builder.append(" ");
				}
			} catch (UnsupportedEncodingException e) {
				log.error("UnsupportedEncodingException while stringifying InputStream." + e.toString());
			} catch (IOException e) {
				log.error("IOException while stringifying InputStream." + e.toString());
			} finally {
				if (reader != null) try { reader.close(); } catch (IOException logOrIgnore) {}
			}
		}
		if(log.isDebugEnabled()) log.debug(builder.toString());
		return builder.toString().trim();
	}
}
