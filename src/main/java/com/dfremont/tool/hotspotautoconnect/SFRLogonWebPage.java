package com.dfremont.tool.hotspotautoconnect;

import org.openqa.selenium.WebDriver;

import com.dfremont.seleniumtemplate.WebPage;

public class SFRLogonWebPage extends WebPage {

	public final String login = "#login";
	public final String password = "#password";
	public final String conditions = "#conditions";
	public final String connexion = "input[name='connexion']";
	public final String already = "already";

	public SFRLogonWebPage(WebDriver driver) {
		super(driver);
	}

	@Override
	public String getUrl() {
		return "https://hotspot.wifi.sfr.fr/";
	}

	@Override
	public Boolean isAt() {
		return driver.getCurrentUrl().contains(getUrl()) //
				&& element(".headerSFR").isDisplayed();
	}

	public void logon(String usr, String pwd) {
		element(login).sendKeys(usr);
		element(password).sendKeys(pwd);
		element(conditions).click();
		element(connexion).click();
	}

	public Boolean isError() {
		return element("#box").isDisplayed() //
				&& element("#contenuBox").getText().contains("ERREUR");
	}

	public void closeError() {
		element("#fermerBox a").click();
	}

}