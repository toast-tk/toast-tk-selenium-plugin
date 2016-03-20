package com.synaptix.toast.adapter.web.component;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.synaptix.toast.automation.driver.web.SeleniumSynchronizedDriver;
import com.synaptix.toast.core.runtime.IWebElementDescriptor;

public class WebComponentElement extends WebAutoElement {
	
	public WebComponentElement(
			IWebElementDescriptor element,
			SeleniumSynchronizedDriver driver) {
			super(element, driver);
	}

	public WebComponentElement(
		IWebElementDescriptor element) {
		super(element);
	}

	public List<String> getValues(){
		WebElement element = getWebElement();
		//match against template and return values list
		return null;
	}
}
