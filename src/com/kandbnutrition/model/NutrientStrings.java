package com.kandbnutrition.model;

import java.text.DecimalFormat;

public class NutrientStrings {
	
	private DecimalFormat df = new DecimalFormat("#,###");
			
	public String Calories, TotalFat, SatFat, TransFat, MonoSatFat, PolySatFat, Cholesterol, Potassium, Sodium, DietaryFiber, Carbohydrate, Sugars,
	Protein, VitaminA, VitaminC, Calcium, Iron;
	
	public String Calories_value, TotalFat_value, SatFat_value, TransFat_value, MonoSatFat_value, PolySatFat_value, Cholesterol_value, Potassium_value, 
	Sodium_value, DietaryFiber_value, Carbohydrate_value, Sugars_value, Protein_value, VitaminA_value, VitaminC_value, Calcium_value, Iron_value;
	
	public String Calories_unit, TotalFat_unit, SatFat_unit, TransFat_unit, MonoSatFat_unit, PolySatFat_unit, Cholesterol_unit, Potassium_unit, Sodium_unit,
	DietaryFiber_unit, Carbohydrate_unit, Sugars_unit, Protein_unit, VitaminA_unit, VitaminC_unit, Calcium_unit, Iron_unit;
	
	public String TotalFat_percent, SatFat_percent, Potassium_percent, Cholesterol_percent, Sodium_percent, Carbohydrate_percent,
	DietaryFiber_percent, VitaminA_percent, VitaminC_percent, Calcium_percent, Iron_percent;
		
	public void setCalories(String name, float value, String unit){
		
		Calories = name;
		Calories_value = String.valueOf(df.format(value));
		Calories_unit = unit;
	}
	
	public String getCalories(){
		return Calories;
	}
	public String getCalories_value(){
		return Calories_value;
	}
	public String getCalories_unit(){
		return Calories_unit;
	}

	public void setTotalFatContents(String name, float value, String unit) {
		
		TotalFat = name;
		TotalFat_value = String.valueOf(df.format(value));
		TotalFat_unit = unit;
		TotalFat_percent = String.valueOf(df.format((value / 65 ) * 100));
	}
	
	public String getTotalFat(){
		return TotalFat;
	}
	public String getTotalFat_value(){
		return TotalFat_value;
	}
	public String getTotalFat_unit(){
		return TotalFat_unit;
	}
	public String getTotalFat_Percent(){		
		return TotalFat_percent;
	}

	public void setSaturatedFatContents(String name, float value, String unit) {
		
		SatFat = name;
		SatFat_value= String.valueOf(df.format(value));;
		SatFat_unit = unit;
		SatFat_percent = String.valueOf(df.format((value / 20) * 100));
	}
	
	public String getSatFat(){
		return SatFat;
	}
	public String getSatFat_value(){
		return SatFat_value;
	}
	public String getSatFat_unit(){
		return SatFat_unit;
	}
	public String getSatFat_percent(){
		return SatFat_percent;
	}

	public void setTransFatContents(String name, float value, String unit) {
	
		TransFat = name;
		TransFat_value = String.valueOf(df.format(value));
		TransFat_unit = unit;
	}
	
	public String getTransFat(){
		return TransFat;
	}
	public String getTransFat_value(){
		return TransFat_value;
	}
	public String getTransFat_unit(){
		return TransFat_unit;
	}

	public void setMonoSatFatContents(String name, float value, String unit) {
		
		MonoSatFat = name;
		MonoSatFat_value = String.valueOf(df.format(value));
		MonoSatFat_unit = unit;
		
	}
	
	public String getMonoSatFat(){
		return MonoSatFat;
	}
	public String getMonoSatFat_value(){
		return MonoSatFat_value;
	}
	public String getMonoSatFat_unit(){
		return MonoSatFat_unit;
	}

	public void setPolySatFatContents(String name, float value, String unit) {
		
		PolySatFat = name;
		PolySatFat_value = String.valueOf(df.format(value));
		PolySatFat_unit = unit;
		
	}
	
	public String getPolySatFat(){
		return PolySatFat;
	}
	public String getPolySatFat_value(){
		return PolySatFat_value;
	}
	public String getPolySatFat_unit(){
		return PolySatFat_unit;
	}

	public void setCholesterol(String name, float value, String unit) {
		
		Cholesterol = name;
		Cholesterol_value = String.valueOf(df.format(value));
		Cholesterol_unit = unit;	
		Cholesterol_percent = String.valueOf(df.format((value / 300) * 100));
	}
	
	public String getCholesterol() {
		return Cholesterol;
	}

	public String getCholesterol_value() {
		return Cholesterol_value;
	}

	public String getCholesterol_unit() {
		return Cholesterol_unit;
	}
	
	public String getCholesterol_percent() {
		return Cholesterol_percent;
	}

