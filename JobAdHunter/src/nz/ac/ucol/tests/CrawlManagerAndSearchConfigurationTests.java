package nz.ac.ucol.tests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import nz.ac.ucol.configuration.JobAd;
import nz.ac.ucol.configuration.Options;
import nz.ac.ucol.configuration.SearchConfiguration;
import nz.ac.ucol.crawler.CrawlManager;

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
		
		searchConfig.addJobAdCondition(Options.musthave, Options.anyWhere, "Java");
		
		results = searchConfig.processJobAds(jobAds);
		assertTrue(results.size() == 1);
		assertTrue(results.get(0).getTitle().equals("Java Developer"));
		assertTrue(results.get(0).getTitle().equals("4 years comercial experiance /n loves all thing tech /n in palmerston north"));
	
		searchConfig = new SearchConfiguration();
		searchConfig.addJobAdCondition(Options.musthave, Options.anyWhere, "C# developer");
		
		results = searchConfig.processJobAds(jobAds);
		assertTrue(results.size() == 1);
		assertTrue(results.get(0).getTitle().equals("C# developer"));
		assertTrue(results.get(0).getTitle().equals("in palmerston north /n no comercial expreiance required"));
		
		searchConfig = new SearchConfiguration();
		searchConfig.addJobAdCondition(Options.musthave, Options.anyWhere, "palmerston north");
		
		results = searchConfig.processJobAds(jobAds);
		assertTrue(results.size() == 2);
		
		jobAd = new JobAd();
		jobAd.setTitle("gardner");
		jobAd.setFullDescription("must love working out doors /n will be based in palemrston north");
		
		jobAds.add(jobAd);
		
		results = searchConfig.processJobAds(jobAds);
		assertTrue(results.size() == 3);
		
		searchConfig.addJobAdCondition(Options.musthave, Options.anyWhere, "[d|D]evelper/b");
		
		results = searchConfig.processJobAds(jobAds);
		assertTrue(results.size() == 2);
	}
	
	@Test
	public void testTwo() {
		SearchConfiguration searchConfig = new SearchConfiguration();
		
		File file = new File("TestSite/testContentHandler.html");
		String fileName = "";
		fileName = file.toURI().toString();
		
		searchConfig.addWebSite(fileName);
		searchConfig.addJobAdCondition("mustHave","title", "\btwo");
		
		File reportFile = new File("TestSite/testReort");
		String reportFileName = reportFile.toURI().toString();
		
		searchConfig.setOutputOption(Options.notepadFile,reportFileName);
		CrawlManager.performJobAdCrawl(searchConfig);
		
		try {
			String result = readFile(fileName);
			assertTrue(result.contains("job two"));
			assertFalse(result.contains("job one"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String readFile( String file ) throws IOException {
	    BufferedReader reader = new BufferedReader( new FileReader (file));
	    String         line = null;
	    StringBuilder  stringBuilder = new StringBuilder();
	    String         ls = System.getProperty("line.separator");

	    while( ( line = reader.readLine() ) != null ) {
	        stringBuilder.append( line );
	        stringBuilder.append( ls );
	    }
	    
	    reader.close();

	    return stringBuilder.toString().replaceAll("(\\r|\\n|\\t)", "");
	}
}
