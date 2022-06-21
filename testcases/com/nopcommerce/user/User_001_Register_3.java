package com.nopcommerce.user;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import java.util.Random;
import commons.BasePage;

public class User_001_Register_3 extends BasePage{
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String email;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver-1");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.get("https://demo.nopcommerce.com/");
		email = "testEmail" + randomNumber() + "@gmail.com";

	}

	@Test
	public void TC_001_Register_Empty_Data() {
		waitForElementClickAble(driver, "//a[@class='ico-register']");
		clickToElement(driver, "//a[@class='ico-register']");

		waitForElementClickAble(driver, "//button[@id='register-button']");
		clickToElement(driver, "//button[@id='register-button']");

		Assert.assertEquals(getElementText(driver, "//span[@id='FirstName-error']"),
				"First name is required.");
		Assert.assertEquals(getElementText(driver, "//span[@id='LastName-error']"), "Last name is required.");
		Assert.assertEquals(getElementText(driver, "//span[@id='Email-error']"), "Email is required.");
		Assert.assertEquals(getElementText(driver, "//span[@id='Password-error']"), "Password is required.");
		Assert.assertEquals(getElementText(driver, "//span[@id='ConfirmPassword-error']"),
				"Password is required.");

	}

	@Test
	public void TC_002_Invalid_Email() {
		waitForElementClickAble(driver, "//a[@class='ico-register']");
		clickToElement(driver, "//a[@class='ico-register']");

		waitForElementVisible(driver, "//input[@id='Email']");

		sendkeyToElement(driver, "//input[@id='FirstName']", "Kyle");
		sendkeyToElement(driver, "//input[@id='LastName']", "Ng");
		sendkeyToElement(driver, "//input[@id='Email']", "invalid@invalid@email");
		sendkeyToElement(driver, "//input[@id='Password']", "abc@12345");
		sendkeyToElement(driver, "//input[@id='ConfirmPassword']", "abc@12345");

		clickToElement(driver, "//button[@id='register-button']");

		sleepInSecond(2);
		Assert.assertEquals(getElementText(driver, "//span[@id='Email-error']"), "Wrong email");
	}

	@Test
	public void TC_003_Register_Success() {
		waitForElementClickAble(driver, "//a[@class='ico-register']");
		clickToElement(driver, "//a[@class='ico-register']");

		waitForElementVisible(driver, "//input[@id='Email']");

		sendkeyToElement(driver, "//input[@id='FirstName']", "Kyle");
		sendkeyToElement(driver, "//input[@id='LastName']", "Ng");
		sendkeyToElement(driver, "//input[@id='Email']", email);
		sendkeyToElement(driver, "//input[@id='Password']", "abc@12345");
		sendkeyToElement(driver, "//input[@id='ConfirmPassword']", "abc@12345");

		clickToElement(driver, "//button[@id='register-button']");
		
		sleepInSecond(2);
		Assert.assertEquals(getElementText(driver, "//div[@class='result']"), "Your registration completed");

		clickToElement(driver, "//a[text()='Log out']");
	}

	@Test
	public void TC_004_Register_Existing_Email() {
		waitForElementClickAble(driver, "//a[@class='ico-register']");
		clickToElement(driver, "//a[@class='ico-register']");

		waitForElementVisible(driver, "//input[@id='Email']");

		sendkeyToElement(driver, "//input[@id='FirstName']", "Kyle");
		sendkeyToElement(driver, "//input[@id='LastName']", "Ng");
		sendkeyToElement(driver, "//input[@id='Email']", email);
		sendkeyToElement(driver, "//input[@id='Password']", "abc@12345");
		sendkeyToElement(driver, "//input[@id='ConfirmPassword']", "abc@12345");

		clickToElement(driver, "//button[@id='register-button']");

		Assert.assertEquals(getElementText(driver, "//div[contains(@class, 'message-error')]//li"),
				"The specified email already exists");
	}

	@Test
	public void TC_005_Register_Password_Less_Than_6() {
		waitForElementClickAble(driver, "//a[@class='ico-register']");
		clickToElement(driver, "//a[@class='ico-register']");

		waitForElementVisible(driver, "//input[@id='Email']");

		sendkeyToElement(driver, "//input[@id='FirstName']", "Kyle");
		sendkeyToElement(driver, "//input[@id='LastName']", "Ng");
		sendkeyToElement(driver, "//input[@id='Email']", email);
		sendkeyToElement(driver, "//input[@id='Password']", "abc");
		sendkeyToElement(driver, "//input[@id='ConfirmPassword']", "abc");

		clickToElement(driver, "//button[@id='register-button']");

		Assert.assertEquals(getElementText(driver, "//span[@id='Password-error']"),
				"Password must meet the following rules:\nmust have at least 6 characters");
	}
	
	@Test
	public void TC_006_Register_Invalid_Confirm_Password() {
		waitForElementClickAble(driver, "//a[@class='ico-register']");
		clickToElement(driver, "//a[@class='ico-register']");
		
		waitForElementVisible(driver, "//input[@id='Email']");

		sendkeyToElement(driver, "//input[@id='FirstName']", "Kyle");
		sendkeyToElement(driver, "//input[@id='LastName']", "Ng");
		sendkeyToElement(driver, "//input[@id='Email']", email);
		sendkeyToElement(driver, "//input[@id='Password']", "abc@123");
		sendkeyToElement(driver, "//input[@id='ConfirmPassword']", "abc12345");

		clickToElement(driver, "//button[@id='register-button']");
		
		Assert.assertEquals(getElementText(driver, "//span[@id='ConfirmPassword-error']"),
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
