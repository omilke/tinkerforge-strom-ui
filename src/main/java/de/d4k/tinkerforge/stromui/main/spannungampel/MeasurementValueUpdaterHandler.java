package de.d4k.tinkerforge.stromui.main.spannungampel;

import java.util.Random;

import org.devoxx4kids.Bricklet;
import org.devoxx4kids.BrickletReader;

import com.tinkerforge.BrickletVoltageCurrent;
import com.tinkerforge.BrickletVoltageCurrent.VoltageListener;
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

	private final LongProperty measuredValueProperty;
	
	private BrickletVoltageCurrent cv;
	private VoltageListener voltageListener;

	public MeasurementValueUpdaterHandler(final LongProperty measuredValueProperty) {

		this.measuredValueProperty = measuredValueProperty;
	}
	
	@Override
	protected Void call() throws Exception {

		try {
			 connectBricklet();
//			mockValues();
		} catch (Exception e) {
			System.out.println("Fehler beim Lesen der Spannung :(");
			System.out.println(e);
		}

		return null;
	}
	
	@Override
	protected void cancelled() {
		
		if (cv != null) {
			cv.removeVoltageListener(voltageListener);
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

		voltageListener = voltage -> setValue((long) voltage);
		cv.addVoltageListener(voltageListener);
		cv.setVoltageCallbackPeriod(1000l);

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