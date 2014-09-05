package dashboard;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Kill {
	public static void main(String[] args) throws IOException {
		ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
		exec.scheduleAtFixedRate(new Runnable() {
			//This method runs once every second, it is used to set the progress bars, automate FIST deployment, and automate Maestro automation
		  @Override
		  public void run() {
		try {
			System.out.println("runnning");
			Runtime.getRuntime().exec("cmd /c start C://Triage_Dashboard.bat//startup.bat");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  }
		}, 0, 5, TimeUnit.MINUTES);
	}

}
