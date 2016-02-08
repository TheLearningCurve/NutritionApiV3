package com.kandbnutrition.service;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/*
 * Created by Kyle Wolff May 8 2015
 */
public class Adapter {

	public GetAPICalls getapicalls;
	
	/*
	 * RequestInterceptor is to add the headers to each call that is made
	 */
	RequestInterceptor requestInterceptor = new RequestInterceptor() {

		  @Override
		  public void intercept(RequestFacade request) {

		    request.addHeader("X-APP-ID", Config.appId);
		    request.addHeader("X-APP-KEY", Config.appKey);

		  }
		};
		
        /*
         * RestAdapter is the class through which your API interfaces are turned into callable objects
         */
        public Adapter() {
			
			 RestAdapter restAdapter = new RestAdapter.Builder()
	          .setEndpoint(Config.BASE_NUTRITIONIX_URL)
//	          .setLogLevel(RestAdapter.LogLevel.FULL)
			  .setRequestInterceptor(requestInterceptor)
	          .setErrorHandler(new ErrorHandling())
	          .build();
	        
	        getapicalls = restAdapter.create(GetAPICalls.class);
	        
		}
}
