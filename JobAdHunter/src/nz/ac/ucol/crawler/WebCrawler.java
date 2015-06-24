package nz.ac.ucol.crawler;

import java.util.ArrayList;

import com.crawljax.core.CrawlerContext;
import com.crawljax.core.CrawljaxRunner;
import com.crawljax.core.configuration.CrawljaxConfiguration;
import com.crawljax.core.configuration.CrawljaxConfiguration.CrawljaxConfigurationBuilder;
import com.crawljax.core.plugin.OnNewStatePlugin;
import com.crawljax.core.state.StateVertex;

public class WebCrawler {
	private static ArrayList<String> result;

	/**
	 * crawl single page without looking for any additional pages
	 * @param url url of web page to crawl
	 * @return an arrayList<String> of the web page DOM 
	 */
	public static ArrayList<String> crawlPage(String url)
	{
		return crawlPage(url, "", "");
	}
	
	/**
	 * crawl specified web page and follow specified link only 
	 * @param url the url of the web page to crawl
	 * @param tagForNext the tag name of next element
	 * @param textForNextLink the text the next link contains 
	 * @return an arrayList<String> of the web pages DOM's 
	 */
	public static ArrayList<String> crawlPage(String url, String tagForNext, String textForNextLink)
	{		
		CrawljaxConfigurationBuilder builder = CrawljaxConfiguration.builderFor(url);
		builder.crawlRules().insertRandomDataInInputForms(false);
		result = new ArrayList<String>();
		
		builder.addPlugin(new OnNewStatePlugin (){

			@Override
			public void onNewState(CrawlerContext context, StateVertex arg1) {
				result.add(context.getBrowser().getStrippedDom());				
			}
			
		});
		builder.crawlRules().followExternalLinks(false);
		builder.crawlRules().click(tagForNext).withText(textForNextLink);
		builder.setMaximumDepth(1);
		
		CrawljaxRunner crawljax = new CrawljaxRunner(builder.build());
		crawljax.call();
		
		return result;
	}
}
