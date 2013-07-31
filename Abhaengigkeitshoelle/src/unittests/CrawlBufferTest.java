package unittests;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import model.http.crawler.persistentbuffer.CrawlBuffer;
import model.http.crawler.persistentbuffer.PersistentBuffer;
import model.persistence.dao.CrawlerModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class CrawlBufferTest {
	private static URL model4;
	private static URL model3;
	private static URL model2;
	private static URL model1;
	private static URL eins;
	private static URL zwei;
	private static URL drei;
	private static URL vier;
	
	@Before
	public final void setUp() {
		try {
			model4 = new URL("http://www.model4.de");
			model3 = new URL("http://www.model3.de");
			model2 = new URL("http://www.model2.de");
			model1 = new URL("http://www.model1.de");
			eins = new URL("http://www.eins.de");
			zwei = new URL("http://www.zwei.de");
			drei = new URL("http://www.drei.de");
			vier = new URL("http://www.vier.de");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public final void testCrawlBuffer() throws MalformedURLException {
		CrawlerModel model = Mockito.mock(CrawlerModel.class);
		PersistentBuffer buffer = new CrawlBuffer(model);
		buffer.add(eins);
		buffer.add(zwei);
		buffer.add(drei);
		buffer.add(vier);
		assertEquals("buffer didn't return the objects with FIFO order.", eins,buffer.poll());
		assertEquals("buffer didn't return the objects with FIFO order.", zwei,buffer.poll());
		assertEquals("buffer didn't return the objects with FIFO order.", drei,buffer.poll());
		assertEquals("buffer didn't return the objects with FIFO order.", vier,buffer.poll());
	}

	@Test
	public final void testModelBufferOrder() {
			CrawlerModel model = null;
			try {
				model = new CrawlerModel() {
					@SuppressWarnings("serial")
					Queue<URL> urls = new LinkedBlockingQueue<URL>() {{
						put(model1);
						put(model2);
						put(model3);
						put(model4);
					}};
					public boolean isCrawled(URL url) {
						return false;
					}
					
					public URL getNotCrawled() {
						return urls.poll();
					}

					public boolean isEmpty() {
						return urls.isEmpty();
					}

					public void add(URL url, boolean crawled) {
						// TODO Auto-generated method stub
						
					}

					public void update(URL url, boolean crawled) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public boolean contains(URL next) {
						// TODO Auto-generated method stub
						return false;
					}
				};
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			assertNotNull(model);
			PersistentBuffer buffer = new CrawlBuffer(model);
			buffer.add(eins);
			buffer.add(zwei);
			buffer.add(drei);
			buffer.add(vier);
			assertEquals("buffer didn't return the objects with FIFO order.", eins,buffer.poll());
			assertEquals("buffer didn't return the objects with FIFO order.", zwei,buffer.poll());
			assertEquals("buffer didn't return the objects with FIFO order.", drei,buffer.poll());
			assertEquals("buffer didn't return the objects with FIFO order.", vier,buffer.poll());
			assertEquals("buffer didn't return the objects with FIFO order.", model1,buffer.poll());
			assertEquals("buffer didn't return the objects with FIFO order.", model2,buffer.poll());
			assertEquals("buffer didn't return the objects with FIFO order.", model3,buffer.poll());
			assertEquals("buffer didn't return the objects with FIFO order.", model4,buffer.poll());
	}

	@Test
	public final void testNull() {
		PersistentBuffer buffer = new CrawlBuffer(null);
		buffer.add(eins);
		assertEquals("buffer didn't return the object.", eins,buffer.poll());
		assertNull("empty buffer needs to return null", buffer.poll());
		
	}
}
