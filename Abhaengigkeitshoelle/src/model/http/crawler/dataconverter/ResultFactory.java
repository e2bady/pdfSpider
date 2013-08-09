package model.http.crawler.dataconverter;

public interface ResultFactory {
	Result getResult(String content, String string);
}
