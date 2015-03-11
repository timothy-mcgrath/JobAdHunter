package nz.ac.ucol.tests;

import java.util.ArrayList;

import nz.ac.ucol.configuration.JobAd;
import nz.ac.ucol.plugin.siteHandler;

public class TestPluginLoad extends siteHandler {

	public ArrayList<JobAd> processSite(String site) {
		ArrayList<JobAd> result = new ArrayList<>();
		result.add(new JobAd());
		result.get(0).setTitle("hello");
		return result;
	}

	@Override
	public String handlesSite() {
		return "test";
	}

}
