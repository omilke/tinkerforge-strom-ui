package de.d4k.tinkerforge.stromui.main;

import java.net.URL;
import java.util.ResourceBundle;

import com.airhacks.afterburner.views.FXMLView;

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
	private AnchorPane content;
	
	private FXMLView activeView;

	@Override
	public void initialize(final URL url, final ResourceBundle rb) {

		// default: Graph anzeigen
		showStromGraph();
	}

	private void displayView(final FXMLView view) {
		
		if (this.activeView instanceof BackgroundTaskView) {
			BackgroundTaskView backgroundTaskView = (BackgroundTaskView) this.activeView;
			backgroundTaskView.cancelBackgroundTask();
		}

		this.content.getChildren().clear();
		
		Parent viewContent = view.getView();
		
		AnchorPane.setTopAnchor(viewContent, 0.0);
		AnchorPane.setLeftAnchor(viewContent, 0.0);
		AnchorPane.setBottomAnchor(viewContent, 0.0);
		AnchorPane.setRightAnchor(viewContent, 0.0);
		
		this.content.getChildren().add(viewContent);
		this.activeView = view;
	}

	public void showStromGraph() {
		displayView(new StromgraphView());
	}

	public void showStromAmpel() {
		displayView(new StromampelView());
	}
	
	public void showSpannungGraph() {
		displayView(new SpannunggraphView());
	}

	public void showSpannungAmpel() {
		displayView(new SpannungampelView());
	}

	public void showLedAmpel() {
		displayView(new LEDampelView());
	}
	
	public void showAmpelHacker() {
		displayView(new AmpelHackerView());
	}
}
