package commons;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	private WebDriver driverBaseTest;
	private String projectPath = System.getProperty("user.dir");

	protected WebDriver getBrowserDriver(String browserName) {
		if (browserName.equals("firefox")) {
			// System.setProperty("webdriver.gecko.driver", projectPath +
			// "/browserDrivers/geckodriver-1");
			WebDriverManager.firefoxdriver().setup();
			driverBaseTest = new FirefoxDriver();
		} else if (browserName.equals("h_firefox")) {
			// System.setProperty("webdriver.gecko.driver", projectPath +
			// "/browserDrivers/geckodriver-1");
			WebDriverManager.firefoxdriver().setup();
			FirefoxOptions options = new FirefoxOptions();
			options.addArguments("-headless");
			options.addArguments("window-size=1920x1080");
			driverBaseTest = new FirefoxDriver(options);
		} else if (browserName.equals("chrome")) {
			// System.setProperty("webdriver.chrome.driver", projectPath +
			// "/browserDrivers/chromedriver-7");
			WebDriverManager.chromedriver().setup();
			driverBaseTest = new ChromeDriver();
		} else if (browserName.equals("h_chrome")) {
			// System.setProperty("webdriver.chrome.driver", projectPath +
			// "/browserDrivers/chromedriver-7");
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("-headless");
			options.addArguments("window-size=1920x1080");
			driverBaseTest = new ChromeDriver(options);
		} else if (browserName.equals("coccoc")) {
			WebDriverManager.chromedriver().driverVersion("copy version name").setup();
			ChromeOptions options = new ChromeOptions();
			options.setBinary("file location in laptop");
			driverBaseTest = new ChromeDriver(options);
		} else {
			throw new RuntimeException("browserName invalid");
		}

		driverBaseTest.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driverBaseTest.get("https://demo.nopcommerce.com/");
		return driverBaseTest;
	}
}
