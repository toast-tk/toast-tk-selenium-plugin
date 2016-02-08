package com.synaptix.toast.adapter.web.component;

import com.synaptix.toast.adapter.web.HasStringValue;
import com.synaptix.toast.adapter.web.HasTextInput;
import com.synaptix.toast.automation.driver.web.SeleniumSynchronizedDriver;
import com.synaptix.toast.core.report.SuccessResult;
import com.synaptix.toast.core.runtime.IWebElementDescriptor;
import com.synaptix.toast.dao.domain.api.test.ITestResult;

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
