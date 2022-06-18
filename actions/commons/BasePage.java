package commons;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
	
	public static BasePage getBasePage() {
		return new BasePage();
	}
	// protected: cac ham chi co the dc goi khi xai extends
	// test cases user_001 (1 & 2) examples cho viec ko goi dc
	// test case user_001 3 examples cho viec goi dc khi xai extends
	protected void openPageUrl(WebDriver driver, String pageUrl) {
		driver.get(pageUrl);
	}

	protected String getTitle(WebDriver driver) {
		return driver.getTitle();
	}

	protected String getCurrentUrl(WebDriver driver) {
		return driver.getCurrentUrl();
	}

	protected String getPageSourceCode(WebDriver driver) {
		return driver.getPageSource();
	}

	protected void backToPage(WebDriver driver) {
		driver.navigate().back();
	}

	protected void forwardToPage(WebDriver driver) {
		driver.navigate().forward();
	}

	protected void refreshCurrentPage(WebDriver driver) {
		driver.navigate().refresh();
	}

	protected Alert waitForAlertPresence(WebDriver driver) {
		WebDriverWait explicitWait = new WebDriverWait(driver, 30);
		return explicitWait.until(ExpectedConditions.alertIsPresent());
	}

	protected void acceptAlert(WebDriver driver) {
		// call wait for alert to present
		waitForAlertPresence(driver).accept();
	}

	protected void cancelAlert(WebDriver driver) {
		// call wait for alert to present
		waitForAlertPresence(driver).dismiss();
	}

	protected String getAlertText(WebDriver driver) {
		// call wait for alert to present
		return waitForAlertPresence(driver).getText();
	}

	protected void sendkeyToAlert(WebDriver driver, String textToSend) {
		// call wait for alert to present
		waitForAlertPresence(driver).sendKeys(textToSend);
	}

	protected void switchWindowByID(WebDriver driver, String parentID) {
		Set<String> allWindows = driver.getWindowHandles();

		for (String runWindow : allWindows) {
			if (!runWindow.equals(parentID)) {
				driver.switchTo().window(runWindow);
				break;
			}
		}
	}

	protected void switchWindowByTitle(WebDriver driver, String title) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String runWindows : allWindows) {
			driver.switchTo().window(runWindows);
			String currentWindow = driver.getTitle();
			if (currentWindow.equals(title)) {
				break;
			}
		}
	}

	protected void closeAllWindowsExceptParent(WebDriver driver, String parentID) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String runWindows : allWindows) {
			if (!runWindows.equals(parentID)) {
				driver.switchTo().window(runWindows);
				driver.close();
			}
		}
		driver.switchTo().window(parentID);
	}

	private By getByXpath(String xpathLocator) {
		return By.xpath(xpathLocator);
	}

	private WebElement getWebElement(WebDriver driver, String xpathLocator) {
		return driver.findElement(getByXpath(xpathLocator));
	}

	private List<WebElement> getListOfWebElements(WebDriver driver, String xpathLocator) {
		return driver.findElements(getByXpath(xpathLocator));
	}

	protected void clickToElement(WebDriver driver, String xpathLocator) {
		getWebElement(driver, xpathLocator).click();
	}

	protected void sendkeyToElement(WebDriver driver, String xpathLocator, String keysToSend) {
		WebElement element = getWebElement(driver, xpathLocator);
		element.clear();
		element.sendKeys(keysToSend);
	}

	protected String getElementText(WebDriver driver, String xpathLocator) {
		return getWebElement(driver, xpathLocator).getText();
	}

	protected String getElementAttribute(WebDriver driver, String xpathLocator, String attributeName) {
		return getWebElement(driver, xpathLocator).getAttribute(attributeName);
	}

	protected String getElementCssValue(WebDriver driver, String xpathLocator, String cssName) {
		return getWebElement(driver, xpathLocator).getAttribute(cssName);
	}

	protected int getHexaColorFromRGBA(WebDriver driver, String xpathLocator) {
		return getListOfWebElements(driver, xpathLocator).size();
	}

	protected String getElementSize(String rgbaValue) {
		return Color.fromString(rgbaValue).asHex();
	}

	protected void sleepInSecond(long timeInSecond) {
		try {
			Thread.sleep(timeInSecond * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Drop-down list
	protected void selectItemInDefaultDropdown(WebDriver driver, String xpathLocator, String itemName) {
		Select select = new Select(getWebElement(driver, xpathLocator));
		select.selectByValue(itemName);
	}

	protected String getSelectedItemInDefaultDropdown(WebDriver driver, String xpathLocator) {
		Select select = new Select(getWebElement(driver, xpathLocator));
		return select.getFirstSelectedOption().getText();
	}

	protected boolean isDropdownMultiple(WebDriver driver, String xpathLocator) {
		Select select = new Select(getWebElement(driver, xpathLocator));
		return select.isMultiple();
	}

	protected void selectItemInCustomDropdown(WebDriver driver, String parentLocator, String itemLocator,
			String expectedItem) {
		// Wait until this element to be click-able
		WebDriverWait explicitWait = new WebDriverWait(driver, 30);
		explicitWait.until(ExpectedConditions.elementToBeClickable(getByXpath(parentLocator)));
		// Move to element to click to open the drop-down list
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].click();", getWebElement(driver, parentLocator));
		sleepInSecond(3);
		// Wait until all the options/items in drop-down list to be presented
		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByXpath(itemLocator)));
		// Store all options/items into a list of web-element
		List<WebElement> allItems = getListOfWebElements(driver, itemLocator);

		System.out.println("Item size: " + allItems.size());

		for (WebElement item : allItems) {
			String itemName = item.getText();
			System.out.println(itemName);
			if (itemName.trim().equals(expectedItem)) { // trim() is used to remove space in the front/back of text
				jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
				sleepInSecond(2);
				item.click();
				sleepInSecond(2);
				break;
			}
		}
	}

	protected void selectItemInEditableDropdown(WebDriver driver, String editableLocator, String itemLocator,
			String expectedItem) {
		WebDriverWait explicitWait = new WebDriverWait(driver, 30);
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		explicitWait.until(ExpectedConditions.elementToBeClickable(getByXpath(editableLocator)));

		getWebElement(driver, editableLocator).clear();
		sleepInSecond(1);

		getWebElement(driver, editableLocator).sendKeys(expectedItem);

		List<WebElement> allItems = getListOfWebElements(driver, itemLocator);

		System.out.println("Item size: " + allItems.size());

		for (WebElement item : allItems) {
			String itemName = item.getText();
			System.out.println(itemName);
			if (itemName.trim().equals(expectedItem)) {
				jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
				sleepInSecond(2);

				explicitWait.until(ExpectedConditions.elementToBeClickable(item));
				jsExecutor.executeScript("arguments[0].click();", item);

				sleepInSecond(2);
				break;
			}
		}
	}

	protected void selectMultipleItemsInDropdown(WebDriver driver, String parentLocator, String itemLocator,
			String[] expectedItem) {
		WebDriverWait explicitWait = new WebDriverWait(driver, 30);
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		explicitWait.until(ExpectedConditions.elementToBeClickable(getByXpath(parentLocator)));

		jsExecutor.executeScript("arguments[0].click();", getWebElement(driver, parentLocator));
		sleepInSecond(3);

		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByXpath(itemLocator)));

		List<WebElement> allItems = getListOfWebElements(driver, itemLocator);

		System.out.println("Item size: " + allItems.size());

		for (WebElement childItem : allItems) {
			String itemName = childItem.getText();
			System.out.println(itemName);

			for (String item : expectedItem) {
				if (itemName.trim().equals(item)) {
					jsExecutor.executeScript("arguments[0].scrollIntoView(true);", childItem);
					sleepInSecond(2);

					jsExecutor.executeScript("arguments[0].click();", childItem);
					sleepInSecond(2);

					List<WebElement> selectedItem = driver.findElements(By.xpath("//li[@class='selected']//input"));
					System.out.println("Items selected: " + selectedItem.size());

					if (expectedItem.length == selectedItem.size()) {
						break;
					}
				}
			}

		}
	}

	// Check-box - Radio
	protected void checkToDefaultCheckBox(WebDriver driver, String xpathLocator) {
		WebElement webelement = getWebElement(driver, xpathLocator);
		if(!webelement.isSelected()) {
			webelement.click();
		}
	}
	
	protected void uncheckToDefaultCheckBox(WebDriver driver, String xpathLocator) {
		WebElement webelement = getWebElement(driver, xpathLocator);
		if(webelement.isSelected()) {
			webelement.click();
		}
	}
	
	protected boolean isElementDisplay(WebDriver driver, String xpathLocator) {
		return getWebElement(driver, xpathLocator).isDisplayed();
	}
	
	protected boolean isElementEnable(WebDriver driver, String xpathLocator) {
		return getWebElement(driver, xpathLocator).isEnabled();
	}
	
	protected boolean isElementSelected(WebDriver driver, String xpathLocator) {
		return getWebElement(driver, xpathLocator).isSelected();
	}
	
	// Frame - iFrame
	protected void switchToFrameIframe(WebDriver driver, String xpathLocator) {
		driver.switchTo().frame(getWebElement(driver, xpathLocator));
	}
	
	protected void switchToDefaultContent(WebDriver driver) {
		driver.switchTo().defaultContent();
	}
	
	// Mouse - keyboard
	protected void hoverMouseToElement(WebDriver driver, String xpathLocator) {
		Actions action = new Actions(driver);
		action.moveToElement(getWebElement(driver, xpathLocator)).perform();
	}
	
	// Javascript executor
	protected void scrollToBottomPage(WebDriver driver) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	protected void highlightElement(WebDriver driver, String xpathLocator) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		WebElement element = getWebElement(driver, xpathLocator);
		String originalStyle = element.getAttribute("style");
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style",
				"border: 2px solid red; border-style: dashed;");
		sleepInSecond(1);
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style",
				originalStyle);
	}

	protected void clickToElementByJS(WebDriver driver, String xpathLocator) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].click();", getWebElement(driver, xpathLocator));
	}

	protected void scrollToElement(WebDriver driver, String xpathLocator) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getWebElement(driver, xpathLocator));
	}

	protected void removeAttributeInDOM(WebDriver driver, String xpathLocator, String attributeRemove) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');",
				getWebElement(driver, xpathLocator));
	}

	protected boolean areJQueryAndJSLoadedSuccess(WebDriver driver) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeOut);
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return ((Long) jsExecutor.executeScript("return jQuery.active") == 0);
				} catch (Exception e) {
					return true;
				}
			}
		};

		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return jsExecutor.executeScript("return document.readyState").toString().equals("complete");
			}
		};

		return explicitWait.until(jQueryLoad) && explicitWait.until(jsLoad);
	}

	protected String getElementValidationMessage(WebDriver driver, String xpathLocator) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		return (String) jsExecutor.executeScript("return arguments[0].validationMessage;",
				getWebElement(driver, xpathLocator));
	}

	protected boolean isImageLoaded(WebDriver driver, String xpathLocator) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		boolean status = (boolean) jsExecutor.executeScript(
				"return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0",
				getWebElement(driver, xpathLocator));
		if (status) {
			return true;
		} else {
			return false;
		}
	}

	// Wait
	protected void waitForElementVisible(WebDriver driver, String xpathLocator) {
		WebDriverWait expliciwait = new WebDriverWait(driver, longTimeOut);
		expliciwait.until(ExpectedConditions.visibilityOfElementLocated(getByXpath(xpathLocator)));
	}

	protected void waitForAllElementVisible(WebDriver driver, String xpathLocator) {
		WebDriverWait expliciwait = new WebDriverWait(driver, longTimeOut);
		expliciwait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByXpath(xpathLocator)));
	}

	protected void waitForElementInvisible(WebDriver driver, String xpathLocator) {
		WebDriverWait expliciwait = new WebDriverWait(driver, longTimeOut);
		expliciwait.until(ExpectedConditions.invisibilityOfElementLocated(getByXpath(xpathLocator)));
	}

	protected void waitForAllElementInvisible(WebDriver driver, String xpathLocator) {
		WebDriverWait expliciwait = new WebDriverWait(driver, longTimeOut);
		expliciwait.until(ExpectedConditions.invisibilityOfAllElements(getListOfWebElements(driver, xpathLocator)));
	}

	protected void waitForElementClickAble(WebDriver driver, String xpathLocator) {
		WebDriverWait expliciwait = new WebDriverWait(driver, longTimeOut);
		expliciwait.until(ExpectedConditions.elementToBeClickable(getByXpath(xpathLocator)));
	}

	private long longTimeOut = 30;
	private long shortTimeout = 5;
}
