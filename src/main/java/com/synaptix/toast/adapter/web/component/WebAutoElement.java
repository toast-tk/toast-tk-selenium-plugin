package com.synaptix.toast.adapter.web.component;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.synaptix.toast.adapter.web.ISyncCall;
import com.synaptix.toast.automation.driver.web.SynchronizedDriver;
import com.synaptix.toast.core.runtime.IFeedableWebPage;
import com.synaptix.toast.core.runtime.IWebAutoElement;
import com.synaptix.toast.core.runtime.IWebElement;

public class WebAutoElement implements IWebAutoElement<WebElement>{

	protected IFeedableWebPage container;

	protected IWebElement wrappedElement;

	protected SynchronizedDriver<WebElement, ?> frontEndDriver;

	protected void setWrappedElement(
		IWebElement wrappedElement) {
		this.wrappedElement = wrappedElement;
	}

	protected SynchronizedDriver<WebElement, ?> getFrontEndDriver() {
		return frontEndDriver;
	}

	public void setFrontEndDriver(
			SynchronizedDriver frontEndDriver) {
		this.frontEndDriver = frontEndDriver;
	}

	public WebAutoElement(
		IWebElement element,
		SynchronizedDriver driver) {
		this.wrappedElement = element;
		this.frontEndDriver = driver;
	}

	public WebAutoElement(
		IWebElement element) {
		this.wrappedElement = element;
	}

	public WebAutoElement() {
	}

	public boolean exists() {
		return frontEndDriver.find(wrappedElement) != null;
	}

	@Override
	public WebElement getWebElement() {
		return frontEndDriver.find(wrappedElement);
	}
	
	@Override
	public List<WebElement> getAllWebElements() {
		return frontEndDriver.findAll(wrappedElement);
	}

	protected void safeAction(ISyncCall res) {
		WebElement find = frontEndDriver.find(wrappedElement);
		if(find != null) {
			res.execute(find);
		}
	}

	@Override
	public IWebElement getWrappedElement() {
		return wrappedElement;
	}

	@Override
	public IFeedableWebPage getContainer() {
		return container;
	}

	@Override
	public void setContainer(IFeedableWebPage container) {
		this.container = container;
	}

}
