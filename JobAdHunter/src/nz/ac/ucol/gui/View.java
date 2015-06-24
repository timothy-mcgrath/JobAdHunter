package nz.ac.ucol.gui;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import nz.ac.ucol.configuration.Options;
import nz.ac.ucol.configuration.PluginNotFoundException;
import nz.ac.ucol.configuration.SearchConfiguration;
import nz.ac.ucol.crawler.CrawlManager;
import nz.ac.ucol.plugin.OutPuter;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class View extends Application {
	Controller controller = new Controller(this);
	BorderPane page;
	BorderPane center;
	
	/**
	 * if an argument is present then run that search configuration else display GUI 
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length == 1)
		{
			// run specified searchConfiguration 
			try {
				CrawlManager.performJobCrawl(args[0]);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (PluginNotFoundException e) {
				e.printStackTrace();
			}
		}
		else
		{
			Application.launch(args);
		}
	}
	
	/**
	 * base GUI stuff
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Scene scene = new Scene(buildGUI(),800,400);
        
        primaryStage.setTitle("Job Ad Hunter");
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
		
	}
	
	/**
	 * creates initial content of the application 
	 * @return
	 */
	private Parent buildGUI()
	{
		page = new BorderPane();
		populateOptions();
		populateAreas();
		populateTypes();
		
		// header
		BorderPane header = new BorderPane();
		Label heading = new Label("Job Ad Hunter");
		//header.setAlignment(heading, Pos.CENTER);
		header.setCenter(heading);
		page.setTop(header);
		
		// menu bar
		GridPane menuBar = new GridPane();
		
		Button createJobCrawlBTN = new Button("Create Job Crawl Configuration");
		createJobCrawlBTN.setId("createjobCrawlBTN");
		createJobCrawlBTN.setOnAction((actionEvent) -> controller.showCreateWebCrawl());
		createJobCrawlBTN.setMaxWidth(Double.MAX_VALUE);
		
		Button runJobCrawlBTN = new Button(" run job crawl");
		runJobCrawlBTN.setId("runJobCrawlBTN");
		runJobCrawlBTN.setMaxWidth(Double.MAX_VALUE);
		runJobCrawlBTN.setOnAction((ActionEvent) -> displayRunJobCrawl());
		
		Button schedulejobCrawlBTN = new Button("Schedule Crawl");
		schedulejobCrawlBTN.setId("schedulejobCrawlBTN");
		schedulejobCrawlBTN.setMaxWidth(Double.MAX_VALUE);
		schedulejobCrawlBTN.setOnAction((ActionEvent) -> displayScheduleJobCrawl());
		
		menuBar.add(createJobCrawlBTN, 0, 0);
		menuBar.add(runJobCrawlBTN, 0, 1);
		menuBar.add(schedulejobCrawlBTN,0,2);
		
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
	
	/**
	 * display the create job crawl sub page
	 */
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
		siteToSearch.setId("webSitesLBL");
		TextField siteToAddTF = new TextField();
		siteToAddTF.setId("webSiteToAddTF");
		
		Button addWebSiteBTN = new Button("Add");
		addWebSiteBTN.setId("addWebSiteBTN");
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
		addRankRuleBTN.setId("addRankingRuleBTN");
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
		addInclusionRuleBTN.setId("addRuleBTN");
		bottomInclusionRuleSection.setCenter(addInclusionRuleBTN);
		inclusionRuleSection.setBottom(bottomInclusionRuleSection);
		
		// setup output section
		GridPane outputSection = new GridPane();
		TextField outPutLocationTF = new TextField();
		outPutLocationTF.setId("outputLocationTF");
		ComboBox<String> outPutOptionsCB = new ComboBox<String>();
		outPutOptionsCB.setId("outputCB");
		outPutOptionsCB.getItems().addAll(OutPuter.txtFile);
		outputSection.add(new Label("Out Put type: "), 0, 0);
		outputSection.add(outPutOptionsCB, 1, 0);
		outputSection.add(new Label("Out Put location: "), 0, 1);
		outputSection.add(outPutLocationTF, 1, 1);
		
		// set up confirmation section 
		BorderPane confirmationSection = new BorderPane(); 
		Button createJobSearchBTN = new Button("Create Job Search");
		createJobSearchBTN.setId("createCrawlBTN");
		confirmationSection.setCenter(createJobSearchBTN);
		createJobSearchBTN.setOnAction((Action) -> controller.createJobCrawl(nameFeildTF,siteToSearch,listofRankingRules,listofInclusionRules,outPutOptionsCB,outPutLocationTF));
		
		sections.addRow(0, nameSection);
		sections.addRow(1, webSitesSection);
		sections.addRow(2, rankingRuleSection);
		sections.addRow(3, inclusionRuleSection);
		sections.addRow(4, outputSection);
		sections.addRow(5, confirmationSection);
		
		center.setCenter(sections);

	}	
	
	/**
	 * add all options to instance listofOptions variable
	 */
	private void populateOptions()
	{
		listOfOptions.add(RegularExpressionMaker.OR);
		listOfOptions.add(RegularExpressionMaker.ANY);
		listOfOptions.add(RegularExpressionMaker.WORDBREAK);
		listOfOptions.add(RegularExpressionMaker.CONTAINSCS);
		listOfOptions.add(RegularExpressionMaker.CONTAINSNS);
		listOfOptions.add(RegularExpressionMaker.NOTCONTAINSNS);
		listOfOptions.add("Delete");
	}
	
	/**
	 * add all types to instance information
	 */
	private void populateTypes()
	{
		listOfAreas.add(Options.anyWhere);
		listOfAreas.add(Options.Description);
		listOfAreas.add(Options.title);
	}
	
	/**
	 * add all area information to instance information 
	 */
	private void populateAreas()
	{
		listOfTypes.add(Options.musthave);
		listOfTypes.add(Options.mustNotHave);
	}
	
	private ArrayList<Object[]> listofRankingRules = new ArrayList<>();
	private ArrayList<String> listOfOptions = new ArrayList<>();
	private ArrayList<String> listOfAreas = new ArrayList<>();
	private ArrayList<String> listOfTypes = new ArrayList<>();
	private int numberOfRankRules = 0;
	
	/**
	 * adds a rank rule to the provided GridPane
	 * @param areaToAddTo
	 */
	public void addRankRule(GridPane areaToAddTo)
	{
		BorderPane rowPane = new BorderPane();
		GridPane rankRulePane = new GridPane();
		rankRulePane.addRow(0, rowPane);
		
		TextField weightTF = new TextField(); 
		weightTF.setPrefWidth(20);
		weightTF.setId("rankingRule1WeightTF");
		BorderPane weightAndLabel = new BorderPane();
		weightAndLabel.setTop(new Label("Weight"));
		weightAndLabel.setBottom(weightTF);
		rowPane.setLeft(weightAndLabel);
		
		GridPane options = new GridPane();
		options.setAlignment(Pos.CENTER);
		ComboBox<String> areaCB = new ComboBox<String>();
		areaCB.getItems().addAll(listOfAreas);
		areaCB.setId("areaCB");
		options.add(new Label(" Rule area: "), 0, 0);
		options.add(areaCB, 1, 0);
		rowPane.setTop(options);
		
		ArrayList<Node> topRow = new ArrayList<Node>();
		ArrayList<Node> bottomRow = new ArrayList<Node>();
		listofRankingRules.add(new Object[]{topRow,bottomRow,weightTF,areaCB});

		addRule(rowPane, topRow, bottomRow,true);
		
		rankRulePane.setAlignment(Pos.CENTER);
		areaToAddTo.addRow(numberOfRankRules + 1, rankRulePane);
		numberOfRankRules++;
	}
	
	ArrayList<Object[]> listofInclusionRules = new ArrayList<>();
	private int numberOfInclusionRules = 0;
	
	/**
	 * adds a inclusion rule to the provided GridPane
	 * @param areaToAddTo
	 */
	public void addinclusionRule(GridPane areaToAddTo)
	{
		BorderPane rowPane = new BorderPane();
		GridPane inclusionRulePane = new GridPane();
		inclusionRulePane.addRow(0, rowPane);
		
		GridPane options = new GridPane();
		options.setAlignment(Pos.CENTER);
		ComboBox<String> areaCB = new ComboBox<String>();
		ComboBox<String> typeCB = new ComboBox<String>(); 
		areaCB.getItems().addAll(listOfAreas);
		typeCB.getItems().addAll(listOfTypes);
		areaCB.setId("areaCB");
		typeCB.setId("typeCB");
		options.add(new Label("Rule type: "), 0, 0);
		options.add(typeCB, 1, 0);
		options.add(new Label(" Rule area: "), 2, 0);
		options.add(areaCB, 3, 0);
		rowPane.setTop(options);
		
		ArrayList<Node> topRow = new ArrayList<Node>();
		ArrayList<Node> bottomRow = new ArrayList<Node>();
		listofInclusionRules.add(new Object[]{topRow,bottomRow,areaCB,typeCB});
		
		addRule(rowPane, topRow, bottomRow,false);
		
		inclusionRulePane.setAlignment(Pos.CENTER);
		areaToAddTo.addRow(numberOfInclusionRules + 1, inclusionRulePane);
		numberOfInclusionRules++;
	}
	
	/**
	 * creates a generic rule in the rowPane provided stores GUI components in appropriate arrayList
	 * @param rowPane
	 * @param topRow
	 * @param bottomRow
	 * @param rankRule
	 */
	public void addRule(BorderPane rowPane, ArrayList<Node> topRow, ArrayList<Node> bottomRow,Boolean rankRule)
	{
	
		BorderPane content = new BorderPane();
		int index = 1; // index of ComboBox and TextFeild for use with ids 
		
		ComboBox<String> conditionCB = new ComboBox<String>();	
		TextField conditionTF = new TextField();
		if(rankRule)
		{
			conditionCB.setId("condtion1RWCB");
			conditionTF.setId("condtion1RWTF");
		}
		else	
		{
			conditionCB.setId("condtion1IRCB");
			conditionTF.setId("condtion1IRTF");
		}
		conditionCB.getItems().addAll(listOfOptions);
		
		
		//content.setCenter(conditionRRCB);
		topRow.add(conditionCB);
		bottomRow.add(conditionTF);
		rowPane.setCenter(content);
		//bottomRowPane.setCenter(contentLower);
		
		// upper line
		BorderPane ruleArea = new BorderPane();
		BorderPane ruleAreaInner = new BorderPane();
		BorderPane leftInner = new BorderPane();
		BorderPane rightInner = new BorderPane();
		ruleArea.setLeft(leftInner);
		
		conditionCB.setOnAction((ActionEvent) -> handleRuleChange(ruleAreaInner,conditionTF,conditionCB,topRow,bottomRow));
		
		ruleAreaInner.setTop(conditionCB);
		ruleAreaInner.setBottom(conditionTF);
		ruleArea.setCenter(ruleAreaInner);
		
		ruleArea.setRight(rightInner);
		content.setCenter(ruleArea);
		
		Button addAfterCondition = new Button("+");
		addAfterCondition.setOnAction((ActionEvent) -> addCondtion(RIGHT,rightInner,addAfterCondition,topRow,bottomRow,rankRule,index + 1));
		content.setRight(addAfterCondition);
		
		Button addBeforeCondition = new Button("+");
		addBeforeCondition.setOnAction((ActionEvent) -> addCondtion(LEFT,leftInner,addBeforeCondition,topRow,bottomRow,rankRule,index - 1));
		content.setLeft(addBeforeCondition);
		
		if(rankRule)
		{
			addAfterCondition.setId("addPostConditionRWBTN");
			addBeforeCondition.setId("addPreviousConditionRWBTN");
		}
		else
		{
			addAfterCondition.setId("addPreviousConditionIRBTN");
			addBeforeCondition.setId("addPostConditionIRBTN");
		}
	}
	
	private final int LEFT = 1;
	private final int RIGHT = 2;
	
	/**
	 * method to add Combo Box, text field pair to either start or end of a rule
	 * new GUI components are add to arrayLists based on direction and button 
	 * event is update to reference newly created layout
	 * 
	 * @param direction int instance variable used to tell if going left or right
	 * @param row row to add new content to 
	 * @param adder Button that triggered this event and to have event updated 
	 * @param topRow  row of ComboBoxes
	 * @param bottomRow row of text Fields 
	 * @param rankRule boolean true for rank rule false other wise
	 * @param index for use with id's
	 */
	private void addCondtion(int direction, BorderPane row,Button adder,ArrayList<Node> topRow,ArrayList<Node> bottomRow, Boolean rankRule,int index)
	{		
		BorderPane inner = new BorderPane();
		BorderPane innerCenter = new BorderPane();
		ComboBox<String> cb = new ComboBox<>();
		TextField tf = new TextField();
		if(rankRule)
		{
			cb.setId("condtion" + index + "RWCB");
			tf.setId("condtion" + index + "RWTF");
		}
		else	
		{
			cb.setId("condtion" + index + "IRCB");
			tf.setId("condtion" + index + "IRTF");
		}
		
		
		cb.getItems().addAll(listOfOptions);
		
		innerCenter.setTop(cb);
		innerCenter.setBottom(tf);
		inner.setCenter(innerCenter);
		
		if(direction == RIGHT)
		{
			topRow.add(cb);
			bottomRow.add(tf);
			BorderPane rightInner = new BorderPane();
			inner.setRight(rightInner);
			adder.setOnAction((ActionEvent) -> addCondtion(direction,rightInner,adder,topRow,bottomRow,rankRule,index + 1));
		}
		else			
		{
			topRow.add(0,cb);
			bottomRow.add(0,tf);
			BorderPane leftInner = new BorderPane();
			inner.setLeft(leftInner);
			adder.setOnAction((ActionEvent) -> addCondtion(direction,leftInner,adder,topRow,bottomRow,rankRule,index - 1));
		}
		cb.setOnAction((ActionEvent) -> handleRuleChange(innerCenter,tf,cb,topRow,bottomRow));
		row.setCenter(inner);
	}
	
	/**
	 * called when a condition ComboBox selection is changed if delete is selected 
	 * components are removed otherwise components are passed onto controller
	 * @param area
	 * @param tf
	 * @param cb
	 * @param topRow
	 * @param bottomRow
	 */
	private void handleRuleChange(BorderPane area, TextField tf, ComboBox<String> cb, ArrayList<Node> topRow, ArrayList<Node> bottomRow)
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
	
	/**
	 * display the run job crawl sub page
	 */
	public void displayRunJobCrawl()
	{
		ArrayList<SearchConfiguration> avaiablejobCrawls = controller.getSearchConfigurations();
		GridPane jobCrawlsListing = new GridPane();
		int i = 0;
		for(SearchConfiguration sc:avaiablejobCrawls)
		{
			GridPane row = new GridPane();
			row.setHgap(10);
			row.setPadding(new Insets(5));
			
			String schedualedFor;
			if(sc.getTimeofNextCrawl() == null)
			{
				schedualedFor = "unschedualed";			
			}
			else
			{
				schedualedFor = sc.getTimeofNextCrawl().toString();
			}
			row.add(new Label("Schedualed for :" + schedualedFor),0, 0);
			row.add(new Label(sc.getName()),1, 0);
			Button runJobCrawlBTN = new Button("Run Search Now");
			runJobCrawlBTN.setId("run" + sc.getName() +"WebCrawl");
			runJobCrawlBTN.setOnAction((ActionEvent) -> controller.runWebCrawlnow(sc)); 
			row.add(runJobCrawlBTN,2, 0);
			jobCrawlsListing.addColumn(i, row);
		}
		
		center.setCenter(jobCrawlsListing);
	}
	
	/**
	 * display the schedule job crawl sub page
	 */
	public void displayScheduleJobCrawl()
	{
		ArrayList<SearchConfiguration> avaiablejobCrawls = controller.getSearchConfigurations();
		GridPane jobCrawlsListing = new GridPane();
		int i = 0;
		for(SearchConfiguration searchConfig:avaiablejobCrawls)
		{
			GridPane row = new GridPane();
			row.setHgap(10);
			row.setPadding(new Insets(5));
			
			String schedualedFor;
			if(searchConfig.getTimeofNextCrawl() == null)
			{
				schedualedFor = "unschedualed";			
			}
			else
			{
				schedualedFor = new SimpleDateFormat("HH:mm").format(searchConfig.getTimeofNextCrawl());
			}
			row.add(new Label("Schedualed for :"),0, 0);
			TextField schedualedForTF = new TextField(schedualedFor);
			row.add(schedualedForTF,1, 0);
			row.add(new Label("Schedualed to repeat :"),2, 0);
			ComboBox<String> schedualedToRepeatCB = new ComboBox<String>(); 
			schedualedToRepeatCB.getItems().addAll(new String[]{"DAILY"});
			schedualedToRepeatCB.setValue(searchConfig.getScheduledToRepeat());
			row.add(schedualedToRepeatCB,3, 0);
			
			Button updateScheduleBTN = new Button("Update Scedule");
			updateScheduleBTN.setId("updateScheduleBTN");
			updateScheduleBTN.setOnAction((ActionEvent) -> controller.updateSchedule(schedualedForTF.getText(),schedualedToRepeatCB.getSelectionModel().getSelectedItem() ,searchConfig));
			row.add(updateScheduleBTN,4, 0);
			
			Button deScheduleBTN = new Button("UnScedule crawl");
			deScheduleBTN.setId("deScheduleBTN");
			deScheduleBTN.setOnAction((ActionEvent) -> controller.deSchedule(searchConfig));
			row.add(deScheduleBTN,5, 0);
			
			jobCrawlsListing.addColumn(i, row);
		}
		
		center.setCenter(jobCrawlsListing);
	}
	
	/**
	 * display message in center window
	 * @param message
	 */
	public void displayMesaage(String message)
	{
		center.setCenter(new Label(message));
	}
	
	/**
	 * method to allow testing via testFX
	 * @return
	 */
	public static Parent getRootNodeForTesting()
	{
		BorderPane testThing = new BorderPane();
		testThing.setCenter(new View().buildGUI());
		testThing.setMinWidth(1000);
		testThing.setMinHeight(1000);
		return testThing;
	}

}
