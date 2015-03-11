package nz.ac.ucol.tests;

import java.util.ArrayList;

import nz.ac.ucol.configuration.JobAd;
import nz.ac.ucol.plugin.siteHandler;

public class TestPluginLoadTwo extends siteHandler {

	@Override
	public ArrayList<JobAd> processSite(String site) {
		ArrayList<JobAd> jobs = new ArrayList<>();
		
		String[] sections = site.split("<li>");
		for(int i = 1; i < sections.length - 1;i++)
		{
			String section = sections[i];
			section.replace("</li>", "");
			String[] jobparts = section.split("<br />");
			
			JobAd job = new JobAd();
			job.setTitle(jobparts[0]);
			job.setFullDescription(jobparts[1]);
		}
		return null;
	}

	@Override
	public String handlesSite() {
		return "testTwo";
	}

}
