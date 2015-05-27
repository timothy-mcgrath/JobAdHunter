package nz.ac.ucol.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import nz.ac.ucol.crawler.WebCrawler;

import org.junit.Test;

import com.crawljax.core.configuration.CrawljaxConfiguration;
import com.crawljax.core.configuration.CrawljaxConfiguration.CrawljaxConfigurationBuilder;

public class WebCrawlerTests {

	// test loading standard page without scripts
	@Test
	public void test() {
		// get local path to test site
		File file = new File("TestSite/test.html");
		String fileName = file.toURI().toString();
		
		String result = WebCrawler.crawlPage(fileName).get(0);
		
		assertTrue(result.contains("hello"));
	}
	
	// test handling of javasccript on loaded page 
	@Test
	public void testJavascript()
	{
		File file = new File("TestSite/testJavascript.html");
		String fileName = file.toURI().toString();
		
		String result = WebCrawler.crawlPage(fileName).get(0);
		
		assertTrue(result.contains("Hello JavaScript!"));
		
		assertFalse(result.contains("getElementById"));
	}
	
	// relies on web site hosted with 000webhost.com 
	@Test
	public void testMultiplePages()
	{		
		ArrayList<String> result = WebCrawler.crawlPage("http://testsite.herobo.com/testSeperatePagesOnline.html","A","next");
		assertTrue(result.size() == 2);
	}

}
