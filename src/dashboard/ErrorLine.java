package dashboard;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ErrorLine {
	
	//private String OfficeLocation="Winston Salem";
	private String OfficeLocation="Hunt Valley";
	private char delimeter;
	
	private String FullLine;
	
	private String fistUser;
	private String fistPass;
	
	private boolean isFinished;
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
		int run = 0;
		System.setProperty("webdriver.chrome.driver", "C://schema_creation/chromedriver.exe");
		WebDriver dr = new ChromeDriver();
		while(run == 0){
		Server server = new Server();
		ArrayList<ListItem> listItems = server.CreateErrorLogs(dr);
		System.out.println(listItems.size());
		ErrorLine erline = new ErrorLine();
		erline.createErrorLine("poconorhra","Spektor33!",listItems,dr);
		}
		
	}
	
	
	public void createErrorLine(String fistUsername, String fistPassword, ArrayList<ListItem> list, WebDriver dr)
	{
		for(int i=0;i<list.size();i++){
			File del = new File("C://Triage_Dashboard//ActiveErrors.txt");
			fistUser=fistUsername;
			fistPass=fistPassword;
			delimeter = '|';
			isFinished=false;
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
			if (fileID.equals("?")==false){
			populateFieldsInFIST(dr);
			createFullLine();
			}
			del.delete();
			writeFullLine();
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
		setFullLine(""+fileID+delimeter+
				isFinished+delimeter+
				date+delimeter+
				time+delimeter+
				server+delimeter+
				serviceLocation+delimeter+
				devName+delimeter+
				errorMessage+delimeter+
				stackTrace+delimeter+
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


	public boolean isFinished() {
		return isFinished;
	}

	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
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