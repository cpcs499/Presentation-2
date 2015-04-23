
package com.mzam.starter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mzam.starter.inProgressShopsComingOrders.VersiAdapter;
import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class ViewProductOrders extends Activity {
	/** Called when the activity is first created. */
			
	
	ListView listView ;
	List<String> ShopsIDs ,UsersIDs,OrdersIDs;
	List<ParseObject> foundObj;
	List<ParseUser> ou;
	ParseQueryAdapter<ParseObject> adapter ;
	TextView profileusername,Productname,ProductQty,TotalCost,shopNAme, createdAt;
	ImageView imgView; 
	Switch switchT1;

	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_orders_list);

	
		  listView = (ListView) findViewById(R.id.listView1);
		  
		  //listView.setAdapter(new VersiAdapter(this));
		  
	}
	
	@Override
	public void onResume()
	    {  // After a pause OR at startup
	    super.onResume();
	    
	    listView.setAdapter(new VersiAdapter(this));
	    }
	
	// Create an Adapter Class extending the BaseAdapter
    class VersiAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;
        String sentProductID = getIntent().getStringExtra("productID");
        
        public VersiAdapter(ViewProductOrders activity) {
            // TODO Auto-generated constructor stub
            layoutInflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            
        	
            ShopsIDs = new ArrayList<String>();
            UsersIDs = new ArrayList<String>();
            OrdersIDs = new ArrayList<String>();
            try {

                ParseQuery<ParseObject> checkThisProductOrders = ParseQuery.getQuery("Order");
				ParseObject productObj = ParseObject.createWithoutData("Product",sentProductID);
				checkThisProductOrders.whereEqualTo("productId", productObj);
            	foundObj = checkThisProductOrders.find();
    			for(ParseObject obj:foundObj){
    				ShopsIDs.add(obj.getParseObject("ShopId").getObjectId());  //shopIDs
    				UsersIDs.add(obj.getParseObject("user_id").getObjectId());//UserIDs
					//obList3.add(obj.getParseObject("productId").getObjectId());
    				OrdersIDs.add(obj.getObjectId());
    			
    				
    				
    			
    			}
    				
    			
            	
    			} catch (ParseException e1) {
    				// TODO Auto-generated catch block
    				e1.printStackTrace();
    			}
          Toast.makeText(getApplicationContext(), ShopsIDs.size()+"", Toast.LENGTH_LONG).show();
 	
        }
        
        @Override
		public int getCount() {
			// TODO Auto-generated method stub
			return ShopsIDs.size();
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
                listItem = layoutInflater.inflate(R.layout.product_orders_row, null);   
            }
            
            // Initialize the views in the layout
             imgView = (ImageView) listItem.findViewById(R.id.imageView1);
             profileusername = (TextView) listItem.findViewById(R.id.textView1);
             Productname = (TextView) listItem.findViewById(R.id.textView2);
             ProductQty = (TextView) listItem.findViewById(R.id.textView3);
             TotalCost = (TextView) listItem.findViewById(R.id.textView4);
             shopNAme = (TextView) listItem.findViewById(R.id.textView6);
             createdAt= (TextView) listItem.findViewById(R.id.textView7);
             switchT1 = (Switch)listItem.findViewById(R.id.switch1);
            
            
          //----------------------------- Start Shop &username name Query ------------------------------------------------ 

            try{
    			ParseQuery<ParseUser> UsersQuery = ParseUser.getQuery();
    			UsersQuery.whereEqualTo("objectId", UsersIDs.get(pos));
            	List<ParseUser> usersList = UsersQuery.find();
    			for(int i = 0; i < usersList.size(); i++) {
     				profileusername.setText("@"+usersList.get(i).get("username").toString()+"");
    				
    				ParseFile fileObject = (ParseFile) usersList.get(i).get("ProfilePic");
            		fileObject.getDataInBackground(new GetDataCallback() {
            			public void done(byte[] data,ParseException e) {
            				if (e == null) {
            					Bitmap bmp = BitmapFactory.decodeByteArray(data, 0,data.length);
            					imgView.setImageBitmap(bmp);
            						} else {
    							Bitmap bmp2 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
    	    					imgView.setImageBitmap(bmp2);
            						}
            					}
            				});
    				}
    				
    				ParseQuery<ParseObject> OrderedProductQuery = ParseQuery.getQuery("Ordered_Product");
    				ParseObject ProductObject = ParseObject.createWithoutData("Product",sentProductID);
    				OrderedProductQuery.whereEqualTo("product_id", ProductObject);
    				List<ParseObject> FoundOrderedProduct = OrderedProductQuery.find();
    				
    				for(ParseObject foundOrdObj:FoundOrderedProduct){
    					//bb2.add(c2.getParseObject("ShopId").getObjectId());
    					ProductQty.setText("Product Qty: "+foundOrdObj.getInt("product_quantity")+"");
    		    		TotalCost.setText("Cost: "+foundOrdObj.getInt("product_quantity")*foundOrdObj.getDouble("unit_cost"));
    		    		createdAt.setText("Ordered @"+foundOrdObj.getCreatedAt().toString());
    		    		
    		    		

    				}
            }catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    			
            switchT1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            	   @Override
            	   public void onCheckedChanged(CompoundButton buttonView,
            	     boolean isChecked) {
            	    
            	    if(isChecked){
            	    	//switchT1.setVisibility(View.INVISIBLE);
            	    	ParseQuery<ParseObject> OrderQuery = ParseQuery.getQuery("Order");
            	    	OrderQuery.getInBackground(OrdersIDs.get(pos), new GetCallback<ParseObject>() {
            	    		  public void done(ParseObject ord, ParseException e) {
            	    		    if (e == null) {
            	    		    	onResume();
            	    		    	//switchT1.setVisibility();
            	    		    		ord.put("order_status", "Payed");
       					    	    	ord.saveInBackground();
       					    	    	Toast.makeText(getApplicationContext(),
       					        				String.valueOf("Your Selected is On"),
       					        					Toast.LENGTH_SHORT).show();
       					    	    	//onResume();
       					    	    	//listView.invalidateViews();
       					    	    	
       					    	    	//listView.removeViewInLayout(ProductQty);
       					    	    	
            	    		    }
            	    		  }
            	    	});
            	    	
            	    	
            	    }else{
            	    	Toast.makeText(getApplicationContext(),
                				String.valueOf("Your Selected is Off"),
                					Toast.LENGTH_SHORT).show();
            	    }

            	   }
            	
            	   
            	  }); 
            
         profileusername.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					String value = UsersIDs.get(pos);
					Intent in = new Intent(ViewProductOrders.this,UserProfile_ME.class);
					in.putExtra("userProfile", value);
					//setResult(in,0);
					startActivity(in);
					//Toast.makeText(getActivity(), obList2.get(pos)+"", Toast.LENGTH_LONG).show();
				}
			});
            
            return listItem; 
            
        }
        
        

    }
	
}