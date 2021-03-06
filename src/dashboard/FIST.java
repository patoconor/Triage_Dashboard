package dashboard;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.thoughtworks.selenium.Selenium;

public class FIST  {
	private WebDriver driver;
	private String user;
	private String pass;
	private WebElement we;
	private String URLstart;
	Dimension screenSize;
	public static void main(String[] args) throws InterruptedException{
		System.setProperty("webdriver.ie.driver","C://schema_creation//IEDriverServer.exe");
	     WebDriver driver1 = new InternetExplorerDriver();
		FIST fist = new FIST("poconorhra", "Spektor33!", "Hunt Valley", false, driver1);
		fist.loginConfig();
		//System.out.println(fist.getDevName("62817"));
		//System.out.println(fist.getDevName("65555"));
		System.out.println(fist.getLocation("63805"));
	}
	FIST (String username, String password, String officeLocation, boolean isHidden, WebDriver dr)
	{
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		user=username;
		pass=password;
		if(officeLocation.equals("Winston Salem"))
		{
			URLstart="https://gray.cbauat.com/";
		}
		else if(officeLocation.equals("Hunt Valley"))
		{
			URLstart="https://cbagray.hewitt.com/";
		}
		
		driver = dr;
//        if (isHidden ==true)
//        {
//        	ChromeOptions options = new ChromeOptions();
//    		options.addArguments("--window-position="+screenSize.width+","+screenSize.height);
//        	driver = new ChromeDriver(options);
//        }
//        else
//        {
//        	driver = new ChromeDriver();
//        }
		
	}
	
	public String getUser() {
		return user;
	}

	public void setUser(String username) {
		this.user = username;
	}
	
	public String getPass() {
		return pass;
	}

	public void setPass(String password) {
		this.pass = password;
	}
	
	public void closeDriver() {
		driver.close();
	}
	
	public void loginProd() throws InterruptedException{
		driver.get("https://cbap.hewitt.com/safe/");
        we = driver.findElement(By.name("username"));
        we.sendKeys(user);
        we = driver.findElement(By.name("password"));
        we.sendKeys(pass);
        we.submit();
        Thread.sleep(5000);
   
	}
	
	public void loginConfig() throws InterruptedException{
		driver.get(URLstart+ "safe/");
		we = driver.findElement(By.name("username"));
        we.sendKeys(user);
        we = driver.findElement(By.name("password"));
        we.sendKeys(pass);
        we.submit();
        Thread.sleep(5000);
   
	}
	public String getDevName(String fileID)
	{
		driver.get("https://cbap.hewitt.com/fist/wicket/bookmarkable/com.aonhewitt.fist.page.FindDeployments?2");
        we = driver.findElement(By.name("fileIDs"));
        we.sendKeys(fileID);
        
        we = driver.findElement(By.name("find"));
        we.click();
        
        String sReturn= "No Developer Found";
        int count = driver.findElements(By.name("developerName")).size();
        if(count > 0)
        {
        	we = new Select(driver.findElement(By.name("developerName"))).getFirstSelectedOption();
        	sReturn = we.getText();
        
        }
        if(driver.findElements(By.className("feedbackPanelERROR")).size()>0){
        	sReturn = "No Developer Found";
        }
        else if(count == 0)
        {
        	we = driver.findElement((By.className("rowOdd")));
            List<WebElement> rows = we.findElements(By.tagName("td"));
            sReturn = rows.get(3).getText();
        }
        return sReturn;
        
	}
	public String getLocation(String fileID)
	{
		driver.get(URLstart+"fist/wicket/bookmarkable/com.aonhewitt.fist.page.ListWebMethodsServices");
        we = driver.findElement(By.name("fileIDs"));
        we.sendKeys(fileID);
        
        we = driver.findElement(By.name("find"));
        we.click();
        
        String sReturn;
        if(driver.findElements(By.className("rowOdd")).size() != 0)
        {
        	we = driver.findElement((By.className("rowOdd")));
            List<WebElement> rows = we.findElements(By.tagName("td"));
            sReturn = rows.get(1).getText();
        }
        else 
        {
        	sReturn = "No Location Found";
        }
        
      
        return sReturn;
	}
	
	public void getExpectationsPage(String fileID, String expectDate)
	{
		driver.get(URLstart+"fist/wicket/bookmarkable/com.aonhewitt.fist.page.SearchExpectations?1");
		
		we = driver.findElement(By.name("beginDate"));
		we.clear();
        we.sendKeys(expectDate);
		we = driver.findElement(By.name("fileIDs"));
        we.sendKeys(fileID);
        we = driver.findElement(By.name("search"));
        we.click();
	}
	
	public int getNumberOfExpectations()
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
	
	public String getEnvironment(int num)
	{
		//must call getExpectationsPage first
		String xpathStart = "//form[@name='expectationsform']/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[4]/td/table/tbody/tr/td/table/tbody/tr[";
		String xpathEnd = "]/td[3]/span";
		we = driver.findElement(By.xpath(xpathStart + num + xpathEnd));
		return we.getText();
	}
	
