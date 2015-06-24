package nz.ac.ucol.plugin;

import java.io.IOException;
import java.util.ArrayList;

import nz.ac.ucol.configuration.JobAd;

public interface OutPutHandler {

	/**
	 * out put's jobAd information to specified location
	 * @param outPutLocation location to put information
	 * @param jobAds information to be outputted
	 * @return true if it worked false otherwise 
	 * @throws IOException 
	 */
	public boolean outPutInfo(String outPutLocation,ArrayList<JobAd> jobAds) throws IOException;
	
	/**
	 * find out what type of output this plugin handles
	 * @return output type
	 */
	public String getOutPutType();
}
