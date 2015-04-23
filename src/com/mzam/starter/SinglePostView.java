package com.mzam.starter;

import java.util.Calendar;
import java.util.Date;

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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SinglePostView extends Activity {
	Context context = this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.singlepostcommentview);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6adbd9"))); // set your desired color
		
		ListView commentsadapter = (ListView) findViewById(R.id.listview);
		
		final ImageView comment = (ImageView) findViewById(R.id.imageView1);
		final ImageView iv = (ImageView) findViewById(R.id.icon);
		final ImageView delete = (ImageView) findViewById(R.id.imageView5);
        final TextView tvTitle = (TextView) findViewById(R.id.textView1);
        final TextView fullname = (TextView) findViewById(R.id.textView2);
        final TextView username = (TextView) findViewById(R.id.textView3);
        final TextView tvDesc = (TextView) findViewById(R.id.releaseYear);
        final ParseImageView picprof = (ParseImageView) findViewById(R.id.imageView3);

        final Typeface font = Typeface.createFromAsset(getAssets(),"Fonts/Rosemary.ttf");
        
        commentsadapter.setAdapter(new CommentCustomAdapter(this));
        
		final String value = getIntent().getExtras().getString("POST_ID");
		comment.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in = new Intent (SinglePostView.this,AddProductComment.class);
				in.putExtra("POST_ID", value);
				startActivity(in);
			}
		});

		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Post");
        query.getInBackground(value, new GetCallback<ParseObject>(){

			@Override
			public void done(final ParseObject object, ParseException e) {
				// TODO Auto-generated method stub
				ParseFile fileObject = (ParseFile) object.get("PostPic");
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
				
		        delete.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						object.deleteEventually();
						finish();								    	
					}
				});
		        
		       
		        Date datecreate = object.getCreatedAt();
		        Date cur = Calendar.getInstance().getTime();
		        long t = cur.getTime() - datecreate.getTime();
		        
		        int days = (int) (t / (1000*60*60*24));  
		        int hours = (int) ((t - (1000*60*60*24*days)) / (1000*60*60)); 
		        int min = (int) (t - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
		       
		        
		        tvDesc.setText(days+"d"+hours+"h"+min+"m");
		        tvDesc.setTypeface(font);
		        
		        tvTitle.setText(object.getString("PostDetail")+"");
		        
		        Button rate = (Button) findViewById(R.id.button1);
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
						ratec.whereEqualTo("post_id", object.getObjectId());
						
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
								query2.whereEqualTo("post_id", object.getObjectId());
								query2.whereNotEqualTo("rate_type", "1");
								query2.getFirstInBackground(new GetCallback<ParseObject>(){
									@Override
									public void done(ParseObject objects, ParseException e) {
										ParseObject productRate = new ParseObject("Post_User_Rate");
										
										if(objects==null){
											// TODO Auto-generated method stub
											productRate.put("post_id", object.getObjectId());
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
											productRate.put("post_id", object.getObjectId());
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
								query2.whereEqualTo("post_id", object.getObjectId());
								query2.whereNotEqualTo("rate_type", "3");
								query2.getFirstInBackground(new GetCallback<ParseObject>(){
									ParseObject productRate = new ParseObject("Post_User_Rate");
									
									@Override
									public void done(ParseObject objects, ParseException e) {
										if((objects==null)){
											// TODO Auto-generated method stub
											
											productRate.put("post_id", object.getObjectId());
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
											productRate.put("post_id", object.getObjectId());
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
								query2.whereEqualTo("post_id", object.getObjectId());
								query2.whereNotEqualTo("rate_type", "2");
								query2.getFirstInBackground(new GetCallback<ParseObject>(){
									ParseObject productRate = new ParseObject("Post_User_Rate");
									
									@Override
									public void done(ParseObject objects, ParseException e) {
										if((objects==null)){
											// TODO Auto-generated method stub
											
											productRate.put("post_id", object.getObjectId());
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
											productRate.put("post_id", object.getObjectId());
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
				
		        ParseQuery<ParseUser> queryuser = ParseQuery.getUserQuery();
		        queryuser.getInBackground(object.getParseUser("PostWriter").getObjectId(), new GetCallback<ParseUser>(){

					@Override
					public void done(ParseUser objects, ParseException e) {
						// TODO Auto-generated method stub
						
						fullname.setText(objects.getString("firstName")+" "+objects.getString("LastName")+"");
				        fullname.setAllCaps(true);
				        fullname.setTypeface(font);
				        username.setText("@"+objects.getUsername());
				        username.setTypeface(font);
				        ParseFile imageFile = objects.getParseFile("ProfilePic");
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
		        	
		        });
		
		        
			}
        	
        });
		
		
		
	}
	
	public class CommentCustomAdapter extends ParseQueryAdapter<ParseObject> {
		//ParseUser fl =  new ParseUser();
		public CommentCustomAdapter(final Context context) {
			// Use the QueryFactory to construct a PQA that will only show
			// Todos marked as high-pri
			super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
				public ParseQuery create() {
					String value = getIntent().getExtras().getString("POST_ID");
					
					ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Post_User_Comment");
		            query.whereEqualTo("post_id", value);
		            query.orderByDescending("createdAt");
					return query;
				}
			});
		}

		
		// Customize the layout by overriding getItemView
		@Override
		public View getItemView(final ParseObject object, View v, ViewGroup parent) {
			if (v == null) {
				v = View.inflate(getContext(), R.layout.list_row, null);
			}

			
			super.getItemView(object, v, parent);
			
			final ImageView replayComment = (ImageView) v.findViewById(R.id.imageView1);
	        
			final ImageView iv = (ImageView) v.findViewById(R.id.icon);
			final ImageView delete = (ImageView) v.findViewById(R.id.imageView5);
			final ImageView repost = (ImageView) v.findViewById(R.id.imageView6);
	        final TextView tvTitle = (TextView) v.findViewById(R.id.textView1);
	        final TextView fullname = (TextView) v.findViewById(R.id.textView2);
	        final TextView username = (TextView) v.findViewById(R.id.textView3);
	        final TextView tvDesc = (TextView) v.findViewById(R.id.releaseYear);
	        final ParseImageView picprof = (ParseImageView) v.findViewById(R.id.imageView3);
	        Button rate = (Button) v.findViewById(R.id.button1);
	        final Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Fonts/Rosemary.ttf");
	       
	        iv.setVisibility(View.GONE);
	        replayComment.setVisibility(View.GONE);
	        repost.setVisibility(View.GONE);
	        rate.setVisibility(View.GONE);
	        delete.setY(-70);
	        ParseQuery<ParseUser> queryuser = ParseQuery.getUserQuery();
	        queryuser.getInBackground(object.getParseUser("user_id").getObjectId(), new GetCallback<ParseUser>(){

				@Override
				public void done(ParseUser objects, ParseException e) {
					// TODO Auto-generated method stub
					
					fullname.setText(objects.getString("firstName")+" "+objects.getString("LastName")+"");
			        fullname.setAllCaps(true);
			        fullname.setTypeface(font);
			        username.setText("@"+objects.getUsername());
			        username.setTypeface(font);
			        ParseFile imageFile = objects.getParseFile("ProfilePic");
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
	        	
	        });
			
	        tvTitle.setText(object.getString("comment_text")+"");
	        Date datecreate = object.getCreatedAt();
	        Date cur = Calendar.getInstance().getTime();
	        long t = cur.getTime() - datecreate.getTime();
	        
	        int days = (int) (t / (1000*60*60*24));  
	        int hours = (int) ((t - (1000*60*60*24*days)) / (1000*60*60)); 
	        int min = (int) (t - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
	       
	        
	        tvDesc.setText(days+"d"+hours+"h"+min+"m");
	        tvDesc.setTypeface(font);
	        
	        delete.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					object.deleteEventually();
							    	
				}
			});
	        
	        v.setBackgroundColor(Color.parseColor("#e7e7e7"));
	   
			return v;
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

}
