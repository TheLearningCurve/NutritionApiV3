package com.kandbnutrition.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.kandbnutrition.handler.SceneManager;
import com.kandbnutrition.model.*;
import com.kandbnutrition.resource.StringValues;
import com.kandbnutrition.service.*;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SearchFieldController extends AnchorPane implements Initializable {
	
	@FXML
	TextField searchField, searchFieldErrorMessgae;	
	
	@FXML
	ListView<String> listView;
	
	@FXML
	ImageView imageButton;
	
	@FXML
	HBox HBoxContainerForListView;
	
	public ObservableList<String> typeaHeadtext;
	public Adapter adapter;
    public String searchFieldText;
    public StringValues stringValues;
    public SceneManager sm;
    public int buttonPress = 0;

 
    public static SearchFieldController controller;
    public SearchListFrameController listController;
    public MainFrameController mainFrameController;
    
	
	public SearchFieldController(SearchListFrameController listController, MainFrameController mainFrameController) {
		
		stringValues = new StringValues();
		adapter = new Adapter();
		typeaHeadtext = FXCollections.observableArrayList();
		sm = SceneManager.INSTANCE;
		searchFieldText = "Empty String";
		this.listController = listController;
		this.mainFrameController = mainFrameController;
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(stringValues.getSearchFieldFxml()));
		fxmlLoader.setController(this);
		fxmlLoader.setRoot(this);
		controller = (SearchFieldController) fxmlLoader.getController();
		
		try {
			fxmlLoader.load();
			this.listController.setSearchFieldController(this);
		}
		
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	
		
		
		/*This is the Key Listener for the search field*/
		searchField.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {	
				
				if(event.getCode() != KeyCode.ENTER) {		
					
					if(searchField.getText().length() != 0 && !searchField.getText().trim().isEmpty()) { // If the text is at least on Char long we want to search for type a head
						QueryVariables.setText(searchField.getText());
						requestTypeAHeadData();
						searchFieldErrorMessgae.setVisible(false);			
					}
					else if(searchField.getText().length() == 0) { // If the text in the search field is empty we do not want the listView to display
						setListViewVisibleFalse(); 
					}
				}
				else if(event.getCode() == KeyCode.ENTER) {
					
					if(searchField.getText().length() != 0 && !searchField.getText().trim().isEmpty()) { // If the text is at least on Char long we want to search for type a head
						searchingLogicForSearchField();
					    // If the enter key is pressed we want to search for Item Results
					}
					else {
						searchFieldErrorMessgae.setVisible(true);
					}
				}
		    } 
	    });	
	  }

	@FXML 
	public void searchMouseListener(MouseEvent event)
	{

		if(event.getSource().equals(listView))
		{
			QueryVariables.setSearchTerm(listView.getSelectionModel().getSelectedItem());
			searchField.setText(listView.getSelectionModel().getSelectedItem());
			
			if(listController.listView.getItems().isEmpty() && searchField.getText().length() != 0 && !searchField.getText().trim().isEmpty())
			{
				FirstSearchCall();
			}
			else if(!listController.listView.getItems().isEmpty() && 
					listView.getSelectionModel().getSelectedItem().compareTo(searchFieldText) != 0)
			{
				ClearListSearchCall();
			}
			else if(listView.getSelectionModel().getSelectedItem().compareTo(searchFieldText) == 0)
			{
				setListViewVisibleFalse();
			}
		}
		else if(event.getSource().equals(imageButton))
		{
	       searchingLogicForSearchField();
		}	
	}
	
	public void searchingLogicForSearchField()
	{
		QueryVariables.setSearchTerm(searchField.getText());
		
		if(listController.listView.getItems().isEmpty() && searchField.getText().length() != 0 && !searchField.getText().trim().isEmpty())
		{
			FirstSearchCall();
		}
		else if(!listController.listView.getItems().isEmpty() && searchField.getText().compareTo(searchFieldText) != 0
				&& searchField.getText().length() != 0 && !searchField.getText().trim().isEmpty())
		{
			ClearListSearchCall();

		}
		else if(searchField.getText().compareTo(searchFieldText) == 0)
		{
			setListViewVisibleFalse();
		}	
		else
		{
			searchFieldErrorMessgae.setVisible(true);
		}
	}
	
	public void FirstSearchCall()
	{
		listController.setErrorMessageVisible(false);
		requestSearchData();
		rememberTextField(searchField.getText());	
		mainFrameController.setLargeLogoImage(false);
		listController.setprogressIndicatorVisible(true);
		setListViewVisibleFalse();
	}
	
	public void ClearListSearchCall()
	{
		QueryVariables.clearOffset();
		listController.setErrorMessageVisible(false);
		listController.listView.getItems().clear();
		requestSearchData();
		setListViewVisibleFalse();
		rememberTextField(searchField.getText());
		mainFrameController.setLargeLogoImage(false);
		listController.setprogressIndicatorVisible(true);
	}
	
	protected void rememberTextField(String string) {
		
		searchFieldText = string;
	}

	public void requestTypeAHeadData()
	{
		adapter.getapicalls.typeAhead(QueryVariables.text, new Callback<List<TypeAHead>>() {
		
			@Override
			public void success(List<TypeAHead> typeAhead, Response response) 
			{
				
				ObservableList<String> typeAheadText = FXCollections.observableArrayList();
				
				if(!searchField.getText().isEmpty())
				{
					
					if(!typeAhead.isEmpty())
					{
						for(TypeAHead h : typeAhead)
						{
							typeAheadText.add(h.text);
						}	
						
						settypeaHeadtext(typeAheadText);
						setListViewVisibleUITrue(); // Display the ListView
					}
					
					if(typeAhead.isEmpty())
					{
						settypeaHeadtext(typeAheadText);
						setListViewVisibleUITrue(); // Display the ListView
					}
				}
			}
			
			@Override
			public void failure(RetrofitError retrofitError) 
			{
				
			}
		
		});
	}
	
	public void requestSearchData()
	{				
		adapter.getapicalls.searchFoodLimitAndOffset(QueryVariables.searchTerm, 50, QueryVariables.offset, new Callback<SearchData>() {
						
			@Override
			public void success(final SearchData searchData, Response response) 
			{				
				listController.getResultLabel(searchData.total,QueryVariables.searchTerm);
				
				if(!searchData.results.isEmpty())
				{
					createList(searchData);	
				}
				else 
				{
					listController.setprogressIndicatorVisible(false);

				}

				
				setListViewVisibleFalse();
			}
			
			@Override
			public void failure(RetrofitError retrofitError) 
			{
				if(retrofitError.getKind().name() == "NETWORK")
				{
					listController.updateErrorMessageUI(retrofitError.getKind().name());
				}
									
			}
		});		
	}
	
	protected void setListViewVisibleUITrue() {
		
		new JFXPanel();
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				
				if(gettypeaHeadtext() != null && !gettypeaHeadtext().isEmpty())
				{
					HBoxContainerForListView.setPrefHeight(140);
					listView.setVisible(true);
					listView.setItems(gettypeaHeadtext());
					listView.scrollTo(0);
				}			
			}
		});
	}
	
	public void setListViewVisibleFalse() {
		HBoxContainerForListView.setPrefHeight(40);
		listView.setVisible(false);
		searchField.clear();
	}
	
	public ObservableList<String> gettypeaHeadtext() {
		return typeaHeadtext;	
	}
	
	public void settypeaHeadtext(ObservableList<String> typeaHeadtext) {
		
		if(this.typeaHeadtext.equals(typeaHeadtext))
    	{
			this.typeaHeadtext = typeaHeadtext;
    	}
		else if(typeaHeadtext.isEmpty())
		{
			this.typeaHeadtext = typeaHeadtext;
		}
    	else 
    	{
    		this.typeaHeadtext = typeaHeadtext;
    	}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1){}

	private void createList(final SearchData searchData) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				
				for(Results results : searchData.results )
				{			
					listController.createListItem(results.itemName, results.brandName, results.nutruentName,
							results.nutrientValue, results.nutrientUom, results.servingQty, results.servingUom, results.resourceId, results.thumbnail);
				}	
				
				listController.displayListView();
			}
		});
	}

	public void reset() {
		listView.getItems().clear();
		searchField.clear();
	}
}
