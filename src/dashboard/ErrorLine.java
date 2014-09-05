package dashboard;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import org.firebirdsql.management.FBManager;
import org.firebirdsql.jdbc.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ErrorLine {
	
	//private String OfficeLocation="Winston Salem";
	private String OfficeLocation="Hunt Valley";
	private char delimeter;
	
	private String FullLine = "";
	
	private String fistUser;
	private String fistPass;
	
	private String status;
	private String fileID;
	private String devName;
	private String date;
	private String time;
	private String serviceLocation;
	private String errorMessage;
	private String server;
	private String stackTrace;
	
	
	private int NumberOfExpectations;
	private ArrayList <String> expectDateList;
	private ArrayList <String> expectIDList;
	private ArrayList <String> statusList;
	private ArrayList <String> analystNameList;
	private ArrayList <String> environmentList;
	private ArrayList <String> startTimeList;
	private ArrayList <String> endTimeList;
	public static void main (String[] args){
//		int run = 0;
//		System.setProperty("webdriver.chrome.driver", "C://schema_creation/chromedriver.exe");
//		WebDriver dr = new ChromeDriver();
//		while(run == 0){
//		Server server = new Server();
//		ArrayList<ListItem> listItems = server.CreateErrorLogs(dr);
//		System.out.println(listItems.size());
//		ErrorLine erline = new ErrorLine();
//		erline.createErrorLine("poconorhra","Spektor33!",listItems,dr);
//		}
//		
		insertMessageQ();
	}
	public static void insertMessageQ(){
		Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;
        Connection connection2 = null;
        ResultSet resultSet2 = null;
        Statement statement2 = null;
		try {
            connection2 = DriverManager
                    .getConnection(
                            "jdbc:firebirdsql://localhost:3050/C:/database/BASE.fdb",
                            "sysdba", "masterkey");
            statement2 = connection2.createStatement();
            resultSet2 = statement2.executeQuery("select DISTINCT * from TRIAGE");
            String connectionURL = "jdbc:jtds:sqlserver://L4DWIPDS011.hewitt.com:13163/;" + "databaseName=FileConfig;user=poconor;password=Spektor27!;";
            connection = DriverManager
                    .getConnection(connectionURL);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT TOP 1000 *   FROM [FileConfig].[dbo].[messageQ]   WHERE msg_toaddressoverride NOT LIKE '%phuong.dam@aonhewitt.com%'   and msg_inserted_dttime > '2014-09-01'   AND msg_toaddressoverride NOT LIKE '%rob.neilson@aonhewitt.com%'   AND msg_toaddressoverride NOT LIKE '%tammy.adams@aonhewitt.com%'   AND msg_toaddressoverride NOT LIKE '%jon.petit@aonhewitt.com%'   AND msg_toaddressoverride NOT LIKE '%rob.neilson@aonhewitt.com%'   AND msg_toaddressoverride NOT LIKE '%sethiya.um@aonhewitt.com%'   AND msg_toaddressoverride NOT LIKE '%chad.smith@aonhewitt.com%'   AND msg_toaddressoverride NOT LIKE '%art.feld@aonhewitt.com%'   AND msg_addldetails NOT LIKE '%Your document has been received,%'   AND msg_addldetails NOT LIKE '%No valid Expectations found%'   AND msg_addldetails NOT LIKE '%completed successfully.%'   AND msg_addldetails NOT LIKE '%is completed successfully%'   AND msg_addldetails NOT LIKE '%Promoting audit profile%'   AND msg_addldetails NOT LIKE '%Removed expiration date%'   AND msg_addldetails NOT LIKE '%Unable to extract the XML%'   AND msg_addldetails NOT LIKE '%Problems archiving.%'   AND msg_addldetails NOT LIKE '%Missing Custom Parameter%'   AND msg_addldetails NOT LIKE '%getExtractorXML%'   AND msg_addldetails NOT LIKE '%updateAppScheduler%'   AND msg_addldetails NOT LIKE '%getAppScheduler%'   AND msg_addldetails NOT LIKE '%getConnection%'   AND msg_addldetails NOT LIKE '%getDataWarehouse%'   AND msg_addldetails NOT LIKE '%There is insufficient system memory in resource pool%'   AND msg_addldetails NOT LIKE '%Invalid FileID%'   AND msg_addldetails NOT LIKE '%Connection reset%'   AND msg_addldetails NOT LIKE '%Recieved an Empty file from%'   AND msg_addldetails NOT LIKE '%The executeQuery method must return%'   AND msg_expectid NOT LIKE '%NULL%'   order by msg_inserted_dttime desc");
            //statement.executeUpdate("INSERT INTO TRIAGE (FILEID, STATUS, ERDATE, ERTIME, SERVER, LOCATION, DEVELOPER, FIST) VALUES ('"+fileID+"', '"+status+"', '"+date+"', '"+time+"', '"+server+"', '"+serviceLocation+"', '"+devName+"', '"+FullLine+"')");
            Boolean insert = true;
            while(resultSet.next()){
            	String server = "";
            	if(resultSet.getString("msg_addldetails").contains("L4DWIPAP")){
            		server = resultSet.getString("msg_addldetails").substring(resultSet.getString("msg_addldetails").indexOf("L4DWIPAP"),resultSet.getString("msg_addldetails").indexOf("L4DWIPAP")+12);
            		server.replaceAll("\r\n", "");
            	}
            	//String text = server+"|"+resultSet.getString("msg_fileid")+"|"+resultSet.getString("msg_addldetails")+"|"+resultSet.getString("msg_inserted_dttime").split(" ")[0].replaceAll("/", "-")+"|"+resultSet.getString("msg_inserted_dttime").split(" ")[1].split(":")[0]+":"+resultSet.getString("msg_inserted_dttime").split(" ")[1].split(":")[1];
            	//System.out.println(text);
            	statement2.executeUpdate("INSERT INTO TRIAGE (FILEID, ERDATE, ERTIME, SERVER,ERROR) VALUES ('"+resultSet.getString("msg_fileid")+"', '"+resultSet.getString("msg_inserted_dttime").split(" ")[0].replaceAll("/", "-")+"', '"+resultSet.getString("msg_inserted_dttime").split(" ")[1].split(":")[0]+":"+resultSet.getString("msg_inserted_dttime").split(" ")[1].split(":")[1]+"', '"+server+"', '"+resultSet.getString("msg_addldetails")+"')");	
          
            }
//            if(insert == true){
//            	statement.executeUpdate("INSERT INTO TRIAGE (FILEID, STATUS, ERDATE, ERTIME, SERVER, LOCATION, DEVELOPER, STACK, ERROR, FIST) VALUES ('"+fileID+"', '"+status+"', '"+date+"', '"+time+"', '"+server+"', '"+serviceLocation+"', '"+devName+"', '"+stackTrace+"', '"+errorMessage+"', '"+FullLine+"')");	
//            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		
		
	}
	
	public void createErrorLine(String fistUsername, String fistPassword, ArrayList<ListItem> list, WebDriver dr)
	{
		for(int i=0;i<list.size();i++){
			File del = new File("C://Triage_Dashboard//ActiveErrors.txt");
			fistUser=fistUsername;
			fistPass=fistPassword;
			delimeter = '|';
			status="0";
			fileID=list.get(i).getFileID();
			date=list.get(i).getDateTime().split(" ")[0].split("-")[1]+"/"+list.get(i).getDateTime().split(" ")[0].split("-")[2]+"/"+list.get(i).getDateTime().split(" ")[0].split("-")[0];
			time=list.get(i).getDateTime().split(" ")[1].split(":")[0]+":"+list.get(i).getDateTime().split(" ")[1].split(":")[1];
			
			
			devName="";
			serviceLocation="";
			errorMessage=list.get(i).getErrorMessage();
			server=list.get(i).getServer();
			stackTrace= list.get(i).getStackTrace();
			
			
			
			NumberOfExpectations=0;	
			expectIDList = new ArrayList <String> ();
			statusList = new ArrayList <String> ();
			analystNameList = new ArrayList <String> ();
			environmentList = new ArrayList <String> ();
			expectDateList = new ArrayList <String> ();
			startTimeList = new ArrayList <String> ();
			endTimeList = new ArrayList <String> ();
			System.out.println(fileID);
			if (fileID.equals("?")==false){
			populateFieldsInFIST(dr);
			createFullLine();
			Connection connection = null;
	        ResultSet resultSet = null;
	        Statement statement = null;
			try {
	            Class.forName("org.firebirdsql.jdbc.FBDriver");
	            connection = DriverManager
	                    .getConnection(
	                            "jdbc:firebirdsql://localhost:3050/C:/database/BASE.fdb",
	                            "sysdba", "masterkey");
	            statement = connection.createStatement();
	            resultSet = statement.executeQuery("select DISTINCT * from TRIAGE");
	            //statement.executeUpdate("INSERT INTO TRIAGE (FILEID, STATUS, ERDATE, ERTIME, SERVER, LOCATION, DEVELOPER, FIST) VALUES ('"+fileID+"', '"+status+"', '"+date+"', '"+time+"', '"+server+"', '"+serviceLocation+"', '"+devName+"', '"+FullLine+"')");
	            Boolean insert = true;
	            while(resultSet.next()){
	            	String text = resultSet.getString("FILEID")+"|"+resultSet.getString("STATUS")+"|"+resultSet.getString("ERDATE")+"|"+resultSet.getString("ERTIME")+"|"+resultSet.getString("SERVER")+"|"+resultSet.getString("LOCATION")+"|"+resultSet.getString("DEVELOPER")+"|"+resultSet.getString("ERROR")+"|"+resultSet.getString("STACK")+"|"+resultSet.getString("FIST");
	            	String textMinus = resultSet.getString("FILEID")+"|"+resultSet.getString("ERDATE")+"|"+resultSet.getString("ERTIME")+"|"+resultSet.getString("SERVER")+"|"+resultSet.getString("LOCATION")+"|"+resultSet.getString("DEVELOPER")+"|"+resultSet.getString("ERROR")+"|"+resultSet.getString("STACK")+"|"+resultSet.getString("FIST");
	            	
	            	System.out.println(text);
	            	String text1 = fileID+"|"+date+"|"+time+"|"+server+"|"+serviceLocation+"|"+devName+"|"+errorMessage+"|"+stackTrace+"|"+FullLine;
	            	if(textMinus.equals(text1)){
	            		insert = false;
	            	}
	            }
	            if(insert == true){
	            	statement.executeUpdate("INSERT INTO TRIAGE (FILEID, STATUS, ERDATE, ERTIME, SERVER, LOCATION, DEVELOPER, STACK, ERROR, FIST) VALUES ('"+fileID+"', '"+status+"', '"+date+"', '"+time+"', '"+server+"', '"+serviceLocation+"', '"+devName+"', '"+stackTrace+"', '"+errorMessage+"', '"+FullLine+"')");	
	            }
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                statement.close();
	                connection.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
			}
			if (fileID.equals("?")){
				Connection connection = null;
		        ResultSet resultSet = null;
		        Statement statement = null;
				try {
		            Class.forName("org.firebirdsql.jdbc.FBDriver");
		            connection = DriverManager
		                    .getConnection(
		                            "jdbc:firebirdsql://localhost:3050/C:/database/BASE.fdb",
		                            "sysdba", "masterkey");
		            statement = connection.createStatement();
		            
		            resultSet = statement.executeQuery("select DISTINCT * from TRIAGE");
		            //statement.executeUpdate("INSERT INTO TRIAGE (FILEID, STATUS, ERDATE, ERTIME, SERVER, LOCATION, DEVELOPER, FIST) VALUES ('"+fileID+"', '"+status+"', '"+date+"', '"+time+"', '"+server+"', '"+serviceLocation+"', '"+devName+"', '"+FullLine+"')");
		            Boolean insert = true;
		            while(resultSet.next()){
		            	String text = resultSet.getString("FILEID")+"|"+resultSet.getString("STATUS")+"|"+resultSet.getString("ERDATE")+"|"+resultSet.getString("ERTIME")+"|"+resultSet.getString("SERVER")+"|"+resultSet.getString("LOCATION")+"|"+resultSet.getString("DEVELOPER")+"|"+resultSet.getString("ERROR")+"|"+resultSet.getString("STACK")+"|"+resultSet.getString("FIST");
		            	String textMinus = resultSet.getString("FILEID")+"|"+resultSet.getString("ERDATE")+"|"+resultSet.getString("ERTIME")+"|"+resultSet.getString("SERVER")+"|"+resultSet.getString("LOCATION")+"|"+resultSet.getString("DEVELOPER")+"|"+resultSet.getString("ERROR")+"|"+resultSet.getString("STACK")+"|"+resultSet.getString("FIST");
		            	
		            	System.out.println(text);
		            	String text1 = fileID+"|"+date+"|"+time+"|"+server+"|"+serviceLocation+"|"+devName+"|"+errorMessage+"|"+stackTrace+"|"+FullLine;
		            	if(textMinus.equals(text1)){
		            		insert = false;
		            	}
		            }
		            if(insert == true){
		            	statement.executeUpdate("INSERT INTO TRIAGE (FILEID, STATUS, ERDATE, ERTIME, SERVER, LOCATION, DEVELOPER, STACK, ERROR, FIST) VALUES ('"+fileID+"', '"+status+"', '"+date+"', '"+time+"', '"+server+"', '"+serviceLocation+"', '"+devName+"', '"+stackTrace+"', '"+errorMessage+"', '"+FullLine+"')");	
		            }
		            
		        } catch (Exception e) {
		            e.printStackTrace();
		        } finally {
		            try {
		                statement.close();
		                connection.close();
		            } catch (Exception e) {
		                e.printStackTrace();
		            }
		        }
			}
			
		}
		
	}

	private void writeFullLine()
	{
		PrintWriter writer = null;
		
				try {
					writer = new PrintWriter(new FileWriter("C://Triage_Dashboard//ActiveErrors.txt", true));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		writer.println(getFullLine());
		writer.close();
	}
	
	
	private void createFullLine()
	{
		setFullLine(""+
				NumberOfExpectations			
				);
		
		
		
	
		for(int i = 0;i < NumberOfExpectations;i++)
		{
			setFullLine(getFullLine()+ delimeter +
					"STARTEXPECTATION" +delimeter+
					expectIDList.get(i) + delimeter+
					statusList.get(i) + delimeter+
					analystNameList.get(i) + delimeter+
					environmentList.get(i) + delimeter+
					expectDateList.get(i) + delimeter+
					startTimeList.get(i) + delimeter+
					endTimeList.get(i) 
					);
		}
		
		setFullLine(getFullLine()+ delimeter +"ENDOFEXPECTATIONS");
		
	}
	
	private void populateFieldsInFIST(WebDriver dr)
	{
		clearExpectations();
		FIST Fdriver = new FIST(fistUser,fistPass,OfficeLocation,false,dr);
    	Fdriver.loginProd();
        setDevName(Fdriver.getDevName(getFileID()));
    	setServiceLocation(Fdriver.getLocation(getFileID()));
    	
    	Fdriver.loginConfig();
    	Fdriver.getExpectationsPage(getFileID(),getDate());
    	
    	
    	setNumberOfExpectations(Fdriver.getNumberOfExpectations());
    	
    	int counter=1;
    	for (int i = getNumberOfExpectations()+1;i>1;i--)
    	{
    		addEnvironmentListItem(getEnvironmentName(Fdriver.getEnvironment(i)));
    		addExpectIDListItem(Fdriver.getExpectID(i));
    		addStatusListItem(Fdriver.getExpectStatus(i));
    		addExpectDateListItem(counter+": "+Fdriver.getExpectDate(i));
    		addStartTimeListItem(Fdriver.getStartTime(i));
    		addEndTimeListItem(Fdriver.getEndTime(i));
    		addAnalystNameListItem(removeAnalystHRA(Fdriver.getAnalyst(i)));
    		counter++;
    	}
		
		
	}	
	
	public void clearExpectations()
   {
	    getEnvironmentList().clear();
	   	getExpectIDList().clear();
	   	getStatusList().clear();
	   	getExpectDateList().clear();
	   	getStartTimeList().clear();
	   	getEndTimeList().clear();
	   	getAnalystNameList().clear();
   }
	
	public String getFileID() {
		return fileID;
	}

	public void setFileID(String fileID) {
		this.fileID = fileID;
	}


	public String status() {
		return status;
	}

	public void setFinished(String status) {
		this.status = status;
	}

	public String getDevName() {
		return devName;
	}

	public void setDevName(String devName) {
		this.devName = devName;
	}


	public String getServiceLocation() {
		return serviceLocation;
	}


	public void setServiceLocation(String serviceLocation) {
		this.serviceLocation = serviceLocation;
	}
	
	public String getStackTrace() {
		return stackTrace;
	}


	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}


	public String getServer() {
		return server;
	}


	public void setServer(String server) {
		this.server = server;
	}


	public String getErrorMessage() {
		return errorMessage;
	}


	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	

	public ArrayList <String> getExpectIDList() {
		return expectIDList;
	}


	public void addExpectIDListItem(String expectIDList) {
		this.expectIDList.add(expectIDList);
	}


	public ArrayList <String> getStatusList() {
		return statusList;
	}


	public void addStatusListItem(String statusList) {
		this.statusList.add(statusList);
	}


	public ArrayList <String> getAnalystNameList() {
		return analystNameList;
	}


	public void addAnalystNameListItem(String analystNameList) {
		this.analystNameList.add(analystNameList);
	}
	
	
	public String removeAnalystHRA(String analyst)
	{
		if(analyst.contains("hra")){
			int length=analyst.length();
			String end = analyst.substring(length-4);
				if (end.equals(" hra"))
				{
					analyst =analyst.substring(0, length-4);
				}
			}
		return analyst;
	}


	public ArrayList <String> getEnvironmentList() {
		return environmentList;
	}


	public void addEnvironmentListItem(String environmentList) {
		this.environmentList.add(environmentList);
	}
	
	private String getEnvironmentName(String Enum)
    {
    	String displayName =Enum;
    	
    	if(Enum.contentEquals("B")){displayName= "B: Blue (Prod P)";}
    	else if(Enum.contentEquals("C")){displayName= "C: Green (Prod P)";}
    	else if(Enum.contentEquals("D")){displayName= "D: Yellow (Prod P)";}
    	else if(Enum.contentEquals("F")){displayName= "F: Crimson (Prod P)";}
    	else if(Enum.contentEquals("H")){displayName= "H: Silver (Prod P)";}
    	else if(Enum.contentEquals("N")){displayName= "N: Gray (Prod P)";}
    	else if(Enum.contentEquals("A")){displayName= "A: Indigo (Prod W)";}
    	else if(Enum.contentEquals("E")){displayName= "E: Topaz (Prod W)";}
    	else if(Enum.contentEquals("G")){displayName= "G: Navy (Prod W)";}
    	else if(Enum.contentEquals("J")){displayName= "J: Cobalt (Prod W)";}
    	else if(Enum.contentEquals("O")){displayName= "O: Red (Prod W)";}
    	else if(Enum.contentEquals("R")){displayName= "R: Orange (Prod W)";}
    	else if(Enum.contentEquals("K")){displayName= "K: White (Prod X)";}
    	else if(Enum.contentEquals("V")){displayName= "V: Gold (Prod X)";}
    	else if(Enum.contentEquals("S")){displayName= "S: Peach (Prod X)";}
    	else if(Enum.contentEquals("Y")){displayName= "Y: Bronze (Prod X)";}
    	else if(Enum.contentEquals("Z")){displayName= "Z: Plum (Prod X)";}
    	else if(Enum.contentEquals("L")){displayName= "L: Brown (Prod X)";}
    	else if(Enum.contentEquals("1")){displayName= "1: Cherry (Prod X)";}
    	else if(Enum.contentEquals("6")){displayName= "6: Ruby (Prod X)";}
    	else if(Enum.contentEquals("7")){displayName= "7: Emerald (Prod X)";}
    	else if(Enum.contentEquals("8")){displayName= "8: Tan (Prod X)";}
    	else if(Enum.contentEquals("9")){displayName= "9: Black (Prod X)";}
    	
    	return displayName;
    }


	public ArrayList <String> getStartTimeList() {
		return startTimeList;
	}


	public void addStartTimeListItem(String startTimeList) {
		this.startTimeList.add(startTimeList);
	}


	public ArrayList <String> getEndTimeList() {
		return endTimeList;
	}


	public void addEndTimeListItem(String endTimeList) {
		this.endTimeList.add(endTimeList);
	}


	public ArrayList <String> getExpectDateList() {
		return expectDateList;
	}


	public void addExpectDateListItem(String expectDate) {
		this.expectDateList.add(expectDate);
	}


	public String getFullLine() {
		return FullLine;
	}


	public void setFullLine(String fullLine) {
		FullLine = fullLine;
	}


	public int getNumberOfExpectations() {
		return NumberOfExpectations;
	}

	public void setNumberOfExpectations(int numberOfExpectations) {
		NumberOfExpectations = numberOfExpectations;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}