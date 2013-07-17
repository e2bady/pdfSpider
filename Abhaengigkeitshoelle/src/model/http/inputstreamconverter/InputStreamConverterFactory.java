package model.http.inputstreamconverter;

import model.http.urlconnector.URLConnector;

public class InputStreamConverterFactory implements IInputStreamConverterFactory {
	/* (non-Javadoc)
	 * @see model.http.IInputStreamConverterFactory#create(model.http.URLConnector)
	 */
	public InputStreamConverter create(URLConnector connector) {
		return new InputStream2String(connector.getResponse(), connector.getCharset());
	}
}
