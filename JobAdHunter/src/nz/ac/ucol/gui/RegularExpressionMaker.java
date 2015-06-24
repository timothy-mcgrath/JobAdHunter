package nz.ac.ucol.gui;

import java.util.ArrayList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class RegularExpressionMaker {

	public final static String WORDBREAK = "Word Break";
	public final static String	ANY = "Any";
	public final static String	CONTAINSCS = "Contains cs";
	public final static String	CONTAINSNS = "Contains ns";
	public final static String	NOTCONTAINSNS = "Does not contain ns";
	public final static String	OR = "OR";
	public final static String	STARTOR = "Start or";
	public final static String	ENDOR = "End of or";
	
	/**
	 * get information from the GUI components and pass onto other makeExpression method to generate regular expression
	 * @param listsOfNodes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String makeExpression(Object[] listsOfNodes)
	{
		ArrayList<ComboBox<String>> condtionTypes = (ArrayList<ComboBox<String>>) listsOfNodes[0];
		ArrayList<TextField> condtionString = (ArrayList<TextField>) listsOfNodes[1];
		
		ArrayList<String> condtionTypesValues = new ArrayList<String>();
		ArrayList<String> condtionStringValues = new ArrayList<String>();
		
		for(ComboBox<String> cb:condtionTypes)
		{
			condtionTypesValues.add(cb.getSelectionModel().getSelectedItem());
		}
		
		for(TextField tf:condtionString)
		{
			condtionStringValues.add(tf.getText());
		}
		
		
		return makeExpression(condtionTypesValues,condtionStringValues);
	}
	
	/**
	 * create a regular expression from provided parameters each index in condtionTypes 
	 * matches to condtionsString index, when appropriate uses this to complete regular expression
	 * @param condtionTypes
	 * @param condtionString
	 * @return
	 */
	public static String makeExpression(ArrayList<String> condtionTypes, ArrayList<String> condtionString)
	{
		String result = "";
		
		for(int i = 0; i < condtionTypes.size();i++)
		{
			String conditionType =condtionTypes.get(i);
			String condtionText = condtionString.get(i);
			
			switch (conditionType)
			{
				case WORDBREAK: result += "\\b";
									break;
				case ANY: result += ".*";
									break;
				case OR: result += "|";
									break;
				case CONTAINSCS: result += condtionText;
									break;
				case CONTAINSNS: result += "(?i)" + condtionText + "(?-i)";
									break;
				case NOTCONTAINSNS: result += "(?i)^((?!" + condtionText + ").)*$(?-i)";
									break;
				case STARTOR: 		result += "(";
									break;
				case ENDOR: 		result += ")";
									break;
			}
		}

		return result;
	}
}
