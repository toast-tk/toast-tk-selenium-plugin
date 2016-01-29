package com.synaptix.toast.automation.driver.web;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.synaptix.toast.automation.api.IMiniResult;
import com.synaptix.toast.core.runtime.IWebElement;

public class SeleniumSynchronizedDriver implements SynchronizedDriver<WebElement, WebDriver> {

	private final WebDriver driver;

	private int defaultRepeat = 5;

	private int defaultTimeout = 1000;

	public SeleniumSynchronizedDriver() {
		this.driver = DriverFactory.getFactory().getChromeDriver();
		// GetEval("selenium.browserbot.setShouldHighlightElement(true)");
	}

	public SeleniumSynchronizedDriver(
		WebDriver driver,
		int defaultRepeat,
		int defaultTimeout) {
		this.driver = driver;
		if(defaultRepeat > -1) {
			this.defaultRepeat = defaultRepeat;
		}
		if(defaultTimeout > -1) {
			this.defaultTimeout = defaultTimeout;
		}
	}

	public SeleniumSynchronizedDriver(
		WebDriver driver) {
		this.driver = driver;
	}

	public WebElement doSynchronizedSelection(
		final IWebElement element) {
		SeleniumHelper.waitCondition(defaultRepeat, defaultTimeout, new IMiniResult() {

			@Override
			public boolean result() {
				return SeleniumHelper.positionSelect(driver, element) != null;
			}
		});
		WebElement positionSelect = SeleniumHelper.positionSelect(driver, element);
		return positionSelect;
	}

	public void open(
		String demoLoginUrl) {
		driver.get(demoLoginUrl);
	}
	
	@Override
	public WebDriver getWebDriver() {
		return driver;
	}

	public void doSynchronizedOnUrl(
		final String urlFragment) {
		SeleniumHelper.waitCondition(defaultRepeat, defaultTimeout, new IMiniResult() {

			@Override
			public boolean result() {
				return driver.getCurrentUrl().contains(urlFragment);
			}
		});
	}

	@Override
	public WebElement find(
		IWebElement element) {
		WebElement doSynchronizedSelection = doSynchronizedSelection(element);
		return doSynchronizedSelection;
	}
	
	@Override
	public List<WebElement> findAll(
		IWebElement element) {
		List<WebElement> result = doSynchronizedMultipleSelection(element);
		return result;
	}

	private List<WebElement> doSynchronizedMultipleSelection(IWebElement element) {
		SeleniumHelper.waitCondition(defaultRepeat, defaultTimeout, new IMiniResult() {

			@Override
			public boolean result() {
				return SeleniumHelper.positionSelect(driver, element) != null;
			}
		});
		List<WebElement> result = SeleniumHelper.selectAll(driver, element);
		return result;
	}
	
	
}
