package com.mzam.starter;

import java.util.List;

import com.mzam.starter.User_Order_Page.UserOrderAdapter;
import com.parse.CountCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class User_All_Orders extends Activity {

	
	
	private static final  String TAG= "UserOrder"; 

	ListView listView ;
	
	
	List<ParseObject>  order_list ;
	
	int[] no_of_products ;
	
	final Context context = this;
	int i=0;
	
	//----------------------Oncreate
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_all_orders);
		Log.d(TAG, "at create");
	    getDataForListView1();
		
		listView = (ListView) findViewById(R.id.user_all_orders_list);
		listView.setAdapter(new UserAllOrderAdapter(this));
	
	
        
	}
	//--------------------------------------End Oncreate
	@Override
	public void onResume()
	    {  // After a pause OR at startup
	    super.onResume();
	    
	   listView.setAdapter(new UserAllOrderAdapter(this));
	    }
	
	  
	  //-----------------------End Activity Methods

	 // A method to build the data in a list 
	public void getDataForListView1()
    {
       
       ParseUser currentUser = ParseUser.getCurrentUser();
       
       //------------------------Start Query
       //-------- Order Query
      try{
       ParseQuery<ParseObject> orderQuery = ParseQuery.getQuery("Order");
   		orderQuery.whereEqualTo("user_id", currentUser);
   		orderQuery.orderByDescending("createdAt");
   		orderQuery.include("ShopId");
   		order_list=  orderQuery.find();
   		
   		no_of_products = new  int[order_list.size()];
   		 i=0;
   		for(ParseObject order_object :order_list){
   			
   			Log.i(TAG, "order is not  null"+i);
		    	Log.d(TAG, "order is not  null");
		    
		    	 ParseQuery<ParseObject> ordered_productQuery = ParseQuery.getQuery("Ordered_Product");
		         
		        
		        ParseObject order= ParseObject.createWithoutData("Order", order_object.getObjectId()) ;
		        ordered_productQuery.whereEqualTo("order_id", order);
		        
		        ordered_productQuery.countInBackground(new CountCallback() {
			           public void done(int count, ParseException e) {
			             if (e == null) {
			               // The count request succeeded. Log the count
			               Log.d(TAG, i+ " the prd no " + count + " ordered prd");
			               Log.i(TAG, i+ " the prd no " + count + " ordered prd");
			               
			              no_of_products[i]= count;
			               i++;
		
			               
			             } else {
			               // The request failed
			            	  Log.d(TAG,  " the prd no failed");
			            	  Log.i(TAG,  " the prd no failed");
			            	 
			             }
			           }
			         });
			    	
			    	
		         /*
		           List<ParseObject> ordered_prd_list = ordered_productQuery.find();
		         no_of_products[i]= ordered_prd_list.size();
	               i++; 
		          * */
   		}
   		
      } catch (ParseException e1) {

			e1.printStackTrace();
		}
   
	       //-------- END Order Query
   		
      
      
      
          } 

	
 // ----------------------A method to build the data in a list

    //----------------------------Adapter Class
    public class UserAllOrderAdapter extends BaseAdapter {

    	
    	private LayoutInflater layoutInflater;
   
    	public UserAllOrderAdapter(User_All_Orders activity) {
            
            layoutInflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //    Toast.makeText(getApplicationContext(), "Adapter Constructor", Toast.LENGTH_LONG).show();
        	
        }
		@Override
		public int getCount() {
		
			//return userOrderList.size();
			return order_list.size();
		}

		@Override
		//public UserOrderDetail getItem(int arg0) {
		public Object getItem(int arg0) {
		
			//return userOrderList.get(arg0);
			return arg0;
		}

		@Override
		public long getItemId(int arg0) {
		
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			final int pos = arg0;
			if(arg1==null)
			{
				LayoutInflater inflater = (LayoutInflater) User_All_Orders.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				arg1 = inflater.inflate(R.layout.user_all_orders_row, null);
			}
			//---------------------------------------------------------------------------------
					//---------------------------------------------------------------------------------
			
			//ParseObject product = product_list.get(pos);
			
			final ParseObject order = order_list.get(pos);
			final ParseObject shop = order.getParseObject("ShopId");
			
			final String order_id = order.getObjectId();
			final String shop_id = shop.getObjectId();
			
			//Now fill the Order info  in the screen 
		   	TextView orderStatus = (TextView)arg1.findViewById(R.id.user_order_status);
		   	TextView totalCost = (TextView)arg1.findViewById(R.id.user_order_total_cost);
		   	TextView no_of_products_textView = (TextView)arg1.findViewById(R.id.user_order_no_of_prd);
		   	
			TextView shopName = (TextView)arg1.findViewById(R.id.user_order_shop_name);
		   
			// --------------Prepare the order status
		String	order_status = order.getString("order_status");
			String user_order_status ="";
		    if(order_status.equalsIgnoreCase("Active"))
		    	user_order_status = " (Waiting for Submit)";
		    else if(order_status.equalsIgnoreCase("Submitted"))
		    	user_order_status = " (Waiting for Confirm)" ;
		    else if(order_status.equalsIgnoreCase("Confirmed"))
		    	user_order_status = " (Waiting for Payment)" ;  
		    else if(order_status.equalsIgnoreCase("Payed"))
		    	user_order_status = " (Waiting for Delivery)" ;
		    else  //if delivered or cancelled or not paid or rejected
		    	user_order_status ="";
			// -------------- END   Prepare the order status
		    
			orderStatus.setText("Status: "+  order_status + user_order_status);
			int no =no_of_products[pos]; 
			no_of_products_textView.setText(""+ no+ " products");
			//PaymentMethod.setText("Payment Method : "+ order.getString("payment_type"));
			//totalCost.setText("Order Total Cost: "+ order_total_cost);
			// for now , to change later
			totalCost.setText("Payment Method : "+ order.getString("payment_type"));
			///* --------------->>>
			shopName.setText(" "+shop.get("shop_name").toString()+"");
			
			 final ParseImageView ShopImg = (ParseImageView)arg1.findViewById(R.id.order_shop_img_row);
			 ParseFile imageFile = shop.getParseFile("shopImage");
				if (imageFile != null) {
					ShopImg.setParseFile(imageFile);
					ShopImg.loadInBackground();
				}
				else
				{
						Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
								R.drawable.ic_launcher);
						ShopImg.setImageBitmap(bitmap); // for pevieeeewwww
						
						
				}
			

	    		    
    		arg1.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					Intent intent = new Intent(User_All_Orders.this, User_Order_Page.class);
					intent.putExtra("orderID", order_id);
					intent.putExtra("shopId", shop_id);
					startActivity(intent);
					
					
				}
			});
		
			return arg1;
		}
		
	

    }
    //----------------------------- END Adapter Class
private void createCutomActionBarTitle(){
        this.getActionBar().setDisplayShowCustomEnabled(true);
        this.getActionBar().setDisplayShowTitleEnabled(false);

        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.custom_action_bar, null);
        Typeface tf = Typeface.createFromAsset(getAssets(),"Fonts/Rosemary.ttf");
        ((TextView)v.findViewById(R.id.titleFragment1)).setTypeface(tf);
        ((TextView)v.findViewById(R.id.titleFragment2)).setTypeface(tf);
        //assign the view to the actionbar
        this.getActionBar().setCustomView(v);
    }


	
}//end class user all orders
