package com.dfremont.tool.hotspotautoconnect;

import static org.fest.assertions.Assertions.assertThat;

import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

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
	
	private WebElement element(String cssSelector) {
		return driver.findElement(By.cssSelector(cssSelector));
	}
	
	// LOGIN
	
	String USERNAME;
	String PASSWORD;
	
	@Before
	public void setUp() throws Exception {
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
		driver.get(SFRLogonPage.url);
		assertThat(driver.getCurrentUrl()).isEqualTo(SFRLogonPage.url);
		assertThat(element(".headerSFR").isDisplayed()).isTrue();

		logon();
		waitUntilUrlAsChanged();
		assertThat(driver.getCurrentUrl()).contains(SFRLogonPage.url);

		if (driver.getCurrentUrl().contains("already")) {

		} else {

			assertThat(element("#box").isDisplayed()).isTrue();
			assertThat(element("#contenuBox").getText()).contains("ERREUR");

			element("#fermerBox a").click();
			assertThat(element("#box").isDisplayed()).isFalse();

			logon();
			waitUntilUrlAsChanged();
			assertThat(driver.getCurrentUrl()).contains(SFRLogonPage.url);
		}

		driver.get(TestPage.url);
		assertThat(driver.getCurrentUrl()).isEqualTo(TestPage.url);
		assertThat(element(TestPage.search).getAttribute("value")).contains(
				"toto666");
	}

	private void logon() {
		element(SFRLogonPage.login).sendKeys(USERNAME);
		element(SFRLogonPage.password).sendKeys(PASSWORD);
		element(SFRLogonPage.conditions).click();
		element(SFRLogonPage.connexion).click();
	}

}