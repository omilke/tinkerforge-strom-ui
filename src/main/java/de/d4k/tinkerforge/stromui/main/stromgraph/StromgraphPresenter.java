package de.d4k.tinkerforge.stromui.main.stromgraph;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;

/**
 * @author Oliver Milke
 */
public class StromgraphPresenter implements Initializable {
	
	@FXML
	LineChart<String, Number> stromChart;


	@Override
	public void initialize(final URL url, final ResourceBundle rb) {

		prepareChart(stromChart);

	}


	private void prepareChart(LineChart<String, Number> chart) {

		
		chart.getXAxis().setTickLabelRotation(45);

		chart.getYAxis().setLabel("[mA]");
	}


}