package com.dfremont.tool.hotspotautoconnect;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

// TODO hide driver
// TODO add rule to kill embedded Browser
public class SFRAutoConnectTest extends AbstractUITest {

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

	// LOGIN

	String USERNAME;
	String PASSWORD;

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
		SFRLogonPage page = new SFRLogonPage(driver);
		TestPage test = new TestPage(driver);

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