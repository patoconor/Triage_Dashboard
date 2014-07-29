package dashboard;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Server {
	String user;
	String pw;
	public void CreateServerLogs() throws FileNotFoundException, UnsupportedEncodingException{
		System.setProperty("webdriver.chrome.driver", "C://schema_creation/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        
        PrintWriter writer = null;
        writer = new PrintWriter("C://Triage_Dashboard//wmTestLogs054.txt", "UTF-8");
        driver.get("https://"+user+":"+pw+"@l4dwipap054/WmRoot/log-server-recent.dsp");
        WebElement baseTable = driver.findElement(By.xpath("//form[@name='logform']/table/tbody/tr[5]/td/table"));
        List<WebElement> tableRows = baseTable.findElements(By.tagName("tr"));
        for(int i=0;i<tableRows.size();i++){
        	writer.println(tableRows.get(i).getText());
        }
        writer.close();
        
        
        PrintWriter writer2 = null;
        writer2 = new PrintWriter("C://Triage_Dashboard//wmTestLogs055.txt", "UTF-8");
        driver.get("https://"+user+":"+pw+"@l4dwipap055/WmRoot/log-server-recent.dsp");
        WebElement baseTable2 = driver.findElement(By.xpath("//form[@name='logform']/table/tbody/tr[5]/td/table"));
        List<WebElement> tableRows2 = baseTable2.findElements(By.tagName("tr"));
        for(int i=0;i<tableRows2.size();i++){
        	writer2.println(tableRows2.get(i).getText());
        }
        writer2.close();
        
        PrintWriter writer3 = null;
        writer3 = new PrintWriter("C://Triage_Dashboard//wmTestLogs056.txt", "UTF-8");
        driver.get("https://"+user+":"+pw+"@l4dwipap056/WmRoot/log-server-recent.dsp");
        WebElement baseTable3 = driver.findElement(By.xpath("//form[@name='logform']/table/tbody/tr[5]/td/table"));
        List<WebElement> tableRows3 = baseTable3.findElements(By.tagName("tr"));
        for(int i=0;i<tableRows3.size();i++){
        	writer3.println(tableRows3.get(i).getText());
        }
        writer3.close();
        
        
        PrintWriter writer4 = null;
        writer4 = new PrintWriter("C://Triage_Dashboard//wmTestLogs057.txt", "UTF-8");
        driver.get("https://"+user+":"+pw+"@l4dwipap057/WmRoot/log-server-recent.dsp");
        WebElement baseTable4 = driver.findElement(By.xpath("//form[@name='logform']/table/tbody/tr[5]/td/table"));
        List<WebElement> tableRows4 = baseTable4.findElements(By.tagName("tr"));
        for(int i=0;i<tableRows4.size();i++){
        	writer4.println(tableRows4.get(i).getText());
        }
        writer4.close();
        
       driver.close();
	}
	
	
}
