package nz.ac.ucol.configuration;

import java.util.ArrayList;

public class SearchConfiguration {

	/**
	 * creates an empty search configuration
	 */
	public SearchConfiguration()
	{
		
	}
	
	/**
	 * loads a search configuration from file
	 * @param urlToSearchConfiguration
	 */
	public SearchConfiguration(String urlToSearchConfiguration)
	{
		
	}
	
	/**
	 * rate this job ad based on configuration settings 
	 * @param jobAd job ad to rate
	 * @return numerical value of job ad
	 */
	public int rate(JobAd jobAd)
	{
		return 0;
	}
	
	/**
	 * process the job adds found and based on settings excludes uninteresting ones and marks jobAds for additional crawling
	 * if required  
	 * @param jobAdslist of JobAds To process
	 * @return jobAds that are of interest or require additional information
	 */
	public void processJobAds(ArrayList<JobAd> jobAds)
	{
		
	}
}
