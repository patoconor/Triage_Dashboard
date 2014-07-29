package dashboard;

import java.awt.Dimension;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.chrome.ChromeDriver;

public class FIST  {
	static WebDriver driver;
	static String user;
	static String pass;
	static WebElement we;
	
	
	
	public static void login(Dimension screenSize){
		System.setProperty("webdriver.chrome.driver", "C://schema_creation/chromedriver.exe");
		
        driver = new ChromeDriver();
        driver.manage().window().setPosition(new Point(screenSize.width,screenSize.height));
        driver.get("https://cbap.hewitt.com/safe/");
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
	
}