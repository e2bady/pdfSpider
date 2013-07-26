package view;
import java.net.URL;
import java.util.Collection;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class TableView extends Scene implements ResultView {
	private ResultViewListener listener = null;
	private javafx.scene.control.ListView<URL> table = new javafx.scene.control.ListView<URL>();
	private final TextArea area;
	private final ProgressIndicator progressindicator;
	private final ProgressBar progressBar;
	private final TextField textbox;
	private Button button;
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
		progressBar = new ProgressBar(0.0);
		progressindicator = new ProgressIndicator(0.0);
		this.endProgress();
		this.textbox = new TextField();
		this.textbox.addEventHandler(javafx.scene.input.KeyEvent.KEY_TYPED, new javafx.event.EventHandler<javafx.scene.input.KeyEvent>() {
			@Override
			public void handle(javafx.scene.input.KeyEvent arg0) {
				boolean noexception = checkIntValue();
				if(noexception)
					button.setDisable(false);
			}
		});
		this.button = new Button("Start Crawl");
		this.button.setDisable(true);
		this.button.setOnAction(new javafx.event.EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				boolean noexception = checkIntValue();
				if(noexception)
					listener.start(parseIntValue());
			}
		});
		vbox.getChildren().addAll(label, table, area, progressBar, progressindicator, textbox, this.button);
		((Group) this.getRoot()).getChildren().addAll(vbox);
	}

	/* (non-Javadoc)
	 * @see view.ResultView#setSelectionChangeListener(view.SelectionChangeListener)
	 */
	@Override
	public void setSelectionChangeListener(final ResultViewListener listener) {
		this.listener = listener;
	}
	
	@Override
	public void startProgress() {
		progressBar.setDisable(false);
		progressindicator.setDisable(false);
		progressBar.setProgress(0.0);
		progressindicator.setProgress(0.0);
	}
	@Override
	public void endProgress() {
		progressBar.setDisable(true);
		progressindicator.setDisable(true);
		progressBar.setProgress(0.0);
		progressindicator.setProgress(0.0);
	}
	@Override
	public void setProgression(double val) {
		progressBar.setDisable(false);
		progressindicator.setDisable(false);
		progressBar.setProgress(val);
		progressindicator.setProgress(val);
	}

	/* (non-Javadoc)
	 * @see view.ResultView#add(java.net.URL)
	 */
	@Override
	public void add(URL item) {
		table.getItems().add(item);
	}

	/* (non-Javadoc)
	 * @see view.ResultView#add(java.util.Collection)
	 */
	@Override
	public void add(Collection<URL> items) {
		table.getItems().addAll(items);
	}

	/* (non-Javadoc)
	 * @see view.ResultView#remove(java.net.URL)
	 */
	@Override
	public void remove(URL item) {
		table.getItems().remove(item);
	}

	/* (non-Javadoc)
	 * @see view.ResultView#remove(java.util.Collection)
	 */
	@Override
	public void remove(Collection<URL> item) {
		table.getItems().removeAll(item);
	}

	/* (non-Javadoc)
	 * @see view.ResultView#clear()
	 */
	@Override
	public void clear() {
		table.getItems().clear();
	}

	/* (non-Javadoc)
	 * @see view.ResultView#setText(java.lang.String)
	 */
	@Override
	public void setText(String string) {
		area.setText(string);
	}

	private boolean checkIntValue() {
		boolean noexception = true;
		try {
			Integer.valueOf( textbox.getText() );
		} catch(NumberFormatException nfe) {
			noexception = false;
			button.setDisable(true);
		}
		return noexception;
	}
	private int parseIntValue() throws NumberFormatException {
		return Integer.valueOf( textbox.getText() );
	}
}
