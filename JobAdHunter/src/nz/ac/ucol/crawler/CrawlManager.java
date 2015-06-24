package nz.ac.ucol.crawler;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import nz.ac.ucol.configuration.JobAd;
import nz.ac.ucol.configuration.PluginNotFoundException;
import nz.ac.ucol.configuration.SearchConfiguration;
import nz.ac.ucol.plugin.OutPuter;
import nz.ac.ucol.plugin.SiteHandler.nextLink;

public class CrawlManager {

	/**
	 * Perform a job Crawl with searchConfiguration stored at specified location
	 * @param urlToSearchConfiguration location of search configuration 
	 * @throws IOException
	 * @throws ParseException
	 * @throws PluginNotFoundException
	 */
	public static void performJobCrawl(String urlToSearchConfiguration) throws IOException, ParseException, PluginNotFoundException
	{
		//load searchConfig
		performJobAdCrawl(new SearchConfiguration(urlToSearchConfiguration));
	}
	
	/**
	 * Perform a job Crawl with Search configuration 
	 * @param sConfig
	 * @throws PluginNotFoundException
	 */
	public static void performJobAdCrawl(SearchConfiguration sConfig) throws PluginNotFoundException
	{
		ArrayList<JobAd> interestingJobAds = new ArrayList<JobAd>();
		for(String webSite:sConfig.getWebSites())
		{
			ArrayList<JobAd> jobAdsFromSite = new ArrayList<JobAd>();
			nextLink next = SiteContentHandler.getNextLink(webSite);
			
			for(String pageOfSearchResults:WebCrawler.crawlPage(webSite,next.getTagName(),next.getTagText()))
			{
				jobAdsFromSite.addAll(SiteContentHandler.handleSiteSearchResults(pageOfSearchResults,webSite));
			}
			jobAdsFromSite = sConfig.processJobAds(jobAdsFromSite);
			for(JobAd jobAd:jobAdsFromSite)
			{
				if(jobAd.requresAdditonalInformation())
				{
					ArrayList<String> webPages = WebCrawler.crawlPage(jobAd.getUrlOFAditionalInfo());
					for(String webPage:webPages)
					{
						SiteContentHandler.handleSiteJobAdFull(webPage,jobAd.getUrlOFAditionalInfo(),jobAd);
					}
				}
			}
			interestingJobAds.addAll(sConfig.processJobAds(jobAdsFromSite));
		}
		// rank job ads
		interestingJobAds = sConfig.rateJobAds(interestingJobAds);
		
		String outPutType = sConfig.getOutPutType();
		try
		{
			OutPuter.outPutInformation(sConfig.getOutPutDestination(), interestingJobAds, outPutType);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
}
