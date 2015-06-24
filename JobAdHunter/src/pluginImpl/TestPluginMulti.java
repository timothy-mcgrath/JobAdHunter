package pluginImpl;

import java.util.ArrayList;

import nz.ac.ucol.configuration.JobAd;
import nz.ac.ucol.plugin.SiteHandler;

public class TestPluginMulti implements SiteHandler {

	@Override
	public ArrayList<JobAd> processSiteSearchResults(String site,String url) {
		ArrayList<JobAd> jobs = new ArrayList<>();
		
		site = site.split("(?i)<ul>")[1];
		site = site.split("(?i)</ul>")[0];
		String[] sections = site.split("(?i)<li>");
		for(int i = 1; i < sections.length;i++)
		{
			String section = sections[i];
			section = section.replace("(?i)</li>", "");
			String[] jobparts = section.split("(?i)<br>");
			
			JobAd job = new JobAd();
			job.setTitle(jobparts[0]);
			job.setUrlOFAditionalInfo(jobparts[1]);
			job.setShortDescription(jobparts[2]);
			job.setSource(url);
			jobs.add(job);
		}
		return jobs;
	}

	@Override
	public JobAd processSiteJobListing(String site, JobAd jobAdToAddInfoToo) {
		String[] result = site.split("(?i)<br />");
		JobAd jobAd = new JobAd();
		String fullDescription = "";
		for(int i = 1; i < result.length;i++)
		{
			fullDescription += result[i];
		}
		jobAd.setFullDescription(fullDescription);
		return jobAd;
	}

	@Override
	public String handlesSite() {
		return "testSeperatePages";
	}

	@Override
	public nextLink getURLofNextPage() {
		return new nextLink("A", "next");
	}

}
