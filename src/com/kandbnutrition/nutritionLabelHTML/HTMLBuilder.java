package com.kandbnutrition.nutritionLabelHTML;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class HTMLBuilder 
{
	/*
	 * 
	 * Code:	'#css' = CSS stylesheet location
	 * 			'##js' = JS external location
	 * 			'##iv' = Input value for setServing() (a.k.a. defaultValue)
	 * 			'##su' = Serving unit for setServing()
	 * 			'###m' = Measurement for setServing()
	 * 			'#spc' = Servings per container for setServing()
	 * 			'###c' = Calories for setCalories()
	 * 			'#cff' = Calories for fat for SetCalories()
	 * 			'##nu' = Nutrition for setLineBold()
	 * 			'###a' = Amount for setLineBold()
	 * 			'###m' = Measurement also for setLineBold()
	 * 			'###p' = percent for setLineBold, Indent, and Extras
	 * 
	 * */
	
	/*	Tutorial copied and pasted from old version:
	 *
	 * HOW TO USE:
	 * 		Instantiate object first, the constructor will call the 'startDocument' method by default.
	 * 
	 * 		Make sure you call the methods in a way that you want the nutrition label to look.
	 * 
	 * 		Then, when you're done, call the 'finishDocument' method to add the last bit of HTML to the string.
	 * 
	 * 		To receive the HTML string call either 'getHTMLStringBuilder' or 'getHTMLString'.  They're pretty self explanatory
	 * 		of what they return.
	 * 
	 * 		Simple as that.
	 * 
	 * WHAT TO LOOK OUT FOR:
	 * 		Follow this link: http://www.nutritionix.com/search/item/5266a2359f05a39eb300fc90
	 * 			Notice how there are no Vitamins, Calcium or Iron lines?  All you have to do is not call those methods and
	 * 			it won't be built.  But don't forget to add a large bar at the end 'setBar1()' if you don't have Vitamin
	 * 			entries.
	 * 
	 * 			If you do need those, call 'setExtras()' and give it proper values (IN THAT ORDER!).  You DON'T have to add 
	 * 			a large bar at the end after the Vitamin entries.
	 * 
	 * 		Follow this link: http://www.nutritionix.com/search/item/547dd3d33055844701f634ae
	 * 			Notice after there is "(134g)" on the serving line.  Some labels don't have this.  There will be an override method with an
	 * 			extra parameter 'measurement'.
	 * 
	 * 			Also notice how under the input box you see a "Servings per container 5".  Some labels don't have this.
	 * 			There will be an override method for this as well 'setServing()'.
	 * 
	 * 			Also note that "Calories from Fat" isn't always there on some labels.  Call overloaded 'setCalories' with an extra parameter.
	 * 
	 * 		In my 'setServing()' method, you will need to give it the default serving value that is in the input box on the website.
	 * 
	 * 		Last things:
	 * 			For the main lines (ex. Protein, Total Fat, Calories) there are a few main types of lines:
	 * 				Line 1: Bold, not italic, not indented.
	 * 				Line 2: Bold, not italic, not indented, percent.
	 * 				Line 3: Not Bold, not italic, indented.
	 * 				Line 4: Not Bold, not italic, indented, percent.
	 * 				Line 5: Not Bold, italic, indented.
	 * 				Line 6: Not Bold, italic, indented, percent.
	 * 
	 * 				The only line that uses the italics is "Trans Fat 0g". 
	 * 
	 * 			IMPORTANT:  For each main line, you will have to provide a name, number, measurement.  There will be an override methods that
	 * 						you will use if there is a percent at the end of the line, if the line is indented, or if the line is indented and 
	 * 						italicized, etc. 
	 * 
	 * 
	 * EXTRA NOTES:
	 * 		Please use numbers that are rounded to the tenth place if they aren't integers.  It will minimize possible bugs.
	 * 
	 */
	
	private StringBuilder html;
	private int lastIndexUsed;
	
	public HTMLBuilder()
	{
		html = new StringBuilder();
		
		startDocument();
	}
	
	private void startDocument()
	{
		
		
		
		try 
		{
			File file = new File(getClass().getResource("StartDocument.txt").toURI());
			Scanner scanner = new Scanner(file);
			String cssPath = getClass().getResource("/com/kandbnutrition/nutritionLabelHTML/nutritionLabelStyles.css").toString();
			String jsPath = getClass().getResource("/com/kandbnutrition/nutritionLabelHTML/nutritionLabelScripts.js").toString();
			int cssIndex;
			int jsIndex;
			
			html.append(scanner.useDelimiter("//A").next());
			
			cssIndex = html.indexOf("#css", 0);
			html.replace(cssIndex, cssIndex + 4, cssPath);
			
			jsIndex = html.indexOf("##js", cssIndex);
			html.replace(jsIndex, jsIndex + 4, jsPath);
			
			lastIndexUsed = cssIndex;
			
			scanner.close();
		} 
		
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
	}
	
	public void setTitle(String title)
	{
		html.append("<div class=\"name\">" + title + "</div>");
	}
	
	public void setServing(String defaultValue, String serving)
	{
				
		try
		{
			File file = new File(getClass().getResource("Serving_DvS.txt").toURI());
			Scanner scanner = new Scanner(file);
			int ivIndex;
			int servingIndex;
			
			html.append(scanner.useDelimiter("//A").next());
			
			ivIndex = html.indexOf("##iv", lastIndexUsed);
			html.replace(ivIndex, ivIndex + 4, defaultValue);
			
			servingIndex = html.indexOf("##su",  ivIndex);
			html.replace(servingIndex, servingIndex + 4, serving);
			
			lastIndexUsed = servingIndex;
			
			scanner.close();
		}
		
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
	}
	
	public void setServing(String defaultValue, String serving, String weightPerServing)
	{
		
		
		try
		{
			File file = new File(getClass().getResource("Serving_DvSM.txt").toURI());
			Scanner scanner = new Scanner(file);
			int ivIndex;
			int servingIndex;
			int measurementIndex;
			
			html.append(scanner.useDelimiter("//A").next());
			
			ivIndex = html.indexOf("##iv", lastIndexUsed);
			html.replace(ivIndex, ivIndex + 4, defaultValue);
			
			servingIndex = html.indexOf("##su", ivIndex);
			html.replace(servingIndex, servingIndex + 4, serving);
			
			measurementIndex = html.indexOf("###m", servingIndex);
			html.replace(measurementIndex, measurementIndex + 4, weightPerServing);
			
			lastIndexUsed = measurementIndex;
			
			scanner.close();
		}
		
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
	}
	
	public void setServing(String defaultValue, String serving, int servingsPerContainer)
	{
		
		
		try
		{
			File file = new File(getClass().getResource("Serving_DvSSpc.txt").toURI());
			Scanner scanner = new Scanner(file);
			int ivIndex;
			int servingIndex;
			int servingsPerContainerIndex;
			
			html.append(scanner.useDelimiter("//A").next());
			
			ivIndex = html.indexOf("##iv", lastIndexUsed);
			html.replace(ivIndex, ivIndex + 4, defaultValue);
			
			servingIndex = html.indexOf("##su",  ivIndex);
			html.replace(servingIndex, servingIndex + 4, serving);
			
			servingsPerContainerIndex = html.indexOf("#spc", servingIndex);
			html.replace(servingsPerContainerIndex, servingsPerContainerIndex + 4, String.valueOf(servingsPerContainer));
			
			lastIndexUsed = servingsPerContainerIndex;
			
			scanner.close();
		}
		
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
	}
	
	public void setServing(String defaultValue, String serving, String weightPerServing, String servingsPerContainer)
	{
		
		
		try
		{
			File file = new File(getClass().getResource("Serving_DvSMSpc.txt").toURI());
			Scanner scanner = new Scanner(file);
			int ivIndex;
			int servingIndex;
			int measurementIndex;
			int servingsPerContainerIndex;
			
			html.append(scanner.useDelimiter("//A").next());
			
			ivIndex = html.indexOf("##iv", lastIndexUsed);
			html.replace(ivIndex, ivIndex + 4, defaultValue);
			
			servingIndex = html.indexOf("##su", ivIndex);
			html.replace(servingIndex, servingIndex + 4, serving);
			
			measurementIndex = html.indexOf("###m", servingIndex);
			html.replace(measurementIndex, measurementIndex + 4, weightPerServing);
			
			servingsPerContainerIndex = html.indexOf("#spc", measurementIndex);
			html.replace(servingsPerContainerIndex, servingsPerContainerIndex + 4, servingsPerContainer);
			
			lastIndexUsed = servingsPerContainerIndex;
			
			scanner.close();
		}
		
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
	}
	
	public void setBar1()
	{
		html.append("<div class=\"bar1\"></div>");
	}
	
	public void setBar2()
	{
		html.append("<div class=\"bar2\"></div>");
	}
	
	public void setAmountPerServing()
	{
		html.append("<div class=\"line m\"><b>Amount Per Serving</b></div>");
	}
	
	public void setDailyValue()
	{
		html.append("<div class=\"line ar\"><b>% Daily Value<sup>*</sup></b></div>");
	}
	
	public void setCalories(String calories)
	{
		
		
		try
		{
			File file = new File(getClass().getResource("Calories_C.txt").toURI());
			Scanner scanner = new Scanner(file);
			int caloriesIndex;
			
			html.append(scanner.useDelimiter("//A").next());
			
			caloriesIndex = html.indexOf("###c", lastIndexUsed);
			html.replace(caloriesIndex, caloriesIndex + 4, calories);
			
			lastIndexUsed = caloriesIndex;
			
			scanner.close();
		}
		
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
	}
	
	public void setCalories(String calories, String caloriesFromFat)
	{
		
		try
		{
			File file = new File(getClass().getResource("Calories_CCff.txt").toURI());
			Scanner scanner = new Scanner(file);
			int caloriesIndex;
			int caloriesFromFatIndex;
			
			html.append(scanner.useDelimiter("//A").next());
			
			caloriesFromFatIndex = html.indexOf("#cff", lastIndexUsed);
			html.replace(caloriesFromFatIndex, caloriesFromFatIndex + 4, caloriesFromFat);
			
			caloriesIndex = html.indexOf("###c", caloriesFromFatIndex);
			html.replace(caloriesIndex, caloriesIndex + 4, calories);
			
		
			
			lastIndexUsed = caloriesIndex;
			
			scanner.close();
		}
		
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
	}
	
	public void setLineBold(String nutrition, String amount, String measurement)
	{
		try
		{
			File file = new File(getClass().getResource("LineBold_NAM.txt").toURI());
			Scanner scanner = new Scanner(file);
			int nutritionIndex;
			int amountIndex;
			int measurementIndex;
			
			html.append(scanner.useDelimiter("//A").next());
			
			nutritionIndex = html.indexOf("##nu", lastIndexUsed);
			html.replace(nutritionIndex, nutritionIndex + 4, nutrition);
			
			amountIndex = html.indexOf("###a", nutritionIndex);
			html.replace(amountIndex, amountIndex + 4, amount);
			
			measurementIndex = html.indexOf("###m", amountIndex);
			html.replace(measurementIndex, measurementIndex + 4, measurement);
			
			lastIndexUsed = measurementIndex;
			
			scanner.close();
		}
		
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
	}
	
	public void setLineBold(String nutrition, String amount, String measurement, String percent)
	{
		
		
		try
		{
			File file = new File(getClass().getResource("LineBold_NAMP.txt").toURI());
			Scanner scanner = new Scanner(file);
			int nutritionIndex;
			int amountIndex;
			int measurementIndex;
			int percentIndex;
			
			html.append(scanner.useDelimiter("//A").next());
			
			percentIndex = html.indexOf("###p", lastIndexUsed);
			html.replace(percentIndex, percentIndex + 4, percent);
			
			nutritionIndex = html.indexOf("##nu", percentIndex);
			html.replace(nutritionIndex, nutritionIndex + 4, nutrition);
			
			amountIndex = html.indexOf("###a", nutritionIndex);
			html.replace(amountIndex, amountIndex + 4, amount);
			
			measurementIndex = html.indexOf("###m", amountIndex);
			html.replace(measurementIndex, measurementIndex + 4, measurement);
			
			lastIndexUsed = measurementIndex;
			
			scanner.close();
		}
		
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
	}
	
	public void setLineIndent(String nutrition, String amount, String measurement)
	{
		
		
		try
		{
			File file = new File(getClass().getResource("LineIndent_NAM.txt").toURI());
			Scanner scanner = new Scanner(file);
			int nutritionIndex;
			int amountIndex;
			int measurementIndex;
			
			html.append(scanner.useDelimiter("//A").next());
			
			nutritionIndex = html.indexOf("##nu", lastIndexUsed);
			html.replace(nutritionIndex, nutritionIndex + 4, nutrition);
			
			amountIndex = html.indexOf("###a", nutritionIndex);
			html.replace(amountIndex, amountIndex + 4, amount);
			
			measurementIndex = html.indexOf("###m", amountIndex);
			html.replace(measurementIndex, measurementIndex + 4, measurement);
			
			lastIndexUsed = measurementIndex;
			
			scanner.close();
		}
		
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
	}
	
	public void setLineIndent(String nutrition, String amount, String measurement, String percent)
	{
		
		
		try
		{
			File file = new File(getClass().getResource("LineIndent_NAMP.txt").toURI());
			Scanner scanner = new Scanner(file);
			int nutritionIndex;
			int amountIndex;
			int measurementIndex;
			int percentIndex;
			
			html.append(scanner.useDelimiter("//A").next());
			
			percentIndex = html.indexOf("###p", lastIndexUsed);
			html.replace(percentIndex, percentIndex + 4, percent);
			
			nutritionIndex = html.indexOf("##nu", percentIndex);
			html.replace(nutritionIndex, nutritionIndex + 4, nutrition);
			
			amountIndex = html.indexOf("###a", nutritionIndex);
			html.replace(amountIndex, amountIndex + 4, amount);
			
			measurementIndex = html.indexOf("###m", amountIndex);
			html.replace(measurementIndex, measurementIndex + 4, measurement);
			
			lastIndexUsed = measurementIndex;
			
			scanner.close();
		}
		
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
	}
	
	public void setTransFat(String transFat, String measurement)
	{
		html.append("<div class=\"line indent\"><i>Trans </i>Fat " + transFat + measurement + "</div>");
	}
	
	public void setExtras(String nutrition, String percent)
	{
		
		
		try
		{
			File file = new File(getClass().getResource("Extra_NP.txt").toURI());
			Scanner scanner = new Scanner(file);
			int nutritionIndex;
			int percentIndex;
			
			html.append(scanner.useDelimiter("//A").next());
			
			percentIndex = html.indexOf("###p", lastIndexUsed);
			html.replace(percentIndex, percentIndex + 4, percent);
			
			nutritionIndex = html.indexOf("##nu", percentIndex);
			html.replace(nutritionIndex, nutritionIndex + 4, nutrition);
			
			lastIndexUsed = nutritionIndex;
			
			scanner.close();
		}
		
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
	}
	
	public void endDocument()
	{
		
		
		try
		{
			File file = new File(getClass().getResource("EndDocument.txt").toURI());
			Scanner scanner = new Scanner(file);
			
			html.append(scanner.useDelimiter("//A").next());
			
			scanner.close();
		}
		
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
	}
	
	public String getHTMLString()
	{
		return html.toString();
	}
	
	public StringBuilder getHTMLStringBuilder()
	{
		return html;
	}
}
