package de.d4k.tinkerforge.stromui.main.stromampel;

import java.net.URL;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * @author Oliver Milke
 */
public class StromampelPresenter implements Initializable {

	@FXML
	FontAwesomeIconView icon;

	@Override
	public void initialize(final URL url, final ResourceBundle rb) {

		System.out.println(icon);

	}


}