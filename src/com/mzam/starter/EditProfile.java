package com.mzam.starter;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Pattern;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EditProfile extends Activity {
	/** Called when the activity is first created. */
	private static final int SELECT_PHOTO = 1;
	String picturePath,fileName;
	ParseImageView pic;
	Calendar myCalendar ;
	EditText birthDate;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editprofile);	
		
		myCalendar = Calendar.getInstance();
		birthDate = (EditText) findViewById(R.id.editText6);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6adbd9"))); // set your desired color
		createCutomActionBarTitle();
		
		Button btn = (Button) findViewById(R.id.textPost);
		
		Typeface tf = Typeface.createFromAsset(getAssets(),"Fonts/Rosemary.ttf");
		//First & Last Name
		final ParseUser currentuser = ParseUser.getCurrentUser();
		final EditText firstlast = (EditText) findViewById(R.id.editText1);
		firstlast.setText(currentuser.getString("firstName")+" "+currentuser.getString("LastName"));
		firstlast.setTypeface(tf);
		final EditText username = (EditText) findViewById(R.id.editText2);
		username.setText(currentuser.getUsername());
		username.setTypeface(tf);
		final EditText desc = (EditText) findViewById(R.id.editText3);
			desc.setText(currentuser.getString("Description"));
		desc.setTypeface(tf);
		
		final EditText email = (EditText) findViewById(R.id.editText4);
		email.setText(currentuser.getEmail());
		email.setTypeface(tf);
		
		if(currentuser.getString("birthDate").isEmpty()){
			birthDate.setHint("Month/Day/Year");	
			birthDate.setTypeface(tf);}
		else{
			birthDate.setText(currentuser.getString("birthDate")+"");
		}
		
		
		final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

		    @Override
		    public void onDateSet(DatePicker view, int year, int monthOfYear,
		            int dayOfMonth) {
		        // TODO Auto-generated method stub
		        myCalendar.set(Calendar.YEAR, year);
		        myCalendar.set(Calendar.MONTH, monthOfYear);
		        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		        updateLabel();
		    }

		};

		birthDate.setOnClickListener(new OnClickListener() {

		        @Override
		        public void onClick(View v) {
		            // TODO Auto-generated method stub
		            new DatePickerDialog(EditProfile.this, date, myCalendar
		                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
		                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
		        }
		    });

		      
		
		
		final EditText phone = (EditText) findViewById(R.id.editText5);
		phone.setText(currentuser.getString("Phonenumber"));
		phone.setTypeface(tf);
		
		pic = (ParseImageView) findViewById(R.id.imageView1);
		
		ParseFile imageFile = currentuser.getParseFile("ProfilePic");
		if (imageFile != null) {
			pic.setParseFile(imageFile);
			pic.loadInBackground();
		}
		else
		{
				Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
						R.drawable.inspiration_6);
				pic.setImageBitmap(bitmap); // for pevieeeewwww
				
				
		}
		
		/*
		ParseFile fileObject = (ParseFile) currentuser.get("ProfilePic");
		
		fileObject.getDataInBackground(new GetDataCallback() {
			public void done(byte[] data,ParseException e) {
				if (e == null) {
					Toast.makeText(getApplicationContext(),
			                  "Successfully", Toast.LENGTH_SHORT)
			                  .show();
							// Decode the Byte[] into
					Bitmap bmp = BitmapFactory.decodeByteArray(data, 0,data.length);

					// Set the pic in the ImageView
					pic.setImageBitmap(bmp);

						} else {
							Toast.makeText(getApplicationContext(),
					                  "Problem", Toast.LENGTH_SHORT)
					                  .show();
						}
					}
				});
		*/
		
		pic.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("image/*");
				startActivityForResult(Intent.createChooser(intent, "Select Picture"),SELECT_PHOTO);
			}
		});
		
		btn.setTypeface(tf);
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				// Email Pattern+password pattern Regex Expression Copy Paste >> ^_^
				String USERNAME_PATTERN = "^[a-z0-9_]{4,12}$";
				String EMAIL_PATTERN ="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
				String PHONE_PATTERN ="^[0-9]{14}$"; // we can add "^[0-9]{9}$" if we want the program locally
				String FULLNAME_PATTERN ="^[a-zA-Z]{3,}[ ][a-zA-z]+([ '-][a-zA-Z]+)*";
				String DESCREPTION_PATTERN ="^.{0,150}";
				
				ParseUser query = ParseUser.getCurrentUser();
				
				String fullname = firstlast.getText().toString();
				int i = fullname.indexOf(" "); // detect the first space character
				final String firstNametxt = fullname.substring(0,i);
				final String LastNametxt = fullname.substring(i+1,fullname.length());
				
				if (fullname.equals("")||username.equals("") || email.equals("") || 
						phone.equals("")||username.getText().toString().contains(" ") ||
						Pattern.compile(FULLNAME_PATTERN).matcher(fullname).matches() == false ||
						Pattern.compile(USERNAME_PATTERN).matcher(username.getText().toString()).matches() == false ||
						Pattern.compile(EMAIL_PATTERN).matcher(email.getText().toString()).matches() == false ||
						Pattern.compile(PHONE_PATTERN).matcher(phone.getText().toString()).matches() == false ||
						Pattern.compile(DESCREPTION_PATTERN).matcher(desc.getText().toString()).matches() == false 
						
						
						/////////////||emailtxt.contains("^[A-Za-z0-9_-.]@(yahoo|gmail|live).(com|net)")
						){
				
					if (fullname.equals("")){ firstlast.setError("Empty"); }
					else if (Pattern.compile(FULLNAME_PATTERN).matcher(fullname).matches() == false)
					{firstlast.setError("Enter valid fullname");}
					
					
				if(username.getText().toString().equals("")){ username.setError("Empty");}
				else if (username.getText().toString().contains(" "))
				{username.setError("Username Must doesn't Contain space");}
				else if (Pattern.compile(USERNAME_PATTERN).matcher(username.getText().toString()).matches()== false)
				{username.setError("Available Character A-z,a-z,0-9,_ and more than 3 Character");}
			    
				
				if (email.getText().toString().equals("")){ email.setError("Empty"); }
				else if (email.getText().toString().contains(" "))
				{email.setError("Email Must doesn't Contain space");}
				else if (Pattern.compile(EMAIL_PATTERN).matcher(email.getText().toString()).matches() == false)
				{email.setError("Please Enter Valid Email");}
			    
				if (phone.getText().toString().equals("")){ phone.setError("Empty"); }
				else if (Pattern.compile(PHONE_PATTERN).matcher(phone.getText().toString()).matches() == false)
				{phone.setError("The Phone Number Cannot be less than 14 digit");}
				// Phonenumber.setError("Please Enter Valid Phone number")
				
				if (Pattern.compile(DESCREPTION_PATTERN).matcher(desc.getText().toString()).matches() == false)
				{desc.setError("Enter Valid Description");}

				
				}
				
				else if ((firstNametxt.equals(currentuser.getString("firstName"))) &&
						(LastNametxt.equals(currentuser.getString("LastName"))) &&
						(phone.getText().toString().equals(currentuser.getString("Phonenumber")))&&
						(email.getText().toString().equals(currentuser.getEmail()))&&
						(desc.getText().toString().equals(currentuser.getString("Description")))&&
						(username.getText().toString().equals(currentuser.getUsername()))
						&&(birthDate.getText().toString().equals(currentuser.getString("birthDate"))))
				{
					Toast.makeText(getApplicationContext(),
			    			"Nothing Updated!!" , Toast.LENGTH_LONG)
		                     .show();
				}
						
						
						
				
				else
				{
				if(!firstNametxt.equals(currentuser.getString("firstName")))
					query.put("firstName",firstNametxt);
				
				if(!LastNametxt.equals(currentuser.getString("LastName")))
					query.put("LastName",LastNametxt);
				
				if(!phone.getText().toString().equals(currentuser.getString("Phonenumber")))
					query.put("Phonenumber",phone.getText().toString());
				
				if(!email.getText().toString().equals(currentuser.getEmail()))
					query.setEmail(email.getText().toString());
				
				if(!desc.getText().toString().equals(currentuser.getString("Description")))
					query.put("Description", desc.getText().toString());
				
				if(!username.getText().toString().equals(currentuser.getUsername()))
					query.setUsername(username.getText().toString());
				
				if (!birthDate.getText().toString().equals(currentuser.getString("birthDate")))
					query.put("birthDate", birthDate.getText().toString());
				
				
					//query.saveInBackground();
		          
		       // toast message
					//Toast.makeText(getApplicationContext(), "Uploaded",
						//	Toast.LENGTH_SHORT).show();
				
				query.saveInBackground(new SaveCallback() {
					  public void done(ParseException e) {
					    if (e == null) {
					      // Now let's update it with some new data. In this case, only cheatMode and score
					      // will get sent to the Parse Cloud. playerName hasn't changed.
					    	//object.put("Phonenumber",phone.getText().toString());
					    	Toast.makeText(getApplicationContext(),
					    			"Succesfully Updated!" , Toast.LENGTH_LONG)
				                     .show();
					    	
					    	//object.put("cheatMode", true);
					    	//object.saveInBackground();
					    	
					    }
					    
					  }
				});
				
				finish();
				} //end elllllllllllllllllse 
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
	        
	        // Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
				
	         ParseUser query = ParseUser.getCurrentUser();
				
			  // Get the Image's file name
	          String fileNameSegments[] = picturePath.split("/");
	          fileName = fileNameSegments[fileNameSegments.length - 1];
	          
	         // Toast.makeText(getApplicationContext(), fileName,
					//	Toast.LENGTH_SHORT).show();
	          
	          //like instgram profile pic 110*110
	          Bitmap prsImgScaled = Bitmap.createScaledBitmap(bitmap, 110, 110* bitmap.getHeight() / bitmap.getWidth(), false);
	       Matrix matrix = new Matrix();
	       Bitmap prsImgScaledRotated = Bitmap.createBitmap(prsImgScaled, 0,
						0, prsImgScaled.getWidth(), prsImgScaled.getHeight(),
						matrix, true);
	       ByteArrayOutputStream bos = new ByteArrayOutputStream();
				prsImgScaledRotated.compress(Bitmap.CompressFormat.JPEG, 100, bos);
				byte[] scaledData = bos.toByteArray();
				ParseFile prsFile = new ParseFile(ParseUser.getCurrentUser().getUsername()+"Pic.jpg", scaledData);
				query.put("ProfilePic", prsFile);
				query.saveInBackground();
	        
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
	
	private void updateLabel() {

	    String myFormat = "MM/dd/yy"; //In which you need put here
	    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
	    birthDate.setText(sdf.format(myCalendar.getTime()));
	    }
}
