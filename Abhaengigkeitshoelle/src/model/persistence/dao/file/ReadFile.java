package model.persistence.dao.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Lazy;

public class ReadFile extends Lazy {
	private static final Logger log = (Logger) LoggerFactory.getLogger(ReadFile.class);
	private String file;
	private String content;

	public ReadFile(String file) {
		this.file = file;
	}
	private String readFile() throws IOException {
		FileReader fr = null;
		BufferedReader reader = null;
		try {
			fr = new FileReader(new File(this.file));
			reader = new BufferedReader( fr );
			String         line = null;
			StringBuilder  stringBuilder = new StringBuilder();

			while( ( line = reader.readLine() ) != null ) {
				stringBuilder.append( line );
				stringBuilder.append( '\n' );
			}

			return stringBuilder.toString();
		} finally {
			if(reader != null)
				reader.close();
			if(fr != null)
				fr.close();
		}
	}
	@Override
	protected boolean load() {
		try {
			this.setContent(this.readFile());
			return true;
		} catch (IOException e) {
			log.error("No read-access to: " + this.file + " possible");
			e.printStackTrace();
			this.setContent(null);
			return true;
		}
	}
	public String getContent() {
		this.lazyLoad();
		return content;
	}
	private void setContent(String content) {
		this.content = content;
	}
}
