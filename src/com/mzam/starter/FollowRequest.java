package com.mzam.starter;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class FollowRequest extends Activity {

	ListView listView ;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_order_list);	
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6adbd9"))); // set your desired color
		
		ListView hh = (ListView)findViewById(R.id.user_order_list_view);
		hh.setAdapter(new FollowRequestCustomAdapter(this));
	
		
	}
	
	public class FollowRequestCustomAdapter extends ParseQueryAdapter<ParseObject> {
		//ParseUser fl =  new ParseUser();
		public FollowRequestCustomAdapter(final Context context) {
			// Use the QueryFactory to construct a PQA that will only show
			// Todos marked as high-pri
			super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
				public ParseQuery create() {
					ParseQuery<ParseObject> query = ParseQuery.getQuery("Follow");
					query.whereEqualTo("to", ParseUser.getCurrentUser());
					query.whereEqualTo("follow_request_status","Processing");
		            query.orderByDescending("createdAt");
					return query;
				}
			});
		}

	
		// Customize the layout by overriding getItemView
		@Override
		public View getItemView(final ParseObject object, View v, ViewGroup parent) {
			if (v == null) {
				v = View.inflate(getContext(), R.layout.followrequest, null);
			}

			super.getItemView(object, v, parent);

			final TextView whorequest = (TextView) v.findViewById(R.id.textView1);
	        final Button accept = (Button) v.findViewById(R.id.button1);
	      
	        final Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Fonts/Rosemary.ttf");
	        
	        ParseQuery<ParseUser> querys = ParseQuery.getUserQuery();
	        querys.getInBackground(object.getParseUser("from").getObjectId().toString(), new GetCallback<ParseUser>(){

				@Override
				public void done(ParseUser objectk, ParseException e) {
					// TODO Auto-generated method stub
					whorequest.setText(objectk.getUsername()+" wants to follow you");
					whorequest.setTypeface(font);
				}
	        	
	        });

	        accept.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					object.put("follow_request_status", "Accepted");
					object.saveInBackground();
					
				}
			});
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
