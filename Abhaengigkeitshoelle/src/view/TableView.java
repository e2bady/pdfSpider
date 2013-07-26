package view;

import java.net.URL;
import java.util.Collection;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class TableView extends Scene {
	public TableView() {
		super(new Group());
		final Label label = new Label("TableView");
		label.setFont(new Font("Arial", 20));
		table.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<URL>() {
					@Override
					public void changed(
							ObservableValue<? extends URL> observale,
							URL oldValue, URL newValue) {
						if(listener != null)
							listener.selected(newValue);
					}
				});
		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		area = new TextArea();
		area.setEditable(false);
		vbox.getChildren().addAll(label, table, area);
		((Group) this.getRoot()).getChildren().addAll(vbox);
	}

	public void setSelectionChangeListener(final SelectionChangeListener listener) {
		this.listener = listener;
	}

	private SelectionChangeListener listener = null;
	private javafx.scene.control.ListView<URL> table = new javafx.scene.control.ListView<URL>();
	private TextArea area;

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
