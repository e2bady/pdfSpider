package controller;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.factory.DbFactory;
import model.persistence.dao.DataWriter;
import model.persistence.dao.MySQLDataWriter;
import model.persistence.dbconfig.IDB;
import view.SelectionChangeListener;
import view.TableView;
import javafx.application.Application;
import javafx.stage.Stage;

public class UI extends Application {
	private static final Logger log = (Logger) LoggerFactory.getLogger(UI.class);
	private final DataWriter writer;
	
	public UI() {
		IDB db = DbFactory.createDB();
		this.writer = new MySQLDataWriter(db);
	}
	
	public static void main(String[] args) {
		Application.launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Table View Sample");
		stage.setWidth(900);
		stage.setHeight(800);
		final TableView view = new TableView();
		view.setSelectionChangeListener(new SelectionChangeListener() {
			@Override
			public void selected(URL url) {
				log.error("url: " + url);
				view.setText(writer.get(url));
			}
		});
		stage.setScene(view);
		stage.show();
		
		view.add(writer.ls());
	}
}
