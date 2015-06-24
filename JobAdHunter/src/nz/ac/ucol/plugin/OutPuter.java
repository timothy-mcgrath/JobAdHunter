package nz.ac.ucol.plugin;

import java.util.ArrayList;

import nz.ac.ucol.configuration.Configuration;
import nz.ac.ucol.configuration.JobAd;
import nz.ac.ucol.configuration.PluginNotFoundException;
import nz.ac.ucol.pluginManager.PluginManager;

public class OutPuter {
	public final static String txtFile = "text File";
	
	/**
	 * Calls plugin to outPut JobAd Information 
	 * @param outPutLocation location to put information
	 * @param jobAds the jobAds to be outputted
	 * @param option the type of outPut desired 
	 * @throws PluginNotFoundException if their is no plugin that handles specified option 
	 */
	public static void outPutInformation(String outPutLocation,ArrayList<JobAd> jobAds,String option) throws PluginNotFoundException
	{
		try {
			for(OutPutHandler outPuter:PluginManager.loadPlugins(OutPutHandler.class, Configuration.PLUGINFOLDERLOCATION))
			{
				if(outPuter.getOutPutType().equals(option))
				{
					outPuter.outPutInfo(outPutLocation, jobAds);
					return;
				}
			}			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		throw new PluginNotFoundException(option);
		
	}
}
