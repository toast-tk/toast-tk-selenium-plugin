package io.toast.tk.adapter.web.component;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import io.toast.tk.adapter.web.HasClickAction;
import io.toast.tk.adapter.web.ISyncCall;
import io.toast.tk.automation.driver.web.SeleniumSynchronizedDriver;
import io.toast.tk.core.runtime.IWebElementDescriptor;
import io.toast.tk.dao.core.report.SuccessResult;
import io.toast.tk.dao.domain.api.test.ITestResult;

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
