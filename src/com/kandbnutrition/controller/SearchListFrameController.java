package com.kandbnutrition.controller;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import com.kandbnutrition.resource.ErrorMessages;
import com.kandbnutrition.resource.StringValues;
import com.kandbnutrition.service.QueryVariables;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.event.EventHandler;


public class SearchListFrameController extends AnchorPane implements Initializable {
	@FXML
	AnchorPane leftPanel;
	
	@FXML
	VBox ButtonListContainer;
	
	@FXML
	Label resultLabel, buttonLabel, ErrorMessageLabel;
	
	@FXML 
	ScrollPane ListContainerScrollPane;
	
	@FXML
	ImageView progressIndicatorImageView;
	
	
	private final ExpandedLabelController expandedLabelController;
	private SingleLabelController singleLabelController;
	private SearchFieldController searchFieldController;
	private NutritionLabelFrameController nutritionLabelFrameController;
	
	private StringValues stringValues;
	private ErrorMessages errorMessages;
	
	private int previousIndex = -1;
	private int ResponseListSize;
	
	private Node previousItem;
	
	public static SearchListFrameController controller;	
	
	public SearchListFrameController(NutritionLabelFrameController nutritionLabelFrameController, MainFrameController mainFrameController) {
		
		errorMessages = new ErrorMessages();
		stringValues = new StringValues();
		
		this.nutritionLabelFrameController = nutritionLabelFrameController;
		
		expandedLabelController = new ExpandedLabelController(this.nutritionLabelFrameController, mainFrameController, this);
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(stringValues.getSearchListFxml()));
		fxmlLoader.setController(this);
		fxmlLoader.setRoot(this);
		controller = (SearchListFrameController) fxmlLoader.getController();
		
		try {
			fxmlLoader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}	
		
		ListContainerScrollPane.vvalueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable,
					Number oldValue, Number newValue) {		
					
				if (newValue.intValue() == 1)
				{
					QueryVariables.setOffset(10);
					searchFieldController.requestSearchData();
				}
			}
		});
		
		this.nutritionLabelFrameController.setSearchListFrameController(this);
	}
	
	public void setSearchFieldController(SearchFieldController searchFieldController) {
		this.searchFieldController = searchFieldController;
	}
	
	public void createListItem(String itemname, String brandname, String nutrientName, float nValue, String nUoM, float sQty, String sUoM, String id, String thumbI) {	
    	
		singleLabelController = new SingleLabelController(itemname);
    	  	
    	ButtonListContainer.getChildren().add(singleLabelController);	   
	    
	    singleLabelController.setOnMousePressed(new EventHandler<MouseEvent>() {

		    @Override
		    public void handle(MouseEvent  event) {
		    	
			    expandedLabelController.updateExpandableCellData(itemname, brandname, nutrientName ,nValue,thumbI,id);

		    	if(previousIndex != -1 ) {
		        	setItem( previousIndex, previousItem );
		        }
		    	
		    	  int currentIndex = getIndexFromEvent(event);
			      Node currentItem = getItem(currentIndex);
			      
			      setItem( currentIndex, expandedLabelController );

		       previousIndex = currentIndex;
		       previousItem = currentItem;     
		             
		       }
		    });			
	    
	    setListViewVisible();   
 }
	
	private void setItem(int previousIndex, Node previousItem) {
		ButtonListContainer.getChildren().set(previousIndex, previousItem);	
	}
	
	private int getIndexFromEvent(MouseEvent event) {
	     return ButtonListContainer.getChildren().indexOf(event.getSource());
	}
	
	private Node getItem(int index) {
		   return ButtonListContainer.getChildren().get(index);
	}
			
	public void setListViewVisible() {
		ListContainerScrollPane.setVisible(true);
		setprogressIndicatorImageView_NotVisible();
	}
	 
	public void setResponseListSize(int ResponseListSize) {
		this.ResponseListSize = ResponseListSize;
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
	
	public void setPreviousIndex(int previousIndex) {
		this.previousIndex = previousIndex;
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
