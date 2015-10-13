package de.d4k.tinkerforge.stromui;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.airhacks.afterburner.injection.Injector;

import de.d4k.tinkerforge.stromui.main.MainView;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * @author Oliver Milke
 */
public class App extends Application {


	@Override
	public void start(final Stage stage) throws Exception {


		final Map<Object, Object> customProperties = new HashMap<>();
		customProperties.put("locale", Locale.GERMANY);

		Injector.setConfigurationSource(customProperties::get);

		final MainView appView = new MainView();
		final Scene scene = new Scene(appView.getView());
		final String uri = getClass().getResource("app.css").toExternalForm();
		scene.getStylesheets().add(uri);

		maximize(stage);
		stage.setTitle("tinkerforge-strom-ui");

		stage.setScene(scene);
		stage.show();
	}

	private void maximize(final Stage stage) {

		final Screen screen = Screen.getPrimary();
		final Rectangle2D bounds = screen.getVisualBounds();

		stage.setX(bounds.getMinX());
		stage.setY(bounds.getMinY());
		stage.setWidth(bounds.getWidth());
		stage.setHeight(bounds.getHeight());
	}

	@Override
	public void stop() throws Exception {
		Injector.forgetAll();
	}

	public static void main(final String[] args) {
		launch(args);
	}
}
