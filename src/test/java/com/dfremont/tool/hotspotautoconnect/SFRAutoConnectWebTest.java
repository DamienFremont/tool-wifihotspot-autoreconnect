package com.dfremont.tool.hotspotautoconnect;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.InputStream;
import java.util.Properties;

import org.junit.Test;
import org.openqa.selenium.WebDriver;

import com.dfremont.seleniumtemplate.WebTest;
import com.dfremont.seleniumtemplate.phantomjs.PhantomJsWebDriverFactory;

// TODO hide driver
// TODO add rule to kill embedded Browser
public class SFRAutoConnectWebTest extends WebTest {

	// LOGIN

	String USERNAME;
	String PASSWORD;

	@Override
	protected WebDriver getDriver() {
		return new PhantomJsWebDriverFactory().get();
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();

		Properties prop = new Properties();
		try (InputStream in = getClass().getClassLoader().getResourceAsStream(
				"cred.properties");) {
			prop.load(in);
		}
		USERNAME = prop.getProperty("sfr.wifi.login");
		PASSWORD = prop.getProperty("sfr.wifi.pass");
	}

	// TEST

	@Test
	public void test_SFR_Wifi() throws Exception {
		SFRLogonWebPage page = new SFRLogonWebPage(driver);
		TestWebPage test = new TestWebPage(driver);

		driver.get(page.getUrl());
		assertTrue(page.isAt());

		page.logon(USERNAME, PASSWORD);
		waitUntilUrlAsChanged();
		assertTrue(page.isAt());

		if (driver.getCurrentUrl().contains(page.already)) {

			fail("deja connecte!");

		} else {

			assertTrue(page.isError());
			page.closeError();

			page.logon(USERNAME, PASSWORD);
			waitUntilUrlAsChanged();
			assertTrue(page.isAt());
		}

		driver.get(test.getUrl());
		assertTrue(test.isAt());
	}

}