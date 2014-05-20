package com.dfremont.seleniumtemplate.phantomjs;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

public class PhantomJsWebDriverFactory {

	public WebDriver get() {
		
		File phantomJsExe = new File("phantomjs-1.9.7-windows/phantomjs.exe");
		Map<String, Object> caps = new HashMap<>();
		caps.put(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
				phantomJsExe.getAbsolutePath());
		DesiredCapabilities capabilities = new DesiredCapabilities(caps);
		WebDriver driver = new PhantomJSDriver(capabilities);
		
		Dimension DEFAULT_WINDOW_SIZE = new Dimension(1024, 768);
		driver.manage().window().setSize(DEFAULT_WINDOW_SIZE);
		
		return driver;
	}
}
