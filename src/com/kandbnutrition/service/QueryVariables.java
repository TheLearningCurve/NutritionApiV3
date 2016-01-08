package com.kandbnutrition.service;

public class QueryVariables {

	public static String searchTerm;
	public static String itemId;
	public static String text;
	public static int offset = 0;
	
	public static void setSearchTerm(String searchTerm) {
		QueryVariables.searchTerm = searchTerm;
	}
	public static void setItemId(String itemId) {
		QueryVariables.itemId = itemId;
	}
	public static void setText(String text) {
		QueryVariables.text = text;
	}
	public static void setOffset(int offset)
	{
		QueryVariables.offset += offset;
	}
	public static void clearOffset()
	{
		QueryVariables.offset = 0;
	}
}
