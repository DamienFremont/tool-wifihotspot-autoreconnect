package com.dfremont.seleniumtemplate;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class WebPage {

	protected WebDriver driver;

	public WebPage(WebDriver driver) {
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
