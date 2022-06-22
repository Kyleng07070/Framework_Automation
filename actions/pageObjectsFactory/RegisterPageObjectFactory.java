package pageObjectsFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import commons.BasePageFactory;
import pageUIs.RegisterPageUI;

public class RegisterPageObjectFactory extends BasePageFactory {
	private WebDriver driver;

	public RegisterPageObjectFactory(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "FirstName")
	private WebElement firstNameTextbox;

	@FindBy(id = "LastName")
	private WebElement lastNameTextbox;

	@FindBy(id = "Email")
	private WebElement emailTextbox;

	@FindBy(id = "Password")
	private WebElement passwordTextbox;

	@FindBy(id = "ConfirmPassword")
	private WebElement confirmPasswordTextbox;

	@FindBy(id = "register-button")
	private WebElement registerButton;

	@FindBy(id = "FirstName-error")
	private WebElement firstNameErrorMessage;

	@FindBy(id = "LastName-error")
	private WebElement lastNameErrorMessage;

	@FindBy(id = "Email-error")
	private WebElement emailErrorMessage;

	@FindBy(id = "Password-error")
	private WebElement passwordErrorMessage;

	@FindBy(id = "ConfirmPassword-error")
	private WebElement confirmPasswordErrorMessage;

	@FindBy(xpath = "//div[@class='result']")
	private WebElement registerSuccessMessage;

	@FindBy(xpath = "//div[contains(@class, 'message-error')]//li")
	private WebElement existingEmailErrorMessage;

	@FindBy(xpath = "//a[text()='Log out']")
	private WebElement logoutLink;

	public void clickRegisterButton() {
		waitForElementClickAble(driver, registerButton);
		clickToElement(driver, registerButton);
	}

	public String getFirstNameErrorMessage() {
		waitForElementVisible(driver, firstNameErrorMessage);
		return getElementText(driver, firstNameErrorMessage);
	}

	public String getLastNameErrorMessage() {
		waitForElementVisible(driver, lastNameErrorMessage);
		return getElementText(driver, lastNameErrorMessage);
	}

	public String getEmailErrorMessage() {
		waitForElementVisible(driver, emailErrorMessage);
		return getElementText(driver, emailErrorMessage);
	}

	public String getPasswordErrorMessage() {
		waitForElementVisible(driver, passwordErrorMessage);
		return getElementText(driver, passwordErrorMessage);
	}

	public String getConfirmPasswordErrorMessage() {
		waitForElementVisible(driver, confirmPasswordErrorMessage);
		return getElementText(driver, confirmPasswordErrorMessage);
	}

	public void inputToFirstNameTextbox(String firstname) {
		waitForElementVisible(driver, firstNameTextbox);
		sendkeyToElement(driver, firstNameTextbox, firstname);
	}

	public void inputToLastNameTextbox(String lastname) {
		waitForElementVisible(driver, lastNameTextbox);
		sendkeyToElement(driver, lastNameTextbox, lastname);
	}

	public void inputToEmailTextbox(String email) {
		waitForElementVisible(driver, emailTextbox);
		sendkeyToElement(driver, emailTextbox, email);
	}

	public void inputToPasswordTextbox(String password) {
		waitForElementVisible(driver, passwordTextbox);
		sendkeyToElement(driver, passwordTextbox, password);
	}

	public void inputToConfirmPasswordTextbox(String confirmPassword) {
		waitForElementVisible(driver, confirmPasswordTextbox);
		sendkeyToElement(driver, confirmPasswordTextbox, confirmPassword);
	}

	public String getRegisterSuccessMessage() {
		waitForElementVisible(driver, registerSuccessMessage);
		return getElementText(driver, registerSuccessMessage);
	}

	public void clickLogoutLink() {
		waitForElementClickAble(driver, logoutLink);
		clickToElement(driver,logoutLink);
	}

	public String getExistingEmailErrorMessage() {
		waitForElementVisible(driver, existingEmailErrorMessage);
		return getElementText(driver, existingEmailErrorMessage);
	}
}
