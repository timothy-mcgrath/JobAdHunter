package nz.ac.ucol.plugin;

import java.util.ArrayList;

import nz.ac.ucol.configuration.Configuration;
import nz.ac.ucol.configuration.SearchConfiguration;
import nz.ac.ucol.pluginManager.PluginManager;

public class Scheduler {

	public final static String DAILY = "DAILY";
	public final static String WEEKLY = "WEEKLY";
	public final static String MONTHLY = "MONTHLY";
	
	/**
	 * loads the plugin to schedule applications for this operating system
	 * @return
	 * @throws Exception
	 */
	private static ScheduleHandler getSchedualHandler() throws Exception
	{
		ScheduleHandler result = null;
		ArrayList<ScheduleHandler> schedualeHandlers = PluginManager.loadPlugins(ScheduleHandler.class, Configuration.PLUGINFOLDERLOCATION);
		for(ScheduleHandler sh:schedualeHandlers)
		{
			if(sh.getSupportedOS().equals(System.getProperty("os.name")))
			{
				result = sh;
				break;
			}
		}
		return result;
	}
	
	/**
	 * schedule the specified search configuration to run with appropriate plugin 
	 * @param sc
	 * @return
	 */
	public static boolean schedualCrawl(SearchConfiguration sc)
	{
		boolean result = false;
		try {
			ScheduleHandler sh = getSchedualHandler();
			result = sh.schedualCrawl(sc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * deschedule's the specified search configuration to run with appropriate plugin 
	 * @param sc
	 * @return
	 */
	public static boolean removeCrawl(SearchConfiguration sc)
	{
		boolean result = false;
		try {
			ScheduleHandler sh = getSchedualHandler();
			result = sh.removeCrawl(sc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * returns information about all scheduling for provided search configurations 
	 * @param listOfSC
	 * @return
	 */
	public static ArrayList<String> getAllSchedualedCrawls(ArrayList<SearchConfiguration> listOfSC)
	{
		ArrayList<String> result = null;
		try {
			ScheduleHandler sh = getSchedualHandler();
			result = sh.getAllSchedualedCrawls(listOfSC);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * update the specified search configuration's schedule with appropriate plugin 
	 * @param sc
	 * @return
	 */
	public static boolean updateCrawl(SearchConfiguration sc)
	{
		boolean result = false;
		try {
			ScheduleHandler sh = getSchedualHandler();
			result = sh.updateCrawl(sc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
