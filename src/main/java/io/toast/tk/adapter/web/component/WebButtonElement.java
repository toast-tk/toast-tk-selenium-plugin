package io.toast.tk.adapter.web.component;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import io.toast.tk.adapter.web.HasClickAction;
import io.toast.tk.automation.driver.web.SeleniumSynchronizedDriver;
import io.toast.tk.core.runtime.IWebElementDescriptor;
import io.toast.tk.dao.core.report.SuccessResult;
import io.toast.tk.dao.domain.api.test.ITestResult;

public class WebButtonElement extends WebAutoElement implements HasClickAction {

	public WebButtonElement(
		IWebElementDescriptor element,
		SeleniumSynchronizedDriver driver) {
		super(element, driver);
	}

	public WebButtonElement(
		IWebElementDescriptor element) {
		super(element);
	}

	@Override
	public ITestResult click() {
		WebElement find = frontEndDriver.find(descriptor);
		find.click();
		return new SuccessResult();
	}

	@Override
	public void dbClick() {
		Actions action = new Actions(((SeleniumSynchronizedDriver)frontEndDriver).getWebDriver());
		WebElement find = frontEndDriver.find(descriptor);
		Action doubleClick = action.doubleClick(find).build();
		doubleClick.perform();
	}
}
