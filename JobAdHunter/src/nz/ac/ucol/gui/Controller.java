package nz.ac.ucol.gui;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import nz.ac.ucol.configuration.PluginNotFoundException;
import nz.ac.ucol.configuration.SearchConfiguration;
import nz.ac.ucol.crawler.CrawlManager;
import nz.ac.ucol.plugin.Scheduler;

public class Controller {
	View page;
	ArrayList<SearchConfiguration> searchs;
	
	/**
	 * create a new controller and loads list of search configurations 
	 * @param page a reference to the programs view 
	 */
	public Controller(View page)
	{
		this.page = page;
		searchs = new ArrayList<SearchConfiguration>();
		
		// read search configurations from folder
		File folder = new File("jobSearches");
		
		if(folder.listFiles().length != 0)
		{
			for (File file:folder.listFiles()) {
				if(file.isFile())
				{
					try 
					{
						searchs.add(new SearchConfiguration(file.getAbsolutePath()));
					} 
					catch (Exception e) 
					{
						System.out.println();
						// continue as its not a search configuration
					}
				}
			}
		}
	}
	
	/**
	 * updates the view to display the create web crawl sub page 
	 */
	public void showCreateWebCrawl()
	{
		page.displayCreateJobCrawl();
	}
	
	/**
	 * create a search configuration object from provided information stored in gui components 
	 * @param nameFeildTF
	 * @param siteToSearch
	 * @param listofRankingRules
	 * @param listofInclusionRules
	 * @param outPutOptionsCB
	 * @param outPutLocationTF
	 */
	public void createJobCrawl(TextField nameFeildTF, Label siteToSearch, ArrayList<Object[]> listofRankingRules, ArrayList<Object[]> listofInclusionRules, ComboBox<String> outPutOptionsCB, TextField outPutLocationTF)
	{
		SearchConfiguration sc = new SearchConfiguration();
		
		sc.setName(nameFeildTF.getText());
		for(String url:siteToSearch.getText().split(", "))
		{
			sc.addWebSite(url);
		}
		for(Object[] rankingRule:listofRankingRules)		
		{
			String area = ((TextField) rankingRule[2]).getText();
			int weight = Integer.valueOf(((TextField)rankingRule[2]).getText());
			sc.addRankingCondition(weight, RegularExpressionMaker.makeExpression(rankingRule),area); 
		}
		for(Object[] inclusionRule:listofInclusionRules)
		{
			String area = ((TextField) inclusionRule[2]).getText();
			String type = ((TextField) inclusionRule[3]).getText();
			sc.addJobAdCondition(type,area, RegularExpressionMaker.makeExpression(inclusionRule));
		}
		sc.setOutPutOption(outPutOptionsCB.getSelectionModel().getSelectedItem(),outPutLocationTF.getText());
		
		searchs.add(sc);
		sc.toFile("jobSearches/"+sc.getName());
	}
	
	/**
	 * runs the selected search configuration in its own thread and tells the user what is happening 
	 * @param sc
	 */
	public void runWebCrawlnow(SearchConfiguration sc)
	{
		page.displayMesaage("Running web crawl now");
		new Runnable() {			
			@Override
			public void run() {
				try {
					CrawlManager.performJobAdCrawl(sc);
				} catch (PluginNotFoundException e) {
					// Rough work around using swing 
					JOptionPane.showMessageDialog(null, "No Plug in to handle crawl " + sc.getName() + " for website " + e.getSearchedRequirement());
				}				
			}
		}.run();
	}
	
	/**
	 * add a web site to the provided label
	 * @param text
	 * @param siteToSearch
	 */
	public void addWebSite(String text, Label siteToSearch) 
	{
		if(siteToSearch.getText() == "")
		{
			siteToSearch.setText(text);
		}
		else
		{
			siteToSearch.setText(siteToSearch.getText() + ", " + text);
		}
	}
	
	/**
	 * updates the view to display an additional ranking rule
	 * @param areaToAddTo
	 */
	public void addRankingRule(GridPane areaToAddTo)
	{
		page.addRankRule(areaToAddTo);
	}
	
	/**
	 * updates the view to display an additional inclusion rule
	 * @param areaToAddTo
	 */
	public void addInclusionRule(GridPane centerInclusionRule) {
		page.addinclusionRule(centerInclusionRule);
	}
	
	/**
	 * updates the corresponding text area based on the selection of the ComboBox
	 * @param tf
	 * @param cb
	 */
	public void updateRuleTextArea(TextField tf,ComboBox<String> cb)
	{
		tf.setText(cb.getSelectionModel().getSelectedItem());
		String conditionChosen = cb.getSelectionModel().getSelectedItem();
		switch (conditionChosen)
		{
			case RegularExpressionMaker.OR: 			tf.setText("OR");
														tf.setEditable(false);
														return;
			case RegularExpressionMaker.WORDBREAK: 		tf.setEditable(false);
														return;
			case RegularExpressionMaker.ANY: 			tf.setEditable(false);
														return;
			default: 									tf.setEditable(true);
					 									tf.setText("");
					 									return;
		}
				
	}
	
	/**
	 * @return ArrayList<SearchConfiguration> stored in view 
	 */
	public ArrayList<SearchConfiguration> getSearchConfigurations() {
		return this.searchs;
	}

	/**
	 * update specified search configuration 
	 * @param startAt the time to start crawl at between 1 and 23 
	 * @param schedualedToRepeat the time interval between runs of search 
	 * @param sc search configuration to schedule 
	 */
	public void updateSchedule(String startAt, String schedualedToRepeat, SearchConfiguration sc) {
		try {
			sc.setTimeofNextCrawl(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(startAt));
		} catch (ParseException e) 
		{
			// Rough work around using swing 
			JOptionPane.showMessageDialog(null, "incorrect formating of date please enter like the following yyyy-MM-dd hh:mm:ss");
		}
		sc.setScheduledToRepeat(schedualedToRepeat);
		Scheduler.updateCrawl(sc);
		sc.toFile("jobSearches/"+sc.getName()); // save updated info to serialized version 
	}

	/**
	 * Cancel the search configurations current scheduled runs 
	 * @param sc
	 */
	public void deSchedule(SearchConfiguration sc) {
		Scheduler.removeCrawl(sc);
		sc.setScheduledToRepeat(null);
		sc.setTimeofNextCrawl(null);
	}

}
