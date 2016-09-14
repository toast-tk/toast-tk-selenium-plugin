package io.toast.tk.adapter.web.component;

import io.toast.tk.automation.driver.web.SeleniumSynchronizedDriver;
import io.toast.tk.core.runtime.IWebElementDescriptor;

public class WebContainerElement extends WebAutoElement {

	public WebContainerElement(
		IWebElementDescriptor element,
		SeleniumSynchronizedDriver driver) {
		super(element, driver);
	}

	public WebContainerElement(
		IWebElementDescriptor element) {
		super(element);
	}

}
