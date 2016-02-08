package com.synaptix.toast.adapter.web.component;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.synaptix.toast.adapter.web.HasClickAction;
import com.synaptix.toast.adapter.web.ISyncCall;
import com.synaptix.toast.automation.driver.web.SeleniumSynchronizedDriver;
import com.synaptix.toast.core.report.SuccessResult;
import com.synaptix.toast.core.runtime.IWebElementDescriptor;
import com.synaptix.toast.dao.domain.api.test.ITestResult;

public class WebLinkElement extends WebAutoElement implements HasClickAction {

	public WebLinkElement(
		IWebElementDescriptor element,
		SeleniumSynchronizedDriver driver) {
		super(element, driver);
	}

	public WebLinkElement(
		IWebElementDescriptor element) {
		super(element);
	}

	@Override
	public ITestResult click() {
		safeAction(new ISyncCall() {

			@Override
			public void execute(
				WebElement e) {
				e.click();
			}
		});
		return new SuccessResult();
	}

	@Override
	public void dbClick() {
		safeAction(new ISyncCall() {
			@Override
			public void execute(
				WebElement e) {
				Actions action = new Actions(((SeleniumSynchronizedDriver)frontEndDriver).getWebDriver());
				action.doubleClick(e);
				action.perform();
				e.click();
			}
		});
	}
}
