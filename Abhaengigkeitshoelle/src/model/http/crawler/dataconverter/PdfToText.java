package model.http.crawler.dataconverter;

import java.io.IOException;
import java.net.URL;

import model.http.urlconnection.ConnectionFactory;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class PdfToText implements DataConverter {
	private ConnectionFactory connectionFactory;

	public PdfToText(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	public String convert(URL dataUrl) throws IOException {
		PDDocument doc = PDDocument.load(connectionFactory.establishConnection(dataUrl).getInputStream());
		PDFTextStripper stripper = new PDFTextStripper();
		String text = stripper.getText(doc);
		return text;
	}
}
