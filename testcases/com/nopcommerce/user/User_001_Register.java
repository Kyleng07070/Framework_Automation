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

public class User_001_Register {
	WebDriver driver;
	BasePage basePage;
	String projectPath = System.getProperty("user.dir");
	String email;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver-1");
		driver = new FirefoxDriver();
		basePage = new BasePage();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.get("https://demo.nopcommerce.com/");
		email = "testEmail" + randomNumber() + "@gmail.com";

	}

	@Test
	public void TC_001_Register_Empty_Data() {
		basePage.waitForElementClickAble(driver, "//a[@class='ico-register']");
		basePage.clickToElement(driver, "//a[@class='ico-register']");

		basePage.waitForElementClickAble(driver, "//button[@id='register-button']");
		basePage.clickToElement(driver, "//button[@id='register-button']");

		Assert.assertEquals(basePage.getElementText(driver, "//span[@id='FirstName-error']"),
				"First name is required.");
		Assert.assertEquals(basePage.getElementText(driver, "//span[@id='LastName-error']"), "Last name is required.");
		Assert.assertEquals(basePage.getElementText(driver, "//span[@id='Email-error']"), "Email is required.");
		Assert.assertEquals(basePage.getElementText(driver, "//span[@id='Password-error']"), "Password is required.");
		Assert.assertEquals(basePage.getElementText(driver, "//span[@id='ConfirmPassword-error']"),
				"Password is required.");

	}

	@Test
	public void TC_002_Invalid_Email() {
		basePage.waitForElementClickAble(driver, "//a[@class='ico-register']");
		basePage.clickToElement(driver, "//a[@class='ico-register']");

		basePage.waitForElementVisible(driver, "//input[@id='Email']");

		basePage.sendkeyToElement(driver, "//input[@id='FirstName']", "Kyle");
		basePage.sendkeyToElement(driver, "//input[@id='LastName']", "Ng");
		basePage.sendkeyToElement(driver, "//input[@id='Email']", "invalid@invalid@email");
		basePage.sendkeyToElement(driver, "//input[@id='Password']", "abc@12345");
		basePage.sendkeyToElement(driver, "//input[@id='ConfirmPassword']", "abc@12345");

		basePage.clickToElement(driver, "//button[@id='register-button']");

		basePage.sleepInSecond(2);
		Assert.assertEquals(basePage.getElementText(driver, "//span[@id='Email-error']"), "Wrong email");
	}

	@Test
	public void TC_003_Register_Success() {
		basePage.waitForElementClickAble(driver, "//a[@class='ico-register']");
		basePage.clickToElement(driver, "//a[@class='ico-register']");

		basePage.waitForElementVisible(driver, "//input[@id='Email']");

		basePage.sendkeyToElement(driver, "//input[@id='FirstName']", "Kyle");
		basePage.sendkeyToElement(driver, "//input[@id='LastName']", "Ng");
		basePage.sendkeyToElement(driver, "//input[@id='Email']", email);
		basePage.sendkeyToElement(driver, "//input[@id='Password']", "abc@12345");
		basePage.sendkeyToElement(driver, "//input[@id='ConfirmPassword']", "abc@12345");

		basePage.clickToElement(driver, "//button[@id='register-button']");
		
		basePage.sleepInSecond(2);
		Assert.assertEquals(basePage.getElementText(driver, "//div[@class='result']"), "Your registration completed");

		basePage.clickToElement(driver, "//a[text()='Log out']");
	}

	@Test
	public void TC_004_Register_Existing_Email() {
		basePage.waitForElementClickAble(driver, "//a[@class='ico-register']");
		basePage.clickToElement(driver, "//a[@class='ico-register']");

		basePage.waitForElementVisible(driver, "//input[@id='Email']");

		basePage.sendkeyToElement(driver, "//input[@id='FirstName']", "Kyle");
		basePage.sendkeyToElement(driver, "//input[@id='LastName']", "Ng");
		basePage.sendkeyToElement(driver, "//input[@id='Email']", email);
		basePage.sendkeyToElement(driver, "//input[@id='Password']", "abc@12345");
		basePage.sendkeyToElement(driver, "//input[@id='ConfirmPassword']", "abc@12345");

		basePage.clickToElement(driver, "//button[@id='register-button']");

		Assert.assertEquals(basePage.getElementText(driver, "//div[contains(@class, 'message-error')]//li"),
				"The specified email already exists");
	}

	@Test
	public void TC_005_Register_Password_Less_Than_6() {
		basePage.waitForElementClickAble(driver, "//a[@class='ico-register']");
		basePage.clickToElement(driver, "//a[@class='ico-register']");

		basePage.waitForElementVisible(driver, "//input[@id='Email']");

		basePage.sendkeyToElement(driver, "//input[@id='FirstName']", "Kyle");
		basePage.sendkeyToElement(driver, "//input[@id='LastName']", "Ng");
		basePage.sendkeyToElement(driver, "//input[@id='Email']", email);
		basePage.sendkeyToElement(driver, "//input[@id='Password']", "abc");
		basePage.sendkeyToElement(driver, "//input[@id='ConfirmPassword']", "abc");

		basePage.clickToElement(driver, "//button[@id='register-button']");

		Assert.assertEquals(basePage.getElementText(driver, "//span[@id='Password-error']"),
				"Password must meet the following rules:\nmust have at least 6 characters");
	}
	
	@Test
	public void TC_006_Register_Invalid_Confirm_Password() {
		basePage.waitForElementClickAble(driver, "//a[@class='ico-register']");
		basePage.clickToElement(driver, "//a[@class='ico-register']");
		
		basePage.waitForElementVisible(driver, "//input[@id='Email']");

		basePage.sendkeyToElement(driver, "//input[@id='FirstName']", "Kyle");
		basePage.sendkeyToElement(driver, "//input[@id='LastName']", "Ng");
		basePage.sendkeyToElement(driver, "//input[@id='Email']", email);
		basePage.sendkeyToElement(driver, "//input[@id='Password']", "abc@123");
		basePage.sendkeyToElement(driver, "//input[@id='ConfirmPassword']", "abc12345");

		basePage.clickToElement(driver, "//button[@id='register-button']");
		
		Assert.assertEquals(basePage.getElementText(driver, "//span[@id='ConfirmPassword-error']"),
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
