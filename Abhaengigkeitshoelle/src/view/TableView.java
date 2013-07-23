package view;

import java.net.URL;
import java.util.Collection;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Callback;

public class TableView extends Scene {
	public TableView() {
		super(new Group());
	}
	private javafx.scene.control.TableView<URL> table = new javafx.scene.control.TableView<URL>();
	private TextArea area;
	public void start(final SelectionChangeListener listener) throws Exception {
 
        final Label label = new Label("TableView");
        label.setFont(new Font("Arial", 20));
 
        TableColumn<URL, String> urlcol = new TableColumn<URL, String>("url");
        urlcol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<URL,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<URL, String> arg0) {
				SimpleStringProperty str = new SimpleStringProperty(arg0.getValue().toExternalForm());
				return str;
			}
		});
        table.getColumns().add(urlcol);
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<URL>() {
			@Override
			public void changed(ObservableValue<? extends URL> observale, URL oldValue, URL newValue) {
				listener.selected(newValue);
			}
		});
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        
        area = new TextArea();
        
        
        
        vbox.getChildren().addAll(label, table, area);
 
        ((Group) this.getRoot()).getChildren().addAll(vbox);
        
        
	}
	
	public void add(URL item) {
		table.getItems().add(item);
	}
	public void add(Collection<URL> items) {
		table.getItems().addAll(items);
	}
	public void remove(URL item) {
		table.getItems().remove(item);
	}
	public void remove(Collection<URL> item) {
		table.getItems().removeAll(item);
	}
	public void clear() {
		table.getItems().clear();
	}

	public void setText(String string) {
		area.setText(string);
	}
}
