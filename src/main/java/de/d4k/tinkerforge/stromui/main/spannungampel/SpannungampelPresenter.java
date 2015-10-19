package de.d4k.tinkerforge.stromui.main.spannungampel;

import java.net.URL;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * @author Oliver Milke
 */
public class SpannungampelPresenter implements Initializable {

	@FXML
	FontAwesomeIconView icon;

	private Task<Void> measuringTask;

	@Override
	public void initialize(final URL url, final ResourceBundle rb) {

		LongProperty measuredValueProperty = new SimpleLongProperty();

		measuredValueProperty.addListener((observable, oldValue, newValue) -> {

			if (oldValue != newValue) {
				icon.setSize((newValue.longValue() * 10) + "");
			}

		});

		this.measuringTask = new MeasurementValueUpdaterHandler(measuredValueProperty);
		Thread t = new Thread(this.measuringTask);
		t.setDaemon(true);
		t.start();
	}
	
	public void cancelTask() {
		this.measuringTask.cancel();
	}

}