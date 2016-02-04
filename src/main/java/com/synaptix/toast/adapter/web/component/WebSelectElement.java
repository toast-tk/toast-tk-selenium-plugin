package com.synaptix.toast.adapter.web.component;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.synaptix.toast.adapter.web.HasSelect;
import com.synaptix.toast.automation.driver.web.SeleniumSynchronizedDriver;
import com.synaptix.toast.core.runtime.IWebElementDescriptor;

public class WebSelectElement extends WebAutoElement implements HasSelect {

	public WebSelectElement(
		IWebElementDescriptor element,
		SeleniumSynchronizedDriver driver) {
		super(element, driver);
	}

	public WebSelectElement(
		IWebElementDescriptor element) {
		super(element);
	}

	@Override
	public void selectByValue(
		String value) {
		WebElement find = frontEndDriver.find(descriptor);
		Select realSelect = new Select(find);
		realSelect.selectByValue(value);
	}

	@Override
	public void selectByIndex(
		int index) {
		WebElement find = frontEndDriver.find(descriptor);
		Select realSelect = new Select(find);
		realSelect.selectByIndex(index);
	}
}
