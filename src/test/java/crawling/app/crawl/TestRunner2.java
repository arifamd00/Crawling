package crawling.app.crawl;

import java.lang.reflect.Method;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import utils.csvReader;

public class TestRunner2 {
	
	WebDriver driver;
	
	@BeforeMethod
	public void settingUp() {
		
	}
	
	@Test(dataProvider="csvReader", dataProviderClass = csvReader.class)
	public void crawl(Object[] data) {
		int crawling = 0;
		String store = null;
		System.out.println(data.length);
		while(crawling<data.length) {
			System.out.println(data[crawling]);
			
			
			
			crawling +=1;
		}
		
		
	}
	
	@AfterMethod
	public void tearDown() {
		//driver.quit();
	}

}
