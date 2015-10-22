package de.d4k.tinkerforge.stromui.main.stromampel;

import com.airhacks.afterburner.views.FXMLView;

import de.d4k.tinkerforge.stromui.main.BackgroundTaskView;

/**
 * @author Oliver Milke
 */
public class StromampelView extends FXMLView implements BackgroundTaskView {

	@Override
	public void cancelBackgroundTask() {
		StromampelPresenter presenter = (StromampelPresenter) this.getPresenter();
		
		presenter.cancelTask();
	}
}
