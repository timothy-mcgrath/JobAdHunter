package nz.ac.ucol.plugin;

import java.util.ArrayList;

import nz.ac.ucol.configuration.JobAd;

import org.java.plugin.Plugin;

public abstract class siteHandler extends Plugin {

	/**
	 * Takes the source code of a web page and extracts a list of JobAds from it 
	 * @param site the document to extract JobAds from 
	 * @return the list of JobAds extracted from site 
	 */	
	public abstract ArrayList<JobAd> processSite(String site);
	
	/**
	 * returns the base url of the site this plugin handles
	 * @return
	 */
	public abstract String handlesSite();
	
	
	@Override
	protected void doStart() throws Exception {
		
	}

	@Override
	protected void doStop() throws Exception {
		
	}
}
