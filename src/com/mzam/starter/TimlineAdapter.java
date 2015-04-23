package com.mzam.starter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

public class TimlineAdapter extends ParseQueryAdapter<ParseObject> {
	public TimlineAdapter(final Context context) {
		// Use the QueryFactory to construct a PQA that will only show
		super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
			public ParseQuery create() {

				
				ParseQuery<ParseObject> innerQuery = ParseQuery.getQuery("Follow");
				innerQuery.whereEqualTo("from", ParseUser.getCurrentUser());

				ParseQuery<ParseObject> postFromFollowedUsers = new ParseQuery<ParseObject>("Post");
				postFromFollowedUsers.whereMatchesKeyInQuery("PostWriterstring", "tostring", innerQuery);
				postFromFollowedUsers.orderByDescending("createdAt");
				
				//ParseQuery<ParseObject> photosFromCurrentUserQuery = new ParseQuery<ParseObject>("Post");
				//photosFromCurrentUserQuery.whereEqualTo("PostWriterstring", ParseUser.getCurrentUser().toString());

				// Get the current user's photos
				/*
				List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
				queries.add(postFromFollowedUsers);
				queries.a
				queries.add(photosFromCurrentUserQuery);
				
				ParseQuery<ParseObject> query = ParseQuery.
				query.include("PostWriterstring");
				query.orderByDescending("createdAt");
				*/
				
				
				
				//ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
				//userQuery.whereMatchesKeyInQuery("objectId","tostring", innerQuery);
				/*
				//String s = innerQuery.getParseO("to").getObjectId().toString();
        		// Get the photos from the Users returned in the previous query
				ParseQuery<ParseObject> postFromFollowedUsers = new ParseQuery<ParseObject>("Post");
				//ParseObject obj = ParseObject.createWithoutData("_User",s);
				postFromFollowedUsers.whereMatchesKeyInQuery("PostWriter", "tostring", innerQuery);
				postFromFollowedUsers.orderByDescending("createdAt");
				
				// Get the current user's photos
				ParseQuery<ParseObject> photosFromCurrentUserQuery = new ParseQuery<ParseObject>("Post");
				photosFromCurrentUserQuery.whereEqualTo("PostWriter", ParseUser.getCurrentUser());
				
				ParseQuery<ParseObject> query = ParseQuery.or(Arrays.asList(postFromFollowedUsers, photosFromCurrentUserQuery ));
				query.include("PostWriter");
				query.orderByDescending("createdAt");
				*/
				return postFromFollowedUsers;
        		
				
			}
		});
	}
	
	public View getItemView(final ParseObject object, View v, ViewGroup parent) {

		if (v == null) {
			v = View.inflate(getContext(), R.layout.timeline_row, null);
		}

		super.getItemView(object, v, parent);

		final ImageView iv = (ImageView) v.findViewById(R.id.icon);
		final ImageView delete = (ImageView) v.findViewById(R.id.imageView5);
        final TextView tvTitle = (TextView) v.findViewById(R.id.textView1);
        final TextView fullname = (TextView) v.findViewById(R.id.textView2);
        final TextView username = (TextView) v.findViewById(R.id.textView3);
        final TextView tvDesc = (TextView) v.findViewById(R.id.releaseYear);
        ParseImageView picprof = (ParseImageView) v.findViewById(R.id.imageView3);
        final ImageView replayComment = (ImageView) v.findViewById(R.id.imageView1);
      
        final Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Fonts/Rosemary.ttf");
        //Toast.makeText(getContext(), object.getObjectId()+"", Toast.LENGTH_SHORT).show();
		
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
        
        
        tvTitle.setText(object.getString("PostDetail")+"");
        
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

        ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
        userQuery.getInBackground(object.getString("PostWriterstring"), new GetCallback<ParseUser>(){
			@Override
			public void done(ParseUser ss, ParseException e) {
				// TODO Auto-generated method stub
				
				fullname.setText(ss.getString("firstName")+" "+ss.getString("LastName")+"");
		        fullname.setAllCaps(true);
		        fullname.setTypeface(font);
		        username.setText("@"+ss.getUsername()+"");

			}
        	
        });
        
        replayComment.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in = new Intent(getContext(),AddPostComment.class);
				in.putExtra("POST_ID", object.getObjectId());
				getContext().startActivity(in);
			}
		});
	 
	v.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent in = new Intent(getContext(),SinglePostView.class);
			in.putExtra("POST_ID", object.getObjectId());
			getContext().startActivity(in);
		}
	});
        
		return v;
	}
}