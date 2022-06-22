package commons;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePageFactory {
	private long longTimeOut = 30;
	private long shortTimeout = 5;

	public static BasePageFactory getBasePage() {
		return new BasePageFactory();
	}

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

	// Wait
	protected void waitForElementVisible(WebDriver driver, WebElement element) {
		WebDriverWait expliciwait = new WebDriverWait(driver, longTimeOut);
		expliciwait.until(ExpectedConditions.visibilityOf(element));
	}

	protected void waitForAllElementVisible(WebDriver driver, List<WebElement> elements) {
		WebDriverWait expliciwait = new WebDriverWait(driver, longTimeOut);
		expliciwait.until(ExpectedConditions.visibilityOfAllElements(elements));
	}

	protected void waitForElementInvisible(WebDriver driver, WebElement element) {
		WebDriverWait expliciwait = new WebDriverWait(driver, longTimeOut);
		expliciwait.until(ExpectedConditions.invisibilityOf(element));
	}

	protected void waitForAllElementInvisible(WebDriver driver, List<WebElement> elements) {
		WebDriverWait expliciwait = new WebDriverWait(driver, longTimeOut);
		expliciwait.until(ExpectedConditions.invisibilityOfAllElements(elements));
	}

	protected void waitForElementClickAble(WebDriver driver, WebElement element) {
		WebDriverWait expliciwait = new WebDriverWait(driver, longTimeOut);
		expliciwait.until(ExpectedConditions.elementToBeClickable(element));
	}

	// Actions
	protected void clickToElement(WebDriver driver, WebElement element) {
		element.click();
	}

	protected void sendkeyToElement(WebDriver driver, WebElement element, String keysToSend) {
		element.clear();
		element.sendKeys(keysToSend);
	}

	protected String getElementText(WebDriver driver, WebElement element) {
		return element.getText();
	}

	protected String getElementAttribute(WebDriver driver, WebElement element, String attributeName) {
		return element.getAttribute(attributeName);
	}

	protected String getElementCssValue(WebDriver driver, WebElement element, String cssName) {
		return element.getAttribute(cssName);
	}

	protected int getElementSize(WebDriver driver, List<WebElement> elements) {
		return elements.size();
	}

	protected String getHexaColorFromRGBA(String rgbaValue) {
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

	// Check-box - Radio
	protected void checkToDefaultCheckBox(WebDriver driver, WebElement element) {
		if (!element.isSelected()) {
			element.click();
		}
	}

	protected void uncheckToDefaultCheckBox(WebDriver driver, WebElement element) {
		if (element.isSelected()) {
			element.click();
		}
	}

	protected boolean isElementDisplay(WebDriver driver, WebElement element) {
		return element.isDisplayed();
	}

	protected boolean isElementEnable(WebDriver driver, WebElement element) {
		return element.isEnabled();
	}

	protected boolean isElementSelected(WebDriver driver, WebElement element) {
		return element.isSelected();
	}
}
