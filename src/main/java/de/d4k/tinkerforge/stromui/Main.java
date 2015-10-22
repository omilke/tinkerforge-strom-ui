package de.d4k.tinkerforge.stromui;

import de.d4k.tinkerforge.stromui.main.spannunggraph.MeasurementListHandler;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.stage.Stage;

/**
 * Einfache Alternativ-Variante zur afterburner-Architektur.
 * 
 * @author Oliver Milke
 *
 */
public class Main extends Application {

	public static void main(String args[]) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		stage.setTitle("tinkerforge-strom-ui");

		final CategoryAxis timeAxis = new CategoryAxis();
		timeAxis.setLabel("Zeitpunkt");
		timeAxis.setTickLabelRotation(45);
		final NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("[A]");
		final LineChart<String, Number> lineChart = new LineChart<>(timeAxis, yAxis);

		lineChart.setTitle("Strom");
		lineChart.setLegendVisible(false);

		XYChart.Series<String, Number> series = new XYChart.Series<>();
		series.setName("Current Measurement");
		final ObservableList<Data<String, Number>> seriesData = series.getData();

		lineChart.getData().add(series);
		Scene scene = new Scene(lineChart, 800, 600);
		stage.setScene(scene);
		stage.show();

		Thread thread = new Thread(new MeasurementListHandler(seriesData));
		thread.setDaemon(true);
		thread.start();

	}

}
