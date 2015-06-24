package nz.ac.ucol.plugin;

import java.util.ArrayList;

import nz.ac.ucol.configuration.SearchConfiguration;

public interface ScheduleHandler {

	/**
	 * Schedule the provided crawl to run with information from search configuration
	 * @param sc the search configuration to schedule 
	 * @return
	 */
	public boolean schedualCrawl(SearchConfiguration sc);
	
	/**
	 * Unschedule the specified search configuration
	 * @param sc to unschedule
	 * @return
	 */
	public boolean removeCrawl(SearchConfiguration sc);
	
	/**
	 * find the scheduled times and regularity of all search configurations in list
	 * @param listOfSC list of search configurations to get information on
	 * @return
	 */
	public ArrayList<String> getAllSchedualedCrawls(ArrayList<SearchConfiguration> listOfSC);
	
	/**
	 * update schedule for specified search configuration 
	 * @param sc search configuration to update
	 * @return
	 */
	public boolean updateCrawl(SearchConfiguration sc);

	/**
	 * what operating system the plugin is designed for
	 * @return string values of systems plugin handles 
	 */
	public String getSupportedOS();
	
	/**
	 * get this plugins name
	 * @return plugins name
	 */
	public String getName();
}
