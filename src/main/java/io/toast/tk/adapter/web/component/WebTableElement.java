package io.toast.tk.adapter.web.component;

import java.util.List;

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.toast.tk.automation.driver.web.SeleniumSynchronizedDriver;
import io.toast.tk.core.runtime.IWebElementDescriptor;

public class WebTableElement extends WebAutoElement {

	private static final String ROW_VALUE_SEPARATOR = "|";

	public WebTableElement(
			IWebElementDescriptor element,
			SeleniumSynchronizedDriver driver) {
		super(element, driver);
	}

	public WebTableElement(
			IWebElementDescriptor element) {
		super(element);
	}

	public int countLines() throws IllegalAccessException {
		WebElement table = getTableWebElement();
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		return rows.size();
	}

	private WebElement getTableWebElement() throws IllegalAccessException {
		WebElement table = frontEndDriver.find(descriptor);

		if (table == null) {
			throw new NullArgumentException("WebElement table is null");
		} else if (table.getTagName().toLowerCase().equals("table")) {
			throw new IllegalAccessException(table.getTagName() + " not supported !");
		}

		return table;
	}

	public int getColumnIndexByName(String columnTitle) throws IllegalAccessException {
		WebElement table = getTableWebElement();

		WebElement tableHead = table.findElement(By.tagName("thead"));
		final WebElement headRow;
		if (tableHead != null) {
			headRow = tableHead.findElement(By.name("tr"));
		} else {
			headRow = table.findElement(By.name("tr"));
		}
		if (headRow == null) {
			throw new IllegalAccessException("targeted table contains no row !");
		}
		List<WebElement> columns = headRow.findElements(By.tagName("td"));
		int i = -1;
		for (WebElement column : columns) {
			i++;
			if (column.getText().toLowerCase().equalsIgnoreCase(columnTitle.toLowerCase())) {
				return i;
			}
		}
		return i;
	}

	public String getCellValueAt(int rowIndex, String columnName) throws IllegalAccessException {
		int columnIndex = getColumnIndexByName(columnName);
		if (columnIndex > -1) {
			String row = getRow(rowIndex);
			String[] rowSplit = row.split(ROW_VALUE_SEPARATOR);
			if (rowIndex > rowSplit.length || rowIndex < 0) {
				throw new IllegalAccessException("Current table size: " + rowSplit.length + " - " + rowIndex + " is out of range !");
			}
			return rowSplit[columnIndex];
		}
		return columnName;
	}

	public String getRow(int rowIndex) throws IllegalAccessException {
		WebElement table = getTableWebElement();

		List<WebElement> findElements = table.findElements(By.tagName("tr"));
		if (rowIndex > findElements.size() || rowIndex < 0) {
			throw new IllegalAccessException("Current table size: " + findElements.size() + " - " + rowIndex + " is out of range !");
		}
		WebElement row = findElements.get(rowIndex);
		List<WebElement> cols = row.findElements(By.tagName("td"));
		return StringUtils.join(cols, ROW_VALUE_SEPARATOR);
	}

	public boolean contains(String text) throws IllegalAccessException {
		WebElement table = getTableWebElement();
		return table.getText().toLowerCase().contains(text.toLowerCase());
	}
}
