package com.dfremont.tool.hotspotautoconnect;

import static org.fest.assertions.Assertions.assertThat;

import org.openqa.selenium.WebDriver;

public class TestPage extends AbstractPage {

	final String search = "input[name='q']";

	public TestPage(WebDriver driver) {
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
