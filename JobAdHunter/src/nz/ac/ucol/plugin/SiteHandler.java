package nz.ac.ucol.plugin;

import java.util.ArrayList;

import nz.ac.ucol.configuration.JobAd;

public interface SiteHandler {

	/**
	 * Takes the source code of a web page representing search results and extracts a list of JobAds from it 
	 * @param site the document to extract JobAds from 
	 * @param url 
	 * @return the list of JobAds extracted from site 
	 */	
	public ArrayList<JobAd> processSiteSearchResults(String site, String url);
	
	/**
	 * Takes the source code of a web page and extracts a list of JobAds from it 
	 * @param site the document to extract JobAds from 
	 * @param url 
	 * @return the list of JobAds extracted from site 
	 */	
	public JobAd processSiteJobListing(String site, JobAd jobAdToAddInfoToo);
	
	/**
	 * returns the base url of the site this plugin handles
	 * @return
	 */
	public String handlesSite();
	
	/**
	 * gets the link details for proceeding through search results on the web page this plugin is set up for
	 * @return nextLink containing tag name and text displayed 
	 */
	public nextLink getURLofNextPage();
	
	/**
	 * next link class for combining tagName and text
	 * @author Tim McGrath
	 *
	 */
	public class nextLink
	{
		private String tagName;
		private String tagText;
		
		/**
		 * @return the tagText
		 */
		public String getTagText() {
			return tagText;
		}

		/**
		 * @param tagText the tagText to set
		 */
		public void setTagText(String tagText) {
			this.tagText = tagText;
		}

		
		/**
		 * @return the tagName
		 */
		public String getTagName() {
			return tagName;
		}

		/**
		 * @param tagName the tagName to set
		 */
		public void setTagName(String tagName) {
			this.tagName = tagName;
		}

		public nextLink(String tagName,String tagText)
		{
			this.tagName = tagName;
			this.tagText = tagText;
		}
	}
}
