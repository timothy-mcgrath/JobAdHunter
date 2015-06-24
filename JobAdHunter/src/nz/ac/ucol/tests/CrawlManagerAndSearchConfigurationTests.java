package nz.ac.ucol.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import nz.ac.ucol.configuration.JobAd;
import nz.ac.ucol.configuration.Options;
import nz.ac.ucol.configuration.PluginNotFoundException;
import nz.ac.ucol.configuration.SearchConfiguration;
import nz.ac.ucol.crawler.CrawlManager;
import nz.ac.ucol.plugin.OutPuter;
import nz.ac.ucol.utility.Utility;

import org.junit.Test;

public class CrawlManagerAndSearchConfigurationTests {

	@Test
	public void test()
	{
		SearchConfiguration searchConfig = new SearchConfiguration();
		ArrayList<JobAd> jobAds = new ArrayList<JobAd>();
		ArrayList<JobAd> results = new ArrayList<JobAd>();
		
		JobAd jobAd = new JobAd();
		jobAd.setTitle("Java Developer");
		jobAd.setFullDescription("4 years comercial experiance /n loves all thing tech /n in palmerston north");
		jobAds.add(jobAd);
		
		jobAd = new JobAd();
		jobAd.setTitle("C# developer");
		jobAd.setFullDescription("in palmerston north /n no comercial expreiance required");
		jobAds.add(jobAd);
		
		results = searchConfig.processJobAds(jobAds);
		
		assertTrue(results.size() == 2);
		assertTrue(results.get(0).getTitle().equals("Java Developer"));
		
		searchConfig.addJobAdCondition(Options.musthave, Options.anyWhere, ".*Java.*");
		
		results = searchConfig.processJobAds(jobAds);
		assertTrue(results.size() == 1);
		assertTrue(results.get(0).getTitle().equals("Java Developer"));
		assertTrue(results.get(0).getFullDescription().equals("4 years comercial experiance /n loves all thing tech /n in palmerston north"));
	
		searchConfig = new SearchConfiguration();
		searchConfig.addJobAdCondition(Options.musthave, Options.anyWhere, ".*C# developer.*");
		
		results = searchConfig.processJobAds(jobAds);
		assertTrue(results.size() == 1);
		assertTrue(results.get(0).getTitle().equals("C# developer"));
		assertTrue(results.get(0).getFullDescription().equals("in palmerston north /n no comercial expreiance required"));
		
		searchConfig = new SearchConfiguration();
		searchConfig.addJobAdCondition(Options.musthave, Options.anyWhere, ".*palmerston north.*");
		
		results = searchConfig.processJobAds(jobAds);
		assertTrue(results.size() == 2);
		
		jobAd = new JobAd();
		jobAd.setTitle("gardner");
		jobAd.setFullDescription("must love working out doors /n will be based in palmerston north");
		
		jobAds.add(jobAd);
		
		results = searchConfig.processJobAds(jobAds);
		assertTrue(results.size() == 3);
		
		searchConfig.addJobAdCondition(Options.musthave, Options.anyWhere, ".*[d|D]eveloper.*");
		
		results = searchConfig.processJobAds(jobAds);
		assertTrue(results.size() == 2);
		
		searchConfig = new SearchConfiguration();
		
		searchConfig.addRankingCondition(-10, ".*Java.*",Options.anyWhere);
		searchConfig.addRankingCondition(10, ".*out doors.*",Options.anyWhere);
		
		results = searchConfig.processJobAds(jobAds);
		results = searchConfig.rateJobAds(results);
		
		assertTrue(results.get(0).getTitle().equals("gardner"));
		assertTrue(results.get(1).getTitle().equals("C# developer"));
		assertTrue(results.get(2).getTitle().equals("Java Developer"));
		
		// test toFile and from file
		SearchConfiguration sc = new SearchConfiguration();
		sc.setName("test");
		sc.setOutPutOption("notePadFile", "C:\\");
		sc.setScheduledToRepeat("DAILY");
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2015-06-21 08:30:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		sc.setTimeofNextCrawl(date);
		sc.addWebSite("www.something.com/jobs");
		sc.addJobAdCondition(Options.musthave, Options.anyWhere, ".*java.*");
		sc.addRankingCondition(30, ".*love all things tech.*", Options.anyWhere);
		sc.toFile("TestSite/test");
		
		try {
			SearchConfiguration other = new SearchConfiguration("TestSite/test");
			assertTrue(sc.getName().equals(other.getName()));
			assertTrue(sc.getOutPutDestination().equals(other.getOutPutDestination()));
			assertTrue(sc.getOutPutType().equals(other.getOutPutType()));
			assertTrue(sc.getScheduledToRepeat().equals(other.getScheduledToRepeat()));
			// add more test for each set of rules 
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		} catch (ParseException e) {
			assertTrue(false);
			e.printStackTrace();
		}
	}
	
	@Test
	public void testTwo() {
		SearchConfiguration searchConfig = new SearchConfiguration();
		
		File file = new File("TestSite/testContentHandler.html");
		String fileName = "";
		fileName = file.toURI().toString();
		
		searchConfig.addWebSite(fileName);
		searchConfig.addJobAdCondition(Options.musthave,Options.title, ".*two.*");
		
		searchConfig.setOutPutOption(OutPuter.txtFile ,"TestSite/testReport");
		try {
			CrawlManager.performJobAdCrawl(searchConfig);
		} catch (PluginNotFoundException e1) {			
			assertTrue(false);
		}
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
		Date date = new Date();
		
		try {
			String result = Utility.readFile("TestSite/testReport" + dateFormat.format(date) + ".txt");
			assertTrue(result.contains("job two"));
			assertFalse(result.contains("job one"));
		} catch (IOException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void testThree() {
		SearchConfiguration searchConfig = new SearchConfiguration();
		
		searchConfig.addWebSite("http://testsite.herobo.com/testSeperatePagesOnline.html");
		searchConfig.addJobAdCondition(Options.musthave,Options.anyWhere, "(?i).*\\bdeveloper\\b.*");
		searchConfig.addRankingCondition(30, "(?i).*C#.*",Options.anyWhere);
		
		searchConfig.setOutPutOption(OutPuter.txtFile,"TestSite/testReport");
		try {
			CrawlManager.performJobAdCrawl(searchConfig);
		} catch (PluginNotFoundException e1) {
			assertTrue(false);
		}
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
		Date date = new Date();
		
		try {
			String result = Utility.readFile("TestSite/testReport" + dateFormat.format(date) + ".txt");
			assertTrue(result.contains("java developer"));
			assertTrue(result.contains(".net developer"));
			assertFalse(result.contains("house keeper"));
		} catch (IOException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
}
