package nz.ac.ucol.crawler;

import com.crawljax.core.CrawlerContext;
import com.crawljax.core.CrawljaxRunner;
import com.crawljax.core.configuration.CrawljaxConfiguration;
import com.crawljax.core.configuration.CrawljaxConfiguration.CrawljaxConfigurationBuilder;
import com.crawljax.core.plugin.OnNewStatePlugin;
import com.crawljax.core.state.StateVertex;

public class WebCrawler {
	private static String result = "";

	public static String crawlPage(String url)
	{		
		CrawljaxConfigurationBuilder builder = CrawljaxConfiguration.builderFor(url);
		builder.crawlRules().insertRandomDataInInputForms(false);
		
		builder.addPlugin(new OnNewStatePlugin (){

			@Override
			public void onNewState(CrawlerContext context, StateVertex arg1) {
				result = context.getBrowser().getStrippedDom();				
			}
			
		});
		
		CrawljaxRunner crawljax = new CrawljaxRunner(builder.build());
		crawljax.call();
		
		return result;
	}
}
