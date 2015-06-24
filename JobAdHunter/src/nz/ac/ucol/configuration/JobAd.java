package nz.ac.ucol.configuration;

public class JobAd {

	private String title;
	private String shortDescription;
	private String FullDescription;
	private String source;
	private boolean requresAdditonalInformation;
	private String urlOFAditionalInfo;
	
	/**
	 * create a JobAd object with null values
	 */
	public JobAd()
	{
		this.title = null;
		this.shortDescription = null;
		this.FullDescription = null;
		this.source = null;
		this.requresAdditonalInformation = false;
		this.urlOFAditionalInfo = null;
	}
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * @return the shortDescription
	 */
	public String getShortDescription() {
		return shortDescription;
	}
	
	/**
	 * @param shortDescription the shortDescription of the JobAd
	 */
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	
	/**
	 * @return the fullDescription
	 */
	public String getFullDescription() {
		return FullDescription;
	}
	
	/**
	 * @param fullDescription the fullDescription of the JobAd
	 */
	public void setFullDescription(String fullDescription) {
		FullDescription = fullDescription;
	}
	
	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}
	
	/**
	 * @param source the source of the JobAd
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return the requresAdditonalInformation
	 */
	public boolean requresAdditonalInformation() {
		return requresAdditonalInformation;
	}

	/**
	 * @param requresAdditonalInformation the requresAdditonalInformation to set
	 */
	public void setRequresAdditonalInformation(boolean requresAdditonalInformation) {
		this.requresAdditonalInformation = requresAdditonalInformation;
	}

	/**
	 * @return the urlOFAditionalInfo
	 */
	public String getUrlOFAditionalInfo() {
		return urlOFAditionalInfo;
	}

	/**
	 * @param urlOFAditionalInfo the urlOFAditionalInfo to set
	 */
	public void setUrlOFAditionalInfo(String urlOFAditionalInfo) {
		this.urlOFAditionalInfo = urlOFAditionalInfo;
	}
	
	/**
	 * combines all sections of this job ad and returns them as a single string
	 * @return combined sections
	 */
	public String getAllSections()
	{
		return this.title + " " + this.shortDescription + " " + this.FullDescription;
	}
}
