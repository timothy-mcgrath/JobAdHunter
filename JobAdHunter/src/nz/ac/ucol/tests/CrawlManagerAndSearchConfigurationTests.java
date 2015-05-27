package nz.ac.ucol.tests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import nz.ac.ucol.configuration.JobAd;
import nz.ac.ucol.configuration.Options;
import nz.ac.ucol.configuration.SearchConfiguration;
import nz.ac.ucol.crawler.CrawlManager;
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
		
		searchConfig.addRankingCondition(10, ".*Java.*");
		searchConfig.addRankingCondition(-10, ".*out doors.*");
		
		results = searchConfig.processJobAds(jobAds);
		
		assertTrue(results.get(0).getTitle().equals("Java Developer"));
		assertTrue(results.get(1).getTitle().equals("C# developer"));
		assertTrue(results.get(2).getTitle().equals("gardner"));
	}
	
	@Test
	public void testTwo() {
		SearchConfiguration searchConfig = new SearchConfiguration();
		
		File file = new File("TestSite/testContentHandler.html");
		String fileName = "";
		fileName = file.toURI().toString();
		
		searchConfig.addWebSite(fileName);
		searchConfig.addJobAdCondition(Options.musthave,Options.title, ".*two.*");
		
		searchConfig.setOutPutOption(Options.notepadFile,"TestSite/testReport");
		CrawlManager.performJobAdCrawl(searchConfig);
		
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
}
