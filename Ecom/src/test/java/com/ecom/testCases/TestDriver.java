package com.ecom.testCases;

import java.io.IOException;
import java.time.Duration;
import java.util.Objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import com.ecom.utilites.TestUtilities;

public class TestDriver extends TestUtilities {

	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	private static String[] args = { "--incognito", "--start-maximized" };

	public static WebDriver getDriver() {
		return driver.get();
	}

	public static void setDriver(WebDriver dr) {
		driver.set(dr);
	}

	public static void unload() {
		driver.remove();
	}

	public static void setUp(String browserName, String url) throws IOException {

		WebDriver driver = null;
		if (Objects.isNull(getDriver())) {
			if (browserName.equalsIgnoreCase("Chrome") || browserName.isEmpty()) {
				
						ChromeOptions options = new ChromeOptions();
						options.addArguments(args);
						driver = new ChromeDriver(options);
					}

					else if (browserName.equalsIgnoreCase("firefox")) {

						FirefoxOptions options = new FirefoxOptions();
						options.addArguments(args);
						driver = new FirefoxDriver(options);
					}

					else if (browserName.equalsIgnoreCase("edge")) {

						EdgeOptions options = new EdgeOptions();
						options.addArguments(args);
						driver = new EdgeDriver(options);
					}
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
					setDriver(driver);
					getDriver().get(url);

				}
			
	}

	public static void tearDown() {

		if (Objects.nonNull(getDriver())) {
			getDriver().close();
			unload();
		}
	}
}
