package nz.ac.ucol.configuration;

public class RankRule {

	private int weight;		
	private String rule;
	private String area;
	
	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}

	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}

	/**
	 * @return the rule
	 */
	public String getRule() {
		return rule;
	}

	/**
	 * @param rule the rule to set
	 */
	public void setRule(String rule) {
		this.rule = rule;
	}
	
	/**
	 * consturct a rank rule with default values
	 */
	public RankRule()
	{
		this.weight = 0;
		this.rule = "";
		this.area = "";
	}
	
	/**
	 * construct a rank rule with provided values
	 * @param weight affect this RankRule has
	 * @param rule the regular expression that defines weather or not a jobAd matches
	 * @param area the area of the jobAd to look in
	 */
	public RankRule(int weight, String rule, String area) {
		this.weight = weight;
		this.rule = rule;
		this.area = area;
	}
	
}
