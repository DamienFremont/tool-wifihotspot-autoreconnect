package com.dfremont.tool.hotspotautoconnect.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class APage {

	protected WebDriver driver;

	public APage(WebDriver driver) {
		this.driver = driver;
	}

	protected WebElement element(String cssSelector) {
		return driver.findElement(By.cssSelector(cssSelector));
	}
	
	public Boolean isAt() {
		throw new IllegalAccessError("You must implement this method first !");
	}
	public String getUrl() {
		throw new IllegalAccessError("You must implement this method first !");
	}
}
