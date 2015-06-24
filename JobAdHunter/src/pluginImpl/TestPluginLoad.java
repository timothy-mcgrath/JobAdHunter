package pluginImpl;

import java.util.ArrayList;
import nz.ac.ucol.configuration.JobAd;
import nz.ac.ucol.plugin.SiteHandler;

public class TestPluginLoad implements SiteHandler {

	public String handlesSite() {
		return "test";
	}

	public nextLink getURLofNextPage() {
		return null;
	}

	public ArrayList<JobAd> processSiteSearchResults(String site,String url) {
		ArrayList<JobAd> result = new ArrayList<>();
		result.add(new JobAd());
		result.get(0).setTitle("hello");
		result.get(0).setSource(url);
		return result;
	}

	public JobAd processSiteJobListing(String site, JobAd jobAdToAddInfoToo) {
		// TODO Auto-generated method stub
		return null;
	}
}
