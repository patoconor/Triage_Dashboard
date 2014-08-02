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
	
	public static int getNumberOfExpectations()
	{
		//must call getExpectationsPage first
		String xpathStart = "//form[@name='expectationsform']/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[4]/td/table/tbody/tr/td/table/tbody/tr[";
		String xpathEnd = "]/td[19]/span";
		int expectNum=0;
		
		for (int i = 2;driver.findElements(By.xpath(xpathStart + i + xpathEnd)).size()!=0;i++)
		{
			expectNum=i-1;
		}
		
		return expectNum;
	}
	
	public static String getEnvironment(int num)
	{
		//must call getExpectationsPage first
		String xpathStart = "//form[@name='expectationsform']/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[4]/td/table/tbody/tr/td/table/tbody/tr[";
		String xpathEnd = "]/td[3]/span";
		we = driver.findElement(By.xpath(xpathStart + num + xpathEnd));
		return we.getText();
	}
	
	public static String getExpectID(int num)
	{
		//must call getExpectationsPage first
		String xpathStart = "//form[@name='expectationsform']/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[4]/td/table/tbody/tr/td/table/tbody/tr[";
		String xpathEnd = "]/td[19]/span";
		we = driver.findElement(By.xpath(xpathStart + num + xpathEnd));
		return we.getText();
	}
	
	public static String getExpectStatus(int num)
	{
		//must call getExpectationsPage first
		String xpathStart = "//form[@name='expectationsform']/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[4]/td/table/tbody/tr/td/table/tbody/tr[";
		String xpathEnd = "]/td[9]/select";
		Select select = new Select(driver.findElement(By.xpath(xpathStart + num + xpathEnd)));
		we = select.getFirstSelectedOption();
		return we.getText();
	}
	
	public static String getExpectDate(int num)
	{
		//must call getExpectationsPage first
		String xpathStart = "//form[@name='expectationsform']/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[4]/td/table/tbody/tr/td/table/tbody/tr[";
		String xpathEnd = "]/td[5]/input";
		we = driver.findElement(By.xpath(xpathStart + num + xpathEnd));
		return we.getAttribute("value");
	}
	
	public static String getStartTime(int num)
	{
		//must call getExpectationsPage first
		String xpathStart = "//form[@name='expectationsform']/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[4]/td/table/tbody/tr/td/table/tbody/tr[";
		String xpathEnd = "]/td[11]/span";
		we = driver.findElement(By.xpath(xpathStart + num + xpathEnd));
		return we.getText();
	}
	
	public static String getEndTime(int num)
	{
		//must call getExpectationsPage first
		String xpathStart = "//form[@name='expectationsform']/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[4]/td/table/tbody/tr/td/table/tbody/tr[";
		String xpathEnd = "]/td[12]/span";
		we = driver.findElement(By.xpath(xpathStart + num + xpathEnd));
		return we.getText();
	}
	
	public static String getAnalyst(int num)
	{
		//call this last since it's on a different page than the other expectation items
		//must call getExpectationsPage first
				String xpathStart ="//form[@name='expectationsform']/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[4]/td/table/tbody/tr/td/table/tbody/tr[";
				String xpathEnd = "]/td[17]/select/option[@value='2']";
				
				we = driver.findElement(By.xpath(xpathStart + num + xpathEnd));
				we.click();
				
				WebDriverWait wait = new WebDriverWait(driver, 10);

				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//html/body/table[2]/tbody/tr/td/table/tbody/tr/td[3]/table/tbody/tr/td/div/div/table/tbody/tr[3]/td/form/table[3]/tbody/tr/td/table/tbody/tr[2]/td[4]/span")));
				
				we=driver.findElement(By.xpath("//html/body/table[2]/tbody/tr/td/table/tbody/tr/td[3]/table/tbody/tr/td/div/div/table/tbody/tr[3]/td/form/table[3]/tbody/tr/td/table/tbody/tr[2]/td[4]/span"));
				String sName = we.getText();
				driver.navigate().back();
				we=driver.findElement(By.xpath("//html"));
				return sName;
	}
	
}