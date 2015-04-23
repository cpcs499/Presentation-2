package com.mzam.starter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

public class ShopCustomAdapter extends ParseQueryAdapter<ParseObject> {
			//ParseUser fl =  new ParseUser();
			public ShopCustomAdapter(final Context context) {
				// Use the QueryFactory to construct a PQA that will only show
				// Todos marked as high-pri
				super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
					public ParseQuery create() {
						ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("shop");
			            query.whereEqualTo("UserOpen", ParseUser.getCurrentUser());
			            query.orderByDescending("createdAt");
						return query;
					}
				});
			}

			
			// Customize the layout by overriding getItemView
			@Override
			public View getItemView(final ParseObject object, View v, ViewGroup parent) {
				if (v == null) {
					v = View.inflate(getContext(), R.layout.shop_row, null);
				}

				
				super.getItemView(object, v, parent);
				
				Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Fonts/Rosemary.ttf");
				
				final TextView shoppname = (TextView) v.findViewById(R.id.textView1);
	            shoppname.setText(object.getString("shop_name"));
	            shoppname.setTypeface(font);
	            
	            final TextView shopdesc = (TextView) v.findViewById(R.id.textView2);
	            shopdesc.setText(object.getString("shop_desc"));
	            shopdesc.setTypeface(font);
	            
	            final ParseImageView shopImg = (ParseImageView) v.findViewById(R.id.imageView1);
	            ParseFile imageFile = object.getParseFile("shopImage");
	    		if (imageFile != null) {
	    			shopImg.setParseFile(imageFile);
	    			shopImg.loadInBackground();
	    		}
	    		else
	    		{
	    				Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.default_shoppic);
	    				shopImg.setImageBitmap(bitmap); // for pevieeeewwww		
	    		}
	    		
	    		
	    		v.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(getContext(), ShopSingleItemView.class);
						intent.putExtra("SHOP_ID", object.getObjectId());
						intent.putExtra("SHOP_NAME", object.getString("shop_name"));
						intent.putExtra("SHOP_DESC", object.getString("shop_desc"));
						getContext().startActivity(intent);
						//Toast.makeText(getContext(), object.getObjectId()+"", Toast.LENGTH_SHORT).show();
						
					}
				});
				
    
				return v;
			}

}
