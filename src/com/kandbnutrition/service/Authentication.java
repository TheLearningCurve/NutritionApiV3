package com.kandbnutrition.service;


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
	private Firebase firebase;
	private UserData userData;
	
	private String email, password, errorMessage;
	
	public Authentication() {
		
		fireBaseService = FireBaseService.INSTANCE;
		firebase = fireBaseService.getFirebase();
		userData = UserData.INSTANCE;
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
												
				firebase.child(FireBaseVariables.USERS + authData.getUid()).addValueEventListener(new ValueEventListener() {
					
					@Override
					public void onDataChange(DataSnapshot data) {
							
						userData.setUserInformation((String) authData.getProviderData().get(FireBaseVariables.Email),  
								data.child(FireBaseVariables.FirstName).getValue().toString(), data.child(FireBaseVariables.LastName).getValue().toString() , authData.getUid());						
					}
					
					@Override
					public void onCancelled(FirebaseError firebaseError) {
						
					}
				});
				
				firebase.child(FireBaseVariables.USERS + authData.getUid()).child(FireBaseVariables.LastLogin).setValue(ServerValue.TIMESTAMP);
			}
		});
	};

	public void setMainController(MainFrameController mainFrameController) {
		this.mainFrameController = mainFrameController;
	}
}
