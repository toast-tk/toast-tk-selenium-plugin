package io.toast.tk.automation.driver.web;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.toast.tk.adapter.constant.AdaptersConfig;
import io.toast.tk.adapter.constant.AdaptersConfigProvider;

public class DriverFactory {

	private static final Logger LOG = LogManager.getLogger(DriverFactory.class);
	private static final DriverFactory INSTANCE = new DriverFactory();

	private FirefoxDriver firefoxDriver;
	private AdaptersConfig config;

	private DriverFactory() {
		AdaptersConfigProvider configProvider = new AdaptersConfigProvider();
		this.config = configProvider.get();
	}

	public static DriverFactory getFactory() {
		return INSTANCE;
	}
	
	public RemoteWebDriver getConfigWebDriver() {
		if (config.getWebDriver() == null) {
			return getChromeDriver();
		}
		WebDriverEnum webDriverEnum = WebDriverEnum.valueOf(config.getWebDriver().toUpperCase());
		switch (webDriverEnum) {
		case CHROME:
			return getChromeDriver();
		case FIREFOX:
			return getFirefoxDriver();
		case IE:
			return getInternetExplorerDriver();
		default:
			return getFirefoxDriver();
		}
	}

	public InternetExplorerDriver getInternetExplorerDriver() {
		System.setProperty("webdriver.ie.driver", config.getWebDriverPath());
		InternetExplorerDriver driver = new InternetExplorerDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}

	public ChromeDriver getChromeDriver() {
		System.setProperty("webdriver.chrome.driver", config.getWebDriverPath());
		final ChromeDriver driver;
		if(config.isSSl()){
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability("chrome.switches", Arrays.asList("--ignore-certificate-errors"));
			driver = new ChromeDriver(capabilities);
		}else{
			driver = new ChromeDriver();
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}

	public FirefoxDriver getFirefoxDriver() {
		if(firefoxDriver == null) {
			if (config.getBrowserPath() != null) {
				File pathBinary = new File(config.getBrowserPath());
				FirefoxBinary binary = new FirefoxBinary(pathBinary);
				FirefoxProfile firefoxPro = new FirefoxProfile();    
				firefoxDriver = new FirefoxDriver(binary, firefoxPro);
			} else {
				firefoxDriver = new FirefoxDriver();
			}
			firefoxDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
		return firefoxDriver;
	}

	public RemoteWebDriver getRemoteDriver(
		boolean canTakeScreenShots)
		throws MalformedURLException {
		RemoteWebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), DesiredCapabilities.firefox());
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}

	public enum WebDriverEnum {
		CHROME, FIREFOX, IE
	}

}