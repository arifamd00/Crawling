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

public class TestRunner {
	
	WebDriver driver;
	
	@BeforeMethod
	public void settingUp() {
		
	}
	
	@Test(dataProvider="csvReader", dataProviderClass = csvReader.class)
	public void crawl(Object[] data) {
		int crawling = 0;
		String store = null;
		
		while(crawling<data.length) {
			String action = data[crawling].toString().split("=>")[0];
			if(action.equals("open")) {
				driver = new ChromeDriver();
				String url = data[crawling].toString().split("=>")[1];
				driver.get(url);
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
			}else if(action.equals("click")) {
				String param = data[crawling].toString().split("=>")[1];
				driver.findElement(By.xpath(param)).click();
			}else if(action.equals("enterText")) {
				String xpath = data[crawling].toString().split("=>")[1];
				String text = data[crawling].toString().split("=>")[2];
				driver.findElement(By.xpath(xpath)).sendKeys(text);
			}else if(action.equals("jsClick")) {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				String xpath = data[crawling].toString().split("=>")[1];
				WebElement obj = driver.findElement(By.xpath(xpath));
				js.executeScript("arguments[0].click();", obj);
			}else if(action.equals("selectFromStaticDropdown")) {
				String xpath = data[crawling].toString().split("=>")[1];
				String dataTobeSelected = data[crawling].toString().split("=>")[2];
				Select select = new Select(driver.findElement(By.xpath(xpath)));
				select.selectByVisibleText(dataTobeSelected);
			} else if(action.equals("getText")) {
				String xpath = data[crawling].toString().split("=>")[1];
				String text = driver.findElement(By.xpath(xpath)).getText();
				System.out.println(text);
			}else if(action.equals("storeResult")) {
				String xpath = data[crawling].toString().split("=>")[1];
				store = driver.findElement(By.xpath(xpath)).getText();
			}
			else if(action.equals("assertEquals")) {
				String expected = data[crawling].toString().split("=>")[1];
				if(store !=null) {
					Assert.assertEquals(store, expected, "Actual and expected condition not matched. Actual: "+store);
				}else {
					Assert.assertNotNull(store, "Cannot perform assert operation because no actual result is stored. please perform getText action first to store result");
				}
			}else if(action.equals("waitFor")) {
				String xpath = data[crawling].toString().split("=>")[1];
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
				
			}
			
			
			
			crawling +=1;
		}
		
	}
	
	@AfterMethod
	public void tearDown() {
		driver.close();
	}

}
