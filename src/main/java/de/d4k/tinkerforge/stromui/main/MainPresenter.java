package de.d4k.tinkerforge.stromui.main;

import java.net.URL;
import java.util.ResourceBundle;

import de.d4k.tinkerforge.stromui.main.ampelhacker.AmpelHackerView;
import de.d4k.tinkerforge.stromui.main.ledampel.LEDampelView;
import de.d4k.tinkerforge.stromui.main.spannungampel.SpannungampelView;
import de.d4k.tinkerforge.stromui.main.spannunggraph.SpannunggraphView;
import de.d4k.tinkerforge.stromui.main.stromampel.StromampelView;
import de.d4k.tinkerforge.stromui.main.stromgraph.StromgraphView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

/**
 * @author Oliver Milke
 */
public class MainPresenter implements Initializable {

	@FXML
	AnchorPane content;

	@Override
	public void initialize(final URL url, final ResourceBundle rb) {

		// default: Graph anzeigen
		showStromGraph();
	}

	private void displayView(final Parent view) {

		this.content.getChildren().clear();
		
		AnchorPane.setTopAnchor(view, 0.0);
		AnchorPane.setLeftAnchor(view, 0.0);
		AnchorPane.setBottomAnchor(view, 0.0);
		AnchorPane.setRightAnchor(view, 0.0);
		
		this.content.getChildren().add(view);
	}

	public void showStromGraph() {
		displayView(new StromgraphView().getView());
	}

	public void showStromAmpel() {
		displayView(new StromampelView().getView());
	}
	
	public void showSpannungGraph() {
		displayView(new SpannunggraphView().getView());
	}

	public void showSpannungAmpel() {
		displayView(new SpannungampelView().getView());
	}

	public void showLedAmpel() {
		displayView(new LEDampelView().getView());
	}
	public void showAmpelHacker() {
		displayView(new AmpelHackerView().getView());
	}
}