	public void setPotassium(String name, float value, String unit) {
		
		Potassium = name;
		Potassium_value = String.valueOf(df.format(value));
		Potassium_unit = unit;
		Potassium_percent = String.valueOf(df.format((value / 3500) * 100));
	}
	
	public String getPotassium() {
		return Potassium;
	}

	public String getPotassium_value() {
		return Potassium_value;
	}

	public String getPotassium_unit() {
		return Potassium_unit;
	}
	
	public String getPotassium_percent(){
		return Potassium_percent;
	}

	public void setSodium(String name, float value, String unit) {
		
		Sodium = name;
		Sodium_value = String.valueOf(df.format(value));
		Sodium_unit = unit;
		Sodium_percent = String.valueOf(df.format((value / 2400) * 100));
	}
	
	public String getSodium() {
		return Sodium;
	}

	public String getSodium_value() {
		return Sodium_value;
	}

	public String getSodium_unit() {
		return Sodium_unit;
	}
	
	public String getSodium_percent(){
		return Sodium_percent;
	}

	public void setDietaryFiber(String name, float value, String unit) {

		DietaryFiber = name;
		DietaryFiber_value = String.valueOf(df.format(value));
		DietaryFiber_unit = unit;
		DietaryFiber_percent = String.valueOf(df.format((value / 25) * 100));
	}

	public String getDietaryFiber() {
		return DietaryFiber;
	}

	public String getDietaryFiber_value() {
		return DietaryFiber_value;
	}

	public String getDietaryFiber_unit() {
		return DietaryFiber_unit;
	}
	
	public String getDietaryFiber_percent() {
		return DietaryFiber_percent;
	}
	
	public void setCarbohydrate(String name, float value, String unit) {

		Carbohydrate = name;
		Carbohydrate_value = String.valueOf(df.format(value));
		Carbohydrate_unit = unit;
		Carbohydrate_percent = String.valueOf(df.format((value / 300) * 100));
	}
	
	public String getCarbohydrate() {
		return Carbohydrate;
	}

	public String getCarbohydrate_value() {
		return Carbohydrate_value;
	}

	public String getCarbohydrate_unit() {
		return Carbohydrate_unit;
	}
	
	public String getCarbohydrate_percent() {
		return Carbohydrate_percent;
	}

	public void setSugars(String name, float value, String unit) {

		Sugars = name;
		Sugars_value = String.valueOf(df.format(value));
		Sugars_unit = unit;
	}

	public String getSugars() {
		return Sugars;
	}

	public String getSugars_value() {
		return Sugars_value;
	}

	public String getSugars_unit() {
		return Sugars_unit;
	}

	public void setProtein(String name, float value, String unit) {

		Protein = name;
		Protein_value = String.valueOf(df.format(value));
		Protein_unit = unit;
	}

	public String getProtein() {
		return Protein;
	}

	public String getProtein_value() {
		return Protein_value;
	}

	public String getProtein_unit() {
		return Protein_unit;
	}

	public void setVitaminA(String name, float value, String unit) {
		
		VitaminA = name;
		VitaminA_value = String.valueOf(df.format(value));
		VitaminA_unit = unit;
		VitaminA_percent = String.valueOf(df.format((value / 5000) * 100));
	}

	public String getVitaminA() {
		return VitaminA;
	}

	public String getVitaminA_value() {
		return VitaminA_value;
	}

	public String getVitaminA_unit() {
		return VitaminA_unit;
	}
	
	public String getVitaminA_percent() {
		return VitaminA_percent;
	}


	public void setVitaminC(String name, float value, String unit) {
		
		VitaminC = name;
		VitaminC_value = String.valueOf(df.format(value));
		VitaminC_unit = unit;
		VitaminC_percent = String.valueOf(df.format((value / 60) * 100));
	}

	public String getVitaminC() {
		return VitaminC;
	}

	public String getVitaminC_value() {
		return VitaminC_value;
	}

	public String getVitaminC_unit() {
		return VitaminC_unit;
	}
	
	public String getVitaminC_percent(){
		return VitaminC_percent;
	}

	public void setCalcium(String name, float value, String unit) {
		
		Calcium = name;
		Calcium_value = String.valueOf(df.format(value));
		Calcium_unit = unit;
		Calcium_percent = String.valueOf(df.format((value / 1000) * 100));
	}
	
	public String getCalcium() {
		return Calcium;
	}

	public String getCalcium_value() {
		return Calcium_value;
	}

	public String getCalcium_unit() {
		return Calcium_unit;
	}
	
	public String getCalcium_percent() {
		return Calcium_percent;
	}

	public void setIron(String name, float value, String unit) {
		
		Iron = name;
		Iron_value = String.valueOf(df.format(value));
		Iron_unit = unit;	
		Iron_percent = String.valueOf(df.format((value / 18) * 100));
	}

	public String getIron() {
		return Iron;
	}

	public String getIron_value() {
		return Iron_value;
	}

	public String getIron_unit() {
		return Iron_unit;
	}
	
	public String getIron_percent(){
		return Iron_percent;
	}

}
