package com.synaptix.toast.adapter.web;

import static com.synaptix.toast.core.adapter.ActionAdapterSentenceRef.VALUE_REGEX;
import static com.synaptix.toast.core.adapter.ActionAdapterSentenceRef.WEB_COMPONENT;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.google.inject.Inject;
import com.synaptix.toast.adapter.web.component.DefaultWebPage;
import com.synaptix.toast.adapter.web.component.WebAutoElement;
import com.synaptix.toast.adapter.web.component.WebSelectElement;
import com.synaptix.toast.automation.driver.web.DriverFactory;
import com.synaptix.toast.automation.driver.web.SeleniumSynchronizedDriver;
import com.synaptix.toast.core.adapter.ActionAdapterKind;
import com.synaptix.toast.core.annotation.Action;
import com.synaptix.toast.core.annotation.ActionAdapter;
import com.synaptix.toast.core.report.FailureResult;
import com.synaptix.toast.core.report.SuccessResult;
import com.synaptix.toast.core.runtime.IFeedableWebPage;
import com.synaptix.toast.core.runtime.IWebAutoElement;
import com.synaptix.toast.dao.domain.api.test.ITestResult;
import com.synaptix.toast.runtime.IActionItemRepository;

@ActionAdapter(name="default-web-driver", value= ActionAdapterKind.web)
public abstract class AbstractWebActionAdapter {

	protected final SeleniumSynchronizedDriver driver;
	protected final IActionItemRepository repo;

	@Inject
	public AbstractWebActionAdapter(IActionItemRepository repository) {
		this.repo = repository;
		driver = new SeleniumSynchronizedDriver(DriverFactory.getFactory().getChromeDriver());
		for (IFeedableWebPage page : repository.getWebPages()) {
			((DefaultWebPage)page).setDriver(driver);
		}
	}

	@Action(id="navigate", action = "Open browser at "+ VALUE_REGEX, description = "")
	public ITestResult openBrowserIn(String url) {
		if(!url.startsWith("http")){
			url = "http://" + url;
		}
		driver.getWebDriver().get(url);
		return new SuccessResult();
	}

	@Action(id="type_in_web_component", action = "Type " + VALUE_REGEX + " in " + WEB_COMPONENT, description = "")
	public ITestResult typeIn(String text, IWebAutoElement<WebElement> pageField) throws Exception {
		pageField.getWebElement().sendKeys(text);
		return new SuccessResult();
	}

	@Action(id="click_on_web_component", action = "Click on " + WEB_COMPONENT, description = "")
	public ITestResult clickOn(IWebAutoElement<WebElement> pageField) throws Exception {
		if(pageField.getWebElement() != null){
			pageField.getWebElement().click();
			return new SuccessResult();
		}
		return new FailureResult("Selector not found: " + pageField.getDescriptor().getLocator());
	}
	
	@Action(id="select_in_web_component", action = "Select " + VALUE_REGEX + " in " + WEB_COMPONENT, description = "")
	public ITestResult selectAtPos(String pos, IWebAutoElement<WebElement> pageFieldAuto) throws Exception {
		WebSelectElement pageField = (WebSelectElement) pageFieldAuto;
		pageField.selectByIndex(Integer.valueOf(pos));
		return new SuccessResult(pageField.getWebElement().getText());
	}

	@Action(id="component_alias", action = "With " + WEB_COMPONENT + " as " + VALUE_REGEX, description = "")
	public ITestResult setComponentAlias(IWebAutoElement<WebElement> pageFieldAuto, String alias) throws Exception {
		repo.getWebComponents().put(alias, pageFieldAuto);
		return new SuccessResult("Component stored with alias: " + alias);
	}
	
	@Action(id="select_at_component", action = "Select the " + VALUE_REGEX + "th " + WEB_COMPONENT + " as " + VALUE_REGEX, description = "")
	public ITestResult selectComponent(String pos, IWebAutoElement<WebElement> pageFieldAuto, String varName) throws Exception {
		int componentPos = Integer.valueOf(pos) - 1;
		pageFieldAuto.getDescriptor().setPosition(componentPos);
		repo.getWebComponents().put(varName, pageFieldAuto);
		return new SuccessResult();
	}
	
	@Action(id="select_at_component_alias", action = "Select the " + VALUE_REGEX + "th " + VALUE_REGEX + " as " + VALUE_REGEX, description = "")
	public ITestResult selectComponent(String pos, 
			String alias,
			String varName) throws Exception {
		WebAutoElement webAutoElement = (WebAutoElement)repo.getWebComponents().get(alias);
		return selectComponent(pos, webAutoElement, varName);
	}
	
	@Action(id="web_component_exists", action = WEB_COMPONENT + " exists", description = "")
	public ITestResult checkExist(WebAutoElement element) {
		if (element.getWebElement().isDisplayed()) {
			return new SuccessResult("Element is available !");
		} else {
			return new FailureResult("Element is not available !");
		}
	}
	
	@Action(id="web_component_exists_alias", action = VALUE_REGEX + " exists", description = "")
	public ITestResult checkExist(String alias) {
		WebAutoElement webAutoElement = (WebAutoElement)repo.getWebComponents().get(alias);
		return checkExist(webAutoElement);
	}
	
	@Action(id="count_component", action = "Count " + WEB_COMPONENT, description = "")
	public ITestResult count(WebAutoElement element) {
		if (element.getWebElement().isDisplayed()) {
			List<WebElement> allWebElements = element.getAllWebElements();
			return new SuccessResult("Found " + allWebElements.size()+ " Element !");
		} else {
			return new FailureResult("No element found with locator: " + element.getDescriptor().getLocator());
		}
	}
	
	@Action(id="count_component_alias", action = "Count " + VALUE_REGEX, description = "")
	public ITestResult count(String alias) {
		WebAutoElement iWebAutoElement = (WebAutoElement) repo.getWebComponents().get(alias);
		if (iWebAutoElement.getWebElement().isDisplayed()) {
			List<WebElement> allWebElements = iWebAutoElement.getAllWebElements();
			return new SuccessResult("Found " + allWebElements.size()+ " Element !");
		} else {
			return new FailureResult("No element found with locator: " + iWebAutoElement.getDescriptor().getLocator());
		}
	}
	
	@Action(id="get_inner_text_alias", action = "Read " + VALUE_REGEX, description = "")
	public ITestResult read(String alias) {
		WebAutoElement webAutoElement = (WebAutoElement)repo.getWebComponents().get(alias);
		return read(webAutoElement);
	}
	
	@Action(id="get_inner_text", action = "Read " + WEB_COMPONENT, description = "")
	public ITestResult read(WebAutoElement element) {
		if (element.getWebElement().isDisplayed()) {
			return new SuccessResult("Element value: " + element.getWebElement().getText());
		} else {
			return new FailureResult("No element found with locator: " + element.getDescriptor().getLocator());
		}
	}
	
	@Action(id="close_browser", action = "Close browser", description = "")
	public ITestResult closeBrowser() {
		driver.getWebDriver().quit();
		return new SuccessResult();
	}
}
