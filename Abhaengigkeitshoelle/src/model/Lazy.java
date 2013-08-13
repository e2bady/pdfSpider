package model;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Lazy {
	private AtomicBoolean ready;
	protected Lazy() {
		ready = new AtomicBoolean(false);
	}
	protected void lazyLoad() {
		if(ready.get() == false) {
			ready.set(this.load());
		}
	}
	protected abstract boolean load();
}