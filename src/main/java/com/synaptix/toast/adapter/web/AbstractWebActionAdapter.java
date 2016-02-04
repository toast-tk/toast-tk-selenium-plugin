package com.synaptix.toast.adapter.web;

import static com.synaptix.toast.core.adapter.ActionAdapterSentenceRef.VALUE_REGEX;
import static com.synaptix.toast.core.adapter.ActionAdapterSentenceRef.WEB_COMPONENT;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.google.inject.Inject;
import com.synaptix.toast.adapter.web.component.DefaultWebPage;
import com.synaptix.toast.adapter.web.component.WebAutoElement;
import com.synaptix.toast.adapter.web.component.WebContainerElement;
import com.synaptix.toast.adapter.web.component.WebSelectElement;
import com.synaptix.toast.automation.driver.web.DriverFactory;
import com.synaptix.toast.automation.driver.web.SeleniumSynchronizedDriver;
import com.synaptix.toast.core.adapter.ActionAdapterKind;
import com.synaptix.toast.core.annotation.Action;
import com.synaptix.toast.core.annotation.ActionAdapter;
import com.synaptix.toast.core.report.TestResult;
import com.synaptix.toast.core.report.TestResult.ResultKind;
import com.synaptix.toast.core.runtime.IFeedableWebPage;
import com.synaptix.toast.core.runtime.IWebAutoElement;
import com.synaptix.toast.runtime.IActionItemRepository;

@ActionAdapter(name="default-web-driver", value= ActionAdapterKind.web)
public abstract class AbstractWebActionAdapter {

	private final SeleniumSynchronizedDriver driver;
	private final IActionItemRepository repo;

	static{
		System.setProperty("webdriver.chrome.driver", "D:\\Apps\\chromedriver.exe");
	}
	@Inject
	public AbstractWebActionAdapter(IActionItemRepository repository) {
		this.repo = repository;
		driver = new SeleniumSynchronizedDriver(DriverFactory.getFactory().getChromeDriver());
		for (IFeedableWebPage page : repository.getWebPages()) {
			((DefaultWebPage)page).setDriver(driver);
		}
	}

	@Action(id="navigate", action = "Open browser at "+ VALUE_REGEX, description = "")
	public TestResult openBrowserIn(String url) {
		if(!url.startsWith("http")){
			url = "http://" + url;
		}
		driver.getWebDriver().get(url);
		return new TestResult();
	}

	@Action(id="type_in_web_component", action = "Type " + VALUE_REGEX + " in " + WEB_COMPONENT, description = "")
	public TestResult typeIn(String text, IWebAutoElement<WebElement> pageField) throws Exception {
		pageField.getWebElement().sendKeys(text);
		return new TestResult();
	}

	@Action(id="click_on_web_component", action = "Click on " + WEB_COMPONENT, description = "")
	public TestResult ClickOn(IWebAutoElement<WebElement> pageField) throws Exception {
		pageField.getWebElement().click();
		return new TestResult();
	}
	

	@Action(id="click_on_var", action = "Click on " + VALUE_REGEX, description = "")
	public TestResult ClickOn(String pageField) throws Exception {
		if(pageField.startsWith("$")){
			String varName = StringUtils.split(pageField, ".")[0];
			String fieldName = StringUtils.split(pageField, ".")[1];
			WebContainerElement container = (WebContainerElement)repo.getUserVariables().get(varName);
			String containerName = container.getDescriptor().getName();
			String pageName = StringUtils.split(containerName, ":")[0];
			IFeedableWebPage page = repo.getWebPage(pageName);
			IWebAutoElement<WebElement> autoElement = (IWebAutoElement<WebElement>) page.getAutoElement(fieldName);
			WebElement findElement = container.getWebElement().findElement(By.cssSelector(autoElement.getDescriptor().getLocator()));
			findElement.click();
			return new TestResult();
		}
		return new TestResult(pageField);
	}

	@Action(id="select_in_web_component", action = "Select " + VALUE_REGEX + " in " + WEB_COMPONENT, description = "")
	public TestResult SelectAtPos(String pos, IWebAutoElement<WebElement> pageFieldAuto) throws Exception {
		WebSelectElement pageField = (WebSelectElement) pageFieldAuto;
		pageField.selectByIndex(Integer.valueOf(pos));
		return new TestResult();
	}
	
	@Action(id="select_at_component", action = "Select the " + VALUE_REGEX + "th " + WEB_COMPONENT + " as " + VALUE_REGEX, description = "")
	public TestResult SelectComponent(String pos, 
			IWebAutoElement<WebElement> pageFieldAuto,
			String varName) throws Exception {
		int componentPos = Integer.valueOf(pos) - 1;
		pageFieldAuto.getDescriptor().setPosition(componentPos);
		repo.getUserVariables().put(varName, pageFieldAuto);
		return new TestResult();
	}
	
	@Action(id="web_component_exists", action = WEB_COMPONENT + " exists", description = "")
	public TestResult checkExist(WebAutoElement element) {
		if (element != null) {
			if (element.getWebElement().isDisplayed()) {
				return new TestResult("Element is available !", ResultKind.SUCCESS);
			} else {
				return new TestResult("Element is not available !", ResultKind.FAILURE);
			}
		}
		return null;
	}
	

	@Action(id="count_component", action = "Count " + WEB_COMPONENT, description = "")
	public TestResult count(WebAutoElement element) {
		if (element != null) {
			if (element.getWebElement().isDisplayed()) {
				List<WebElement> allWebElements = element.getAllWebElements();
				return new TestResult("Found " + allWebElements.size()+ " Element !", ResultKind.SUCCESS);
			} else {
				return new TestResult("No element found with locator: " + element.getDescriptor().getLocator(), ResultKind.FAILURE);
			}
		}
		return null;
	}
	
	
	@Action(id="get_inner_text", action = "Read " + WEB_COMPONENT, description = "")
	public TestResult read(WebAutoElement element) {
		if (element != null) {
			if (element.getWebElement().isDisplayed()) {
				return new TestResult("Element value: " + element.getWebElement().getText(), ResultKind.SUCCESS);
			} else {
				return new TestResult("No element found with locator: " + element.getDescriptor().getLocator(), ResultKind.FAILURE);
			}
		}
		return null;
	}
	
	@Action(id="close_browser", action = "Close browser", description = "")
	public TestResult closeBrowser() {
		driver.getWebDriver().quit();
		return new TestResult();
	}
}
