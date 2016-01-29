package com.synaptix.toast.adapter.web.component;

import com.synaptix.toast.automation.driver.web.SeleniumSynchronizedDriver;
import com.synaptix.toast.core.runtime.IWebElement;

public class WebContainerElement extends WebAutoElement {

	public WebContainerElement(
		IWebElement element,
		SeleniumSynchronizedDriver driver) {
		super(element, driver);
	}

	public WebContainerElement(
		IWebElement element) {
		super(element);
	}

}
