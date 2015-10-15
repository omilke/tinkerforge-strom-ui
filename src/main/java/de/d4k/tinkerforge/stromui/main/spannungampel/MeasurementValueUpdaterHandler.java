package de.d4k.tinkerforge.stromui.main.spannungampel;

import java.util.Random;

import org.devoxx4kids.Bricklet;
import org.devoxx4kids.BrickletReader;

import com.tinkerforge.BrickletVoltageCurrent;
import com.tinkerforge.IPConnection;

import javafx.application.Platform;
import javafx.beans.property.LongProperty;

/**
 * Kümmert sich um das Verarbeiten der Messwerte.
 * 
 * @author Oliver Milke
 *
 */
public class MeasurementValueUpdaterHandler extends Thread {

	final LongProperty measuredValueProperty;

	public MeasurementValueUpdaterHandler(final LongProperty measuredValueProperty) {

		setDaemon(true);
		setName("Measurement Thread");

		this.measuredValueProperty = measuredValueProperty;
	}

	@Override
	public void run() {
		try {
			// connectBricklet();
			mockValues();
		} catch (Exception e) {
			System.out.println("Fehler beim Lesen der Spannung :(");
			System.out.println(e);
		}

	}

	private void mockValues() {

		while (true) {
			try {
				Thread.sleep(1000l);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			long value = Math.abs(new Random().nextLong() % 100);

			setValue(value);
		}

	}

	private void setValue(Long value) {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				measuredValueProperty.set(value);
			}
		});

	}

	@SuppressWarnings("unused")
	private void connectBricklet() throws Exception {

		BrickletReader brickletReader = new BrickletReader();
		Bricklet currentVoltageBricklet = brickletReader.readBricklets()
				.getBrickletByDeviceId(BrickletVoltageCurrent.DEVICE_IDENTIFIER);

		IPConnection ipcon = new IPConnection();
		BrickletVoltageCurrent cv = new BrickletVoltageCurrent(currentVoltageBricklet.getUid(), ipcon);

		ipcon.connect(BrickletReader.HOST, BrickletReader.PORT);

		cv.setVoltageCallbackPeriod(1000l);
		cv.addVoltageListener(voltage -> setValue((long) voltage));

	}

}