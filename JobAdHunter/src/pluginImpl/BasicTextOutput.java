package pluginImpl;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import nz.ac.ucol.configuration.JobAd;
import nz.ac.ucol.plugin.OutPutHandler;
import nz.ac.ucol.plugin.OutPuter;

public class BasicTextOutput implements OutPutHandler {

	@Override
	public boolean outPutInfo(String outPutLocation, ArrayList<JobAd> jobAds) throws IOException{
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
		Date date = new Date();
		File yourFile = new File(outPutLocation + dateFormat.format(date) + ".txt");
		if(!yourFile.exists()) {
		    yourFile.createNewFile();
		} 
		
		PrintWriter outPutFile = new PrintWriter(yourFile);
		outPutFile.println(" JobAd Hunter Results for :" + dateFormat.format(date) + "/n/c");
		for(JobAd jobAd:jobAds)
		{
			outPutFile.println(jobAd.getTitle());
			outPutFile.println(jobAd.getSource());
			outPutFile.println(jobAd.getFullDescription());
		}
		outPutFile.close();
		return false;
	}

	@Override
	public String getOutPutType() {
		return OutPuter.txtFile;
	}

}
