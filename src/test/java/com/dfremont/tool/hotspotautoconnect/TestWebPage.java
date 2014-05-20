package com.dfremont.tool.hotspotautoconnect;

import static org.fest.assertions.Assertions.assertThat;

import org.openqa.selenium.WebDriver;

import com.dfremont.seleniumtemplate.WebPage;

public class TestWebPage extends WebPage {

	final String search = "input[name='q']";

	public TestWebPage(WebDriver driver) {
		super(driver);
	}

	@Override
	public String getUrl() {
		return "https://www.google.fr/search?q=toto666";
	}

	@Override
	public Boolean isAt() {
		assertThat(driver.getCurrentUrl()).isEqualTo(getUrl());
		assertThat(element(search).getAttribute("value")).contains("toto666");
		return true;
	}
}
