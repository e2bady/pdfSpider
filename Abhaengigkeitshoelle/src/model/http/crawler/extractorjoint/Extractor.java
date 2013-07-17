package model.http.crawler.extractorjoint;

import java.util.Set;

public interface Extractor<T> {
	Set<T> get();
}