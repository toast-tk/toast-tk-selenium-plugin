package io.toast.tk.adapter.web;

import io.toast.tk.automation.driver.web.SynchronizedDriver;

public interface IDrivableWebPage {

	/**
	 * set the driver that will be used by the automation elements
	 */
	public void setDriver(SynchronizedDriver<?, ?> sDvr);
}
