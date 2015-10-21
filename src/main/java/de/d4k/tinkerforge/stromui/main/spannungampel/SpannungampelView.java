package de.d4k.tinkerforge.stromui.main.spannungampel;

import com.airhacks.afterburner.views.FXMLView;

import de.d4k.tinkerforge.stromui.main.BackgroundTaskView;

/**
 * @author Oliver Milke
 */
public class SpannungampelView extends FXMLView implements BackgroundTaskView {

	@Override
	public void cancelBackgroundTask() {
		SpannungampelPresenter presenter = (SpannungampelPresenter) this.getPresenter();
		
		presenter.cancelTask();
	}
}
