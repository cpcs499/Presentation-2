package com.mzam.starter;

import java.util.Calendar;
import java.util.Date;

import com.mzam.starter.SinglePostView.CommentCustomAdapter;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ProductComments extends Activity {
	Context context = this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.productcommentlist);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6adbd9"))); // set your desired color
		
		
		final String value = getIntent().getExtras().getString("PRODUCT_ID");
		
		ListView commentsadapter = (ListView) findViewById(R.id.productcomment_list_view);
		commentsadapter.setAdapter(new CommentCustomAdapter(this));
		
		Button add = (Button)findViewById(R.id.button1);
		add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in = new Intent(ProductComments.this,AddProductComment.class);
				in.putExtra("PRODUCT_ID", value);
				startActivity(in);
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
					String value = getIntent().getExtras().getString("PRODUCT_ID");
					ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Product_User_Comment");
		            query.whereEqualTo("product_id", value);
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
	        
	      v.setBackgroundColor(Color.parseColor("#fbfcf7"));
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
