package de.d4k.tinkerforge.stromui.main.spannunggraph;

import com.airhacks.afterburner.views.FXMLView;

import de.d4k.tinkerforge.stromui.main.BackgroundTaskView;

/**
 * @author Oliver Milke
 */
public class SpannunggraphView extends FXMLView implements BackgroundTaskView {

	@Override
	public void cancelBackgroundTask() {
		SpannunggraphPresenter presenter = (SpannunggraphPresenter) this.getPresenter();
		
		presenter.cancelTask();
	}
}
