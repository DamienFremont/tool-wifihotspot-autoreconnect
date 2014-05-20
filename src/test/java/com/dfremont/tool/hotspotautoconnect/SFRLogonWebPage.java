package com.dfremont.tool.hotspotautoconnect.page;

import static org.fest.assertions.Assertions.assertThat;

import org.openqa.selenium.WebDriver;

public class SFRLogonPage extends APage {

	public final String login = "#login";
	public final String password = "#password";
	public final String conditions = "#conditions";
	public final String connexion = "input[name='connexion']";
	public final String already = "already";

	public SFRLogonPage(WebDriver driver) {
		super(driver);
	}

	@Override
	public String getUrl() {
		return "https://hotspot.wifi.sfr.fr/";
	}

	@Override
	public Boolean isAt() {
		assertThat(driver.getCurrentUrl()).contains(getUrl());
		assertThat(element(".headerSFR").isDisplayed()).isTrue();
		return true;
	}

	public void logon(String usr, String pwd) {
		element(login).sendKeys(usr);
		element(password).sendKeys(pwd);
		element(conditions).click();
		element(connexion).click();
	}

	public boolean isError() {
		assertThat(element("#box").isDisplayed()).isTrue();
		assertThat(element("#contenuBox").getText()).contains("ERREUR");
		return true;
	}

	public void closeError() {
		element("#fermerBox a").click();
		assertThat(element("#box").isDisplayed()).isFalse();
	}

}