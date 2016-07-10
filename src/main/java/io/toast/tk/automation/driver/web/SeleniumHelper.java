package io.toast.tk.automation.driver.web;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.toast.tk.automation.api.IMiniResult;
import io.toast.tk.core.runtime.IWebElementDescriptor;

public class SeleniumHelper {


	private static final Logger LOG = LogManager.getLogger(SeleniumHelper.class);

	public static void wait(
		int timeMs) {
		try {
			Thread.sleep(timeMs);
		}
		catch(InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	public static void waitCondition(
		int retry,
		int timeMs,
		IMiniResult condition) {
		try {
			for(int i = 0; i <= retry; i++) {
				Thread.sleep(timeMs);
				if(condition.result()) {
					break;
				}
			}
		}
		catch(InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	public static WebElement positionSelect(
		WebDriver driver,
		IWebElementDescriptor item) {
		WebElement el = null;
		try {
			switch(item.getMethod()) {
				case CSS :
					return driver.findElements(By.cssSelector(item.getLocator())).get(item.getPosition());
				case XPATH :
					return driver.findElements(By.xpath(item.getLocator())).get(item.getPosition());
				case ID :
					return driver.findElement(By.id(item.getLocator()));
				default :
					return null;
			}
		}
		catch(IndexOutOfBoundsException e) {
			LOG.error("Locator: " + item.getLocator() + " - Position: " + item.getPosition() + " - Not Found !");
		}
		return el;
	}

	public static WebElement singleSelect(
		WebDriver driver,
		IWebElementDescriptor item) {
		switch(item.getMethod()) {
			case CSS :
				return driver.findElement(By.cssSelector(item.getLocator()));
			case XPATH :
				return driver.findElement(By.xpath(item.getLocator()));
			case ID :
				return driver.findElement(By.id(item.getLocator()));
			default :
				return null;
		}
	}

	public static List<WebElement> selectAll(WebDriver driver,
			IWebElementDescriptor item) {
		List<WebElement> elements = null;
		try {
			switch(item.getMethod()) {
				case CSS :
					return driver.findElements(By.cssSelector(item.getLocator()));
				case XPATH :
					return driver.findElements(By.xpath(item.getLocator()));
				default :
					return null;
			}
		}
		catch(IndexOutOfBoundsException e) {
			System.err.println("Locator: " + item.getLocator() + " - Position: " + item.getPosition());
			e.printStackTrace();
		}
		return elements;
	}
}
