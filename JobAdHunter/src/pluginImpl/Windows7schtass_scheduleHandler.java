package pluginImpl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import nz.ac.ucol.configuration.SearchConfiguration;
import nz.ac.ucol.plugin.ScheduleHandler;

public class Windows7schtass_scheduleHandler implements ScheduleHandler 
{
	String name = "Windows7schtass_scheduleHandler";
	private String error;
	
	private String runComand(String comand)
	{
		String errorString = "";
		String consolString = "";
		String result = "";
		try {
			String line;
			Process p = Runtime.getRuntime().exec("cmd");
			BufferedWriter out = new BufferedWriter( new OutputStreamWriter(p.getOutputStream()));
			out.write(comand);
			out.newLine();
			out.flush();
			out.close();
			
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream())); 
			while ((line = input.readLine()) != null) 
			{ 
				consolString +=line + System.lineSeparator();
			} 
			input.close();
			
			input = new BufferedReader(new InputStreamReader(p.getErrorStream())); 
			while ((line = input.readLine()) != null) 
			{ 
				errorString +=line + System.lineSeparator();
			} 
			input.close();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(errorString != "")
		{
			error = errorString;
			result = errorString;
		}
		else
		{
			result = consolString;
		}
		
		return result;
	}
	
	@Override
	public boolean schedualCrawl(SearchConfiguration sc)
	{
		boolean result = true;
		// get location of application
		String path = ScheduleHandler.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		String outPut = "";
		try {
			String decodedPath = URLDecoder.decode(path, "UTF-8");
			outPut = decodedPath;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// for testing purposes as the jar file only shows up when runnign from a jar 
		if(outPut.substring(outPut.length() - 3) != "jar" )
		{
			outPut += "jobAdHunter.jar";
		}
		
		File scLocation = new File("jobSearches/" + sc.getName());
		String bashContents = "start javaw -jar \"" + outPut.substring(1) + "\" \"" + scLocation.getAbsolutePath() + "\"";
		String fileName = "Scripts/" + sc.getName() +".bat";
		
		try 
		{
			Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName)));
			writer.write(bashContents);
			writer.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		File bashScript = new File(fileName);
		String pathToScript = bashScript.toURI().toString().replace("%20", " ");
		String bashScriptLocation = "\"\\\"" + pathToScript.substring(6,pathToScript.length()) + " \\\"\""; 
		
		String time = new SimpleDateFormat("HH:mm").format(sc.getTimeofNextCrawl());

		String comand = "schtasks /create /sc " + sc.getScheduledToRepeat();
		comand = comand + " /tn " +sc.getName() + " /tr " + bashScriptLocation + " /st " + time + " /F";
		
		//runComand("schtasks /create /sc daily /tn testFromjavatwo /tr \"\\\" C:\\Users\\Tim McGrath\\Documents\\Ucol\\testJava.bat \\\"\" /st 18:22 /f");
		
		runComand(comand);
		if(error != "")
		{
			result = false;
		}
		return result;
	}

	@Override
	public boolean removeCrawl(SearchConfiguration sc)
	{
		boolean result = true;
		runComand("schtasks //delete //tn " + sc.getName() + " //F");
		// also add something to remove associated bash script
		if(error != "")
		{
			result = false;
		}
		return result;
	}

	@Override
	public ArrayList<String> getAllSchedualedCrawls( ArrayList<SearchConfiguration> listOfSC) 
	{
		ArrayList<String> result = new ArrayList<>();
		for(SearchConfiguration sc:listOfSC)
		{
			String detailedListing = runComand("schtasks /query /tn " + sc.getName() + " /fo LIST /v ");
			String[] lines = detailedListing.split(System.lineSeparator());
			
			String[] runTimeLine = lines[8].split(":");
			String nextRunTime = runTimeLine[1].trim() + ":" + runTimeLine[2].trim() + ":" +  runTimeLine[3].trim();
			
			String scheduleType = lines[24].split(":")[1].trim();
			
			String[] startTimeLines = lines[25].split(":");
			String StartTime = startTimeLines[1].trim() + ":" + startTimeLines[2].trim() + ":" +  startTimeLines[3].trim();
			
			result.add(sc.getName() + "," + nextRunTime + ","+ scheduleType + "," + StartTime);
		}
		
		return result;
	}

	@Override
	public boolean updateCrawl(SearchConfiguration sc)
	{
		return schedualCrawl(sc);
	}

	@Override
	public String getSupportedOS()
	{
		return "Windows 7";
	}

	@Override
	public String getName()
	{
		return name;
	}

}
