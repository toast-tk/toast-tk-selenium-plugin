package io.toast.tk.adapter.web.component;

import io.toast.tk.adapter.web.HasStringValue;
import io.toast.tk.adapter.web.HasTextInput;
import io.toast.tk.automation.driver.web.SeleniumSynchronizedDriver;
import io.toast.tk.core.runtime.IWebElementDescriptor;
import io.toast.tk.dao.core.report.SuccessResult;
import io.toast.tk.dao.domain.api.test.ITestResult;

public class WebInputElement extends WebAutoElement implements HasTextInput, HasStringValue {

	public WebInputElement(
		IWebElementDescriptor element,
		SeleniumSynchronizedDriver driver) {
		super(element, driver);
	}

	public WebInputElement(
		IWebElementDescriptor element) {
		super(element);
	}

	@Override
	public ITestResult setInput(
		String e) {
		frontEndDriver.find(descriptor).sendKeys(e);
		return new SuccessResult();
	}

	@Override
	public String getValue() {
		return frontEndDriver.find(descriptor).getText();
	}

}
