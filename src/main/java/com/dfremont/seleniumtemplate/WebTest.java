package com.dfremont.seleniumtemplate;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class WebTest {

	// DRIVER

	private int PAGE_TIMEOUT_SEC = 5;

	protected WebDriver driver;
	protected WebDriverWait wait;

	@Before
	public void setUp() throws Exception {
		driver = getDriver();
		wait = new WebDriverWait(driver, PAGE_TIMEOUT_SEC);
	}

	/**
	 * Overrive me!
	 * 
	 * @return
	 */
	protected WebDriver getDriver() {
		return new FirefoxDriver();
	}

	// UTIL

	@Rule
	public TestRule testWatcher = new TestWatcher() {
		@Override
		public void failed(Throwable t, Description test) {
			takeScreenshot("some name");
		}
	};

	// TODO move me
	void takeScreenshot(String screenshotName) {
		if (driver instanceof TakesScreenshot) {
			File tempFile = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(tempFile, new File("target/screenshots/"
						+ screenshotName + ".png"));
			} catch (IOException e) {
				// TODO handle exception
			}
		}
	}

	// TODO move me
	protected void waitUntilUrlAsChanged() {
		final String previousURL = driver.getCurrentUrl();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		ExpectedCondition<Boolean> e = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return (d.getCurrentUrl() != previousURL);
			}
		};
		wait.until(e);
	}

}