	public String getExpectID(int num)
	{
		//must call getExpectationsPage first
		String xpathStart = "//form[@name='expectationsform']/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[4]/td/table/tbody/tr/td/table/tbody/tr[";
		String xpathEnd = "]/td[19]/span";
		we = driver.findElement(By.xpath(xpathStart + num + xpathEnd));
		return we.getText();
	}
	
	public String getExpectStatus(int num)
	{
		//must call getExpectationsPage first
		String xpathStart = "//form[@name='expectationsform']/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[4]/td/table/tbody/tr/td/table/tbody/tr[";
		String xpathEnd = "]/td[9]/select";
		Select select = new Select(driver.findElement(By.xpath(xpathStart + num + xpathEnd)));
		we = select.getFirstSelectedOption();
		return we.getText();
	}
	
	public String getExpectDate(int num)
	{
		//must call getExpectationsPage first
		String xpathStart = "//form[@name='expectationsform']/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[4]/td/table/tbody/tr/td/table/tbody/tr[";
		String xpathEnd = "]/td[5]/input";
		
		
		
		we = driver.findElement(By.xpath(xpathStart + num + xpathEnd));
		if(driver.findElement(By.xpath(xpathStart + num + "]")).getAttribute("class").equals("rowFail"))
		{
			return (we.getAttribute("value") + "   ERROR");
		}
		else if(driver.findElement(By.xpath(xpathStart + num + "]")).getAttribute("class").equals("rowPaused"))
		{
			return (we.getAttribute("value") + "   PAUSED");
		}
		else
		{
			return we.getAttribute("value");
		}
	}
	
	public String getStartTime(int num)
	{
		//must call getExpectationsPage first
		String xpathStart = "//form[@name='expectationsform']/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[4]/td/table/tbody/tr/td/table/tbody/tr[";
		String xpathEnd = "]/td[11]/span";
		we = driver.findElement(By.xpath(xpathStart + num + xpathEnd));
		return we.getText();
	}
	
	public String getEndTime(int num)
	{
		//must call getExpectationsPage first
		String xpathStart = "//form[@name='expectationsform']/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[4]/td/table/tbody/tr/td/table/tbody/tr[";
		String xpathEnd = "]/td[12]/span";
		we = driver.findElement(By.xpath(xpathStart + num + xpathEnd));
		return we.getText();
	}
	
	public String getAnalyst(int num)
	{
		//call this last since it's on a different page than the other expectation items
		//must call getExpectationsPage first
				String xpathStart ="//form[@name='expectationsform']/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[4]/td/table/tbody/tr/td/table/tbody/tr[";
				String xpathEnd = "]/td[17]/select/option[@value='2']";
				
				we = driver.findElement(By.xpath(xpathStart + num + xpathEnd));
				we.click();
				
				WebDriverWait wait = new WebDriverWait(driver, 10);
				try{
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//html/body/table[2]/tbody/tr/td/table/tbody/tr/td[3]/table/tbody/tr/td/div/div/table/tbody/tr[3]/td/form/table[3]/tbody/tr/td/table/tbody/tr[2]/td[4]/span")));
				}catch(TimeoutException toe){
					 
					return "";
			 
				}
				we=driver.findElement(By.xpath("//html/body/table[2]/tbody/tr/td/table/tbody/tr/td[3]/table/tbody/tr/td/div/div/table/tbody/tr[3]/td/form/table[3]/tbody/tr/td/table/tbody/tr[2]/td[4]/span"));
				String sName = we.getText();
				driver.navigate().back();
				we=driver.findElement(By.xpath("//html"));
				return sName;
	}
	
	public boolean errorReply(int expectNum, String fileID, String reply)
	{
		//must call getExpectationsPage first
			boolean bReplied=false;
		
				String xpathStart ="//form[@name='expectationsform']/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[4]/td/table/tbody/tr/td/table/tbody/tr[";
				String xpathEnd = "]/td[17]/select/option[@value='1']";
				
				we = driver.findElement(By.xpath(xpathStart + expectNum + xpathEnd));
				we.click();
				
				try{
				
				WebDriverWait wait = new WebDriverWait(driver, 10);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(fileID)));
				
				
		        	we = driver.findElement(By.partialLinkText(fileID));
		        	we.click();
		        	we = driver.findElement(By.name("selectedReply"));
		        	we.click();
		        	
		        	we.sendKeys(reply);
		        	
		        	
		        	we = driver.findElement(By.name("replyButton"));
		        	we.click();
		        	
		        	bReplied=true;
		        	
		        }
				catch(Exception e)
				{
					driver.quit();
					JOptionPane.showMessageDialog(null,"The selected expectation is not an error.");
					bReplied=false;
				}
				
				return bReplied;
	}
	
}