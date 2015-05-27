package nz.ac.ucol.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.sun.javafx.collections.transformation.SortedList;

import nz.ac.ucol.configuration.SearchConfiguration;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class View extends Application {
	Controller controller = new Controller(this);
	BorderPane page;
	BorderPane center;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Scene scene = new Scene(buildGUI(),800,400);
        
        primaryStage.setTitle("Job Ad Hunter");
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
		
	}
	
	private Parent buildGUI()
	{
		page = new BorderPane();
		populateOptions();
		
		// header
		BorderPane header = new BorderPane();
		Label heading = new Label("Job Ad Hunter");
		//header.setAlignment(heading, Pos.CENTER);
		header.setCenter(heading);
		page.setTop(header);
		
		// menu bar
		GridPane menuBar = new GridPane();
		
		Button createJobCrawlBTN = new Button("Create Job Crawl Configuration");
		createJobCrawlBTN.setId("createjobCrawl");
		createJobCrawlBTN.setOnAction((actionEvent) -> controller.showCreateWebCrawl());
		createJobCrawlBTN.setMaxWidth(Double.MAX_VALUE);
		
		Button runJobCrawlBTN = new Button(" run job crawl");
		runJobCrawlBTN.setId("runJobCrawlBTN");
		runJobCrawlBTN.setMaxWidth(Double.MAX_VALUE);
		
		
		menuBar.add(createJobCrawlBTN, 0, 0);
		menuBar.add(runJobCrawlBTN, 0, 1);
		
		menuBar.setVgap(10);
		menuBar.setPadding(new Insets(5));
		page.setLeft(menuBar);
		
		// center
		BorderPane centerPage = new BorderPane();
		center = new BorderPane();
		centerPage.setCenter(center);
		centerPage.setStyle("-fx-background-color: white;");
		page.setCenter(centerPage);
		
		// footer
		BorderPane footer = new BorderPane();
		Label footerMessage = new Label("by timothy mcgrath");
		footer.setLeft(footerMessage);
		page.setBottom(footer);
		
		return page;
	}
	
	public void displayCreateJobCrawl()
	{
		GridPane sections = new GridPane();
		sections.setId("jobCrawlCreationPagePNL");
		
		// name section
		GridPane nameSection = new GridPane();
		TextField nameFeildTF = new TextField();
		nameFeildTF.setId("nameFeildTF");
		
		nameSection.addRow(0, new Label("Crawl Name : "));
		nameSection.addRow(1, nameFeildTF);
		
		// web sites to search section
		GridPane webSitesSection = new GridPane();
		
		Label siteToSearch = new Label("");		
		TextField siteToAddTF = new TextField();
		
		Button addWebSiteBTN = new Button("Add");
		addWebSiteBTN.setOnAction((ActionEvent)-> controller.addWebSite(siteToAddTF.getText(),siteToSearch));
		
		webSitesSection.add(new Label("Web Sites: "), 0, 0);
		webSitesSection.add(siteToSearch, 1, 0);
		webSitesSection.add(addWebSiteBTN, 0, 1);
		webSitesSection.add(siteToAddTF, 1, 1);
		
		// set up ranking rule section
		BorderPane rankingRuleSection = new BorderPane();
		
		BorderPane top = new BorderPane();
		top.setCenter(new Label("ranking Rules"));
		rankingRuleSection.setTop(top);
		
		GridPane centerRankRule = new GridPane();		
		rankingRuleSection.setCenter(centerRankRule);
		
		BorderPane bottomRankRuleSection = new BorderPane();
		Button addRankRuleBTN = new Button("Add Rank Rule");
		addRankRuleBTN.setOnAction((ActionEvent) -> controller.addRankingRule(centerRankRule));
		bottomRankRuleSection.setCenter(addRankRuleBTN);
		rankingRuleSection.setBottom(bottomRankRuleSection);
		
		// set up inclusion rules section 
		BorderPane inclusionRuleSection = new BorderPane();
		BorderPane topInclusion = new BorderPane();
		topInclusion.setCenter(new Label("Inclusion Rules"));
		inclusionRuleSection.setTop(topInclusion);
		
		GridPane centerInclusionRule = new GridPane();		
		inclusionRuleSection.setCenter(centerInclusionRule);
		
		BorderPane bottomInclusionRuleSection = new BorderPane();
		Button addInclusionRuleBTN = new Button("Add Inclusion Rule");
		addInclusionRuleBTN.setOnAction((ActionEvent) -> controller.addInclusionRule(centerInclusionRule));
		bottomInclusionRuleSection.setCenter(addInclusionRuleBTN);
		inclusionRuleSection.setBottom(bottomInclusionRuleSection);
		
		// setup output section
		GridPane outputSection = new GridPane();
		TextField outPutLocationTF = new TextField();
		ComboBox<String> outPutOptionsCB = new ComboBox<String>();
		outPutOptionsCB.getItems().addAll("note pad File","email");
		outputSection.add(new Label("Out Put type: "), 0, 0);
		outputSection.add(outPutOptionsCB, 1, 0);
		outputSection.add(new Label("Out Put location: "), 0, 1);
		outputSection.add(outPutLocationTF, 1, 1);
		
		// set up confirmation section 
		BorderPane confirmationSection = new BorderPane(); 
		Button createJobSearchBTN = new Button("Create Job Search");
		confirmationSection.setCenter(createJobSearchBTN);
		
		sections.addRow(0, nameSection);
		sections.addRow(1, webSitesSection);
		sections.addRow(2, rankingRuleSection);
		sections.addRow(3, inclusionRuleSection);
		sections.addRow(4, outputSection);
		sections.addRow(5, confirmationSection);
		
		center.setCenter(sections);
	}
	
	// for checkign tha vlues can be read 
	public void readValues()
	{
		for(Object[] ruleFeilds:listofRankingRules)
		{
			ArrayList<Node> topRow = (ArrayList<Node>)ruleFeilds[0];
			ArrayList<Node> bottomRow = (ArrayList<Node>)ruleFeilds[1];
			String topValues = "";
			String bottomValues = "";
			for(int i = 0; i < topRow.size();i++)
			{
				
			}
		}
			
		ArrayList<Node> topRow = (ArrayList<Node>) listofRankingRules.get(0)[0];
		for(int i = 0; i < topRow.size();i++)
		{
			System.out.println(((ComboBox)topRow.get(i)).getSelectionModel().getSelectedItem());
		}
	}
	
	private void populateOptions()
	{
		listOfOptions.add("OR");
		listOfOptions.add("Word Break");
		listOfOptions.add("Contains cs");
		listOfOptions.add("Contains ns");
		listOfOptions.add("does not contain cs");
		listOfOptions.add("Does not contain ns");
		listOfOptions.add("Delete");
	}
	
	ArrayList<Object[]> listofRankingRules = new ArrayList<>();
	ArrayList<String> listOfOptions = new ArrayList<>();
	private int numberOfRankRules = 0;
	
	public void addRankRule(GridPane areaToAddTo)
	{
		BorderPane rowPane = new BorderPane();
		GridPane rankRulePane = new GridPane();
		rankRulePane.addRow(0, rowPane);
		
		ArrayList<Node> topRow = new ArrayList<Node>();
		ArrayList<Node> bottomRow = new ArrayList<Node>();
		listofRankingRules.add(new Object[]{topRow,bottomRow});
		TextField weightTF = new TextField(); 
		weightTF.setPrefWidth(20);
		
		BorderPane weightAndLabel = new BorderPane();
		weightAndLabel.setTop(new Label("Weight"));
		weightAndLabel.setBottom(weightTF);
		rowPane.setLeft(weightAndLabel);
		
		addRule(rowPane, topRow, bottomRow);
		
		System.out.println(areaToAddTo.getRowConstraints().size());
		
		areaToAddTo.addRow(numberOfRankRules + 1, rankRulePane);
		numberOfRankRules++;
	}
	
	ArrayList<Object[]> listofInclusionRules = new ArrayList<>();
	private int numberOfInclusionRules = 0;
	
	public void addinclusionRule(GridPane areaToAddTo)
	{
		BorderPane rowPane = new BorderPane();
		GridPane inclusionRulePane = new GridPane();
		inclusionRulePane.addRow(0, rowPane);
		
		ArrayList<Node> topRow = new ArrayList<Node>();
		ArrayList<Node> bottomRow = new ArrayList<Node>();
		listofInclusionRules.add(new Object[]{topRow,bottomRow});
		
		addRule(rowPane, topRow, bottomRow);
		
		areaToAddTo.addRow(numberOfInclusionRules + 1, inclusionRulePane);
		numberOfInclusionRules++;
	}
	
	public void addRule(BorderPane rowPane, ArrayList<Node> topRow, ArrayList<Node> bottomRow)
	{
	
		BorderPane content = new BorderPane();
		//BorderPane contentLower = new BorderPane();
		
		ComboBox<String> conditionRRCB = new ComboBox<String>();	
		TextField conditionRRTF = new TextField();
		conditionRRCB.getItems().addAll(listOfOptions);
		
		
		//content.setCenter(conditionRRCB);
		topRow.add(conditionRRCB);
		bottomRow.add(conditionRRTF);
		rowPane.setCenter(content);
		//bottomRowPane.setCenter(contentLower);
		
		// upper line
		BorderPane ruleArea = new BorderPane();
		BorderPane ruleAreaInner = new BorderPane();
		BorderPane leftInner = new BorderPane();
		BorderPane rightInner = new BorderPane();
		ruleArea.setLeft(leftInner);
		
		conditionRRCB.setOnAction((ActionEvent) -> handleRuleChange(true,ruleAreaInner,conditionRRTF,conditionRRCB,topRow,bottomRow));
		
		ruleAreaInner.setTop(conditionRRCB);
		ruleAreaInner.setBottom(conditionRRTF);
		ruleArea.setCenter(ruleAreaInner);
		
		ruleArea.setRight(rightInner);
		content.setCenter(ruleArea);
		
		Button addAfterCondition = new Button("+");
		addAfterCondition.setOnAction((ActionEvent) -> addCondtion(RIGHT,rightInner,addAfterCondition,topRow,bottomRow));
		content.setRight(addAfterCondition);
		
		Button addBeforeCondition = new Button("+");
		addBeforeCondition.setOnAction((ActionEvent) -> addCondtion(LEFT,leftInner,addBeforeCondition,topRow,bottomRow));
		content.setLeft(addBeforeCondition);
	}
	
	private final int LEFT = 1;
	private final int RIGHT = 2;
	
	private void addCondtion(int direction, BorderPane row,Button adder,ArrayList<Node> topRow,ArrayList<Node> bottomRow)
	{		
		BorderPane inner = new BorderPane();
		BorderPane innerCenter = new BorderPane();
		ComboBox<String> cb = new ComboBox<>();
		TextField tf = new TextField();
		cb.getItems().addAll(listOfOptions);
		
		innerCenter.setTop(cb);
		innerCenter.setBottom(tf);
		inner.setCenter(innerCenter);
		topRow.add(cb);
		bottomRow.add(tf);
		
		if(direction == RIGHT)
		{

			BorderPane rightInner = new BorderPane();
			inner.setRight(rightInner);
			adder.setOnAction((ActionEvent) -> addCondtion(direction,rightInner,adder,topRow,bottomRow));
		}
		else			
		{
			BorderPane leftInner = new BorderPane();
			inner.setLeft(leftInner);
			adder.setOnAction((ActionEvent) -> addCondtion(direction,leftInner,adder,topRow,bottomRow));
		}
		cb.setOnAction((ActionEvent) -> handleRuleChange(true,innerCenter,tf,cb,topRow,bottomRow));
		row.setCenter(inner);
	}
	
	private void handleRuleChange(Boolean rankRule,BorderPane area, TextField tf, ComboBox cb, ArrayList<Node> topRow, ArrayList<Node> bottomRow)
	{
		if(cb.getSelectionModel().getSelectedItem() == "Delete")
		{
			area.setTop(null);
			area.setBottom(null);
			topRow.remove(cb);
			bottomRow.remove(tf);
		}
		else
		{
			controller.updateRuleTextArea(tf, cb);
		}
	}
	
	public void displayRunJobCrawl(ArrayList<SearchConfiguration> avaiablejobCrawls)
	{
		GridPane jobCrawlsListing = new GridPane();
		int i = 0;
		for(SearchConfiguration sc:avaiablejobCrawls)
		{
			GridPane row = new GridPane();
			row.addRow(0, new Label("Schedualed for :" + sc.getTimeofNextCrawl()));
			row.addRow(1, new Label(sc.getName()));
			Button runJobCrawlBTN = new Button("Run Search Now");
			runJobCrawlBTN.setOnAction((ActionEvent) -> controller.runWebCrawlnow(sc)); 
			row.addRow(2, runJobCrawlBTN);
			jobCrawlsListing.addColumn(i, row);
		}
		
		center.setCenter(jobCrawlsListing);
	}
	
	public void displayMesaage(String message)
	{
		center.setCenter(new Label(message));
	}
	
	public static Parent getRootNodeForTesting()
	{
		return new View().buildGUI();
	}

}
