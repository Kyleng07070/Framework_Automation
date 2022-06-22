package pageObjectsFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import commons.BasePageFactory;

public class LoginPageObjectFactory extends BasePageFactory {
	private WebDriver driver;

	public LoginPageObjectFactory(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@CacheLookup // dung cache lai webelemt - tranh viec goi nhieu lan
	@FindBy(xpath = "//button[contains(@class,'login-button')]")
	private WebElement loginButton;

	@FindBy(id = "Email-error")
	private WebElement emailErrorMessage;

	@FindBy(id = "Email")
	private WebElement emailTextbox;

	@FindBy(id = "Password")
	private WebElement passwordTextbox;

	@FindBy(xpath = "//div[contains(@class,'validation-summary-errors')]")
	private WebElement accountNotFoundErrorMessage;

	
	public void clickLoginButton() {
		waitForElementClickAble(driver, loginButton);
		clickToElement(driver, loginButton);
	}

	public String getEmailErrorMessage() {
		waitForElementVisible(driver, emailErrorMessage);
		return getElementText(driver, emailErrorMessage);
	}

	public void inputToEmailTextbox(String email) {
		waitForElementVisible(driver, emailTextbox);
		sendkeyToElement(driver, emailTextbox, email);
	}

	public void inputToPasswordTextbox(String password) {
		waitForElementVisible(driver, passwordTextbox);
		sendkeyToElement(driver, passwordTextbox, password);	
	}

	public String getAccountErrorMessage() {
		waitForElementVisible(driver, accountNotFoundErrorMessage);
		return getElementText(driver, accountNotFoundErrorMessage);
	}

	public void clearPasswordTextbox() {
		waitForElementVisible(driver, passwordTextbox);
		sendkeyToElement(driver, passwordTextbox, "");
	}
}
