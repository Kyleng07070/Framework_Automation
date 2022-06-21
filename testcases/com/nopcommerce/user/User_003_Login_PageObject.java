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
import pageObjects.LoginPageObject;
import pageObjects.RegisterPageObject;

public class User_003_Login_PageObject {
	private WebDriver driver;
	private String projectPath = System.getProperty("user.dir");
	private String firstName, lastName, email, password, confirmPassword;
	private HomePageObject homePage;
	private LoginPageObject loginPage;
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
		lastName = "Ng" + randomNumber();
		password = "abc@12345";
		confirmPassword = "abc@12345";

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

		homePage = new HomePageObject(driver);
	}

	@Test
	public void Login_001_Empty_Data() {
		System.out.println("Step 1: click on Login link");
		homePage.clickLoginLink();

		loginPage = new LoginPageObject(driver);

		System.out.println("Step 2: click Login button");
		loginPage.clickLoginButton();

		System.out.println("Step 3: verify error message");
		Assert.assertEquals(loginPage.getEmailErrorMessage(), "Please enter your email");
	}

	@Test
	public void Login_002_Not_Register_Email() {
		System.out.println("Step 1: click on Login link");
		homePage.clickLoginLink();

		loginPage = new LoginPageObject(driver);

		System.out.println("Step 2: input Not Register Email");
		loginPage.inputToEmailTextbox("hungnd7@gmail.com");
		loginPage.inputToPasswordTextbox("abcdef@1234");

		System.out.println("Step 3: click Login button");
		loginPage.clickLoginButton();

		System.out.println("Step 4: verify error message");
		Assert.assertEquals(loginPage.getAccountErrorMessage(),
				"Login was unsuccessful. Please correct the errors and try again.\nNo customer account found");
	}

	@Test
	public void Login_003_Invalid_Email() {
		System.out.println("Step 1: click on Login link");
		homePage.clickLoginLink();

		loginPage = new LoginPageObject(driver);

		System.out.println("Step 2: input invalid Email");
		loginPage.inputToEmailTextbox("hungnd7");
		loginPage.inputToPasswordTextbox(password);

		System.out.println("Step 3: click Login button");
		loginPage.clickLoginButton();

		System.out.println("Step 4: verify error message");
		Assert.assertEquals(loginPage.getEmailErrorMessage(), "Wrong email");
	}

	@Test
	public void Login_004_Correct_Email_And_Empty_Password() {
		System.out.println("Step 1: click on Login link");
		homePage.clickLoginLink();

		loginPage = new LoginPageObject(driver);

		System.out.println("Step 2: input registered Email and empty password");
		loginPage.inputToEmailTextbox(email);
		loginPage.clearPasswordTextbox();

		System.out.println("Step 3: click Login button");
		loginPage.clickLoginButton();

		System.out.println("Step 4: verify error message");
		Assert.assertEquals(loginPage.getAccountErrorMessage(),
				"Login was unsuccessful. Please correct the errors and try again.\nThe credentials provided are incorrect");
	}

	@Test
	public void Login_005_Correct_Email_And_Wrong_Password() {
		System.out.println("Step 1: click on Login link");
		homePage.clickLoginLink();

		loginPage = new LoginPageObject(driver);

		System.out.println("Step 2: input registered Email and wrong password");
		loginPage.inputToEmailTextbox(email);
		loginPage.inputToPasswordTextbox("ewew@1234");

		System.out.println("Step 3: click Login button");
		loginPage.clickLoginButton();

		System.out.println("Step 4: verify error message");
		Assert.assertEquals(loginPage.getAccountErrorMessage(),
				"Login was unsuccessful. Please correct the errors and try again.\nThe credentials provided are incorrect");
	}

	@Test
	public void Login_006_Valid_Email_And_Password() {
		System.out.println("Step 1: click on Login link");
		homePage.clickLoginLink();

		loginPage = new LoginPageObject(driver);

		System.out.println("Step 2: input registered Email and wrong password");
		loginPage.inputToEmailTextbox(email);
		loginPage.inputToPasswordTextbox(password);

		System.out.println("Step 3: click Login button");
		loginPage.clickLoginButton();

		homePage = new HomePageObject(driver);

		System.out.println("Step 4: My Account link display");
		Assert.assertTrue(homePage.isMyAccountLinkDisplay());
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
