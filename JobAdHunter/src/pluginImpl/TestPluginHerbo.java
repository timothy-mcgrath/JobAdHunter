package pluginImpl;

import java.util.ArrayList;

import nz.ac.ucol.configuration.JobAd;
import nz.ac.ucol.plugin.SiteHandler;

public class TestPluginHerbo implements SiteHandler{

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
			String[] jobparts = section.split("(?i)<br />");
			if(jobparts.length < 2)
			{
				jobparts = section.split("(?i)<br>");
			}
			JobAd job = new JobAd();
			job.setTitle(jobparts[0]);
			
			String moreLink = jobparts[1];
			moreLink = moreLink.split("\"")[1];
			job.setSource(url);
			job.setUrlOFAditionalInfo(url.substring(0, url.lastIndexOf("/")) + moreLink);
			
			job.setShortDescription(jobparts[2]);
			job.setRequresAdditonalInformation(true);
			jobs.add(job);
		}
		return jobs;
	}

	@Override
	public JobAd processSiteJobListing(String site, JobAd jobAdToAddInfoToo) {
		jobAdToAddInfoToo.setFullDescription(site);
		return jobAdToAddInfoToo;
	}

	@Override
	public String handlesSite() {
		return ".*herobo.*";
	}

	@Override
	public nextLink getURLofNextPage() {
		// TODO Auto-generated method stub
		return new nextLink("a", "next");
	}
	
	

}
