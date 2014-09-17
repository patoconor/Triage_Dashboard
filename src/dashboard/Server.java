package dashboard;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import dashboard.ListItem;

public class Server {
	static String user;
	static String pw;
	
	static public void serverLogin(String serverName,int serverSelect)
	{
		System.setProperty("webdriver.chrome.driver", "C://schema_creation/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        if(serverSelect==0)
        {
		driver.get("https://" + user + ":" + pw + "@" + serverName + ".hewitt.com/");
        }
        else if (serverSelect==1)
        {
        	driver.get("https://" + user + ":" + pw + "@" + serverName + ".hewitt.com/WmRoot/log-server-recent.dsp");
        }
        else if(serverSelect==2)
        {
        	driver.get("https://" + user + ":" + pw + "@" + serverName + ".hewitt.com/WmRoot/log-error-recent.dsp");
        }
	}	
	
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
	
	
	public ArrayList<ListItem> CreateErrorLogs(WebDriver driver){
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ArrayList<ListItem> listItems = new ArrayList<ListItem>();
			System.setProperty("webdriver.chrome.driver", "C://schema_creation/chromedriver.exe");
	        for(int m=4;m<8;m++){
	        	int fileCount = 0;
				String temp[];	
		 	driver.get("https://pconor:Spektor33!@l4dwipap05"+m+"/WmRoot/log-error-recent.dsp");
	        WebElement baseTable1 = driver.findElement(By.xpath("//form[@name='logform']/table/tbody/tr[5]/td/table"));
	        WebElement baseTable1a = driver.findElement(By.xpath("//form[@name='logform']/table/tbody/tr[5]/td/table/tbody/tr[1]"));
	        WebElement baseTable2a = driver.findElement(By.xpath("//form[@name='logform']/table/tbody/tr[5]/td/table/tbody/tr[2]"));
	        WebElement baseTable3a = driver.findElement(By.xpath("//form[@name='logform']/table/tbody/tr[5]/td/table/tbody/tr[3]"));

	        List<WebElement> tableRows1 = baseTable1.findElements(By.tagName("td"));
	        for(int i=0; i<tableRows1.size();i++){
	        	if(tableRows1.get(i).getText().startsWith("2014")){
	        		fileCount++;
	        	}
	        		
	        }
	        System.out.println(fileCount);
	        List<WebElement> row1 = baseTable1a.findElements(By.tagName("td"));
	        List<WebElement> row2 = baseTable2a.findElements(By.tagName("td"));
	        List<WebElement> row3 = baseTable3a.findElements(By.tagName("td"));
	        ArrayList<String[]> data = new ArrayList<String[]>();
	        if(fileCount>0){
	        	for(int i = 4; i<fileCount+4; i++){
	        		temp = new String[4];
	        		for(int i2=1; i2<5; i2++ ){
	        			temp[i2-1] = driver.findElement(By.xpath("//form[@name='logform']/table/tbody/tr[5]/td/table/tbody/tr["+i+"]/td["+i2+"]")).getText().replaceAll("(\\r|\\n|\\t)", "*");
	        		}
	        		data.add(temp);
	        	}
	        }
	        
	        
	        for(int i=0; i<data.size();i++){
	        	System.out.println(data.get(i)[0]+"|"+data.get(i)[1]+"|"+data.get(i)[2]+"|"+data.get(i)[3]);
	        }
	        
	        if(data.size()==0){
	        	
	        }
	        if(data.size()==1){
	        	for(int i=0; i<data.size();i++){
	        	String dateTime = data.get(i)[0];
		        String service = data.get(i)[1];
		        String stack = data.get(i)[2];
		        String error = data.get(i)[3];
		        String nums = "0123456789";
		        String server = "l4dwipap05"+m;
		        String fileID = "?";
		        if(service.contains(":HA")&& numCheck(service.charAt(service.indexOf(":HA")+3))==true){
        			fileID = service.substring(service.indexOf(":HA")+3,service.indexOf(":HA")+8);
        		}
        		if(stack.contains(":HA")&& numCheck(stack.charAt(stack.indexOf(":HA")+3))==true){
        			fileID = stack.substring(stack.indexOf(":HA")+3,stack.indexOf(":HA")+8);
        		}
        		if(error.contains(":HA")&& numCheck(error.charAt(error.indexOf(":HA")+3))==true){
        			fileID = error.substring(error.indexOf(":HA")+3,error.indexOf(":HA")+8);
        		}
        		if(fileID.equals("?")){
        			driver.get("https://pconor:Spektor33!@l4dwipap05"+m+"/WmRoot/log-server-recent.dsp");
        			String longstr = driver.getPageSource().toString();
        	    	String[] line = longstr.split("\n");

        	    	for (int i2=0;i2<line.length;i2++){
        	    		if(line[i2].contains(dateTime.split(" ")[1])&&line[i2].contains("Successfully inserted in messageQ table")){
        	    			fileID = line[i2].substring(line[i2].indexOf("----")-5, line[i2].indexOf("----"));
        	    			
        	    		}
        	    	}
        		}
        		System.out.println("fileID: "+fileID);
		        listItems.add(new ListItem(fileID,dateTime,server,stack,error,service));
	        	}
	        }
	        ArrayList<String> uniques = new ArrayList<String>();
	        if(data.size()>1){
	        for(int i=1; i<data.size();i++){
	        	uniques.add(data.get(i)[0]);
	        }
	        	@SuppressWarnings("unchecked")
				Set<String> uniqueValues = new HashSet<String>(uniques);
	        	String[] uV = new String[uniqueValues.size()];
	        	for(int h=0;h<uV.length;h++){
	        		uV[h]= uniqueValues.toArray()[h].toString();
	        	}
	        	Arrays.sort(uV, Collections.reverseOrder());
	        for(int j = 0; j< uV.length;j++){
	        	System.out.println(uV[j]);
	        }
	        
	        for(int k=0;k<uV.length;k++){
	        	String dateTime = "";
	            String fileID = "?";
	            String service = "" ;
	            String stack = "" ;
	            String error = "";
	            String server1 = "l4dwipap05"+m;
	            String nums = "0123456789";
	        	for(int l=0;l<data.size();l++){
	        		if(data.get(l)[0].equals(uV[k])){
	        			if(service.equals(data.get(l)[1])){
	        				service = data.get(l)[1];
	        			}
	        			else{
	        	        service += "*"+data.get(l)[1];
	        			}
	        			if(stack.equals( data.get(l)[2])){
	        				stack =  data.get(l)[2];
	        			}
	        			else{
	        				stack += "*"+data.get(l)[2];
	        			}
	        			if(error.equals( data.get(l)[3])){
	        				error =  data.get(l)[3];
	        			}
	        			else{
	        				error += "*"+data.get(l)[3];
	        			}
	        	        
	        		}
	        		dateTime = uV[k];
	        		if(service.contains(":HA")&& numCheck(service.charAt(service.indexOf(":HA")+3))==true){
	        			fileID = service.substring(service.indexOf(":HA")+3,service.indexOf(":HA")+8);
	        		}
	        		if(stack.contains(":HA")&& numCheck(stack.charAt(stack.indexOf(":HA")+3))==true){
	        			fileID = stack.substring(stack.indexOf(":HA")+3,stack.indexOf(":HA")+8);
	        		}
	        		if(error.contains(":HA")&& numCheck(error.charAt(error.indexOf(":HA")+3))==true){
	        			fileID = error.substring(error.indexOf(":HA")+3,error.indexOf(":HA")+8);
	        		}
	        	}
	        	if(fileID.equals("?")){
        			driver.get("https://pconor:Spektor33!@l4dwipap05"+m+"/WmRoot/log-server-recent.dsp");
        			String longstr = driver.getPageSource().toString();
        	    	String[] line = longstr.split("\n");

        	    	for (int i2=0;i2<line.length;i2++){
        	    		if(line[i2].contains(dateTime.split(" ")[1])&&line[i2].contains("Successfully inserted in messageQ table")){
        	    			fileID = line[i2].substring(line[i2].indexOf("----")-5, line[i2].indexOf("----"));
        	    			
        	    		}
        	    	}
        		}
	        	listItems.add(new ListItem(fileID,dateTime,server1,stack,error,service));
	        }
	}
	        }
	        for(int i=0;i<listItems.size();i++){
	        	System.out.println((listItems.get(i).getFileID()+"|"+listItems.get(i).getDateTime()+"|"+listItems.get(i).getServer()+"|"+listItems.get(i).getService()+"|"+listItems.get(i).getErrorMessage()+"|"+listItems.get(i).getStackTrace()));
	        }
	        
	        return listItems;
	        
	}
	public Boolean numCheck(char test){
		Boolean ret = false;
		if(test=='0'||test=='1'||test=='2'||test=='3'||test=='4'||test=='5'||test=='6'||test=='7'||test=='8'||test=='9'){
			ret = true;
		}
		return ret;
	}
	
}
