package nz.ac.ucol.crawler;

import java.util.ArrayList;

import nz.ac.ucol.configuration.Configuration;
import nz.ac.ucol.configuration.JobAd;
import nz.ac.ucol.configuration.PluginNotFoundException;
import nz.ac.ucol.plugin.SiteHandler;
import nz.ac.ucol.plugin.SiteHandler.nextLink;
import nz.ac.ucol.pluginManager.PluginManager;


public class SiteContentHandler {

	/**
	 * process the suppled search result site with correct plug in 
	 * @param site site to handle
	 * @param url url for site to get correct plug in
	 * @return ArrayList of jobAds 
	 * @throws PluginNotFoundException 
	 */
	public static ArrayList<JobAd> handleSiteSearchResults(String site,String url) throws PluginNotFoundException
	{
		SiteHandler siteHandler = loadPlugin(url);
		if(siteHandler == null)
		{
			throw new PluginNotFoundException(url);
		}
		else
		{
			return siteHandler.processSiteSearchResults(site.replaceAll("(\\r|\\n|\\t)", ""),url);
		}
	}
	
	/**
	 * process the suppled job specific site with correct plug in 
	 * @param site site to handle
	 * @param url url for site to get correct plug in
	 * @return jobAddToFillIn to add more information too 
	 * @throws PluginNotFoundException 
	 */
	public static JobAd handleSiteJobAdFull(String site,String url,JobAd jobAddToFillIn) throws PluginNotFoundException
	{
		SiteHandler siteHandler = loadPlugin(url);
		if(siteHandler == null)
		{
			throw new PluginNotFoundException(url);
		}
		else
		{
			return siteHandler.processSiteJobListing(site.replaceAll("(\\r|\\n|\\t)", ""),jobAddToFillIn);
		}
	}
	
	/**
	 * find how the specified site links to next page of search results from plugin
	 * @param url of web site
	 * @return
	 * @throws PluginNotFoundException
	 */
	public static nextLink getNextLink(String url) throws PluginNotFoundException
	{
		SiteHandler siteHandler = loadPlugin(url);
		if(siteHandler == null)
		{
			throw new PluginNotFoundException(url);
		}
		else
		{
			return loadPlugin(url).getURLofNextPage();
		}
	}
	
	/**
	 * loads plugin from plugin folder specified in Configuration.PLUGINFOLDERLOCATION that handles specified url
	 * @param url the plugin needs to be able to handle 
	 * @return plugin if one exsists that can support specified url or null
	 */
	private static SiteHandler loadPlugin(String url)
	{
		try {
			ArrayList<SiteHandler> list = PluginManager.loadPlugins(SiteHandler.class, Configuration.PLUGINFOLDERLOCATION);
			for(int i = 0 ; i < list.size();i++)
			{
				if(url.matches(list.get(i).handlesSite()))
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
