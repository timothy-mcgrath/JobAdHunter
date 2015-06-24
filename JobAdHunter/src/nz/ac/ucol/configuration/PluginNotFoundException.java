package nz.ac.ucol.configuration;

public class PluginNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	private String searchedRequirement;
	
	/**
	 * creates an exception for when their is no plugin matching parameter
	 * @param paramater that couldn't be found
	 */
	public PluginNotFoundException(String offendingSite)
	{
		super();
		this.searchedRequirement = offendingSite;
	}

	/**
	 * @return the requirement that couldn't be found
	 */
	public String getSearchedRequirement() {
		return searchedRequirement;
	}
}
