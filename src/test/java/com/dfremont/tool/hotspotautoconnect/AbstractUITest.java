package com.dfremont.tool.hotspotautoconnect;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class AbstractUITest {

	// DRIVER

	private int PAGE_TIMEOUT_SEC = 5;
	
	protected WebDriver driver;
	protected WebDriverWait wait;
	
	@Before
	public void setUp() throws Exception {
		driver = new WebDriverFactory().get();
		wait = new WebDriverWait(driver, PAGE_TIMEOUT_SEC);
	}

	// UTIL

	@Rule
	public TestRule testWatcher = new TestWatcher() {
		@Override
		public void failed(Throwable t, Description test) {
			takeScreenshot("some name");
		}
	};

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

}
