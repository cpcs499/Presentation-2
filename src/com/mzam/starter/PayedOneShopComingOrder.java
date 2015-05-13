package com.mzam.starter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.CursorJoiner.Result;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class PayedOneShopComingOrder extends Activity {

	ListView listView ;
	ImageView userpic;
	List<String> obList ,obList2,obList3,obList4,obList5,obList6;
	List<ParseObject> ob,os;
	List<ParseUser> ou;
	ParseUser fl = ParseUser.getCurrentUser();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shopcomingorders_main);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		listView = (ListView) findViewById(R.id.listView1);
		  
		  Bundle extras = getIntent().getExtras();
	      String value = extras.getString("SHOP_ID");
	      Toast.makeText(getApplicationContext(), value+"", Toast.LENGTH_LONG).show();
          
		  listView.setAdapter(new VersiAdapter(this));
		  
			
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
	
	// Create an Adapter Class extending the BaseAdapter
    class VersiAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;
       // Intent in = getIntent();
        Bundle extras = getIntent().getExtras();
        String value = extras.getString("SHOP_ID");

        
        public VersiAdapter(PayedOneShopComingOrder activity) {
            // TODO Auto-generated constructor stub
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	
            obList = new ArrayList<String>();
            obList2 = new ArrayList<String>();
            obList3 = new ArrayList<String>();
            obList4 = new ArrayList<String>();
            obList5 = new ArrayList<String>();
            obList6 = new ArrayList<String>();
            try {
            	
        	ParseQuery<ParseObject> innerQuery = ParseQuery.getQuery("Order");
        	ParseObject obj = ParseObject.createWithoutData("shop",value);
        	innerQuery.whereEqualTo("order_status", "Payed"); // Submitted == not Confirmed yet
        	innerQuery.orderByDescending("createdAt");
        	innerQuery.whereEqualTo("ShopId", obj);
        	ob = innerQuery.find();
        	for (ParseObject num : ob) {
			obList.add(num.getParseObject("ShopId").getObjectId());
			obList2.add(num.getParseObject("user_id").getObjectId());
			obList3.add(num.getParseObject("productId").getObjectId());
			obList4.add(num.getObjectId());
			obList5.add(num.getString("payment_type"));
			obList6.add(num.getString("order_status"));
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

        @
        Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // Inflate the item layout and set the views
            View listItem = convertView;
            final int pos = position;
            if (listItem == null) {
                listItem = layoutInflater.inflate(R.layout.all_orders_row, null);   
            }
            
            // Initialize the views in the layout
             final TextView profileusername = (TextView) listItem.findViewById(R.id.textView1);
            final ImageView iv = (ImageView) listItem.findViewById(R.id.imageView1);
            final TextView Productname = (TextView) listItem.findViewById(R.id.textView2);
            final TextView ProductQty = (TextView) listItem.findViewById(R.id.textView3);
            final TextView TotalCost = (TextView) listItem.findViewById(R.id.textView4);
            final TextView shopNAme = (TextView) listItem.findViewById(R.id.textView6);
            final TextView paymentMethod = (TextView) listItem.findViewById(R.id.textView7);

            Spinner orderstatues = (Spinner) listItem.findViewById(R.id.spinner1);
            
            paymentMethod.setText("Payment Type: "+obList5.get(pos)+"");
            shopNAme.setVisibility(View.GONE);
            
            try{
                
    			ParseQuery<ParseUser> vv = ParseUser.getQuery();
            	vv.whereEqualTo("objectId", obList2.get(pos));
            	List<ParseUser> oo9 = vv.find();
    			for(int i = 0; i < oo9.size(); i++) {
     				profileusername.setText("@"+oo9.get(i).get("username").toString()+"");
    				
    				ParseFile fileObject = (ParseFile) oo9.get(i).get("ProfilePic");
            		fileObject.getDataInBackground(new GetDataCallback() {
            			public void done(byte[] data,ParseException e) {
            				if (e == null) {
            					Bitmap bmp = BitmapFactory.decodeByteArray(data, 0,data.length);
            					iv.setImageBitmap(bmp);
            						} else {
    							Bitmap bmp2 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
    	    					iv.setImageBitmap(bmp2);
            						}
            					}
            				});
    				}
    				
            	ParseQuery<ParseObject> cc2 = ParseQuery.getQuery("Product");
            	cc2.whereEqualTo("objectId", obList3.get(pos));
            	List<ParseObject> oo3 = cc2.find();
    			for(ParseObject m2:oo3){
    				Productname.setText("Product name: "+m2.get("ProductName").toString()+"");
    			}
    			
    				ParseQuery<ParseObject> j = ParseQuery.getQuery("Ordered_Product");
    				ParseObject obj2 = ParseObject.createWithoutData("Product",obList3.get(pos));
    				j.whereEqualTo("product_id", obj2);
    				List<ParseObject> gg2 = j.find();
    				
    				for(ParseObject c2:gg2){
    					//bb2.add(c2.getParseObject("ShopId").getObjectId());
    					ProductQty.setText("Product Qty: "+c2.getInt("product_quantity")+"");
    		    		TotalCost.setText("Cost: "+c2.getInt("product_quantity")*c2.getDouble("unit_cost"));
    		    		
    		    		/*
        				ParseFile fileObject = (ParseFile) c2.get("product_pic");
        				if(fileObject!=null){
        				fileObject.getDataInBackground(new GetDataCallback() {
                			public void done(byte[] data,ParseException e) {
                				if (e == null) {
                					Bitmap bmp = BitmapFactory.decodeByteArray(data, 0,data.length);
                					iv.setImageBitmap(bmp);
                						} else {
                							
                						}
                					}
                				});
        				}
        				*/

    				}
            }catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		
            
            if((obList6.get(pos)).equals("Confirmed")){
            	
            ArrayAdapter<CharSequence> sa = ArrayAdapter.createFromResource(getApplicationContext(),
                    R.array.order_payed_statues, android.R.layout.simple_spinner_item);
            sa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            orderstatues.setAdapter(sa);
            }
            
            orderstatues.setOnItemSelectedListener(new OnItemSelectedListener(){
				@Override
				public void onItemSelected(final AdapterView<?> parent, View view,
						final int position, long id) {
					// TODO Auto-generated method stub
					ParseQuery<ParseObject> query = ParseQuery.getQuery("Order");
        	    	query.getInBackground(obList4.get(pos), new GetCallback<ParseObject>() {
        	    		  public void done(ParseObject ord, ParseException e) {
        	    		    if (e == null) {
        	    		    	if(position==1){
        	    		    		ord.put("order_status", "Delivered");
   					    	    	ord.saveInBackground();
   					    	    	onResume();
    	    		    		
    	    		    	}
        	    		    	//onResume();
        	    		    }
        	    		  }
        	    		  });
        	    	
					
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
				
            });
            
            
            return listItem;
        }

		

    }
	
}
