package com.synaptix.toast.adapter.web.component;

import com.synaptix.toast.automation.driver.web.SeleniumSynchronizedDriver;
import com.synaptix.toast.core.runtime.IWebElementDescriptor;

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
