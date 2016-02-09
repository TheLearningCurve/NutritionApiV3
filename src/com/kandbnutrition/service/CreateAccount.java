package com.kandbnutrition.service;

/*
 * Created by Kyle Wolff 2/9/2016 
 * 
 * This is the Create Account class to create new users
 */

import java.util.Map;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class CreateAccount {
			
	private Firebase fireBase;
	private FireBaseService fireBaseService;
	private Authentication authentication;
	
	public CreateAccount(Authentication authentication) {
		
		fireBaseService = FireBaseService.INSTANCE;
		fireBase = fireBaseService.getFirebase();
		this.authentication = authentication;
	}
	
	public void createAccount(String firstName, String lastName, String email, String password) {
		
		fireBase.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {

			@Override
			public void onError(FirebaseError firebaseError) {
				
			}

			@Override
			public void onSuccess(Map<String, Object> map) {
			
				/*
				 * Pushing the UID right away to our Firebase DB so we can add key values 
				 */
				Firebase users = fireBase.child(FireBaseVariables.USERS + map.get("uid"));
				users.push();	
				
				authentication.setPutValue(firstName, lastName, email);
				authentication.setUserInformation(email, password);
				authentication.authenticate();		
			}	
		});
	}
}
