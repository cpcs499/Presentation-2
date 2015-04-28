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
import android.content.DialogInterface;
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
import android.widget.Toast;

public class SinglePostView extends Activity {
	Context context = this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.singlepostcommentview);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6adbd9"))); // set your desired color
		
		
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
				v = View.inflate(getContext(), R.layout.commentview, null);
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
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setMessage("Are You Sure ?");
					builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					               // User clicked OK button
					        	   object.deleteEventually();
					        	   Toast.makeText(getApplicationContext(), "Post Deleted", Toast.LENGTH_SHORT).show();
					        	   onResume();
					           }
					       });
					builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					               // User cancelled the dialog
					        	   dialog.dismiss();
					           }
					       });
					AlertDialog alertDialog = builder.create();
					alertDialog.show();
							    	
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
	
	public void onResume(){
		super.onResume();{
			
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
					Intent in = new Intent (SinglePostView.this,AddPostComment.class);
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
							AlertDialog.Builder builder = new AlertDialog.Builder(context);
							builder.setMessage("Are You Sure ?");
							builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
							           public void onClick(DialogInterface dialog, int id) {
							               // User clicked OK button
							        	   object.deleteEventually();
										   finish();	
							        	   Toast.makeText(getApplicationContext(), "Post Deleted", Toast.LENGTH_SHORT).show();
							        	  //onResume();
							           }
							       });
							builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							           public void onClick(DialogInterface dialog, int id) {
							               // User cancelled the dialog
							        	   dialog.dismiss();
							           }
							       });
							AlertDialog alertDialog = builder.create();
							alertDialog.show();
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
	        
			Button wow = (Button)findViewById(R.id.button1);
			Button wtf = (Button)findViewById(R.id.button2);
			Button good = (Button)findViewById(R.id.button3);
			
			final TextView wowcount = (TextView)findViewById(R.id.textView7);
			final TextView goodcount = (TextView)findViewById(R.id.textView6);
			final TextView wtfcount = (TextView)findViewById(R.id.TextView01);
			
			//final String value = getIntent().getExtras().getString("POST_ID");
			
			final ParseQuery<ParseObject> ratec = ParseQuery.getQuery("Post_User_Rate");
			ratec.whereEqualTo("post_id", value);
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
			
			final ParseQuery<ParseObject> rate = ParseQuery.getQuery("Post_User_Rate");
			rate.whereEqualTo("user_id", ParseUser.getCurrentUser());
			rate.whereEqualTo("post_id", value);
			final ParseObject postRate = new ParseObject("Post_User_Rate");
			
			wow.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					rate.getFirstInBackground(new GetCallback<ParseObject>(){
						
						
						@Override
						public void done(ParseObject object, ParseException e) {
							// TODO Auto-generated method stub
							if(object!=null && object.getString("rate_type").equals("1"))
							{
								Toast.makeText(getApplicationContext(), "you chhose it before", Toast.LENGTH_SHORT).show();
							}
							else if(object!=null&&(object.getString("rate_type").equals("2")||object.getString("rate_type").equals("3")))
							{
								postRate.put("rate_type", "1");
								postRate.saveInBackground();
								Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
							}
							else if (object==null)
							{
								Toast.makeText(getApplicationContext(), "Not Exist", Toast.LENGTH_SHORT).show();
								postRate.put("post_id", value);
								postRate.put("user_id", ParseUser.getCurrentUser());
								postRate.put("rate_type", "1");
								postRate.saveInBackground();
							}	
							
							try
							{
							rate.whereEqualTo("rate_type", "1");
							wowcount.setText(rate.count()+"");
							rate.whereEqualTo("rate_type", "2");
							goodcount.setText(rate.count()+"");
							rate.whereEqualTo("rate_type", "3");
							wtfcount.setText(rate.count()+"");
							}catch(ParseException ee){
								ee.printStackTrace();
							}
						}
						
					});
					
				}
				
			});
			
			good.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					rate.getFirstInBackground(new GetCallback<ParseObject>(){
						@Override
						public void done(ParseObject object, ParseException e) {
							// TODO Auto-generated method stub
							if(object!=null && object.getString("rate_type").equals("2"))
							{
								Toast.makeText(getApplicationContext(), "you chhose it before", Toast.LENGTH_SHORT).show();
							}
							else if(object!=null&&(object.getString("rate_type").equals("1")||object.getString("rate_type").equals("3")))
							{
								postRate.put("rate_type", "2");
								postRate.saveInBackground();
								Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
							}
							else if (object==null)
							{
								Toast.makeText(getApplicationContext(), "Not Exist", Toast.LENGTH_SHORT).show();
								postRate.put("post_id", value);
								postRate.put("user_id", ParseUser.getCurrentUser());
								postRate.put("rate_type", "2");
								postRate.saveInBackground();
							}
							try
							{
							rate.whereEqualTo("rate_type", "1");
							wowcount.setText(rate.count()+"");
							rate.whereEqualTo("rate_type", "2");
							goodcount.setText(rate.count()+"");
							rate.whereEqualTo("rate_type", "3");
							wtfcount.setText(rate.count()+"");
							}catch(ParseException ee){
								ee.printStackTrace();
							}
							
						}
					});
					
				}
			});
			
			wtf.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					rate.getFirstInBackground(new GetCallback<ParseObject>(){
						@Override
						public void done(ParseObject object, ParseException e) {
							// TODO Auto-generated method stub
							if(object!=null && object.getString("rate_type").equals("3"))
							{
								Toast.makeText(getApplicationContext(), "you chhose it before", Toast.LENGTH_SHORT).show();
							}
							else if(object!=null&&(object.getString("rate_type").equals("1")||object.getString("rate_type").equals("2")))
							{
								postRate.put("rate_type", "3");
								postRate.saveInBackground();
								
								
								Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
							
							}
							else if (object==null)
							{
								Toast.makeText(getApplicationContext(), "Not Exist", Toast.LENGTH_SHORT).show();
								postRate.put("post_id", value);
								postRate.put("user_id", ParseUser.getCurrentUser());
								postRate.put("rate_type", "3");
								postRate.saveInBackground();
								
							}
							try
							{
							rate.whereEqualTo("rate_type", "1");
							wowcount.setText(rate.count()+"");
							rate.whereEqualTo("rate_type", "2");
							goodcount.setText(rate.count()+"");
							rate.whereEqualTo("rate_type", "3");
							wtfcount.setText(rate.count()+"");
							}catch(ParseException ee){
								ee.printStackTrace();
							}
						}
					});
					
					
				}
			});
		}
	}

}
