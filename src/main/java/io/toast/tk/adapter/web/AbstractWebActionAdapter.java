package io.toast.tk.adapter.web;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.List;

import org.openqa.selenium.WebElement;

import com.google.inject.Inject;

import io.toast.tk.adapter.web.component.DefaultWebPage;
import io.toast.tk.adapter.web.component.WebAutoElement;
import io.toast.tk.adapter.web.component.WebSelectElement;
import io.toast.tk.automation.driver.web.DriverFactory;
import io.toast.tk.automation.driver.web.SeleniumSynchronizedDriver;
import io.toast.tk.core.adapter.ActionAdapterKind;
import io.toast.tk.core.annotation.Action;
import io.toast.tk.core.annotation.ActionAdapter;
import io.toast.tk.core.runtime.IFeedableWebPage;
import io.toast.tk.core.runtime.IWebAutoElement;
import io.toast.tk.dao.core.report.FailureResult;
import io.toast.tk.dao.core.report.SuccessResult;
import io.toast.tk.dao.domain.api.test.ITestResult;
import io.toast.tk.runtime.IActionItemRepository;

import static io.toast.tk.core.adapter.ActionAdapterSentenceRef.VALUE_REGEX;
import static io.toast.tk.core.adapter.ActionAdapterSentenceRef.WEB_COMPONENT;

@ActionAdapter(name="default-web-driver", value= ActionAdapterKind.web)
public abstract class AbstractWebActionAdapter {

	protected final SeleniumSynchronizedDriver driver;
	protected final IActionItemRepository repo;

	@Inject
	public AbstractWebActionAdapter(IActionItemRepository repository) {
		this.repo = repository;
		driver = new SeleniumSynchronizedDriver(DriverFactory.getFactory().getConfigWebDriver());
		for (IFeedableWebPage page : repo.getWebPages()) {
			((DefaultWebPage)page).setDriver(driver);
		}
	}

	@Action(id="navigate", action = "Open browser at "+ VALUE_REGEX, description = "")
	public ITestResult openBrowserIn(String url) {
		if(!url.startsWith("http")){
			url = "http://" + url;
		}
		driver.getWebDriver().get(url);
		for (IFeedableWebPage page : repo.getWebPages()) {
			((DefaultWebPage)page).setDriver(driver);
		}
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
	public ITestResult selectComponentAt(String pos, IWebAutoElement<WebElement> pageFieldAuto, String varName) throws Exception {
		int componentPos = Integer.valueOf(pos) - 1;
		pageFieldAuto.getDescriptor().setPosition(componentPos);
		repo.getWebComponents().put(varName, pageFieldAuto);
		return new SuccessResult();
	}
	
	@Action(id="select_at_component_alias", action = "Select the " + VALUE_REGEX + "th " + VALUE_REGEX + " as " + VALUE_REGEX, description = "")
	public ITestResult selectComponentByAlias(String pos,
			String alias,
			String varName) throws Exception {
		WebAutoElement webAutoElement = (WebAutoElement)repo.getWebComponents().get(alias);
		return selectComponentAt(pos, webAutoElement, varName);
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
	
	@Action(id="double_click_on_web_component", action = "Double click on " + WEB_COMPONENT, description = "")
	public ITestResult doubleClickOn(IWebAutoElement<WebElement> pageField) throws Exception {
		if(pageField.getWebElement().isDisplayed()) {
			int x = pageField.getWebElement().getLocation().getX();
			int y = pageField.getWebElement().getLocation().getY();
			Robot robot = new Robot();
			robot.mouseMove(x, y);
			robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			return new SuccessResult();
		}
		return new FailureResult("Element not found : " + pageField.getDescriptor().getLocator());
	}
}
