package model.persistence.dao.file;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WriteFile {
	private static final Logger log = (Logger) LoggerFactory.getLogger(WriteFile.class);
	private final String path;
	private final String content;
	
	public WriteFile(String path, String content) {
		this.path = path;
		this.content = content;
	}
	
	public boolean write() {
		PrintWriter out = null;
		try {
			out = new PrintWriter(this.path);
			out.println(this.content);
			return true;
		} catch (FileNotFoundException e) {
			log.error("Failed to write to " + this.path);
			e.printStackTrace();
			return false;
		}
		finally {
			if(out != null)
				out.close();
		}
	}
}
