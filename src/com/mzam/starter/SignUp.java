package com.mzam.starter;

import java.io.ByteArrayOutputStream;
import java.util.regex.Pattern;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.ProgressCallback;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class SignUp extends Activity {
	@SuppressLint("NewApi")
	
	private static final int SELECT_PHOTO = 1;
	String picturePath,fileName;
	ParseFile prsFile;
	ImageView pic;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);	
		
	
		getActionBar().setDisplayHomeAsUpEnabled(true);
		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6adbd9"))); // set your desired color
		createCutomActionBarTitle();
		Typeface font = Typeface.createFromAsset(getAssets(),"Fonts/Rosemary.ttf");
		Button button2 = (Button) findViewById(R.id.textPost);
		button2.setTypeface(font);
		final EditText username = (EditText) findViewById(R.id.postcontent);
		final EditText email = (EditText) findViewById(R.id.EditText01);
		final EditText pass = (EditText) findViewById(R.id.posttitle);
		final EditText firstLastName = (EditText) findViewById(R.id.editText3);
		final EditText Phonenumber = (EditText) findViewById(R.id.editText4);
		pic = (ImageView) findViewById(R.id.imageView1);
		
		username.setTypeface(font);
		email.setTypeface(font);
		pass.setTypeface(font);
		Phonenumber.setTypeface(font);
		firstLastName.setTypeface(font);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		
		pic.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("image/*");
				startActivityForResult(Intent.createChooser(intent, "Select Picture"),SELECT_PHOTO);
			}
		});
		
		
		button2.setOnClickListener(new OnClickListener() {	 
			public void onClick(View arg0) {
			
				// Email Pattern+password pattern Regex Expression Copy Paste >> ^_^
				final String firstNametxt ;
				final String LastNametxt;
				String USERNAME_PATTERN = "^[a-z0-9_]{4,12}$";
				String EMAIL_PATTERN ="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
				String PHONE_PATTERN ="^[0-9]{14}$"; // we can add "^[0-9]{9}$" if we want the program locally
				String PASSWORD_PATTERN = "((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[?=.*[@#$%]]*.{6,20})";
				String FULLNAME_PATTERN ="^[a-zA-Z]{3,}[ ][a-zA-z]+([ '-][a-zA-Z]+)*";
				//Matcher matcher;
			String fullname = firstLastName.getText().toString();
			// Retrieve the text entered from the EditText
			final String usernametxt = username.getText().toString();
			// Force user to fill up the form
			final String emailtxt = email.getText().toString();
			final String passwordtxt = pass.getText().toString();
			// Force user to fill up the form
			final String Phonenumbertxt = Phonenumber.getText().toString();
			
			if (fullname.equals("")||usernametxt.equals("") || emailtxt.equals("") || 
					passwordtxt.equals("") || Phonenumbertxt.equals("")||
					usernametxt.contains(" ") ||
					Pattern.compile(FULLNAME_PATTERN).matcher(fullname).matches() == false ||
					Pattern.compile(USERNAME_PATTERN).matcher(usernametxt).matches() == false ||
					Pattern.compile(EMAIL_PATTERN).matcher(emailtxt).matches() == false ||
					Pattern.compile(PHONE_PATTERN).matcher(Phonenumbertxt).matches() == false ||
					Pattern.compile(PASSWORD_PATTERN).matcher(passwordtxt).matches() == false ||
					passwordtxt.length()<6 || passwordtxt.length()>20 ||
					passwordtxt.contains("*")
					
					
					/////////////||emailtxt.contains("^[A-Za-z0-9_-.]@(yahoo|gmail|live).(com|net)")
					){
			
				if (fullname.equals("")){ firstLastName.setError("Empty"); }
				else if (Pattern.compile(FULLNAME_PATTERN).matcher(fullname).matches() == false)
				{firstLastName.setError("Enter valid fullname");}
				
				
			if(usernametxt.equals("")){ username.setError("Empty");}
			else if (usernametxt.contains(" "))
			{username.setError("Username Must doesn't Contain space");}
			
			else if (Pattern.compile(USERNAME_PATTERN).matcher(usernametxt).matches()== false)
			{username.setError("Available Character A-z,a-z,0-9,_ and more than 3 Character");}
		    
			
			if (emailtxt.equals("")){ email.setError("Empty"); }
			else if (emailtxt.contains(" "))
			{email.setError("Email Must doesn't Contain space");}
			else if (Pattern.compile(EMAIL_PATTERN).matcher(emailtxt).matches() == false)
			{email.setError("Please Enter Valid Email");}
		    
			//else if (emailtxt.contains("^[A-Za-z0-9_-.]@(yahoo|gmail|live).(com|net)"))
			//	{email.setError("Please Enter Valid Email");}
			
			if (passwordtxt.equals("")) { pass.setError("Empty");}
			else if (passwordtxt.length()<6 || passwordtxt.length()>20) { pass.setError("The Password length is between 6 and 20");}
			else if (passwordtxt.contains("*")){pass.setError("Password must doesn't Contain “*” ");}
			else if (Pattern.compile(PASSWORD_PATTERN).matcher(passwordtxt).matches() == false)
			{pass.setError("The password Must Contain numbers ,Uppercase and Lowercase letters");}
			
			if (Phonenumbertxt.equals("")){ Phonenumber.setError("Empty"); }
			else if (Pattern.compile(PHONE_PATTERN).matcher(Phonenumbertxt).matches() == false)
			{Phonenumber.setError("The Phone Number Cannot be less than 14 digit");}
			// Phonenumber.setError("Please Enter Valid Phone number")
			
			}
			else
			{
				
				int i = fullname.indexOf(" "); // detect the first space character
				firstNametxt = fullname.substring(0,i);
				LastNametxt = fullname.substring(i+1,firstLastName.length());
				
				Bitmap bitmap = BitmapFactory.decodeFile(picturePath);//////////
				if(bitmap!=null){
				Bitmap prsImgScaled = Bitmap.createScaledBitmap(bitmap, 110, 110* bitmap.getHeight() / bitmap.getWidth(), false);
		        Matrix matrix = new Matrix();
		        Bitmap prsImgScaledRotated = Bitmap.createBitmap(prsImgScaled, 0,
							0, prsImgScaled.getWidth(), prsImgScaled.getHeight(),
							matrix, true);
		        ByteArrayOutputStream bos = new ByteArrayOutputStream();//
		        prsImgScaledRotated.compress(Bitmap.CompressFormat.JPEG, 100, bos);
					byte[] scaledData = bos.toByteArray();
					final ParseFile prsFile = new ParseFile(usernametxt+".jpg", scaledData);
					prsFile.saveInBackground(new SaveCallback() {
					    @Override
					    public void done(ParseException e) {
					        if(e!=null){
					        	Toast.makeText(getApplicationContext(), "ERROR in Save File!", Toast.LENGTH_SHORT).show();
								
					        }
					        else
					        {
					        	//Toast.makeText(getApplicationContext(), "DONE!", Toast.LENGTH_SHORT).show();
								
					        }
					    }
					},new ProgressCallback() {
					    @Override
					    public void done(Integer integer) {
					            
					    	ParseUser user = new ParseUser();
					    	
					    	
				user.setUsername(usernametxt);
				user.setEmail(emailtxt);
				user.setPassword(passwordtxt);
				user.put("firstName", firstNametxt);
				user.put("LastName", LastNametxt);
				user.put("Phonenumber", Phonenumbertxt);
				user.put("Description","");
				user.put("ProfilePic", prsFile);
				//Toast.makeText(getApplicationContext(), "Hi", Toast.LENGTH_SHORT).show();
				
				user.signUpInBackground(new SignUpCallback() {
					//public void done(ParseException e){
						public void done(ParseException e) {
							if (e == null) {
								Toast.makeText(getApplicationContext(),
													"Successfully Signed up, please log in.",
													Toast.LENGTH_LONG).show();
								
											finish();
											Intent intent = new Intent(SignUp.this, ParseStarterProjectActivity.class);
											startActivity(intent);
										} else {
											
											Toast.makeText(getApplicationContext(),
													e.toString()+"",
													Toast.LENGTH_LONG).show();
									}
							}

				});
					    }
					});
				
			}
				else
				{
					ParseUser user = new ParseUser();
			    	
			    	
					user.setUsername(usernametxt);
					user.setEmail(emailtxt);
					user.setPassword(passwordtxt);
					user.put("firstName", firstNametxt);
					user.put("LastName", LastNametxt);
					user.put("Phonenumber", Phonenumbertxt);
					user.signUpInBackground(new SignUpCallback() {
						//public void done(ParseException e){
							public void done(ParseException e) {
								if (e == null) {
									Toast.makeText(getApplicationContext(),
														"Successfully Signed up, please log in.",
														Toast.LENGTH_LONG).show();
									
												finish();
												Intent intent = new Intent(SignUp.this, ParseStarterProjectActivity.class);
												startActivity(intent);
											} else {
												
												Toast.makeText(getApplicationContext(),
														e.toString()+"",
														Toast.LENGTH_LONG).show();
										}
								}

					});
				}
			}
			
			}
			
	});
}
	
	@Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	     super.onActivityResult(requestCode, resultCode, data);
	      
	     if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && null != data) {
	         Uri selectedImage = data.getData();
	         String[] filePathColumn = { MediaStore.Images.Media.DATA };
	 
	         Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
	         cursor.moveToFirst();
	 
	         int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	         picturePath = cursor.getString(columnIndex);
	         cursor.close();
	         
	        // ImageView imageView = (ImageView) findViewById(R.id.imgView);
	        // final Bitmap bitmap = BitmapFactory.decodeByteArray(picturePath.getBytes(),0,picturePath.getBytes().length);
	         Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
	         //picturePath.getBytes();
	        //if(bitmap.getByteCount()<=5242880)
	         pic.setImageBitmap(bitmap); // for pevieeeewwww
	        // else
	        // Toast.makeText(getApplicationContext(), "Big Size, Choose Another Pic", Toast.LENGTH_SHORT).show();
	        //Toast.makeText(getApplicationContext(), picturePath, Toast.LENGTH_SHORT).show();
	        
	        
	     }
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        // Respond to the action bar's Up/Home button
        case android.R.id.home:
          //  NavUtils.navigateUpFromSameTask(this);
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
	 }
	
	private void createCutomActionBarTitle(){
        this.getActionBar().setDisplayShowCustomEnabled(true);
        this.getActionBar().setDisplayShowTitleEnabled(false);

        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.custom_action_bar, null);
        Typeface tf = Typeface.createFromAsset(getAssets(),"Fonts/Rosemary.ttf");
        ((TextView)v.findViewById(R.id.titleFragment1)).setTypeface(tf);
        ((TextView)v.findViewById(R.id.titleFragment2)).setTypeface(tf);
        //assign the view to the actionbar
        this.getActionBar().setCustomView(v);
    }
}