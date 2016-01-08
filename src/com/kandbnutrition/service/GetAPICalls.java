package com.kandbnutrition.service;

import java.util.List;

import com.kandbnutrition.model.ItemData;
import com.kandbnutrition.model.SearchData;
import com.kandbnutrition.model.TypeAHead;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/*
 * Created by Kyle Wolff May 8 2015
 */
public interface GetAPICalls {
	
	@GET("/search")
	void searchFoodLimitAndOffset(@Query("q") String searchTerm, @Query("limit") int limit, @Query("offset") int offset, Callback<SearchData> cb);
	
	@GET("/search")
	void searchFoodAllResults(@Query("q") String searchTerm, Callback<SearchData> cb);
	
	@GET("/item/{id}")
	void itemFacts(@Path("id") String itemId, Callback<ItemData> cb);
	
	@GET("/autocomplete")
	void typeAhead(@Query("q") String text, Callback<List<TypeAHead>> cb);

}
