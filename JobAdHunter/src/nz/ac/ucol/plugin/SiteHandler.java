package nz.ac.ucol.plugin;

import java.util.ArrayList;

import nz.ac.ucol.configuration.JobAd;

//import org.java.plugin.Plugin;
import net.xeoh.plugins.base.Plugin;

public interface SiteHandler {

	/**
	 * Takes the source code of a web page and extracts a list of JobAds from it 
	 * @param site the document to extract JobAds from 
	 * @return the list of JobAds extracted from site 
	 */	
	public ArrayList<JobAd> processSite(String site);
	
	/**
	 * returns the base url of the site this plugin handles
	 * @return
	 */
	public String handlesSite();
	
	public String getURLofNextPage();
}
