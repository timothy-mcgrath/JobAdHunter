package nz.ac.ucol.tests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import net.xeoh.plugins.base.annotations.Capabilities;
import nz.ac.ucol.configuration.JobAd;
import nz.ac.ucol.plugin.SiteHandler;

public class TestPluginHandlingContent implements SiteHandler {

	public ArrayList<JobAd> processSite(String site) {
		ArrayList<JobAd> jobs = new ArrayList<>();
		
		site = site.split("<ul>")[1];
		site = site.split("</ul>")[0];
		String[] sections = site.split("<li>");
		for(int i = 1; i < sections.length;i++)
		{
			String section = sections[i];
			section = section.replace("</li>", "");
			String[] jobparts = section.split("<br />");
			
			JobAd job = new JobAd();
			job.setTitle(jobparts[0]);
			job.setFullDescription(jobparts[1]);
			jobs.add(job);
		}
		return jobs;
	}

	public String handlesSite() {
		return "testTwo";
	}

	@Override
	public String getURLofNextPage() {
		return null;
	}
}
