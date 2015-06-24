package pluginImpl;

import java.util.ArrayList;

import nz.ac.ucol.configuration.JobAd;
import nz.ac.ucol.plugin.SiteHandler;

public class TestPluginHandlingContent implements SiteHandler {

	public String handlesSite() {
		return ".*testTwo.*|.*testContentHandler.*";
	}

	public nextLink getURLofNextPage() {
		return new nextLink("", "");
	}

	public ArrayList<JobAd> processSiteSearchResults(String site,String url) {
		ArrayList<JobAd> jobs = new ArrayList<>();
		
		site = site.split("(?i)<ul>")[1];
		site = site.split("(?i)</ul>")[0];
		String[] sections = site.split("(?i)<li>");
		for(int i = 1; i < sections.length;i++)
		{
			String section = sections[i];
			section = section.replace("(?i)</li>", "");
			String[] jobparts = section.split("(?i)<br />");
			if(jobparts.length < 2)
			{
				jobparts = section.split("(?i)<br>");
			}
			JobAd job = new JobAd();
			job.setTitle(jobparts[0]);
			job.setFullDescription(jobparts[1]);
			job.setRequresAdditonalInformation(false);
			job.setSource(url);
			jobs.add(job);
		}
		return jobs;
	}

	public JobAd processSiteJobListing(String site, JobAd jobAdToAddInfoToo) {
		// TODO Auto-generated method stub
		return null;
	}
}
