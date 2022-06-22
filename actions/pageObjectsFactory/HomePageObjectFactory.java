package pageObjectsFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import commons.BasePageFactory;

public class HomePageObjectFactory extends BasePageFactory{
	private WebDriver driver;

	public HomePageObjectFactory(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	// PageUI - cach 1
	@FindBy(how= How.XPATH, using = "//a[@class='ico-register']")
	private WebElement registerLink;
	// PageUI - cach 2
	@FindBy(xpath = "//a[@class='ico-login']")
	private WebElement loginLink;
	
	@FindBy(css = "a[class='ico-account']")
	private WebElement myAccountLink;
	
	public void clickRegisterLink() {
		waitForElementClickAble(driver, registerLink);
		clickToElement(driver, registerLink);
	}
	public void clickLoginLink() {
		waitForElementClickAble(driver, loginLink);
		clickToElement(driver, loginLink);
	}
	public boolean isMyAccountLinkDisplay() {
		waitForElementVisible(driver, myAccountLink);
		return isElementDisplay(driver, myAccountLink);
	}
	
}
