package view;

import java.util.concurrent.atomic.AtomicBoolean;

public class DebounceTask implements Runnable {
	volatile private AtomicBoolean cancel = new AtomicBoolean(false);
	private Runnable run;
	private int sleep;
	public DebounceTask(int sleep,Runnable run) {
		this.run = run;
		this.sleep = sleep;
	}
	@Override
	public void run() {
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(cancel.get() == false) this.run.run(); 
	}
	public void cancel() {
		cancel.set(true);
	}
}
