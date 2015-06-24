package nz.ac.ucol.configuration;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import nz.ac.ucol.utility.Utility;

public class SearchConfiguration {

	private ArrayList<RankRule> rankRules;
	private ArrayList<String> webSites;
	private ArrayList<JobAdCondition> jobAdConditions;
	private String outPutType;
	private String outPutDestination;
	private Date timeofNextCrawl;
	private String name;
	private String scheduledToRepeat;
	
	/**
	 * how often the crawl will be done
	 * @return the scheduledToRepeat
	 */
	public String getScheduledToRepeat() {
		return scheduledToRepeat;
	}

	/**
	 * how often the crawl will be done
	 * @param scheduledToRepeat the scheduledToRepeat to set
	 */
	public void setScheduledToRepeat(String scheduledToRepeat) {
		this.scheduledToRepeat = scheduledToRepeat;
	}

	/**
	 * creates an empty search configuration primarily for when creating new ones
	 */
	public SearchConfiguration()
	{
		rankRules = new ArrayList<RankRule>();
		webSites = new ArrayList<String>();
		jobAdConditions = new ArrayList<JobAdCondition>();
		outPutType = "";
		outPutDestination = "";
	}
	
	/**
	 * loads a search configuration from file 
	 * @param urlToSearchConfiguration path to serialized search configuration 
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public SearchConfiguration(String urlToSearchConfiguration) throws IOException, ParseException
	{
		String[] sections = Utility.readFile(urlToSearchConfiguration).split(seperator);
		this.name = sections[0];
		this.outPutDestination = sections[1];
		this.outPutType = sections[2];
		this.scheduledToRepeat = sections[3];
		if(sections[4].length() != 0)
		{
			this.timeofNextCrawl = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(sections[4]);
		}
		
		// inclusions rules
		this.jobAdConditions = new ArrayList<JobAdCondition>();
		String inclusionRules = sections[5];
		if(inclusionRules.length() != 0)
		{
			for(String inclusionRULE:inclusionRules.split(innerSeperator))
			{
				String[] ruleParts = inclusionRULE.split(deepestSeperator);
				this.addJobAdCondition(ruleParts[2], ruleParts[0], ruleParts[1]);
			}
		}
		
		// ranking rules
		this.rankRules = new ArrayList<RankRule>();
		String rankingRules = sections[6];
		if(rankingRules.length() != 0)
		{
			for(String rule:rankingRules.split(innerSeperator))
			{
				String[] ruleParts = rule.split(deepestSeperator);
				this.addRankingCondition(Integer.valueOf(ruleParts[2]), ruleParts[1], ruleParts[1]);
			}
		}
		
		//web sites
		this.webSites = new ArrayList<String>();
		String websites = sections[7];
		if(websites.length() != 0)
		{
			for(String webSite:websites.split(innerSeperator))
			{
				this.webSites.add(webSite);
			}		
		}
	}
	
	final String seperator = "_,_";
	final String innerSeperator = "_~_";
	final String deepestSeperator = "_-_";
	
	/**
	 * Serializes all search configuration information into a file at specified location
	 * @param fileName the name of the file to be created 
	 */
	public boolean toFile(String fileName)
	{
		boolean result = true;
		try 
		{
			Writer writer = new BufferedWriter(new FileWriter(fileName));
			writer.write(this.getName() + seperator);
			writer.write(this.getOutPutDestination() + seperator);
			writer.write(this.getOutPutType() + seperator);
			writer.write(this.getScheduledToRepeat() + seperator);
			if(this.getTimeofNextCrawl() != null)
			{
				writer.write(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(this.getTimeofNextCrawl()));
			}
			writer.write(seperator);
			
			for(JobAdCondition jac:this.getjobAdCondtions())
			{
				writer.write(jac.getArea() + deepestSeperator);
				writer.write(jac.getExpresion() + deepestSeperator);
				writer.write(jac.getType()  + deepestSeperator);
				writer.write(innerSeperator);
			}
			writer.write(seperator);
			
			for(RankRule jac:this.getRankingConditions())
			{
				writer.write(jac.getArea() + deepestSeperator);
				writer.write(jac.getRule() + deepestSeperator);
				writer.write(jac.getWeight()  + deepestSeperator);
				writer.write(innerSeperator);
			}
			writer.write(seperator);
			
			for(String webSite:this.getWebSites())
			{
				writer.write(webSite + innerSeperator);
			}
			writer.close();
		}
		catch (Exception e)
		{
			result = false;
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * ranks an ArrayList of jobAd's 
	 * @param jobAds job ads to rank
	 * @return ranked ArrayList of jobAd's
	 */
	public ArrayList<JobAd> rateJobAds(ArrayList<JobAd> jobAds)
	{
		ArrayList<int[]> ratings = new ArrayList<>();
		ArrayList<JobAd> results = new ArrayList<JobAd>();
		
		for(int i = 0; i < jobAds.size();i++)
		{
			int[] ratting = new int[]{i,rate(jobAds.get(i))};
			ratings.add(ratting);
		}
		
		ratings.sort(new Comparator<int[]>() {

			@Override
			public int compare(int[] first, int[] secound) {
				return Integer.compare(secound[1],first[1]);
			}
		});
		
		for(int i = 0; i < jobAds.size();i++)
		{
			results.add(jobAds.get(ratings.get(i)[0]));
		}
		
		return results;
	}
	
	/**
	 * rates the jobAd based on ranking rules 
	 * @param jobAd job ad to rate
	 * @return numerical value of job ad
	 */
	private int rate(JobAd jobAd)
	{
		int result = 0;
		for(RankRule rule:this.rankRules)
		{
			String contents = jobAd.getAllSections();
			if(contents.matches(rule.getRule()))
			{
				result += rule.getWeight();
			}
		}
		return result;
	}
	
	/**
	 * process the job adds found and based on settings excludes uninteresting ones 
	 * @param jobAdslist of JobAds To process
	 * @return jobAds that are of interest or require additional information
	 */
	public ArrayList<JobAd> processJobAds(ArrayList<JobAd> jobAds)
	{
		ArrayList<JobAd> result = new ArrayList<JobAd>();
		for(int i = 0 ; i < jobAds.size();i++)
		{
			JobAd current = jobAds.get(i);
			if(testjobAd(current))
			{
				result.add(current);
			}
		}
		return result;
	}
	
	/**
	 * test provided jobAd with this searchConfigurations conditions
	 * @param jobAdToTest
	 * @return boolean true if jobAd passes the conditions false otherwise
	 */
	private boolean testjobAd(JobAd jobAdToTest)
	{
		boolean result = true;
		for(JobAdCondition requirement:this.jobAdConditions)
		{
			String type = requirement.getType();
			String area = requirement.getArea();
			String expresion = requirement.getExpresion();
			if(type.equals(Options.musthave))
			{	
				if (area.equals(Options.title))
				{
					if(!jobAdToTest.getTitle().matches(expresion))
					{
						result = false;
					}
				}
				
				if (area.equals(Options.Description ))
				{
					if(!jobAdToTest.getShortDescription().matches(expresion) || !jobAdToTest.getFullDescription().matches(expresion))
					{
						// as if the whole description hasn't been loaded it might be in remaining section
						if(jobAdToTest.getFullDescription() != null)
						{
							result = false;
						}
					}
				}
				
				if(area.equals(Options.anyWhere))
				{
					if(!jobAdToTest.getAllSections().matches(expresion))
					{
						if(jobAdToTest.getFullDescription() != null)
						{
							result = false;
						}
					}
				}
			}
			else if(type.equals(Options.mustNotHave))
			{
				if (area.equals(Options.title))
				{
					if(jobAdToTest.getTitle().matches(expresion))
					{
						result = false;
					}
				}
				
				if (area.equals(Options.Description))
				{
					if(jobAdToTest.getShortDescription().matches(expresion) || !jobAdToTest.getFullDescription().matches(expresion))
					{
						result = false;
					}
				}
				
				if(area.equals(Options.anyWhere))
				{
					if(jobAdToTest.getAllSections().matches(expresion))
					{
						result = false;
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * add web site to crawl to current searchConfiguration
	 * @param url web site to add
	 */
	public void addWebSite(String url)
	{
		this.webSites.add(url);
	}
	
	/**
	 * get all web sites this search configuration is currently crawling
	 * @return list of web sites
	 */
	public ArrayList<String> getWebSites()
	{
		return this.webSites;
	}
	
	/**
	 * removes selected web site from crawl
	 * @param siteToRemove
	 * @return
	 */
	public boolean removeWebSite(String siteToRemove)
	{
		return this.webSites.remove(siteToRemove);
	}
	
	/**
	 * add Ranking rule
	 * @param weight weight of new rule
	 * @param Expresion expression for new rule
	 * @param area area of job add new rule applies too
	 */
	public void addRankingCondition(int weight,String Expresion,String area)
	{
		this.rankRules.add(new RankRule(weight, Expresion,area));
	}
	
	/**
	 * returns all ranking conditions of this search configuration 
	 * @return ArrayList<RankRule>
	 */
	public ArrayList<RankRule> getRankingConditions()
	{
		return this.rankRules;
	}
	
	/**
	 * add JobAd inclusion condition
	 * @param type type of rule
	 * @param area area of JobAd to apply expression to
	 * @param expression to match
	 */
	public void addJobAdCondition(String type,String area,String expression)
	{
		this.jobAdConditions.add(new JobAdCondition(type, area, expression));
	}
	
	/**
	 * gets all jobAdCondtions for current search configuration
	 * @return ArrayList<JobAdCondtion>
	 */
	public ArrayList<JobAdCondition> getjobAdCondtions()
	{
		return this.jobAdConditions;
	}
	
	/**
	 * remove specified condition from this search configuration 
	 * @param condtionToRemove
	 * @return true if it worked false other wise 
	 */
	public boolean removejobAddCondtion(JobAdCondition condtionToRemove)
	{
		return this.jobAdConditions.remove(condtionToRemove);
	}
	
	/**
	 * set how this search configuration results are reported
	 * @param option what type of outOut
	 * @param target where to put output
	 */
	public void setOutPutOption(String option, String target)
	{
		this.outPutDestination = target;
		this.outPutType = option;
	}
	
	/**
	 * find what this search configuration's output type is
	 * @return outPut type as string
	 */
	public String getOutPutType()
	{
		return this.outPutType;
	}
	
	/**
	 * find out where this search configuration is currently putting its out put
	 * @return location of out put
	 */
	public String getOutPutDestination()
	{
		return this.outPutDestination;
	}

	/**
	 * @return the timeofNextCrawl
	 */
	public Date getTimeofNextCrawl() {
		return timeofNextCrawl;
	}

	/**
	 * @param timeofNextCrawl the timeofNextCrawl to set
	 */
	public void setTimeofNextCrawl(Date timeofNextCrawl) {
		this.timeofNextCrawl = timeofNextCrawl;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
