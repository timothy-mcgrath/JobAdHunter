package nz.ac.ucol.crawler;

import nz.ac.ucol.configuration.SearchConfiguration;

public class CrawlManager {

	public static void performJobCrawl(String urlToSearchConfiguration)
	{
		//load searchConfig
		performJobAdCrawl(new SearchConfiguration(urlToSearchConfiguration));
	}
	
	public static void performJobAdCrawl(SearchConfiguration sConfig)
	{
		
		//for each web site
		// use webcrawler to crawl specified page and any additional pages for same site 
		// pass result to SiteContentHandler process output with SearchConfiguration
		// crawl additional pages related to first if needed
	}
}
