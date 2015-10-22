package de.d4k.tinkerforge.stromui.main.stromgraph;

import java.time.LocalTime;
import java.util.Random;

import org.devoxx4kids.Bricklet;
import org.devoxx4kids.BrickletReader;

import com.tinkerforge.BrickletVoltageCurrent;
import com.tinkerforge.BrickletVoltageCurrent.CurrentListener;
import com.tinkerforge.IPConnection;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.chart.XYChart.Data;

/**
 * Kümmert sich um das Verarbeiten der Messwerte.
 * 
 * @author Oliver Milke
 *
 */
public class MeasurementListHandler extends Task<Void> {
	final ObservableList<Data<String, Number>> chartData;
	private BrickletVoltageCurrent cv;
	private CurrentListener currentListener;

	public MeasurementListHandler(final ObservableList<Data<String, Number>> chartData) {

		this.chartData = chartData;
	}

	@Override
	protected Void call() throws Exception {
		try {
			// connectBricklet();
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

		while (true) {
			try {
				Thread.sleep(1000l);

				long value = Math.abs(new Random().nextLong() % 100);
				addValue(value);

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

		currentListener = current -> addValue((long) current);
		cv.addCurrentListener(currentListener);
		cv.setCurrentCallbackPeriod(1000l);

	}

	private void addValue(Long value) {

		String label = getFormattedTimestamp();

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				chartData.add(new Data<>(label, value));
			}
		});

	}

	private String getFormattedTimestamp() {

		LocalTime now = LocalTime.now();
		return now.toString();

	}

}