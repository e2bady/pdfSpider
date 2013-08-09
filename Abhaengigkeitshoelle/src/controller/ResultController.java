package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.http.crawler.IDataRetriever;
import model.http.crawler.dataconverter.Result;
import model.persistence.dao.DataWriter;
import javafx.application.Platform;
import view.ResultView;
import view.ResultViewListener;

final class ResultController implements ResultViewListener {
	private static final Logger log = (Logger) LoggerFactory.getLogger(ResultController.class);
	/**
	 * 
	 */
	private final ResultView view;
	private DataWriter writer;
	private IDataRetriever retriever;

	ResultController(DataWriter writer, IDataRetriever retriever,ResultView view) {
		this.view = view;
		this.writer = writer;
		this.retriever = retriever;
	}

	@Override
	public void selected(URL url) {
		log.error("url: " + url);
		Result result = writer.get(url);
		log.error(result.toString());
		view.setText(result.getContent());
	}

	@Override
	public void start(final int n) {
		log.error("start: " + n);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				for(int i=0;i<n;i++) {
					log.error("runnint: " + i + " / " + n);
					view.setProgression(i/n);
					retriever.crawl(1);
					view.clear();
					view.add(writer.ls());
				}
			}
		});
	}

	@Override
	public void query(String text) {
		this.view.clear();
		List<Result> rs = this.writer.get(text);
		List<URL> us = new ArrayList<>(rs.size());
		for(Result r : rs) {
			us.add(r.getOrigin());
		}
		this.view.add(us);
	}
}