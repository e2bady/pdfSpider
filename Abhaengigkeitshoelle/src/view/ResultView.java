package view;

import java.net.URL;
import java.util.Collection;

public interface ResultView {
	void setSelectionChangeListener(ResultViewListener listener);
	void add(URL item);
	void add(Collection<URL> items);
	void remove(URL item);
	void remove(Collection<URL> item);
	void clear();
	void setText(String string);
	void startProgress();
	void setProgression(double val);
	void endProgress();
}