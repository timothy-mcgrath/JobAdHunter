package nz.ac.ucol.tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nz.ac.ucol.configuration.JobAd;
import nz.ac.ucol.configuration.Options;
import nz.ac.ucol.configuration.SearchConfiguration;
import nz.ac.ucol.crawler.CrawlManager;
import nz.ac.ucol.gui.View;

import org.junit.Test;
//import org.loadui.testfx.GuiTest;
//import org.loadui.testfx.controls.Commons;
import org.loadui.testfx.*;
import org.hamcrest.*;
import org.loadui.testfx.Assertions;

import static org.junit.Assert.*;

public class GUITests extends  GuiTest {
	
	@Test
	public void testTeck(){
		final Button button = find( "#createjobCrawl" );
	    click(button);
	    Assertions.assertNodeExists("it worked");
	}

	@Override
	protected Parent getRootNode() {
		// TODO Auto-generated method stub
		return View.getRootNodeForTesting();
	}
	
	/*
	 * creates a crawl for http://testsite.herobo.com/testSeperatePagesOnline.html
	 * ranks jobs containing c# sharp higher then those that don't and excludes any
	 *  that don't include developer
	 */
	@Test
	public void testCreateCrawl()
	{
		// transition to creation screen
		Button btn = find("#createjobCrawlBTN");
		click(btn);
		Assertions.assertNodeExists("#jobCrawlCreationpagePNL");
		
		// set name
		TextField tf = find("#nameFeildTF");
		click(tf);
		type("test Web Crawl");
		
		// add site
		Label lbl = find("#webSitesLBL");
		assertTrue(lbl.getText().equals(""));
		
		tf = find("#webSiteToAddTF");
		click(tf);
		type("http://testsite.herobo.com/testSeperatePagesOnline.html");
		
		btn = find("#addWebSiteBTN");
		click(btn);
		
		assertTrue(lbl.getText().equals("http://testsite.herobo.com/testSeperatePagesOnline.html"));
			
		//add ranking rule
		btn = find("#addRankingRuleBTN");
		
		Assertions.assertNodeExists("#rankingRule1WeightTF");
		
		tf = find("#rankingRule1WeightTF");
		click(tf);
		type("30");
		
		ComboBox cb = find("condtion1RWCB");
		cb.getSelectionModel().select(1); // where 1 is option wanted 'non case sensitive'
		
		tf = find("condition1RWTF");
		click(tf);
		type("C#");
		
		// set what comes before  
		btn = find("addPreviousConditionRWBTN");
		click(tf);
		
		Assertions.assertNodeExists("#condtion0RWCB");
		
		cb = find("#condtion0RWCB");
		cb.getSelectionModel().select(1); // select word break
		
		// set what comes after
		btn = find("addPostConditionRWBTN");
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
		btn = find("addPreviousConditionIRBTN");
		click(tf);
				
		Assertions.assertNodeExists("#condtion0IRCB");
				
		cb = find("#condtion0IRCB");
		cb.getSelectionModel().select(1); // select word break
				
		// set what comes after
		btn = find("addPostConditionIRBTN");
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
		
		
	}


}
