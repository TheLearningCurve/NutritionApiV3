package com.kandbnutrition.controller;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import com.kandbnutrition.model.DetailData;
import com.kandbnutrition.resource.ErrorMessages;
import com.kandbnutrition.resource.StringValues;
import com.kandbnutrition.service.QueryVariables;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;


public class SearchListFrameController extends AnchorPane implements Initializable {
	
	@FXML
	AnchorPane leftPanel;
	
	@FXML
	ListView<DetailData> listView;
	
	@FXML
	Label resultLabel, buttonLabel, ErrorMessageLabel;
	
	@FXML
	ImageView progressIndicatorImageView;
	
	private MainFrameController	mainFrameController;
	private SearchFieldController searchFieldController;
	private NutritionLabelFrameController nutritionLabelFrameController;
	
	private StringValues stringValues;
	private ErrorMessages errorMessages;
	
	public ObservableList<DetailData> detailList;

	public static SearchListFrameController controller;	
	
	public SearchListFrameController(NutritionLabelFrameController nutritionLabelFrameController, MainFrameController mainFrameController) {
		
		detailList = FXCollections.observableArrayList();
		errorMessages = new ErrorMessages();
		stringValues = new StringValues();
		
		this.mainFrameController = mainFrameController;
		this.nutritionLabelFrameController = nutritionLabelFrameController;
								
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(stringValues.getSearchListFxml()));
		fxmlLoader.setController(this);
		fxmlLoader.setRoot(this);
		controller = (SearchListFrameController) fxmlLoader.getController();
		
		try {
			fxmlLoader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}	

		this.nutritionLabelFrameController.setSearchListFrameController(this);
	}
	
	public void setSearchFieldController(SearchFieldController searchFieldController) {
		this.searchFieldController = searchFieldController;
	}
	
	public void createListItem(String itemName, String brandName, String nutrientName, Float nValue, String nUoM, Float sQty, String sUoM, String id, String thumbI) {	  		    
		
		detailList.add(new DetailData(itemName, brandName, nutrientName, nValue, nUoM, sQty, sUoM, id, thumbI));
	}
	
	/*
	 * Once the data is completed from the call we can call this method to display the list.
	 * 
	 * We switched to using the JavaFX listView which as the recycler built in when using the cellFactory.
	 */
	
	public void displayListView() {
		
		
		listView.setItems(detailList);
	    setListViewVisible();   
		listView.setCellFactory(new Callback<ListView<DetailData>, ListCell<DetailData>>() {
			
			@Override
			public ListCell<DetailData> call(ListView<DetailData> param) {
				return new CustomSearchCell();
			}
		});
		
		/*
		 * This was the only way I could find to access the scroll bar for the listView. 
		 * onScroll has never worked for some strange reason. 
		 * 
		 * We listen for the scroll value to hit the bottom and make the next call for search (offset) 
		 */
		
		for (Node node: listView.lookupAll(".scroll-bar")) {
		      if (node instanceof ScrollBar) {
		        final ScrollBar bar = (ScrollBar) node;
		        bar.valueProperty().addListener(new ChangeListener<Number>() {
		          @Override public void changed(ObservableValue<? extends Number> value, Number oldValue, Number newValue) {
		        	  		        	  
		        	  /*
		        	   * Once the scroll bar reaches the end we make the call to get the next set of items
		        	   */
		        	  if(newValue.doubleValue() == 1.0 ) {
		        		  QueryVariables.setOffset(10);
						  searchFieldController.requestSearchData();
		        	  }
		          }
		        });
		      }
		}
	}
		
	public void setListViewVisible() {
		listView.setVisible(true);
		setprogressIndicatorImageView_NotVisible();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {}
	
	public void getResultLabel(int total, String searchTerm) {
		
		new JFXPanel();
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				DecimalFormat df = new DecimalFormat("#,###");

					resultLabel.setText(String.valueOf(df.format(total)) + " Results for " + searchTerm);
					resultLabel.setVisible(true);
			}
		});
	}
	
	public void setprogressIndicatorImageViewVisible() {
		progressIndicatorImageView.setVisible(true);
	}
	
	public void setprogressIndicatorImageView_NotVisible() {
		progressIndicatorImageView.setVisible(false);
	}
	
	public void setErrorMessageLabel(String error) {
		ErrorMessageLabel.setText(error);
	}
	
	public void setErrorMessageUI_NotVisible() {
		ErrorMessageLabel.setVisible(false);
	}

	public void updateErrorMessageUI(String errorMessage) {
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				
				if(errorMessage == "NETWORK") {
					resultLabel.setVisible(false);
					ErrorMessageLabel.setText(errorMessage + errorMessages.getNetworkError());
					ErrorMessageLabel.setVisible(true);
					setprogressIndicatorImageView_NotVisible();
				}					
			}
		});
	}		
}
