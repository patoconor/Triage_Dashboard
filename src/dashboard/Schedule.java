package dashboard;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.wm.util.FileUtil;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;

public class Schedule {
	static String onDuty = "";
	public static void main (String[] args) throws IOException, ParserException{
		
		
		System.out.println(System.getProperty("user.home") + "/Desktop");
		
		FileInputStream fin = new FileInputStream("R://BenIT//Files//All//Tools//TriageDashboard//Triage Calendar.ics");

		CalendarBuilder builder = new CalendarBuilder();

		Calendar calendar = builder.build(fin);
		ArrayList<Component> comps = new ArrayList<Component>();
		calendar.getProperties().add(new ProdId("-//Ben Fortuna//iCal4j 1.0//EN"));
		calendar.getProperties().add(Version.VERSION_2_0);
		calendar.getProperties().add(CalScale.GREGORIAN);
		for(int i=0;i<calendar.getComponents().size();i++){
			comps.add((Component) calendar.getComponents().get(i));
		}
		System.out.println(comps.size());
		
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String current = dateFormat.format(date);
		//String current = "20140925100000";
		for(int i=1;i<calendar.getComponents().size();i++){
			String name = comps.get(i).toString().split("\n")[10].split(":")[1];
			String start = comps.get(i).toString().split("\n")[6].split(":")[1];
			String end = comps.get(i).toString().split("\n")[4].split(":")[1];
			name = name.replaceAll("\r", "");
			start = start.replaceAll("\r", "");
			end = end.replaceAll("\r", "");
			start = start.replaceAll("T", "");
			end = end.replaceAll("T", "");
			//System.out.println(name+" "+start+" "+end);
			if(Double.parseDouble(start)<=Double.parseDouble(current) && Double.parseDouble(end)>=Double.parseDouble(current)){
				System.out.println(name);
				onDuty = name;
			}
		}
//		for (Iterator i = calendar.getComponents().iterator(); i.hasNext();) {
//		    Component component = (Component) i.next();
//		    //System.out.println("Component [" + component.getName() + "]");
//
//		    for (Iterator j = component.getProperties().iterator(); j.hasNext();) {
//		    	String name = "";
//		    	String start = "";
//		    	String end = "";
//		        Property property = (Property) j.next();
//		        if(property.getName().equals("SUMMARY")){
//		        name = property.getValue();
//		        }
//		        if(property.getName().equals("DTSTART")){
//			        start = property.getValue();
//			    }
//			    if(property.getName().equals("DTEND")){
//				    end = property.getValue();
//				}
//			    System.out.println(name+" "+start+" "+end);
//		    }
//		
//		}
		String[] lines = calendar.toString().split("\n");
		//System.out.println(lines[2]);
//		for(int i=0;i<lines.length;i++){
//			if(lines[i].startsWith("SUMMARY")){
//				System.out.println(lines[i]);
//			}
//		}
		//System.out.println(calendar.toString());
	
	}

}
