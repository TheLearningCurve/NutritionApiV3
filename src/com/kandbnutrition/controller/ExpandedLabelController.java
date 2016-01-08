package com.kandbnutrition.controller;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import com.kandbnutrition.handler.SceneManager;
import com.kandbnutrition.model.ItemData;
import com.kandbnutrition.model.NutrientStrings;
import com.kandbnutrition.model.Nutrients;
import com.kandbnutrition.nutritionLabelHTML.HTMLBuilder;
import com.kandbnutrition.resource.StringValues;
import com.kandbnutrition.service.Adapter;
import com.kandbnutrition.service.QueryVariables;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ExpandedLabelController extends AnchorPane implements Initializable {

	@FXML
	Label calorieLabel, ItemNameLabel, BrandNameLabel;

	@FXML
	ImageView Thumbnail, NutritionLabelIcon;

	public Tooltip tooltip;
	public Adapter adapter;
	private NutrientStrings nutrientStrings;
	private StringValues stringValues;
	private DecimalFormat df;
	public NutritionLabelFrameController nutritionLabelFrameController;
	public SearchListFrameController searchListcontroller;
	public SceneManager sm;

	public ExpandedLabelController(NutritionLabelFrameController nutritionLabelFrameController, MainFrameController mainFrameController, SearchListFrameController controller) {
		this.nutritionLabelFrameController = nutritionLabelFrameController;
		searchListcontroller = controller;
		stringValues = new StringValues();
		df = new DecimalFormat("#,###.00");
		nutrientStrings = new NutrientStrings();
		adapter = new Adapter();
		tooltip = new Tooltip();

		sm = SceneManager.getInstance();

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(stringValues.getExpandedLabelFxml()));
		fxmlLoader.setController(this);
		fxmlLoader.setRoot(this);

		try {
			fxmlLoader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		ItemNameLabel.setTooltip(tooltip);

		NutritionLabelIcon.setOnMousePressed(new EventHandler < MouseEvent > () {

			@Override
			public void handle(MouseEvent mousevent) {

				mainFrameController.setNutritionLabel(.5, true);
			}
		});

	}

	public void updateExpandableCellData(String ItemNameLabel, String BrandNameLabel, String nutrientName, Float calorieLabel, String thumbNail, String resourceId) {
		// Making the call to get the Item Label so the label is populated quicker
		QueryVariables.setItemId(resourceId);
		getItem();

		this.calorieLabel.setText(String.valueOf(nutrientName + " " + calorieLabel.intValue()));
		this.ItemNameLabel.setText(ItemNameLabel);
		this.BrandNameLabel.setText("From " + BrandNameLabel);


		Image i = new Image(thumbNail);
		if (i.exceptionProperty().getValue() == null) {
			Thumbnail.setImage(i);
		}

		tooltip.setText(ItemNameLabel);

	}

	public void getItem() {
		adapter.getapicalls.itemFacts(QueryVariables.itemId, new Callback < ItemData > () {

			@Override
			public void success(ItemData itemData, Response response) {
				HTMLBuilder html = new HTMLBuilder();

				boolean servingPercontainerIsNotZero = String.valueOf(itemData.label.serving.quantity) != null && itemData.label.serving.uom != null && itemData.label.serving.perContainer != 0 && (String.valueOf(itemData.label.serving.metric.qty) + itemData.label.serving.metric.uom) != null;

				boolean servingPercontainerIsNull = String.valueOf(itemData.label.serving.quantity) != null && itemData.label.serving.uom != null && itemData.label.serving.perContainer == 0 && itemData.label.serving.metric.qty == 0 && itemData.label.serving.metric.uom == null;

				boolean servingWeightIsNotNull = String.valueOf(itemData.label.serving.quantity) != null && itemData.label.serving.uom != null && itemData.label.serving.perContainer == 0 && itemData.label.serving.metric.qty != 0 && itemData.label.serving.metric.uom != null;

				html.setTitle(itemData.name);

				if (servingPercontainerIsNotZero) {

					if (itemData.label.serving.metric.uom == null) {
						html.setServing(String.valueOf(df.format(itemData.label.serving.quantity)), itemData.label.serving.uom,
						String.valueOf(itemData.label.serving.perContainer));
					} else {
						html.setServing(String.valueOf(df.format(itemData.label.serving.quantity)), itemData.label.serving.uom,
						String.valueOf(itemData.label.serving.metric.qty) + itemData.label.serving.metric.uom,
						String.valueOf(itemData.label.serving.perContainer));
					}
				} else if (servingPercontainerIsNull) {

					html.setServing(String.valueOf(df.format(itemData.label.serving.quantity)), itemData.label.serving.uom);

				} else if (servingWeightIsNotNull) {

					html.setServing(String.valueOf(df.format(itemData.label.serving.quantity)), itemData.label.serving.uom,
					String.valueOf(itemData.label.serving.metric.qty) + itemData.label.serving.metric.uom);
				}

				if (itemData.label.nutrients != null) {

					for (Nutrients n: itemData.label.nutrients) {

						if (itemData.brand.brandName.compareTo("USDA") == 0) {
							USDA_IFStatements(n);
						} else {
							Non_USDA_IFStatements(n);
						}
					}
				}

				if (itemData.brand.brandName.compareTo("USDA") == 0) {
					createHtml(USDA_Label(html));
				} else {
					createHtml(NON_USDA_Label(html));
				}
			}

			@Override
			public void failure(RetrofitError retrofitError) {
				searchListcontroller.updateErrorMessageUI(retrofitError.getKind().name());
			}
		});
	}

	public void Non_USDA_IFStatements(Nutrients n) {

		if (n.name != null && n.name.compareTo("Calories") == 0) {

			nutrientStrings.setCalories(n.name, n.value, n.unit);
		} else if (n.name != null && n.name.compareTo("Total Fat") == 0) {

			nutrientStrings.setTotalFatContents(n.name, n.value, n.unit);
		} else if (n.name != null && n.name.compareTo("Saturated Fat") == 0) {

			nutrientStrings.setSaturatedFatContents(n.name, n.value, n.unit);
		} else if (n.name != null && n.name.compareTo("Trans Fat") == 0) {

			nutrientStrings.setTransFatContents(n.name, n.value, n.unit);
		} else if (n.name != null && n.name.compareTo("Monounsaturated Fat") == 0) {

			nutrientStrings.setMonoSatFatContents(n.name, n.value, n.unit);
		} else if (n.name != null && n.name.compareTo("Polyunsaturated Fat") == 0) {

			nutrientStrings.setPolySatFatContents(n.name, n.value, n.unit);
		} else if (n.name != null && n.name.compareTo("Cholesterol") == 0) {

			nutrientStrings.setCholesterol(n.name, n.value, n.unit);
		} else if (n.name != null && n.name.compareTo("Potassium") == 0) {

			nutrientStrings.setPotassium(n.name, n.value, n.unit);
		} else if (n.name != null && n.name.compareTo("Sodium") == 0) {

			nutrientStrings.setSodium(n.name, n.value, n.unit);
		} else if (n.name != null && n.name.compareTo("Dietary Fiber") == 0) {

			nutrientStrings.setDietaryFiber(n.name, n.value, n.unit);
		} else if (n.name != null && n.name.compareTo("Total Carbohydrate") == 0) {

			nutrientStrings.setCarbohydrate(n.name, n.value, n.unit);
		} else if (n.name != null && n.name.compareTo("Sugars") == 0) {

			nutrientStrings.setSugars(n.name, n.value, n.unit);
		} else if (n.name != null && n.name.compareTo("Protein") == 0) {

			nutrientStrings.setProtein(n.name, n.value, n.unit);
		} else if (n.name != null && n.name.compareTo("Vitamin A %") == 0) {

			nutrientStrings.setVitaminA("Vitamin A", n.value, "%");
		} else if (n.name != null && n.name.compareTo("Vitamin C %") == 0) {

			nutrientStrings.setVitaminC("Vitamin C", n.value, "%");
		} else if (n.name != null && n.name.compareTo("Calcium %") == 0) {

			nutrientStrings.setCalcium("Calcium", n.value, "%");
		} else if (n.name != null && n.name.compareTo("Iron %") == 0) {

			nutrientStrings.setIron("Iron", n.value, "%");
		}
	}

	public void USDA_IFStatements(Nutrients n) {

		if (n.name != null && n.name.compareTo("Energy") == 0 && n.unit == "kcal") {
			nutrientStrings.setCalories("Calories", n.value, n.unit);
		} else if (n.name != null && n.name.compareTo("Total lipid (fat)") == 0) {
			nutrientStrings.setTotalFatContents("Total Fat", n.value, n.unit);
		} else if (n.name != null && n.name.compareTo("Fatty acids, total saturated") == 0) {
			nutrientStrings.setSaturatedFatContents("Saturated Fat", n.value, n.unit);
		} else if (n.name != null && n.name.compareTo("Fatty acids, total trans") == 0) {
			nutrientStrings.setTransFatContents("Trans Fat", n.value, n.unit);
		} else if (n.name != null && n.name.compareTo("Fatty acids, total monounsaturated") == 0) {
			nutrientStrings.setMonoSatFatContents("Monounsaturated Fat", n.value, n.unit);
		} else if (n.name != null && n.name.compareTo("Fatty acids, total polyunsaturated") == 0) {
			nutrientStrings.setPolySatFatContents("Polyunsaturated Fat", n.value, n.unit);
		} else if (n.name != null && n.name.compareTo("Cholesterol") == 0) {
			nutrientStrings.setCholesterol(n.name, n.value, n.unit);
		} else if (n.name != null && n.name.compareTo("Potassium, K") == 0) {
			nutrientStrings.setPotassium("Potassium", n.value, n.unit);
		} else if (n.name != null && n.name.compareTo("Sodium, Na") == 0) {
			nutrientStrings.setSodium("Sodium", n.value, n.unit);
		} else if (n.name != null && n.name.compareTo("Fiber, total dietary") == 0) {
			nutrientStrings.setDietaryFiber("Dietary Fiber", n.value, n.unit);
		} else if (n.name != null && n.name.compareTo("Carbohydrate, by difference") == 0) {
			nutrientStrings.setCarbohydrate("Total Carbohydrate", n.value, n.unit);
		} else if (n.name != null && n.name.compareTo("Sugars, total") == 0) {
			nutrientStrings.setSugars("Sugars", n.value, n.unit);
		} else if (n.name != null && n.name.compareTo("Protein") == 0) {
			nutrientStrings.setProtein(n.name, n.value, n.unit);
		} else if (n.name != null && n.name.compareTo("Vitamin A, IU") == 0) {
			nutrientStrings.setVitaminA("Vitamin A", n.value, "%");
		} else if (n.name != null && n.name.compareTo("Vitamin C, total ascorbic acid") == 0) {
			nutrientStrings.setVitaminC("Vitamin C", n.value, "%");
		} else if (n.name != null && n.name.compareTo("Calcium, Ca") == 0) {
			nutrientStrings.setCalcium("Calcium", n.value, "%");
		} else if (n.name != null && n.name.compareTo("Iron, Fe") == 0) {
			nutrientStrings.setIron("Iron", n.value, "%");
		}
	}

	public HTMLBuilder NON_USDA_Label(HTMLBuilder html) {
		html.setBar2();
		html.setAmountPerServing();

		if (nutrientStrings.getCalories_value() != null) {
			html.setCalories(nutrientStrings.getCalories_value());
		}
		html.setBar1();
		html.setDailyValue();
		html.setLineBold(nutrientStrings.getTotalFat(), nutrientStrings.getTotalFat_value(), nutrientStrings.getTotalFat_unit(), nutrientStrings.getTotalFat_Percent());

		if (nutrientStrings.getTotalFat_value().compareTo("0") != 0) {
			html.setLineIndent(nutrientStrings.getSatFat(), nutrientStrings.getSatFat_value(), nutrientStrings.getSatFat_unit(), nutrientStrings.getSatFat_percent());
			html.setLineIndent(nutrientStrings.getTransFat(), nutrientStrings.getTransFat_value(), nutrientStrings.getTransFat_unit());
			html.setLineIndent(nutrientStrings.getMonoSatFat(), nutrientStrings.getMonoSatFat_value(), nutrientStrings.getMonoSatFat_unit());
			html.setLineIndent(nutrientStrings.getPolySatFat(), nutrientStrings.getPolySatFat_value(), nutrientStrings.getPolySatFat_unit());
		}
		if (nutrientStrings.getCholesterol_value().compareTo("0") != 0) {
			html.setLineBold(nutrientStrings.getCholesterol(), nutrientStrings.getCholesterol_value(), nutrientStrings.getCholesterol_unit(), nutrientStrings.getCholesterol_percent());
		}
		if (nutrientStrings.getPotassium_value().compareTo("0") != 0) {
			html.setLineBold(nutrientStrings.getPotassium(), nutrientStrings.getPotassium_value(), nutrientStrings.getPotassium_unit(), nutrientStrings.getPotassium_percent());
		}
		if (nutrientStrings.getSodium_value().compareTo("0") != 0); {
			html.setLineBold(nutrientStrings.getSodium(), nutrientStrings.getSodium_value(), nutrientStrings.getSodium_unit(), nutrientStrings.getSodium_percent());
		}

		html.setLineBold(nutrientStrings.getCarbohydrate(), nutrientStrings.getCarbohydrate_value(), nutrientStrings.getCarbohydrate_unit(), nutrientStrings.getCarbohydrate_percent());

		if (nutrientStrings.getCarbohydrate_value().compareTo("0") != 0) {
			html.setLineIndent(nutrientStrings.getDietaryFiber(), nutrientStrings.getDietaryFiber_value(), nutrientStrings.getDietaryFiber_unit(), nutrientStrings.getDietaryFiber_percent());
			html.setLineIndent(nutrientStrings.getSugars(), nutrientStrings.getSatFat_value(), nutrientStrings.getSugars_unit());
		}
		if (nutrientStrings.getProtein_value().compareTo("0") != 0) {
			html.setLineBold(nutrientStrings.getProtein(), nutrientStrings.getProtein_value(), nutrientStrings.getProtein_unit());
		}
		html.setBar2();
		if (nutrientStrings.getVitaminA_value() != null) {
			html.setExtras(nutrientStrings.getVitaminA(), nutrientStrings.getVitaminA_value());
		}
		if (nutrientStrings.getVitaminC_value() != null) {
			html.setExtras(nutrientStrings.getVitaminC(), nutrientStrings.getVitaminC_value());
		}
		if (nutrientStrings.getCalcium_value() != null) {
			html.setExtras(nutrientStrings.getCalcium(), nutrientStrings.getCalcium_value());
		}
		if (nutrientStrings.getIron_value() != null) {
			html.setExtras(nutrientStrings.getIron(), nutrientStrings.getIron_value());
		}
		html.endDocument();
		return html;
	}

	public HTMLBuilder USDA_Label(HTMLBuilder html) {
		html.setBar2();
		html.setAmountPerServing();

		if (nutrientStrings.getCalories_value() != null) {
			html.setCalories(nutrientStrings.getCalories_value());
		}
		html.setBar1();
		html.setDailyValue();
		html.setLineBold(nutrientStrings.getTotalFat(), nutrientStrings.getTotalFat_value(), nutrientStrings.getTotalFat_unit(), nutrientStrings.getTotalFat_Percent());

		if (nutrientStrings.getTotalFat_value().compareTo("0") != 0) {
			html.setLineIndent(nutrientStrings.getSatFat(), nutrientStrings.getSatFat_value(), nutrientStrings.getSatFat_unit(), nutrientStrings.getSatFat_percent());
			if (nutrientStrings.getTransFat_value() != null) {
				html.setLineIndent(nutrientStrings.getTransFat(), nutrientStrings.getTransFat_value(), nutrientStrings.getTransFat_unit());
			}
			if (nutrientStrings.getMonoSatFat_value() != null) {
				html.setLineIndent(nutrientStrings.getMonoSatFat(), nutrientStrings.getMonoSatFat_value(), nutrientStrings.getMonoSatFat_unit());
			}
			if (nutrientStrings.getPolySatFat_value() != null) {
				html.setLineIndent(nutrientStrings.getPolySatFat(), nutrientStrings.getPolySatFat_value(), nutrientStrings.getPolySatFat_unit());
			}
		}
		if (nutrientStrings.getCholesterol_value().compareTo("0") != 0) {
			html.setLineBold(nutrientStrings.getCholesterol(), nutrientStrings.getCholesterol_value(), nutrientStrings.getCholesterol_unit(), nutrientStrings.getCholesterol_percent());
		}
		if (nutrientStrings.getPotassium_value().compareTo("0") != 0) {
			html.setLineBold(nutrientStrings.getPotassium(), nutrientStrings.getPotassium_value(), nutrientStrings.getPotassium_unit(), nutrientStrings.getPotassium_percent());
		}
		if (nutrientStrings.getSodium_value().compareTo("0") != 0) {
			html.setLineBold(nutrientStrings.getSodium(), nutrientStrings.getSodium_value(), nutrientStrings.getSodium_unit(), nutrientStrings.getSodium_percent());
		}

		html.setLineBold(nutrientStrings.getCarbohydrate(), nutrientStrings.getCarbohydrate_value(), nutrientStrings.getCarbohydrate_unit(), nutrientStrings.getCarbohydrate_percent());

		if (nutrientStrings.getCarbohydrate_value().compareTo("0") != 0) {
			html.setLineIndent(nutrientStrings.getDietaryFiber(), nutrientStrings.getDietaryFiber_value(), nutrientStrings.getDietaryFiber_unit(), nutrientStrings.getDietaryFiber_percent());
			html.setLineIndent(nutrientStrings.getSugars(), nutrientStrings.getSatFat_value(), nutrientStrings.getSugars_unit());
		}
		if (nutrientStrings.getProtein_value().compareTo("0") != 0) {
			html.setLineBold(nutrientStrings.getProtein(), nutrientStrings.getProtein_value(), nutrientStrings.getProtein_unit());
		}
		html.setBar2();
		if (nutrientStrings.getVitaminA_percent() != null) {
			html.setExtras(nutrientStrings.getVitaminA(), nutrientStrings.getVitaminA_percent());
		}
		if (nutrientStrings.getVitaminC_percent() != null) {
			html.setExtras(nutrientStrings.getVitaminC(), nutrientStrings.getVitaminC_percent());
		}
		if (nutrientStrings.getCalcium_percent() != null) {
			html.setExtras(nutrientStrings.getCalcium(), nutrientStrings.getCalcium_percent());
		}
		if (nutrientStrings.getIron_percent() != null) {
			html.setExtras(nutrientStrings.getIron(), nutrientStrings.getIron_percent());
		}
		html.endDocument();
		return html;
	}

	public void createHtml(HTMLBuilder html) {
		Platform.runLater(new Runnable() {@Override public void run() {
				nutritionLabelFrameController.sendHtml(html);
			}
		});
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {}

}