package dashboard;

public class ListItem {

	private String fileID;
	private String dateTime;
	private boolean isFinished;
	private String devName;
	private String serviceLocation;
	
	public ListItem(String fileID)
	{
		this.fileID=fileID;
		isFinished=false;
		devName="";
		setServiceLocation("");
	}

	
	public String getFileID() {
		return fileID;
	}

	public void setFileID(String fileID) {
		this.fileID = fileID;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
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
	
	
}
