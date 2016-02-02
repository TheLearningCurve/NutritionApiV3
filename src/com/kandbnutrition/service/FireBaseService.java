package com.kandbnutrition.service;

/*
 * Created 1/30/2016 by Kyle Wolff
 * 
 * This singleton class is used to create the Firebase object. 
 */

import com.firebase.client.Firebase;

public class FireBaseService {
	
	protected static Firebase firebase;
	
	protected FireBaseService() {
		
	}

	public static Firebase getFirebaseInstance() {
		
		if(firebase == null) {
			firebase = new Firebase(Config.BASE_FIREBASE_URL);
		}
		
		return firebase;
	}
}
