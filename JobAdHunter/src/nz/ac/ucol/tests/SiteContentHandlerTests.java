package nz.ac.ucol.tests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import nz.ac.ucol.configuration.JobAd;
import nz.ac.ucol.crawler.SiteContentHandler;
import nz.ac.ucol.utility.Utility;

import org.junit.Test;

public class SiteContentHandlerTests {

	@Test
	public void test() {
		ArrayList<JobAd> result = SiteContentHandler.handleSiteSearchResults("dosent matter for first one", "test");
		assertTrue(result.get(0).getTitle().equals("hello"));
	}
	
	@Test
	public void test2()	
	{
		try {
			ArrayList<JobAd> result;
			result = SiteContentHandler.handleSiteSearchResults(Utility.readFile("TestSite/testContentHandler.html"), "testTwo");
			assertTrue(result.get(0).getTitle().equals("job one"));
			assertTrue(result.get(0).getFullDescription().equals("something about job one"));
			
			assertTrue(result.get(1).getTitle().equals("job two"));
			assertTrue(result.get(1).getFullDescription().equals("for java developers"));
			
			assertTrue(result.size() == 2);
		} catch (IOException e) {
			assertTrue(false);
		}
	}	
}
