package model.http.valueextractor;

public interface HttpValueExtractor {
	String getValue(String contentType);
	String getPrefix();
}