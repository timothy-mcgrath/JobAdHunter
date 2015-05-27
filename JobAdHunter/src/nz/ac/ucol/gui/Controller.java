package nz.ac.ucol.gui;

import java.util.ArrayList;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import nz.ac.ucol.configuration.SearchConfiguration;
import nz.ac.ucol.crawler.CrawlManager;

public class Controller {
	View page;
	ArrayList<SearchConfiguration> searchs = new ArrayList<SearchConfiguration>();
	
	public Controller(View page)
	{
		this.page = page;
	}
	
	public void showCreateWebCrawl()
	{
		page.displayCreateJobCrawl();
	}
	
	public void createJobCrawl()
	{
		SearchConfiguration sc = new SearchConfiguration();
		// fill in with data from gui 
		
		searchs.add(sc);
	}
	
	public void showRunWebCrawl()
	{
		page.displayRunJobCrawl(null);
	}
	
	public void runWebCrawlnow(SearchConfiguration sc)
	{
		page.displayMesaage("Running web crawl now");
		CrawlManager.performJobAdCrawl(sc);
	}
	
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
	
	public void addRankingRule(GridPane areaToAddTo)
	{
		page.addRankRule(areaToAddTo);
	}
	
	public void addInclusionRule(GridPane centerInclusionRule) {
		page.addinclusionRule(centerInclusionRule);
	}
	
	public void updateRuleTextArea(TextField tf,ComboBox<String> cb)
	{
		tf.setText(cb.getSelectionModel().getSelectedItem());
		String conditionChosen = cb.getSelectionModel().getSelectedItem();
		switch (conditionChosen)
		{
			case "OR": 			tf.setText("OR");
								tf.setEditable(false);
								return;
			case "Word Break": 	tf.setEditable(false);
								return;
			default: tf.setEditable(true);
					 tf.setText("");
					 return;
		}
				
	}

}
