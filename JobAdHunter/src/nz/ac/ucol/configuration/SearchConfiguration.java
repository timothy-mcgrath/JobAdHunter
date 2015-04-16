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
	
	public boolean toFile(String fileName)
	{
		return true;
	}
	
	/**
	 * rate this job ad based on configuration settings 
	 * @param jobAd job ad to rate
	 * @return numerical value of job ad
	 */
	public ArrayList<JobAd> rateJobAds(ArrayList<JobAd> jobAds)
	{
		return (ArrayList<JobAd>)jobAds.clone();
	}
	
	/**
	 * process the job adds found and based on settings excludes uninteresting ones and marks jobAds for additional crawling
	 * if required  
	 * @param jobAdslist of JobAds To process
	 * @return jobAds that are of interest or require additional information
	 */
	public ArrayList<JobAd> processJobAds(ArrayList<JobAd> jobAds)
	{
		return (ArrayList<JobAd>)jobAds.clone();
	}
	
	public void addWebSite(String url)
	{
		
	}
	
	public void addRankingCondition(int weight,String Expresion)
	{
		
	}
	
	public void addJobAdCondition(String type,String area,String expression)
	{
		
	}
	
	public void setOutputOption(String option, String target)
	{
		
	}
}
