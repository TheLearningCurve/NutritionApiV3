package com.kandbnutrition.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Created 1/30/2016 by Kyle Wolff
 * 
 * This Authentication class is used to authenticate the user. 
 * 
 * We will use this class for Login and Create Account.
 */

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.Firebase.AuthResultHandler;
import com.firebase.client.FirebaseError;
import com.firebase.client.ServerValue;
import com.firebase.client.ValueEventListener;
import com.kandbnutrition.controller.MainFrameController;
import com.kandbnutrition.model.UserData;

public class Authentication {
	
	private MainFrameController mainFrameController;
	
	private FireBaseService fireBaseService;
	private Firebase firebase, users;
	private UserData userData;
	private CreateAccount createAccount;
	
	private String email, password, errorMessage;
	private List<String> newUser;
	
	public Authentication() {
		
		fireBaseService = FireBaseService.INSTANCE;
		userData = UserData.INSTANCE;
		firebase = fireBaseService.getFirebase();
		createAccount = new CreateAccount(this);
		newUser = new ArrayList<>();
	}
	
	public void setUserInformation(String email, String password) {
		
		this.email = email;
		this.password = password;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void authenticate() {
		
		firebase.authWithPassword(this.email, this.password, new AuthResultHandler() {
			
			@Override
			public void onAuthenticationError(FirebaseError fireBaseError) {
				
				mainFrameController.loadingIconVisible(false);
				errorMessage = fireBaseError.getMessage();
				mainFrameController.setErrorMessage(errorMessage);
			}
			
			@Override
			public void onAuthenticated(AuthData authData) {
						
				mainFrameController.loadingIconVisible(false);
				mainFrameController.login();
						
						if(!newUser.isEmpty()) {
							
							firebase.child(FireBaseVariables.USERS + authData.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
								
								@Override
								public void onDataChange(DataSnapshot data) {
									
									/*
									 * For some reason a hashMap was not working but this way does work. 
									 * 
									 * We are getting the AuthData UID then setting the values to the correct key.
									 * The index will not change for FirstName, LastName, Email
									 */
									
									firebase.child(FireBaseVariables.USERS + authData.getUid()).child(FireBaseVariables.FirstName).setValue(newUser.get(0));
									firebase.child(FireBaseVariables.USERS + authData.getUid()).child(FireBaseVariables.LastName).setValue(newUser.get(1));
									firebase.child(FireBaseVariables.USERS + authData.getUid()).child(FireBaseVariables.Email).setValue(newUser.get(2));
									firebase.child(FireBaseVariables.USERS + authData.getUid()).child(FireBaseVariables.CreatedDate).setValue(ServerValue.TIMESTAMP);
								}
								
								@Override
								public void onCancelled(FirebaseError fireBaseError) {
									
								}
							});
						}
						
						firebase.child(FireBaseVariables.USERS + authData.getUid()).addValueEventListener(new ValueEventListener() {
							
							@Override
							public void onDataChange(DataSnapshot data) {
								
								/*
								 * The Email and UID cannot be NULL 
								 */
								
								if(authData.getUid() != null && data.child(FireBaseVariables.Email).getValue() != null) {
									
									userData.setUserInformation((String) authData.getProviderData().get(FireBaseVariables.Email),  
											data.child(FireBaseVariables.FirstName).getValue().toString(), data.child(FireBaseVariables.LastName).getValue().toString() , authData.getUid());						
								}
							}
							
							@Override
							public void onCancelled(FirebaseError firebaseError) {
								
							}
						});
						
						firebase.child(FireBaseVariables.USERS + authData.getUid()).child(FireBaseVariables.LastLogin).setValue(ServerValue.TIMESTAMP);			
			}
		});
	};
	
	/*
	 * Set a new users information for Authentication purposes 
	 */
	
	public void setPutValue(String firstName, String lastName, String email) {
	
		newUser.add(firstName);
		newUser.add(lastName);
		newUser.add(email);	
	}
	
	public void createAccount(String firstName, String lastName, String email, String password) {
		
		createAccount.createAccount(firstName, lastName, email, password);
	}

	public void setMainController(MainFrameController mainFrameController) {
		this.mainFrameController = mainFrameController;
	}
}
