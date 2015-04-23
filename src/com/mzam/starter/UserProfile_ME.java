package com.mzam.starter;

import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class UserProfile_ME extends Activity {
	/** Called when the activity is first created. */
	
	ArrayAdapter<String> listAdapter;
	ListView listView ;
	ParseImageView userpic;
	TextView profilefirstlastname,profileusername,desc,postnumber,postList,followersnumber,followingnumber;
	ParseUser fl = ParseUser.getCurrentUser();
	List<String> obList ,obList2;
	List<ParseObject> ob;
	Typeface font;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_tab);	
		
		font = Typeface.createFromAsset(getAssets(),"Fonts/Rosemary.ttf");
		((TextView) findViewById(R.id.textView7)).setTypeface(font);
		((TextView) findViewById(R.id.textView4)).setTypeface(font);
		((TextView) findViewById(R.id.textView5)).setTypeface(font);
		//Bundle extras = getIntent().getExtras();
		//String value = extras.getString("userProfile");
		//Intent in = getActivity().getIntent();
		//String value = in.getStringExtra("userProfile");
    	
		//Toast.makeText(getApplicationContext(), "Hiiiiiiiiiiii"+value, Toast.LENGTH_LONG).show();
		
		listView = (ListView) findViewById(R.id.listView1);
		userpic = (ParseImageView) findViewById(R.id.imageView1);
		
		//First & Last Name
		profilefirstlastname = (TextView) findViewById(R.id.textView2);
		profileusername = (TextView) findViewById(R.id.textView1);
		desc = (TextView) findViewById(R.id.textView3);

        ImageView sett = (ImageView) findViewById(R.id.imageView3);
		sett.setAlpha(0.6f);
        sett.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in = new Intent(UserProfile_ME.this , Acc_Setting.class);
				//add data to the Intent object
				startActivity(in);
				
			}
		});
           
	}
	
	//To update the information in the user profile page if the user edit any information
	@Override
	public void onResume()
	    {  // After a pause OR at startup
	    super.onResume();
	    //Refresh your stuff here
	
	    profilefirstlastname.setText(fl.getString("firstName")+" "+fl.getString("LastName"));
		profilefirstlastname.setAllCaps(true);
		profilefirstlastname.setTypeface(font);
		
		profileusername.setText("@"+fl.getUsername());
		profileusername.setTypeface(font);
		
		desc.setText(fl.getString("Description"));
		desc.setTypeface(font);
		
		/*
		ParseFile fileObject = (ParseFile) fl.get("ProfilePic");
		fileObject.getDataInBackground(new GetDataCallback() {
			public void done(byte[] data,ParseException e) {
				if (e == null) {
							// Decode the Byte[] into
					Bitmap bmp = BitmapFactory.decodeByteArray(data, 0,data.length);

					// Set the pic in the ImageView
					userpic.setImageBitmap(bmp);
						}
					}
				});
		
		*/
		
		ParseFile imageFile = fl.getParseFile("ProfilePic");
		if (imageFile != null) {
			userpic.setParseFile(imageFile);
			userpic.loadInBackground();
		}
		else
		{
				Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
						R.drawable.inspiration_6);
				userpic.setImageBitmap(bitmap); // for pevieeeewwww
				
				
		}

		PostCustomAdapter ff = new PostCustomAdapter(this);
		listView.setAdapter(ff);
		
		
		try{
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Post");
		query.whereEqualTo("PostWriter", fl);
		int count = query.count();
		postnumber = (TextView) findViewById(R.id.TextView01);
		postnumber.setText(""+count);
    	postnumber.setGravity(Gravity.CENTER);
		}
		catch(ParseException e)
		{
			e.printStackTrace();
		}
		
			ParseQuery<ParseObject> query = ParseQuery.getQuery("Follow");
			ParseObject obj = ParseObject.createWithoutData("_User",ParseUser.getCurrentUser().getObjectId());
			query.whereEqualTo("to", obj);
			query.findInBackground(new FindCallback<ParseObject>(){

				@Override
				public void done(List<ParseObject> arg0, ParseException arg1) {
					// TODO Auto-generated method stub
					
					followersnumber = (TextView) findViewById(R.id.textView6);
					followersnumber.setText(""+arg0.size());
					followersnumber.setGravity(Gravity.CENTER);
				}
				
			});
			
			ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Follow");
			ParseObject obj2 = ParseObject.createWithoutData("_User",ParseUser.getCurrentUser().getObjectId());
			query2.whereEqualTo("from", obj2);
			query2.findInBackground(new FindCallback<ParseObject>(){
				
				@Override
				public void done(List<ParseObject> arg0, ParseException arg1) {
					// TODO Auto-generated method stub
					followingnumber = (TextView) findViewById(R.id.textView8);
					followingnumber.setText(""+arg0.size());
					followingnumber.setGravity(Gravity.CENTER);
				}
			});
	}

	
}