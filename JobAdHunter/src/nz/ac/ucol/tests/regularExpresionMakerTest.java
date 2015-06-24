package nz.ac.ucol.tests;

import java.util.ArrayList;

import nz.ac.ucol.gui.RegularExpressionMaker;
import org.junit.Test;
import static org.junit.Assert.*;

public class regularExpresionMakerTest {

	ArrayList<String> topRow = new ArrayList<>();
	ArrayList<String> bottomRow = new ArrayList<>();
	
	@Test
	public void simpleExpression()
	{
		resetParamaters();
		addcondtion(RegularExpressionMaker.ANY, "");
		String expression = RegularExpressionMaker.makeExpression(topRow,bottomRow);
		assertTrue("the".matches(expression));
		
		resetParamaters();
		addcondtion(RegularExpressionMaker.ANY, "");
		addcondtion(RegularExpressionMaker.CONTAINSNS, "hat");
		addcondtion(RegularExpressionMaker.ANY, "");
		expression = RegularExpressionMaker.makeExpression(topRow,bottomRow);
		assertTrue("the hat".matches(expression));
		
		resetParamaters();
		addcondtion(RegularExpressionMaker.ANY, "");
		addcondtion(RegularExpressionMaker.CONTAINSCS, "hat");
		addcondtion(RegularExpressionMaker.ANY, "");
		expression = RegularExpressionMaker.makeExpression(topRow,bottomRow);
		assertTrue("the hat".matches(expression));
		assertFalse("the HAT".matches(expression));
		
		resetParamaters();
		addcondtion(RegularExpressionMaker.ANY, "");
		addcondtion(RegularExpressionMaker.CONTAINSCS, "Hat");
		addcondtion(RegularExpressionMaker.ANY, "");
		expression = RegularExpressionMaker.makeExpression(topRow,bottomRow);
		assertFalse("the hat".matches(expression));
		
		resetParamaters();
		addcondtion(RegularExpressionMaker.ANY, "");
		addcondtion(RegularExpressionMaker.WORDBREAK, "");
		addcondtion(RegularExpressionMaker.CONTAINSCS, "hat");
		addcondtion(RegularExpressionMaker.WORDBREAK, "");
		addcondtion(RegularExpressionMaker.ANY, "");
		expression = RegularExpressionMaker.makeExpression(topRow,bottomRow);
		assertTrue("the hat".matches(expression));
		assertTrue("the hat is blue".matches(expression));
		assertTrue("hat".matches(expression));
		assertFalse("the hab is blue".matches(expression));
		
		resetParamaters();
		addcondtion(RegularExpressionMaker.ANY, "");
		addcondtion(RegularExpressionMaker.WORDBREAK, "");
		addcondtion(RegularExpressionMaker.NOTCONTAINSNS, "The");
		addcondtion(RegularExpressionMaker.WORDBREAK, "");
		addcondtion(RegularExpressionMaker.ANY, "");
		expression = RegularExpressionMaker.makeExpression(topRow,bottomRow);
		assertTrue("th hat".matches(expression));
		assertTrue("te hat is blue".matches(expression));
		assertFalse(" blah The ".matches(expression));
		assertFalse(" the hab is blue".matches(expression));
		assertFalse("the".matches(expression));
		assertFalse("The".matches(expression));
		
		resetParamaters();
		addcondtion(RegularExpressionMaker.ANY, "");
		addcondtion(RegularExpressionMaker.WORDBREAK, "");
		addcondtion(RegularExpressionMaker.STARTOR, "");
		addcondtion(RegularExpressionMaker.CONTAINSCS, "The");
		addcondtion(RegularExpressionMaker.OR, "");
		addcondtion(RegularExpressionMaker.CONTAINSCS, "HAT");
		addcondtion(RegularExpressionMaker.ENDOR, "");
		addcondtion(RegularExpressionMaker.WORDBREAK, "");
		addcondtion(RegularExpressionMaker.ANY, "");
		expression = RegularExpressionMaker.makeExpression(topRow,bottomRow);
		assertTrue("The cap".matches(expression));
		assertTrue("a HAT".matches(expression));
		assertTrue("The HAT".matches(expression));
		assertFalse("the sdfg ".matches(expression));
		assertFalse(" the hab is blue".matches(expression));
		assertTrue("The".matches(expression));
		assertTrue("HAT".matches(expression));
	}
	
	private void resetParamaters()
	{
		topRow = new ArrayList<String>();
		bottomRow = new ArrayList<String>();		
	}
	
	private void addcondtion(String option,String text)
	{
		topRow.add(option);
		bottomRow.add(text);
	}
}
