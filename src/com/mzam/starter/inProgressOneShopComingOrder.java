package com.mzam.starter;

import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class inProgressOneShopComingOrder extends Activity {

	ListView listView ;
	ImageView userpic;
	List<String> obList ,obList2,obList3,obList4;
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

        
        public VersiAdapter(inProgressOneShopComingOrder activity) {
            // TODO Auto-generated constructor stub
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	
            obList = new ArrayList<String>();
            obList2 = new ArrayList<String>();
            obList3 = new ArrayList<String>();
            obList4 = new ArrayList<String>();
            try {
            	
        	ParseQuery<ParseObject> innerQuery = ParseQuery.getQuery("Order");
        	ParseObject obj = ParseObject.createWithoutData("shop",value);
        	innerQuery.whereEqualTo("order_status", "inProgress");
        	innerQuery.orderByDescending("createdAt");
        	innerQuery.whereEqualTo("ShopId", obj);
        	ob = innerQuery.find();
        	for (ParseObject num : ob) {
			obList.add(num.getParseObject("ShopId").getObjectId());
			obList2.add(num.getParseObject("productId").getObjectId());
			obList3.add(num.getParseObject("user_id").getObjectId());
			obList4.add(num.getString("PaymentMethod"));
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
                listItem = layoutInflater.inflate(R.layout.orders_row, null);   
            }
            
            // Initialize the views in the layout
            final ImageView iv = (ImageView) listItem.findViewById(R.id.imageView1);
            final TextView profileusername = (TextView) listItem.findViewById(R.id.textView1);
            final TextView Productname = (TextView) listItem.findViewById(R.id.textView2);
            final TextView ProductQty = (TextView) listItem.findViewById(R.id.textView3);
            final TextView TotalCost = (TextView) listItem.findViewById(R.id.textView4);
            final TextView paymentMethod = (TextView) listItem.findViewById(R.id.textView6);
            
            paymentMethod.setText("Payment Type: "+obList4.get(pos)+"");
            
            
            try{
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Ordered_Product");
                ParseObject obj = ParseObject.createWithoutData("Product",obList2.get(pos));
                query.whereEqualTo("product_id", obj);
                List<ParseObject> oo = query.find();
    			for(int i = 0; i < oo.size(); i++){
                	int Qty = oo.get(i).getInt("product_quantity");
                    ProductQty.setText("Quantity: "+Qty);
                    Double QtyCostwithoutDiscount = Qty*oo.get(i).getDouble("unit_cost"); 
                    TotalCost.setText("Cost: "+(QtyCostwithoutDiscount-(QtyCostwithoutDiscount*(oo.get(i).getDouble("product_discount")/100))));
                }
                            	
                }catch(ParseException e){
                	
                }
            
            try{
            	ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Product");
            	query2.whereEqualTo("objectId", obList2.get(pos));
				List<ParseObject> cc = query2.find();
				for(ParseObject m:cc){
					Productname.setText("ProductName: "+m.get("ProductName").toString());
				}
            }catch(ParseException e){
            	
            }
            
            try {
            	ParseQuery<ParseUser> query2 = ParseUser.getQuery();
            	query2.whereEqualTo("objectId", obList3.get(pos));
				List<ParseUser> oo = query2.find();
				for(ParseObject m:oo){
					profileusername.setText("@"+m.get("username").toString());
					//Toast.makeText(getApplicationContext(),usernamee, Toast.LENGTH_LONG).show(); 
					
					 ParseFile fileObject = (ParseFile) m.get("ProfilePic");
                		
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
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            
            
            return listItem;
        }

		

    }
	
}