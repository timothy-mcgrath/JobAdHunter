package nz.ac.ucol.crawler;

import java.util.ArrayList;

import nz.ac.ucol.configuration.JobAd;
import nz.ac.ucol.plugin.SiteHandler;


public class SiteContentHandler {

	/**
	 * process the suppled site with correct plug in 
	 * @param site site to handle
	 * @param url url for site to get correct plug in
	 * @return ArrayList of jobAds 
	 */
	public static ArrayList<JobAd> handleSite(String site,String url)
	{
		SiteHandler siteHandler = loadPlugin(url);
		return siteHandler.processSite(site.replaceAll("(\\r|\\n|\\t)", ""));
	}
	
	private static SiteHandler loadPlugin(String url)
	{
		try {
			ArrayList<SiteHandler> list = PluginManager.loadPlugins(SiteHandler.class, "Plugin/");
			for(int i = 0 ; i < list.size();i++)
			{
				if(list.get(i).handlesSite() == url)
				{
					return list.get(i);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
