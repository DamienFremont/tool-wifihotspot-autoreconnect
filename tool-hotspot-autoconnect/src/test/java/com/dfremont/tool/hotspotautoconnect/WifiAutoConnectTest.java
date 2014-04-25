package com.dfremont.tool.hotspotautoconnect;

import static org.fest.assertions.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WifiAutoConnectTest {

	int PAGE_TIMEOUT_SEC = 5;

	WebDriver driver;
	WebDriverWait wait;
	
	// DRIVER
	
	@Before
	public void setUp() throws Exception {

		File phantomJsExe = new File("phantomjs-1.9.7-windows/phantomjs.exe");
		Map<String, Object> caps = new HashMap<>();
		caps.put(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
				phantomJsExe.getAbsolutePath());
		DesiredCapabilities capabilities = new DesiredCapabilities(caps);
		driver = new PhantomJSDriver(capabilities);
		Dimension DEFAULT_WINDOW_SIZE = new Dimension(1024, 768);
		driver.manage().window().setSize(DEFAULT_WINDOW_SIZE);

		// driver = new FirefoxDriver();
		wait = new WebDriverWait(driver, PAGE_TIMEOUT_SEC);
	}

	@Rule
	public TestRule testWatcher = new TestWatcher() {
		@Override
		public void failed(Throwable t, Description test) {
			takeScreenshot("some name");
		}
	};

	public void takeScreenshot(String screenshotName) {
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
	
	@Before
	public void setUpLogin() throws Exception {
		Properties prop = new Properties();
		InputStream in = getClass().getClassLoader().getResourceAsStream("cred.properties");
		prop.load(in);
		in.close();
		
		USERNAME = prop.getProperty("sfr.wifi.login");
		PASSWORD = prop.getProperty("sfr.wifi.pass");
	}
	
	String USERNAME;
	String PASSWORD;

	@Test
	public void test_SFR_Wifi() throws Exception {
		driver.get(LogonPage.url);
		assertThat(driver.getCurrentUrl()).isEqualTo(LogonPage.url);
		assertThat(element(".headerSFR").isDisplayed()).isTrue();

		logon();
		waitUntilUrlAsChanged();
		assertThat(driver.getCurrentUrl()).contains(LogonPage.url);

		if (driver.getCurrentUrl().contains("already")) {

		} else {

			assertThat(element("#box").isDisplayed()).isTrue();
			assertThat(element("#contenuBox").getText()).contains("ERREUR");

			element("#fermerBox a").click();
			assertThat(element("#box").isDisplayed()).isFalse();

			logon();
			waitUntilUrlAsChanged();
			assertThat(driver.getCurrentUrl()).contains(LogonPage.url);
		}

		driver.get(TestPage.url);
		assertThat(driver.getCurrentUrl()).isEqualTo(TestPage.url);
		assertThat(element(TestPage.search).getAttribute("value")).contains("toto666");
	}
	
	private WebElement element(String cssSelector) {
		return driver.findElement(By.cssSelector(cssSelector));
	}
	
	private void logon() {
		element(LogonPage.login).sendKeys(USERNAME);
		element(LogonPage.password).sendKeys(PASSWORD);
		element(LogonPage.conditions).click();
		element(LogonPage.connexion).click();
	}
	
	// PAGE PATTERN
	
	class LogonPage {
		static final String url = "https://hotspot.wifi.sfr.fr/";

		static final String login = "#login";
		static final String password = "#password";
		static final String conditions = "#conditions";
		static final String connexion = "input[name='connexion']";
	}

	class TestPage {
		static final String url = "https://www.google.fr/search?q=toto666";
		static final String search = "input[name='q']";
	}
}