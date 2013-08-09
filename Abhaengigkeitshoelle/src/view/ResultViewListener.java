package view;

import java.net.URL;

public interface ResultViewListener {
	void selected(URL url);
	void start(int n);
	void query(String text);
}
