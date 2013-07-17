package model.http.valueextractor;

public class FieldValueExtractor implements HttpValueExtractor {
	private final String prefix;
	public FieldValueExtractor(String prefix) {
		this.prefix = prefix;
	}
	/* (non-Javadoc)
	 * @see model.http.HttpValueExtractor#getCharset(java.lang.String)
	 */
	public String getValue(String contentType) {
		String charset = "UTF-8";//default
		if(contentType != null)
			for (String param : contentType.replace(" ", "").split(";")) {
				if (param.startsWith(prefix)) {
					charset = param.split("=", 2)[1];
					break;
				}
			}
		return charset;
	}
	public String getPrefix() {
		return this.prefix;
	}
}
