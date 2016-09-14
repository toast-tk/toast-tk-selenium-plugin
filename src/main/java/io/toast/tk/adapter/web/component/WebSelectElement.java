package io.toast.tk.adapter.web.component;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import io.toast.tk.adapter.web.HasSelect;
import io.toast.tk.automation.driver.web.SeleniumSynchronizedDriver;
import io.toast.tk.core.runtime.IWebElementDescriptor;

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
