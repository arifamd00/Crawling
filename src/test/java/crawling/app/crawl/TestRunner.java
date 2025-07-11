package crawling.app.crawl;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
	private Logger log = LogManager.getLogger(TestRunner.class);
	@BeforeMethod
	public void settingUp() {

	}

	@Test(dataProvider="csvReader2", dataProviderClass = csvReader.class)
	public void crawl(Object[] data) {
		int crawling = 0;
		String store = null;
		try {
			while(crawling<data.length) {
				String action = data[crawling].toString().split("=>")[0];
				if(action.equals("open")) {
					log.info("Initializing browser");
					driver = new ChromeDriver();
					String url = data[crawling].toString().split("=>")[1];
					driver.get(url);
					driver.manage().window().maximize();
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
				}else if(action.equals("click")) {
					String param = data[crawling].toString().split("=>")[1];
					log.info("performing click operation");
					driver.findElement(By.xpath(param)).click();
				}else if(action.equals("enterText")) {
					String xpath = data[crawling].toString().split("=>")[1];
					String text = data[crawling].toString().split("=>")[2];
					driver.findElement(By.xpath(xpath)).sendKeys(text);
				}else if(action.equals("jsClick")) {
					System.out.println("invoked jsClick");
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

				}else if(action.equals("getTexts")) {
					System.out.println("invoking getTexts");
					String xpath = data[crawling].toString().split("=>")[1];
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
					List<String> texts = driver.findElements(By.xpath(xpath)).stream().map(e->e.getText()).collect(Collectors.toList());
					for(String text: texts) {
						System.out.println(text);
					}
				}else if(action.equalsIgnoreCase("jsChangeAttribute")) {
					System.out.println("invoking js change attribute");
					String xpath = data[crawling].toString().split("=>")[1];
					String attribute = data[crawling].toString().split("=>")[2].split(":")[0];
					String value = data[crawling].toString().split("=>")[2].split(":")[1];
					WebElement ele = driver.findElement(By.xpath(xpath));
					JavascriptExecutor js = (JavascriptExecutor)driver;
					
					js.executeScript("arguments[0].style.display='none';", ele);
					
					//"arguments[0].setAttribute('"+attribute+"','"+value+"');", ele
				}



				crawling +=1;
			}
		}catch(Exception e) {
			log.error(e.getMessage());
		}

	}
	////div[@id='showApplication0']//div[contains(@class,'step')]//div[@name='statusName']/span
	//,jsClick=>//div[contains(@class, 'owl-item')][1]//div[contains(@class,'customCard3')]//button[contains(@class,'customButton')]
	@AfterMethod
	public void tearDown() {
		//driver.close();
	}
	//open=>https://career.infosys.com/login

}

//open=>https://career.infosys.com/login,enterText=>//input[@id='username']=>mohaarif0123@gmail.com,enterText=>//input[@id='password']=>arifAMD@001,click=>//button[@id='btnSubmit'],waitFor=>//a[text()='My Application'],"jsClick=>//div[contains(@class,'dialog')]//button//span[text()='Ok']",jsClick=>//a[text()='My Application'],getTexts=>//div[@class='row app-cardStyle']/div/div//h2

