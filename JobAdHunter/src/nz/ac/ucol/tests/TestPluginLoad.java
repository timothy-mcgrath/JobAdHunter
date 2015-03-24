package nz.ac.ucol.tests;

import java.util.ArrayList;

import net.xeoh.plugins.base.annotations.Capabilities;
import nz.ac.ucol.configuration.JobAd;
import nz.ac.ucol.plugin.SiteHandler;

public class TestPluginLoad implements SiteHandler {

	public ArrayList<JobAd> processSite(String site) {
		ArrayList<JobAd> result = new ArrayList<>();
		result.add(new JobAd());
		result.get(0).setTitle("hello");
		return result;
	}

	public String handlesSite() {
		return "test";
	}

	@Override
	public String getURLofNextPage() {
		return null;
	}
}
