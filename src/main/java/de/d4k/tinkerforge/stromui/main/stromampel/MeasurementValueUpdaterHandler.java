package de.d4k.tinkerforge.stromui.main.stromampel;

import java.util.Random;

import org.devoxx4kids.Bricklet;
import org.devoxx4kids.BrickletReader;

import com.tinkerforge.BrickletVoltageCurrent;
import com.tinkerforge.BrickletVoltageCurrent.CurrentListener;
import com.tinkerforge.IPConnection;

import javafx.application.Platform;
import javafx.beans.property.LongProperty;
import javafx.concurrent.Task;

/**
 * Kümmert sich um das Verarbeiten der Messwerte.
 * 
 * @author Oliver Milke
 *
 */
public class MeasurementValueUpdaterHandler extends Task<Void> {

	final LongProperty measuredValueProperty;

	private BrickletVoltageCurrent cv;
	private CurrentListener currentListener;

	public MeasurementValueUpdaterHandler(final LongProperty measuredValueProperty) {

		this.measuredValueProperty = measuredValueProperty;
	}

	@Override
	protected Void call() throws Exception {

		try {
//			connectBricklet();
			 mockValues();
		} catch (Exception e) {
			System.out.println("Fehler beim Lesen des Stroms :(");
			System.out.println(e);
		}

		return null;
	}

	@Override
	protected void cancelled() {

		if (cv != null) {
			cv.removeCurrentListener(currentListener);
		}
	}

	@SuppressWarnings("unused")
	private void mockValues() {

		while (!isCancelled()) {
			try {
				Thread.sleep(1000l);

				long value = Math.abs(new Random().nextLong() % 100);
				setValue(value);

			} catch (InterruptedException e) {
				if (isCancelled()) {
					break;
				} else {
					e.printStackTrace();
				}
			}
		}

		System.out.println("Task wurde beendet...");

	}

	@SuppressWarnings("unused")
	private void connectBricklet() throws Exception {

		BrickletReader brickletReader = new BrickletReader();
		Bricklet currentVoltageBricklet = brickletReader.readBricklets()
				.getBrickletByDeviceId(BrickletVoltageCurrent.DEVICE_IDENTIFIER);

		IPConnection ipcon = new IPConnection();
		cv = new BrickletVoltageCurrent(currentVoltageBricklet.getUid(), ipcon);

		ipcon.connect(BrickletReader.HOST, BrickletReader.PORT);

		currentListener = current -> setValue((long) current);
		cv.addCurrentListener(currentListener);
		cv.setCurrentCallbackPeriod(1000l);

	}

	private void setValue(Long value) {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				measuredValueProperty.set(value);
			}
		});

	}

}