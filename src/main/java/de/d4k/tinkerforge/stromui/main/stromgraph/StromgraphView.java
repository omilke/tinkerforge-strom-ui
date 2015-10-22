package de.d4k.tinkerforge.stromui.main.stromgraph;

import com.airhacks.afterburner.views.FXMLView;

import de.d4k.tinkerforge.stromui.main.BackgroundTaskView;

/**
 * @author Oliver Milke
 */
public class StromgraphView extends FXMLView implements BackgroundTaskView {

	@Override
	public void cancelBackgroundTask() {
		StromgraphPresenter presenter = (StromgraphPresenter) this.getPresenter();
		
		presenter.cancelTask();
	}
}