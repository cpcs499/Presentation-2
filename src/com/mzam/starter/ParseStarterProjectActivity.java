package com.mzam.starter;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ParseStarterProjectActivity extends Activity {
	/** Called when the activity is first created. */
	final Context context = this;
	Typeface font;
	public void onCreate(Bundle savedInstanceState) {
		//getActionBar().hide();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);	
		
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		font = Typeface.createFromAsset(getAssets(),"Fonts/Rosemary.ttf");
				
		Button button2 = (Button) findViewById(R.id.button2);
		Button button1 = (Button) findViewById(R.id.textPost);
		
		final EditText et = (EditText) findViewById(R.id.postcontent);
		final EditText et2 = (EditText) findViewById(R.id.posttitle);
		TextView forgetpass = (TextView) findViewById(R.id.textView2);
		
		button1.setTypeface(font);
		button2.setTypeface(font);
		et.setTypeface(font);
		et2.setTypeface(font);
		forgetpass.setTypeface(font);
		
		
		forgetpass.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				//showDialog(0);
				
				LayoutInflater li = LayoutInflater.from(context);
				View passReset = li.inflate(R.layout.emailreset, null);
				AlertDialog.Builder Builder = new AlertDialog.Builder(
						context);
				Builder.setView(passReset);

				final EditText userInput = (EditText)passReset.findViewById(R.id.shopname);
				
				Builder.setCancelable(false)
						.setPositiveButton("Reset Pass",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										ParseUser.requestPasswordResetInBackground(userInput.getText().toString(),
			                    		        new RequestPasswordResetCallback() {
			                    						public void done(ParseException e) {
			                    						if (e == null) {
			                    							 Toast.makeText(getBaseContext(),
			                    		                            "email was successfully sent", Toast.LENGTH_SHORT).show();
			                    						// An email was successfully sent with reset instructions.
			                    						} else {
			                    							Toast.makeText(getBaseContext(),
			                    		                            "No such user exist, please enter another email", Toast.LENGTH_SHORT).show();
			                    						// Something went wrong. Look at the ParseException to see what's up.
			                    						}
			                    					}
			                    				});
									}
								})
						.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});

				// create
				AlertDialog alertDialog = Builder.create();
				alertDialog.show();
				
			}
		});
		
		
		button1.setOnClickListener(new OnClickListener() {	 
			public void onClick(View arg0) {
				Intent intent = new Intent(ParseStarterProjectActivity.this, SignUp.class);
				startActivity(intent);
			}
		});
		
		button2.setOnClickListener(new OnClickListener() {	 
			public void onClick(View arg0) {

			// Retrieve the text entered from the EditText
			final String usernametxt = et.getText().toString();
			String passwordtxt = et2.getText().toString();

			
			//Send data to Parse.com for verification
			ParseUser.logInInBackground(usernametxt, passwordtxt, new LogInCallback() {
						public void done(ParseUser user, ParseException e) {
							
							// check if email is verified or not -------------------------------
							//user.getBoolean("emailVerified") == true
							if (user != null ) {
								
								//Bundle b=new Bundle();
								
								// If user exist and authenticated, send user to Welcome.class
								Intent intent = new Intent(ParseStarterProjectActivity.this, Tabs_TimelinePostsShopsProfile.class);
								startActivity(intent);
								Toast.makeText(getApplicationContext(),"Successfully Logged in",Toast.LENGTH_LONG).show();
								
								
								//Intent intent2 = new Intent(ParseStarterProjectActivity.this, UserProfile_ME.class);
								//b.putString("un", usernametxt);
								//intent2.putExtras(b);
								
								//intent.putExtra("un", usernametxt);
								//Pass username
		
								//Intent intent2 = new Intent(ParseStarterProjectActivity.this, UserProfile_ME.class);
								//intent2.putExtra("un", usernametxt);
								
								finish();
							} 
							
							else
							{
								Toast.makeText(
										getApplicationContext(),
										"No such user exist, please signup",
										Toast.LENGTH_LONG).show();
							}
						}

			});
			
			}
		});
	}
	
	
}