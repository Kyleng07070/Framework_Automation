package com.nopcommerce.user;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import java.util.Random;
import pageObjects.HomePageObject;
import pageObjects.RegisterPageObject;

public class User_002_Register_PageObject {
	private WebDriver driver;
	private String projectPath = System.getProperty("user.dir");
	private String firstName, lastName, email, password, confirmPassword;
	private HomePageObject homePage;
	private RegisterPageObject registerPage;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver-1");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.get("https://demo.nopcommerce.com/");

		homePage = new HomePageObject(driver);

		email = "testEmail" + randomNumber() + "@gmail.com";
		firstName = "Kyle";
		lastName = "Ng";
		password = "abc@12345";
		confirmPassword = "abc@12345";
	}

	@Test
	public void Register_001_Empty_Data() {

		System.out.println("Register_001 - Step 01: click Register link");
		homePage.clickRegisterLink();

		// khoi tao trang register sau khi qua trang register
		registerPage = new RegisterPageObject(driver);
		
		System.out.println("Register_001 - Step 02: click Register button");
		registerPage.clickRegisterButton();

		System.out.println("Register_001 - Step 03: verify error message");
		Assert.assertEquals(registerPage.getFirstNameErrorMessage(), "First name is required.");
		Assert.assertEquals(registerPage.getLastNameErrorMessage(), "Last name is required.");
		Assert.assertEquals(registerPage.getEmailErrorMessage(), "Email is required.");
		Assert.assertEquals(registerPage.getPasswordErrorMessage(), "Password is required.");
		Assert.assertEquals(registerPage.getConfirmPasswordErrorMessage(), "Password is required.");
	}

	@Test
	public void Register_002_Invalid_Email() {
		System.out.println("Register_002 - Step 01: click Register link");
		homePage.clickRegisterLink();

		// khoi tao trang register sau khi qua trang register
		registerPage = new RegisterPageObject(driver);
		
		System.out.println("Register_002 - Step 02: input data");
		registerPage.inputToFirstNameTextbox(firstName);
		registerPage.inputToLastNameTextbox(lastName);
		registerPage.inputToEmailTextbox("invalid@invalid@email");
		registerPage.inputToPasswordTextbox(password);
		registerPage.inputToConfirmPasswordTextbox(password);

		System.out.println("Register_002 - Step 03: click Register button");
		registerPage.clickRegisterButton();

		System.out.println("Register_002 - Step 04: verify error message");
		Assert.assertEquals(registerPage.getEmailErrorMessage(), "Wrong email");
	}

	@Test
	public void Register_003_Success() {
		System.out.println("Register_003 - Step 01: click Register link");
		homePage.clickRegisterLink();
		
		// khoi tao trang register sau khi qua trang register
		registerPage = new RegisterPageObject(driver);

		System.out.println("Register_003 - Step 02: input data");
		registerPage.inputToFirstNameTextbox(firstName);
		registerPage.inputToLastNameTextbox(lastName);
		registerPage.inputToEmailTextbox(email);
		registerPage.inputToPasswordTextbox(password);
		registerPage.inputToConfirmPasswordTextbox(confirmPassword);

		System.out.println("Register_003 - Step 03: click Register button");
		registerPage.clickRegisterButton();

		System.out.println("Register_003 - Step 04: verify success message");
		Assert.assertEquals(registerPage.getRegisterSuccessMessage(), "Your registration completed");

		System.out.println("Register_003 - Step 05: click Logout link");
		registerPage.clickLogoutLink();
	}

	@Test
	public void Register_004_Existing_Email() {
		System.out.println("Register_004 - Step 01: click Register link");
		homePage.clickRegisterLink();
		
		// khoi tao trang register sau khi qua trang register
		registerPage = new RegisterPageObject(driver);

		System.out.println("Register_004 - Step 02: input data");
		registerPage.inputToFirstNameTextbox(firstName);
		registerPage.inputToLastNameTextbox(lastName);
		registerPage.inputToEmailTextbox(email);
		registerPage.inputToPasswordTextbox(password);
		registerPage.inputToConfirmPasswordTextbox(confirmPassword);

		System.out.println("Register_004 - Step 03: click Register button");
		registerPage.clickRegisterButton();

		System.out.println("Register_004 - Step 04: verify error message");
		Assert.assertEquals(registerPage.getExistingEmailErrorMessage(), "The specified email already exists");
	}

	@Test
	public void Register_005_Password_Less_Than_6() {
		System.out.println("Register_005 - Step 01: click Register link");
		homePage.clickRegisterLink();
		
		// khoi tao trang register sau khi qua trang register
		registerPage = new RegisterPageObject(driver);

		System.out.println("Register_005 - Step 02: input data");
		registerPage.inputToFirstNameTextbox(firstName);
		registerPage.inputToLastNameTextbox(lastName);
		registerPage.inputToEmailTextbox(email);
		registerPage.inputToPasswordTextbox("abc");
		registerPage.inputToConfirmPasswordTextbox("abc");

		System.out.println("Register_005 - Step 03: click Register button");
		registerPage.clickRegisterButton();

		System.out.println("Register_005 - Step 04: verify error message");
		Assert.assertEquals(registerPage.getPasswordErrorMessage(),
				"Password must meet the following rules:\nmust have at least 6 characters");
	}

	@Test
	public void Register_006_Invalid_Confirm_Password() {
		System.out.println("Register_006 - Step 01: click Register link");
		homePage.clickRegisterLink();
		
		// khoi tao trang register sau khi qua trang register
		registerPage = new RegisterPageObject(driver);

		System.out.println("Register_006 - Step 02: input data");
		registerPage.inputToFirstNameTextbox(firstName);
		registerPage.inputToLastNameTextbox(lastName);
		registerPage.inputToEmailTextbox(email);
		registerPage.inputToPasswordTextbox(password);
		registerPage.inputToConfirmPasswordTextbox("abc12345");

		System.out.println("Register_006 - Step 03: click Register button");
		registerPage.clickRegisterButton();

		System.out.println("Register_006 - Step 04: verify error message");
		Assert.assertEquals(registerPage.getConfirmPasswordErrorMessage(),
				"The password and confirmation password do not match.");
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public int randomNumber() {
		Random rand = new Random();
		return rand.nextInt(9999);
	}
}
