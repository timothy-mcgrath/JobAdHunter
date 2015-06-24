package nz.ac.ucol.tests;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import nz.ac.ucol.gui.RegularExpressionMaker;
import nz.ac.ucol.gui.View;
import org.junit.Test;
import org.loadui.testfx.*;
import org.loadui.testfx.Assertions;
import com.sun.javafx.tk.Toolkit;

import static org.junit.Assert.*;

public class GUITests extends  GuiTest {
	
	@Override
	protected Parent getRootNode() {
		// TODO Auto-generated method stub
		return View.getRootNodeForTesting();
	}
	
	/*
	 * creates a crawl for http://testsite.herobo.com/testSeperatePagesOnline.html
	 * ranks jobs containing c# higher then those that don't and excludes any
	 *  that don't include developer
	 *  
	 *  currently broken 
	 */
	@Test
	public void testCreateCrawl()
	{
		// transition to creation screen
		Button btn = find("#createjobCrawlBTN");
		click(btn);
		Assertions.assertNodeExists("#jobCrawlCreationPagePNL");
		
		// set name
		TextField tf = find("#nameFeildTF");
		click(tf);
		type("test");
		
		// add site
		Label lbl = find("#webSitesLBL");
		Toolkit.getToolkit().firePulse();
		assertTrue(lbl.getText().equals(""));
		
		tf = find("#webSiteToAddTF");
		click(tf);
		type("http");
		press(KeyCode.SHIFT).press(KeyCode.SEMICOLON).release(KeyCode.SEMICOLON).release(KeyCode.SHIFT);		
		type("//testsite.herobo.com/testSeperatePagesOnline.html");
		
		btn = find("#addWebSiteBTN");
		click(btn);
		
		assertTrue(lbl.getText().equals("http://testsite.herobo.com/testSeperatePagesOnline.html"));
			
		//add ranking rule
		btn = find("#addRankingRuleBTN");
		click(btn);
		
		Assertions.assertNodeExists("#rankingRule1WeightTF");
		
		tf = find("#rankingRule1WeightTF");
		click(tf);
		type("30");
		
		ComboBox<String> cb = find("#condtion1RWCB");
		cb.getSelectionModel().select(RegularExpressionMaker.CONTAINSCS); 
		
		tf = find("#condtion1RWTF");
		click(tf);
		//type("C#");
		tf.setText("c#");
		
		// set what comes before  
		btn = find("#addPreviousConditionRWBTN");
		click(tf);
		
		cb = find("#condtion0RWCB");
		cb.getSelectionModel().select(1); // select word break 
		
		// set what comes after
		btn = find("#addPostConditionRWBTN");
		click(tf);
		
		Assertions.assertNodeExists("#condtion2RWCB");
		
		cb = find("#condtion2RWCB");
		cb.getSelectionModel().select(1); 
		
		// add rule to only include job ads including developer
		btn = find("#addRuleBTN");
		click(btn);
		
		Assertions.assertNodeExists("#condtion1IRCB");
		
		cb = find("#condtion1IRCB");
		cb.getSelectionModel().select(1); 
		
		tf = find("#condtion1IRTF");
		click(tf);
		type("developer");
		
		// set what comes before  
		btn = find("#addPreviousConditionIRBTN");
		click(tf);
				
		Assertions.assertNodeExists("#condtion0IRCB");
				
		cb = find("#condtion0IRCB");
		cb.getSelectionModel().select(1); // select word break
				
		// set what comes after
		btn = find("#addPostConditionIRBTN");
		click(tf);
				
		Assertions.assertNodeExists("#condtion2IRCB");
		
		// set output type and location  
		cb = find("#outputCB");
		cb.getSelectionModel().select(1); // text file option
		
		tf = find("#outputLocationTF");
		click(tf);
		type("TestSite/testReport");
		
		// create job crawl
		btn = find("#createCrawlBTN");
		click(btn);
		
//		//run new job crawl
//		btn = find("#runJobCrawlBTN");
//		click(btn);
//		
//		btn = find("#runtestWebCrawl");
//		click(btn);
//		
//		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
//		Date date = new Date();
//		
//		try {
//			String result = Utility.readFile("TestSite/testReport" + dateFormat.format(date) + ".txt");
//			assertTrue(result.contains("java developer"));
//			assertTrue(result.contains(".net developer"));
//			assertFalse(result.contains("house keeper"));
//		} catch (IOException e) {
//			e.printStackTrace();
//			assertTrue(false);
//		}
//		
	}


}
