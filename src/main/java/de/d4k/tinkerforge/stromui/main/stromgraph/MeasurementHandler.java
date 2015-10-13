package de.d4k.tinkerforge.stromui.main.stromgraph;

import java.time.LocalTime;

import org.devoxx4kids.Bricklet;
import org.devoxx4kids.BrickletReader;

import com.tinkerforge.BrickletVoltageCurrent;
import com.tinkerforge.IPConnection;
import com.tinkerforge.BrickletVoltageCurrent.CurrentListener;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart.Data;

/**
 * Kümmert sich um das Behandlen der Messwerte.
 * 
 * @author Oliver Milke
 *
 */
public class MeasurementHandler extends Thread {
	final ObservableList<Data<String, Number>> chartData;

	public MeasurementHandler(final ObservableList<Data<String, Number>> chartData) {
		setDaemon(true);
		setName("Measurement Thread");

		this.chartData = chartData;
	}

	@Override
	public void run() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {

				try {
					// connectBricklet();
					mockValues();
				} catch (Exception e) {
					System.out.println("Fehler beim Lesen des Stroms :(");
					System.out.println(e);
				}
			}

		});

	}

	private void mockValues() {

		long i = 0;
		while (i++ < 10000) {

			addValue(getFormattedTimestamp(), i);
		}

	}

	private void addValue(String label, Long value) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				chartData.add(new Data<>(label, value));
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

		cv.setCurrentCallbackPeriod(1000l);
		cv.addCurrentListener(new CurrentListener() {

			@Override
			public void current(int current) {
				addValue(getFormattedTimestamp(), (long) current);
			}
		});

	}

	private String getFormattedTimestamp() {

		LocalTime now = LocalTime.now();
		return now.toString();

	}
}