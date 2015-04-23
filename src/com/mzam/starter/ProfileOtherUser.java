package com.mzam.starter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileOtherUser extends Activity {

	ArrayAdapter<String> listAdapter;
	ListView listView ;
	ParseImageView userpic;
	TextView profilefirstlastname,profileusername,desc,postnumber,postList,followersnumber,followingnumber;
	Typeface font;
	Button follow;
	ParseUser user;
	ArrayList<String> obList;
	ParseFile obList2;
	final Context context = this;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_other);	
		getActionBar().setDisplayHomeAsUpEnabled(true);
		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6adbd9"))); // set your desired color
		
		font = Typeface.createFromAsset(getAssets(),"Fonts/Rosemary.ttf");
		((TextView) findViewById(R.id.textView7)).setTypeface(font);
		((TextView) findViewById(R.id.textView4)).setTypeface(font);
		((TextView) findViewById(R.id.textView5)).setTypeface(font);
		
		listView = (ListView) findViewById(R.id.listView1);
		userpic = (ParseImageView) findViewById(R.id.imageView1);
		follow = (Button) findViewById(R.id.button1);
		
		//First & Last Name
		profilefirstlastname = (TextView) findViewById(R.id.textView2);
		profileusername = (TextView) findViewById(R.id.textView1);
		desc = (TextView) findViewById(R.id.textView3);

		
	}
	
	@Override
	public void onResume()
	    {  // After a pause OR at startup
	    super.onResume();
	    //Refresh your stuff here
	
	    Bundle extras = getIntent().getExtras();
		final String value = extras.getString("USER_ID");
		
	    ParseQuery<ParseUser> y = ParseQuery.getUserQuery();
	    y.getInBackground(value, new GetCallback<ParseUser>(){
			@Override
			public void done(ParseUser object, ParseException e) {
				// TODO Auto-generated method stub
				profilefirstlastname.setText(object.getString("firstName")+" "+object.getString("LastName"));
				profilefirstlastname.setAllCaps(true);
				profilefirstlastname.setTypeface(font);
			
				profileusername.setText("@"+object.getUsername());
				profileusername.setTypeface(font);
				
				desc.setText(object.getString("Description"));
				desc.setTypeface(font);
				
				ParseFile imageFile = object.getParseFile("ProfilePic");
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
			}	
	    });
    	
	    
	    ParseQuery<ParseObject> postdisplay = ParseQuery.getQuery("Follow");
	    postdisplay.whereEqualTo("from", ParseUser.getCurrentUser());
	    ParseObject obj3 = ParseObject.createWithoutData("_User",value);
	    postdisplay.whereEqualTo("to", obj3);
	    postdisplay.whereEqualTo("follow_request_status","Accepted");
	    postdisplay.getFirstInBackground(new GetCallback<ParseObject>(){
			@Override
			public void done(ParseObject object, ParseException e) {
				// TODO Auto-generated method stub
				if(object!=null){
							OtherPostCustomAdapter ff = new OtherPostCustomAdapter(ProfileOtherUser.this);
							listView.setAdapter(ff);
				}
				else{
					
            				listView.setBackgroundResource(R.drawable.loock);
            		
				}
			}
			
	    });
	    try{
			ParseQuery<ParseObject> query = ParseQuery.getQuery("Post");
			ParseObject obj = ParseObject.createWithoutData("_User",value);
			query.whereEqualTo("PostWriter", obj);
			int count = query.count();
			postnumber = (TextView) findViewById(R.id.TextView01);
			postnumber.setText(""+count);
	    	postnumber.setGravity(Gravity.CENTER);
			}
			catch(ParseException ee)
			{
				ee.printStackTrace();
			}

	    

		    ParseQuery<ParseObject> query = ParseQuery.getQuery("Follow");
			ParseObject obj = ParseObject.createWithoutData("_User",value);
			query.whereEqualTo("to", obj);
			query.findInBackground(new FindCallback<ParseObject>(){
				
				@Override
				public void done(List<ParseObject> arg0, ParseException arg1) {
					// TODO Auto-generated method stub
					followersnumber = (TextView) findViewById(R.id.textView6);
					followersnumber.setText(""+arg0.size());
					followersnumber.setGravity(Gravity.CENTER);
					
					//to hide the follow button
					for(int i=0;i<arg0.size();i++){
						if(user!=arg0.get(i))
							follow.setBackgroundResource(R.drawable.followcopy);
							follow.setText("unfollow");
					}
					
				}
				
				
			});
			ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Follow");
			ParseObject obj2 = ParseObject.createWithoutData("_User",value);
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
			
			follow.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Follow");
					ParseObject obj = ParseObject.createWithoutData("_User",value);
	            	query2.whereEqualTo("to", obj);
	            	query2.whereEqualTo("from", ParseUser.getCurrentUser());
	            	query2.getFirstInBackground(new GetCallback<ParseObject>(){
						@Override
						public void done(ParseObject object, ParseException e) {
							// TODO Auto-generated method stub
							if(object!=null){
								object.deleteEventually();
								follow.setBackgroundResource(R.drawable.follow);
								follow.setText("follow");
								
								ParseQuery<ParseObject> query = ParseQuery.getQuery("Follow");
								ParseObject obj = ParseObject.createWithoutData("_User",value);
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

							}
							else{
								 ParseObject follows= new ParseObject("Follow");
								 follows.put("from", ParseUser.getCurrentUser());
								 ParseObject obj = ParseObject.createWithoutData("_User",value);
								 follows.put("to", obj);
								 follows.saveInBackground();
								 follow.setBackgroundResource(R.drawable.followcopy);
								 follow.setText("unfollow");
								 ParseQuery<ParseObject> query = ParseQuery.getQuery("Follow");
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
								 
								 
							}
						}
	            		
	            	});

				}
				
				
	});	
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        // Respond to the action bar's Up/Home button
        case android.R.id.home:
          //  NavUtils.navigateUpFromSameTask(this);+
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
	 }
	
	
	
	// Create an Adapter Class extending the BaseAdapter
    class OtherPostCustomAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;
       // Intent in = getIntent();
        Bundle extras = getIntent().getExtras();
        String value = extras.getString("USER_ID");

        
        public OtherPostCustomAdapter(ProfileOtherUser activity) {
            // TODO Auto-generated constructor stub
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	
            obList = new ArrayList<String>();
            
            try {
            	
            	ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Post");
            	ParseObject obj = ParseObject.createWithoutData("_User",value);
	            query.whereEqualTo("PostWriter", obj);
	            query.orderByDescending("createdAt");
	            List<ParseObject>ob = query.find();
	        	for (ParseObject num : ob) {
				    obList.add(num.getObjectId());
					
			}
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            
            //Toast.makeText(getApplicationContext(), value+"", Toast.LENGTH_LONG).show();
            //Toast.makeText(getApplicationContext(), obList.size()+"", Toast.LENGTH_LONG).show();
        }
        
        @Override
		public int getCount() {
			// TODO Auto-generated method stub
			return obList.size();
		}
        
        @
        Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @
        Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public View getView(int position , View convertView, ViewGroup parent) {

            // Inflate the item layout and set the views
            View v = convertView;
            final int pos = position;
            if (v == null) {
                v = layoutInflater.inflate(R.layout.list_row, null);   
            }
            
            // Initialize the views in the layout
            final ImageView iv = (ImageView) v.findViewById(R.id.icon);
    		final TextView tvTitle = (TextView) v.findViewById(R.id.textView1);
            final TextView fullname = (TextView) v.findViewById(R.id.textView2);
            final TextView username = (TextView) v.findViewById(R.id.textView3);
            final TextView tvDesc = (TextView) v.findViewById(R.id.releaseYear);
            ParseImageView picprof = (ParseImageView) v.findViewById(R.id.imageView3);
            
            try {
            	ParseQuery<ParseUser> query2 = ParseUser.getQuery();
            	query2.whereEqualTo("objectId", value);
				List<ParseUser> oo = query2.find();
				for(ParseObject m:oo){
					fullname.setText(m.getString("firstName")+" "+m.getString("LastName")+"");
		            fullname.setAllCaps(true);
		            fullname.setTypeface(font);
		           
		            username.setText("@"+m.getString("username"));
		            username.setTypeface(font);
		    		
					ParseFile imageFile = m.getParseFile("ProfilePic");
					if (imageFile != null) {
		    			picprof.setParseFile(imageFile);
		    			picprof.loadInBackground();
		    		}
		    		else
		    		{
		    				Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.inspiration_6);
		    				picprof.setImageBitmap(bitmap); // for pevieeeewwww		
		    		}
                					
				}
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            
            try {
            	
            	ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Post");
            	query.whereEqualTo("objectId", obList.get(pos));
            	query.orderByDescending("createdAt");
            	List<ParseObject> oo = query.find();
				for(ParseObject ss:oo){
					tvTitle.setText(ss.getString("PostDetail")+"");
					ParseFile fileObject = (ParseFile) ss.get("PostPic");
			        if (fileObject != null) {
			        	fileObject.getDataInBackground(new GetDataCallback() {
							@Override
							public void done(byte[] data, ParseException e) {
								// nothing to do
								Bitmap bmp = BitmapFactory.decodeByteArray(data, 0,data.length);
								// Set the pic in the ImageView
								iv.setImageBitmap(bmp);
							}
						});
			        }
			        else
			        {
			        	iv.setVisibility(View.GONE);
			        }
	            
			        Date datecreate = ss.getCreatedAt();
			        Date cur = Calendar.getInstance().getTime();
			        long t = cur.getTime() - datecreate.getTime();
			        
			        int days = (int) (t / (1000*60*60*24));  
			        int hours = (int) ((t - (1000*60*60*24*days)) / (1000*60*60)); 
			        int min = (int) (t - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
			       
			        
			        tvDesc.setText(days+"d"+hours+"h"+min+"m");
			        tvDesc.setTypeface(font);
			        
			        
				}
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            
            Button rate = (Button) v.findViewById(R.id.button1);
    		rate.setOnClickListener(new OnClickListener() {
    			public void onClick(View arg0) {
    				//showDialog(0);
    				
    				LayoutInflater li = LayoutInflater.from(context);
    				View rate = li.inflate(R.layout.rate, null);
    				AlertDialog.Builder Builder = new AlertDialog.Builder(context);
    				Builder.setView(rate);
    				
    				
    				final Button wow = (Button)rate.findViewById(R.id.button1);
    				Button wtf = (Button)rate.findViewById(R.id.button2);
    				Button good = (Button)rate.findViewById(R.id.button3);
    				
    				final TextView wowcount = (TextView)rate.findViewById(R.id.textView7);
    				final TextView goodcount = (TextView)rate.findViewById(R.id.textView6);
    				final TextView wtfcount = (TextView)rate.findViewById(R.id.textView5);
    				
    				final ParseQuery<ParseObject> ratec = ParseQuery.getQuery("Post_User_Rate");
    				ratec.whereEqualTo("post_id", obList.get(pos));
    				
    				try{
    				ratec.whereEqualTo("rate_type", "1");
    				wowcount.setText(ratec.count()+"");
    				ratec.whereEqualTo("rate_type", "2");
    				goodcount.setText(ratec.count()+"");
    				ratec.whereEqualTo("rate_type", "3");
    				wtfcount.setText(ratec.count()+"");
    				}catch(ParseException e){
    					e.printStackTrace();
    				}
    				
    				wow.setOnClickListener(new View.OnClickListener() {
    					@Override
    					public void onClick(View arg0) {
    						final ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Post_User_Rate");
    						query2.whereEqualTo("user_id", ParseUser.getCurrentUser());
    						query2.whereEqualTo("post_id", obList.get(pos));
    						query2.whereNotEqualTo("rate_type", "1");
    						query2.getFirstInBackground(new GetCallback<ParseObject>(){
    							@Override
    							public void done(ParseObject objects, ParseException e) {
    								ParseObject productRate = new ParseObject("Post_User_Rate");
    								
    								if(objects==null){
    									// TODO Auto-generated method stub
    									productRate.put("post_id", obList.get(pos));
    									productRate.put("user_id", ParseUser.getCurrentUser());
    									productRate.put("rate_type", "1");
    									productRate.saveInBackground();
    									try
    									{
    										wowcount.setText(query2.count()+"");
    										query2.whereEqualTo("rate_type", "2");
    										goodcount.setText(query2.count()+"");
    										query2.whereEqualTo("rate_type", "3");
    										wtfcount.setText(query2.count()+"");
    									}catch(ParseException ee){
    											ee.printStackTrace();
    									}
    								}
    								else{
    									
    									objects.deleteEventually();
    									productRate.put("post_id", obList.get(pos));
    									productRate.put("user_id", ParseUser.getCurrentUser());
    									productRate.put("rate_type", "1");
    									productRate.saveInBackground();
    									try
    									{
    										wowcount.setText(query2.count()+"");
    										query2.whereEqualTo("rate_type", "2");
    										goodcount.setText(query2.count()+"");
    										query2.whereEqualTo("rate_type", "3");
    										wtfcount.setText(query2.count()+"");
    									}catch(ParseException ee){
    											ee.printStackTrace();
    									}
    								}
    							}
    						});
    					}
    					
    				});
    				
    				wtf.setOnClickListener(new View.OnClickListener() {
    					@Override
    					public void onClick(View arg0) {
    						final ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Post_User_Rate");
    						query2.whereEqualTo("user_id", ParseUser.getCurrentUser());
    						query2.whereEqualTo("post_id", obList.get(pos));
    						query2.whereNotEqualTo("rate_type", "3");
    						query2.getFirstInBackground(new GetCallback<ParseObject>(){
    							ParseObject productRate = new ParseObject("Post_User_Rate");
    							
    							@Override
    							public void done(ParseObject objects, ParseException e) {
    								if((objects==null)){
    									// TODO Auto-generated method stub
    									
    									productRate.put("post_id", obList.get(pos));
    									productRate.put("user_id", ParseUser.getCurrentUser());
    									productRate.put("rate_type", "3");
    									productRate.saveInBackground();
    									try
    									{
    										wtfcount.setText(query2.count()+"");
    										query2.whereEqualTo("rate_type", "2");
    										goodcount.setText(query2.count()+"");
    										query2.whereEqualTo("rate_type", "1");
    										wowcount.setText(query2.count()+"");
    									}catch(ParseException ee){
    											ee.printStackTrace();
    									}
    								}else{
    									objects.deleteEventually();
    									productRate.put("post_id", obList.get(pos));
    									productRate.put("user_id", ParseUser.getCurrentUser());
    									productRate.put("rate_type", "3");
    									productRate.saveInBackground();
    									try
    									{
    										wtfcount.setText(query2.count()+"");
    										query2.whereEqualTo("rate_type", "2");
    										goodcount.setText(query2.count()+"");
    										query2.whereEqualTo("rate_type", "1");
    										wowcount.setText(query2.count()+"");
    									}catch(ParseException ee){
    											ee.printStackTrace();
    									}
    								}
    							}
    						});
    					}
    					
    				});
    				
    				good.setOnClickListener(new View.OnClickListener() {
    					@Override
    					public void onClick(View arg0) {
    						final ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Post_User_Rate");
    						query2.whereEqualTo("user_id", ParseUser.getCurrentUser());
    						query2.whereEqualTo("post_id", obList.get(pos));
    						query2.whereNotEqualTo("rate_type", "2");
    						query2.getFirstInBackground(new GetCallback<ParseObject>(){
    							ParseObject productRate = new ParseObject("Post_User_Rate");
    							
    							@Override
    							public void done(ParseObject objects, ParseException e) {
    								if((objects==null)){
    									// TODO Auto-generated method stub
    									
    									productRate.put("post_id", obList.get(pos));
    									productRate.put("user_id", ParseUser.getCurrentUser());
    									productRate.put("rate_type", "2");
    									productRate.saveInBackground();
    									try
    									{
    										goodcount.setText(query2.count()+"");
    										query2.whereEqualTo("rate_type", "3");
    										wtfcount.setText(query2.count()+"");
    										query2.whereEqualTo("rate_type", "1");
    										wowcount.setText(query2.count()+"");
    									}catch(ParseException ee){
    											ee.printStackTrace();
    									}
    								}else{
    									objects.deleteEventually();
    									productRate.put("post_id", obList.get(pos));
    									productRate.put("user_id", ParseUser.getCurrentUser());
    									productRate.put("rate_type", "2");
    									productRate.saveInBackground();
    									try
    									{
    										goodcount.setText(query2.count()+"");
    										query2.whereEqualTo("rate_type", "3");
    										wtfcount.setText(query2.count()+"");
    										query2.whereEqualTo("rate_type", "1");
    										wowcount.setText(query2.count()+"");
    									}catch(ParseException ee){
    											ee.printStackTrace();
    									}
    								}
    							}
    						});
    					}
    					
    				});
    				
    				// create
    				AlertDialog alertDialog = Builder.create();
    				alertDialog.show();
    				
    			}
    			
    		});
            return v;
        }

		

    }
	

}
