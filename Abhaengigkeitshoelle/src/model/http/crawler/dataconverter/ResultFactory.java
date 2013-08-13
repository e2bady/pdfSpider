package model.http.crawler.dataconverter;

public interface ResultFactory {
	Result getResult(String origin, String content, String type);
}
