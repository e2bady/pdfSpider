package controller;

import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.factory.DbFactory;
import controller.factory.ModelFactory;
import controller.factory.ProxyFactory;
import model.http.crawler.IDataRetriever;
import model.http.crawler.dataconverter.ResultFactoryImpl;
import model.http.proxy.IProxySetter;
import model.persistence.dao.DataWriter;
import model.persistence.dao.MySQLDataWriter;
import model.persistence.dbconfig.IDB;
import view.ResultView;
import view.ResultViewListener;
import view.TableView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UI extends Application {
	private static final Logger log = (Logger) LoggerFactory.getLogger(UI.class);
	private final DataWriter writer;
	private final IDataRetriever retriever;
	
	public UI() throws MalformedURLException {
		IDB db = DbFactory.createDB();
		ResultFactoryImpl resultfactory = new ResultFactoryImpl("BGH", "Recht");
		this.writer = new MySQLDataWriter(db, resultfactory);
		IProxySetter proxy = ProxyFactory.createProxy();
		String crawlNamespace = "http://juris\\.bundesgerichtshof\\.de/cgi-bin/rechtsprechung/.*";
		String dataNamespace = "http://juris\\.bundesgerichtshof\\.de/cgi-bin/rechtsprechung/document\\.py?.*\\.pdf";
		String startAt = "http://juris.bundesgerichtshof.de/cgi-bin/rechtsprechung/list.py?Gericht=bgh&Art=en&Datum=Aktuell&Sort=12288";
		
		retriever = ModelFactory
				.createMySQLHttpRetriever(proxy, db, crawlNamespace, 
						dataNamespace, startAt, resultfactory);
	}
	
	public static void main(String[] args) {
		Application.launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Table View Sample");
		stage.setWidth(900);
		stage.setHeight(800);
		final ResultView view = new TableView();
		view.setSelectionChangeListener(new ResultViewListener() {
			@Override
			public void selected(URL url) {
				log.error("url: " + url);
				view.setText(writer.get(url).getContent());
			}

			@Override
			public void start(final int n) {
				log.error("start: " + n);
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						for(int i=0;i<n;i++) {
							view.setProgression(i/n);
							retriever.crawl(1);
							view.clear();
							view.add(writer.ls());
						}
					}
				});
			}
		});
		stage.setScene((Scene) view);
		stage.show();
		
		view.add(writer.ls());
	}
}
