package de.d4k.tinkerforge.stromui.main;

import java.net.URL;
import java.util.ResourceBundle;

import de.d4k.tinkerforge.stromui.main.stromgraph.StromgraphView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * @author Oliver Milke
 */
public class MainPresenter implements Initializable {

	@FXML
	VBox content;

	@Override
	public void initialize(final URL url, final ResourceBundle rb) {

		final Parent view = new StromgraphView().getView();
		VBox.setVgrow(view, Priority.ALWAYS);
		this.content.getChildren().add(view);
	}


}
