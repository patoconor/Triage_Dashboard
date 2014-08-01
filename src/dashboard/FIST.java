package dashboard;

import java.awt.Dimension;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.chrome.ChromeDriver;

public class FIST  {
	static WebDriver driver;
	static String user;
	static String pass;
	static WebElement we;
	
	public static void startDriver(Dimension screenSize){
		System.setProperty("webdriver.chrome.driver", "C://schema_creation/chromedriver.exe");
		
        driver = new ChromeDriver();
        driver.manage().window().setPosition(new Point(screenSize.width,screenSize.height)); 
	}
	
	public static void loginProd(){
		driver.get("https://cbap.hewitt.com/safe/");
        WebElement element = driver.findElement(By.name("username"));
        WebElement element1 = driver.findElement(By.name("password"));
        
        
        element.sendKeys(user);
        element1.sendKeys(pass);
        element1.submit();
   
	}
	
	public static void loginConfig(){
		driver.get("https://gray.cbauat.com/safe/");
        WebElement element = driver.findElement(By.name("username"));
        WebElement element1 = driver.findElement(By.name("password"));
        
        
        element.sendKeys(user);
        element1.sendKeys(pass);
        element1.submit();
   
	}
	public static String getDevName(String fileID)
	{
		driver.get("https://cbap.hewitt.com/fist/wicket/bookmarkable/com.aonhewitt.fist.page.FindDeployments?2");
        we = driver.findElement(By.cssSelector("input[name='fileIDs'"));
        we.sendKeys(fileID);
        
        we = driver.findElement(By.cssSelector("input[name='find'"));
        we.click();
        
        
        we = new Select(driver.findElement(By.name("developerName"))).getFirstSelectedOption();
        return we.getText();
        
	}
	public static String getLocation(String fileID)
	{
		driver.get("https://cbap.hewitt.com/fist/wicket/bookmarkable/com.aonhewitt.fist.page.ListWebMethodsServices?5");
        we = driver.findElement(By.cssSelector("input[name='fileIDs'"));
        we.sendKeys(fileID);
        
        we = driver.findElement(By.cssSelector("input[name='find'"));
        we.click();
        
        
        we = driver.findElement((By.className("rowOdd")));
        List<WebElement> rows = we.findElements(By.tagName("td"));
      
        return rows.get(1).getText();
	}
	
	public static void getExpectationsPage(String fileID, String expectDate)
	{
		driver.get("https://gray.cbauat.com/fist/wicket/bookmarkable/com.aonhewitt.fist.page.SearchExpectations?1");
		
		we = driver.findElement(By.cssSelector("input[name='beginDate'"));
		we.clear();
        we.sendKeys(expectDate);
		we = driver.findElement(By.cssSelector("input[name='fileIDs'"));
        we.sendKeys(fileID);
        we = driver.findElement(By.cssSelector("input[name='search'"));
        we.click();
	}
	
	public static String getEnvironment()
	{
		//must call getExpectationsPage first
		String xpath = "//form[@name='expectationsform']/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[4]/td/table/tbody/tr/td/table/tbody/tr[2]/td[3]/span";
		we = driver.findElement(By.xpath(xpath));
		return we.getText();
	}
	
	public static String getExpectID()
	{
		//must call getExpectationsPage first
		String xpath = "//form[@name='expectationsform']/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[4]/td/table/tbody/tr/td/table/tbody/tr[2]/td[19]/span";
		we = driver.findElement(By.xpath(xpath));
		return we.getText();
	}
	
	public static String getAnalyst()
	{
		//must call getExpectationsPage first
				String xpath = "//form[@name='expectationsform']/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[4]/td/table/tbody/tr/td/table/tbody/tr[2]/td[17]/select/option[@value='2']";
				we = driver.findElement(By.xpath(xpath));
				we.click();
				
				WebDriverWait wait = new WebDriverWait(driver, 10);

				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//html/body/table[2]/tbody/tr/td/table/tbody/tr/td[3]/table/tbody/tr/td/div/div/table/tbody/tr[3]/td/form/table[3]/tbody/tr/td/table/tbody/tr[2]/td[4]/span")));
				
				we=driver.findElement(By.xpath("//html/body/table[2]/tbody/tr/td/table/tbody/tr/td[3]/table/tbody/tr/td/div/div/table/tbody/tr[3]/td/form/table[3]/tbody/tr/td/table/tbody/tr[2]/td[4]/span"));
				return we.getText();
	}
	
}