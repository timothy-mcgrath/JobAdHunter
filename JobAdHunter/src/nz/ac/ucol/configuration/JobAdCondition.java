package nz.ac.ucol.configuration;

public class JobAdCondition {
	private String type;
	private String area;
	private String expresion;
	
	/**
	 * create a jobAdCondtion with provided values
	 * @param type the type of requirement it is from Options.java
	 * @param area the area of the job add to use
	 * @param expression the expression to match 
	 */
	public JobAdCondition(String type,String area,String expression) {
		this.area = area;
		this.type = type;
		this.expresion = expression;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

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
	 * @return the expression
	 */
	public String getExpresion() {
		return expresion;
	}

	/**
	 * @param expresion the expression to set
	 */
	public void setExpresion(String expresion) {
		this.expresion = expresion;
	}
}
