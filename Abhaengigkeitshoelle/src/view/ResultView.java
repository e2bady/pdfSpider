package view;

import java.net.URL;
import java.util.Collection;

public interface ResultView {

	public abstract void setSelectionChangeListener(
			ResultViewListener listener);

	public abstract void add(URL item);

	public abstract void add(Collection<URL> items);

	public abstract void remove(URL item);

	public abstract void remove(Collection<URL> item);

	public abstract void clear();

	public abstract void setText(String string);

	void startProgress();

	void setProgression(double val);

	void endProgress();

}