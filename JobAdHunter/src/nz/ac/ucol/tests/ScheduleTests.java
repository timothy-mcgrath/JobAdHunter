package nz.ac.ucol.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import nz.ac.ucol.configuration.Options;
import nz.ac.ucol.configuration.SearchConfiguration;
import nz.ac.ucol.gui.View;
import nz.ac.ucol.plugin.OutPuter;
import nz.ac.ucol.plugin.Scheduler;
import nz.ac.ucol.utility.Utility;

import org.junit.Test;

public class ScheduleTests {

	@Test
	public void testRunAppFromScheduler()
	{
		// create a search configuration and save it to file 
		SearchConfiguration searchConfig = new SearchConfiguration();
		
		searchConfig.addWebSite("http://testsite.herobo.com/testSeperatePagesOnline.html");
		searchConfig.setName("anotherTest");
		searchConfig.addJobAdCondition(Options.musthave,Options.anyWhere, "(?i).*\\bdeveloper\\b.*");
		searchConfig.addRankingCondition(30, "(?i).*C#.*",Options.anyWhere);
		
		searchConfig.setOutPutOption(OutPuter.txtFile,"TestSite/testReportTwo");
		
		String fileName = "jobSearches/" + searchConfig.getName();
		searchConfig.toFile(fileName);
		
		// run stored search configuration
		View.main(new String[]{fileName});

		
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
		Date date = new Date();
		
		try {
			String result = Utility.readFile("TestSite/testReportTwo" + dateFormat.format(date) + ".txt");
			assertTrue(result.contains("java developer"));
			assertTrue(result.contains(".net developer"));
			assertFalse(result.contains("house keeper"));
		} catch (IOException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void testScheduler()
	{
		SearchConfiguration sc = null;
		try {
			 sc = new SearchConfiguration("TestSite/anotherTest");
		} catch (Exception e) {
			assertTrue(false);
		}
		Calendar calendar = Calendar.getInstance(); 
		calendar.add(Calendar.MINUTE, 2);

		Date date = calendar.getTime();
		sc.setScheduledToRepeat(Scheduler.DAILY);
		sc.setTimeofNextCrawl(date);
		sc.setOutPutOption(OutPuter.txtFile,"TestSite/tesingTheSchedulerplugin");
		Scheduler.schedualCrawl(sc);
		String fileName = "jobSearches/" + sc.getName();
		sc.toFile(fileName);
		
		try {
			Thread.sleep(130000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
		date = new Date();
		
		try {
			String result = Utility.readFile("TestSite/tesingTheSchedulerplugin" + dateFormat.format(date) + ".txt");
			assertTrue(result.contains("java developer"));
			assertTrue(result.contains(".net developer"));
			assertFalse(result.contains("house keeper"));
		} catch (IOException e) {
			e.printStackTrace();
			assertTrue(false);
		}

		Scheduler.removeCrawl(sc);
	}
}
