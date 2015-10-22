package de.d4k.tinkerforge.stromui.main.stromgraph;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;

/**
 * @author Oliver Milke
 */
public class StromgraphPresenter implements Initializable {

	@FXML
	LineChart<String, Number> stromChart;

	private ObservableList<Data<String, Number>> chartData;

	private Task<Void> measuringTask;

	@Override
	public void initialize(final URL url, final ResourceBundle rb) {

		prepareChart(stromChart);

		this.measuringTask = new MeasurementListHandler(chartData);
		Thread t = new Thread(this.measuringTask);
		t.setDaemon(true);
		t.start();
	}
	
	private void prepareChart(LineChart<String, Number> chart) {

		chart.setLegendVisible(false);
		chart.getXAxis().setTickLabelRotation(45);

		chart.getYAxis().setLabel("[mA]");

		Series<String, Number> series = new XYChart.Series<String, Number>();
		series.setName("Current Values");

		chartData = series.getData();

		chart.getData().add(series);

	}
	
	public void cancelTask() {
		
		this.measuringTask.cancel();
		
	}

}