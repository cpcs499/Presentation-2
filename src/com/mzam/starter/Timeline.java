package com.mzam.starter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Timeline extends Activity {

	List<ParseObject> ob,os,obccc;
	List<String> obList ,obList2,obListsize,obListff,obListff3,obListff5,obListff6;
	List<Date> obListff4;
	
	//int obListsize;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timeline);	
		// TODO Auto-generated method stub

		
	
		
	}	
	
	public void onResume(){
		super.onResume();{
			ListView nn = (ListView) findViewById(R.id.listView1);
			//nn.setAdapter(new TimlineAdapter(this));
			nn.setAdapter(new VersiAdapter(this));
			
			/*
			ParseQuery<ParseObject> innerQuery = ParseQuery.getQuery("Follow");
			innerQuery.whereEqualTo("from", ParseUser.getCurrentUser());

			ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
			userQuery.whereMatchesKeyInQuery("objectId","tostring", innerQuery);
			
			userQuery.findInBackground(new FindCallback<ParseUser>() {
			@Override
			public void done(List<ParseUser> objects, ParseException e) {
				// TODO Auto-generated method stub
				
				for (ParseObject i : objects) {
					
				Toast.makeText(getApplicationContext(), i.getObjectId()+"", Toast.LENGTH_SHORT).show();
				}
			}
			});
			*/
			
		}
	}
	
	// Create an Adapter Class extending the BaseAdapter
    class VersiAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;
      
        public VersiAdapter(Timeline activity) {
            // TODO Auto-generated constructor stub
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	
            obList = new ArrayList<String>();
            obList2 = new ArrayList<String>();
            obListsize = new ArrayList<String>();
            obListff = new ArrayList<String>();
            obListff3 = new ArrayList<String>();
            obListff4 = new ArrayList<Date>();
            obListff5 = new ArrayList<String>();
            obListff6 = new ArrayList<String>();
            try {
            	
            	ParseQuery<ParseObject> innerQuery = ParseQuery.getQuery("Follow");
				innerQuery.whereEqualTo("from", ParseUser.getCurrentUser());
				ParseQuery<ParseObject> postFromFollowedUsers = new ParseQuery<ParseObject>("Post");
				postFromFollowedUsers.whereMatchesKeyInQuery("PostWriterstring", "tostring", innerQuery);
				postFromFollowedUsers.orderByDescending("createdAt");
				
	        	ob = postFromFollowedUsers.find();
	        	for (ParseObject num : ob) {
	        		
				obList.add(num.getString("PostWriterstring"));
				}
            	
	        	
	        	ParseQuery<ParseObject> photosFromCurrentUserQuery = new ParseQuery<ParseObject>("Post");
				photosFromCurrentUserQuery.whereEqualTo("PostWriterstring", ParseUser.getCurrentUser().getObjectId());
				photosFromCurrentUserQuery.orderByDescending("createdAt");
				os = photosFromCurrentUserQuery.find();
				
				for (ParseObject num2 : os) {
				obList2.add(num2.getString("PostWriterstring"));
				}
				
				obListsize.addAll(obList);
				obListsize.addAll(obList2);
				
				ParseQuery<ParseObject> tryyy = new ParseQuery<ParseObject>("Post");
				//for(int i=0;i<obListsize.size();i++){
					tryyy.whereContainedIn("PostWriterstring", obListsize);
				//tryyy.whereEqualTo("PostWriterstring", obListsize.get(i));
				tryyy.orderByDescending("createdAt");
				obccc = tryyy.find();
	        	for (ParseObject num : obccc) {
				obListff.add(num.getString("PostDetail"));
				obListff3.add(num.getObjectId());
				obListff4.add(num.getCreatedAt());
				obListff5.add(num.getString("PostWriterstring"));
				obListff6.add(num.getString("Product_Name"));
	        	//}
	        	
	        	
				}
					
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            
            //Toast.makeText(getApplicationContext(), value+"", Toast.LENGTH_LONG).show();
           // Toast.makeText(getApplicationContext(), obListsize.size()+"", Toast.LENGTH_LONG).show();
        }
        
        @Override
		public int getCount() {
			// TODO Auto-generated method stub
			return (obListsize.size());
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

        @
        Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // Inflate the item layout and set the views
            View listItem = convertView;
            final int pos = position;
            if (listItem == null) {
                listItem = layoutInflater.inflate(R.layout.timeline_row, null);   
            }
            
            final ImageView iv = (ImageView) listItem.findViewById(R.id.icon);
    		//final ImageView delete = (ImageView) listItem.findViewById(R.id.imageView5);
            final TextView PostDetailssss = (TextView) listItem.findViewById(R.id.textView1);
            final TextView fullname = (TextView) listItem.findViewById(R.id.textView2);
            final TextView username = (TextView) listItem.findViewById(R.id.textView3);
            final TextView tvDesc = (TextView) listItem.findViewById(R.id.releaseYear);
            final ParseImageView picprof = (ParseImageView) listItem.findViewById(R.id.imageView3);
            final ImageView replayComment = (ImageView) listItem.findViewById(R.id.imageView1);
            
            //final Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Fonts/Rosemary.ttf");
            
            final Typeface font = Typeface.createFromAsset(getAssets(),"Fonts/Rosemary.ttf");
            //Toast.makeText(getContext(), object.getObjectId()+"", Toast.LENGTH_SHORT).show();
          
            //Toast.makeText(getApplicationContext(), obListsize.get(pos), Toast.LENGTH_SHORT).show();
            
            PostDetailssss.setText(obListff.get(pos)+"");
            
            ParseQuery<ParseObject> getInfo = ParseQuery.getQuery("Post");
            //getInfo.whereEqualTo("PostWriterstring", obListff5.get(pos));
            getInfo.getInBackground(obListff3.get(pos),new GetCallback<ParseObject>(){
				@Override
				public void done(ParseObject objectsssss, ParseException e) {
					// TODO Auto-generated method stub
					ParseFile fileObject = (ParseFile) objectsssss.get("PostPic");
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
				}
            	
            });
            
           //Toast.makeText(getContext(), object.getObjectId()+"", Toast.LENGTH_SHORT).show();
    		
            
            
            Date datecreate =  obListff4.get(pos);
            Date cur = Calendar.getInstance().getTime();
            long t = cur.getTime() - datecreate.getTime();
            
            int days = (int) (t / (1000*60*60*24));  
            int hours = (int) ((t - (1000*60*60*24*days)) / (1000*60*60)); 
            int min = (int) (t - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
           
            
            tvDesc.setText(days+"d"+hours+"h"+min+"m");
            tvDesc.setTypeface(font);
            
            ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
            userQuery.getInBackground(obListff5.get(pos), new GetCallback<ParseUser>(){
    			@Override
    			public void done(ParseUser ss, ParseException e) {
    				// TODO Auto-generated method stub
    				
    				fullname.setText(ss.getString("firstName")+" "+ss.getString("LastName")+"");
    		        fullname.setAllCaps(true);
    		        fullname.setTypeface(font);
    		        username.setText("@"+ss.getUsername()+"");
    		        
    		        ParseFile imageFile = ss.getParseFile("ProfilePic");
    				if (imageFile != null) {
    					picprof.setParseFile(imageFile);
    					picprof.loadInBackground();
    				}
    				else
    				{
    						Bitmap bitmap = BitmapFactory.decodeResource(getBaseContext().getResources(),R.drawable.inspiration_6);
    						picprof.setImageBitmap(bitmap); // for pevieeeewwww		
    				}

    			}
            	
            });
            
            replayComment.setOnClickListener(new View.OnClickListener() {
    			@Override
    			public void onClick(View arg0) {
    				// TODO Auto-generated method stub
    				ParseQuery<ParseObject> prodid = ParseQuery.getQuery("Product");
                	prodid.whereEqualTo("ProductName", obListff6.get(pos));
                	prodid.getFirstInBackground(new GetCallback<ParseObject>(){
    					@Override
    					public void done(ParseObject object, ParseException e) {
    						// TODO Auto-generated method stub
    						//object.getObjectId();
    						if(object!=null){
    							Intent in = new Intent(getBaseContext(),AddProductComment.class);
    		    				in.putExtra("PRODUCT_ID", obListff3.get(pos));
    		    				startActivity(in);
    						}
    						else{
    							Intent in = new Intent(getBaseContext(),AddPostComment.class);
    		    				in.putExtra("POST_ID", obListff3.get(pos));
    		    				startActivity(in);
    						}
    				
    			}
    		});
            
    			}
            });
            
            picprof.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent in = new Intent(getBaseContext(),ProfileOtherUser.class);
        			in.putExtra("USER_ID", obListff5.get(pos));
        			startActivity(in);
				}
			});
            
            
            listItem.setOnClickListener(new View.OnClickListener() {
        		@Override
        		public void onClick(View arg0) {
        			// TODO Auto-generated method stub

        			//if(!obListff6.get(pos).equals("")){
        				ParseQuery<ParseObject> prodid = ParseQuery.getQuery("Product");
                    	prodid.whereEqualTo("ProductName", obListff6.get(pos));
                    	prodid.getFirstInBackground(new GetCallback<ParseObject>(){
        					@Override
        					public void done(ParseObject object, ParseException e) {
        						// TODO Auto-generated method stub
        						//object.getObjectId();
        						if(object!=null){
        						Intent in = new Intent(getBaseContext(),ProductPage.class);
        	        			in.putExtra("productid", object.getObjectId());
        	        			Toast.makeText(getApplicationContext(), 
        	        					object.getObjectId()+"",
        	    						Toast.LENGTH_SHORT).show();
        	        			startActivity(in);
        						}
        						else{
        							
        		        				Intent in = new Intent(getBaseContext(),SinglePostView.class);
        		            			in.putExtra("POST_ID", obListff3.get(pos));
        		            			startActivity(in);
        		        			
        						}
        					}
                    		
                    	});
        			
        			//}
        			
        		}
        	});
            
            return listItem;
        }

		

    }
}
