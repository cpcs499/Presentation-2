package com.mzam.starter;

import java.util.ArrayList;
import java.util.List;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils.Permissions.User;
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

public class User_Order_Page extends Activity {
	
	private static final  String TAG= "UserOrder"; 

	ListView listView ;
	
	String paymentType, order_status, order_id, shop_id;
	List<ParseObject>   ordered_product_list ;
	ParseObject shop , order ;
	
	final Context context = this;
	//UserOrderAdapter adapter =  new UserOrderAdapter(this);
	//----------------------Oncreate
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_order_list);
		Log.d(TAG, "at create");
		getDataForListView1();
		
		listView = (ListView) findViewById(R.id.user_order_list_view);
		
		
		listView.setAdapter(new UserOrderAdapter(this));
		
		onSubmitOrder();
		onCancelOrder();
		//Toast.makeText(getApplicationContext(), "Hiiiiiiiiiiii", Toast.LENGTH_LONG).show();
        
	}
	//--------------------------------------End Oncreate
	@Override
	public void onResume()
	    {  // After a pause OR at startup
	    super.onResume();
	    
	   listView.setAdapter(new UserOrderAdapter(this));
	    }
	
	  
	  //-----------------------End Activity Methods

	protected void onSubmitOrder() {
		final Button submitOrder = (Button) findViewById(R.id.submit_all_order);
		
		if(!(order_status.equalsIgnoreCase("Active"))) // if order status is not "Active" then, no submit
		{
			submitOrder.setVisibility(View.GONE);
			
		}
		
		submitOrder.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				/* Alert Dialog Code Start*/ 	
				AlertDialog.Builder alert = new AlertDialog.Builder(context);
				alert.setTitle("Submit the Order"); //Set Alert dialog title here
				alert.setMessage("Do you want to Submit the Order? \n No Changes Allowed after Submission "); //Message here
				
				alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
				
					order.put("order_status","Submitted");
					order.saveInBackground();
					  
					submitOrder.setVisibility(View.GONE);
					
					//---- Now to update the order page
					Intent intent = new Intent(User_Order_Page.this,
	                        User_Order_Page.class);
				
			  		intent.putExtra("orderID", order_id);
					intent.putExtra("shopId", shop_id);
					
	                startActivity(intent);
	                finish();
					
				}	
				}); //End of alert.setPositiveButton
				
				alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
				// Canceled.
				dialog.cancel();
			
				 
				
				
				}
				}); //End of alert.setNegativeButton
				AlertDialog alertDialog = alert.create();
				alertDialog.show();
							
			
			}
			});
		
		}//-------- END  onSubmitOrder()
	
	protected void onCancelOrder() {
		final Button cancelOrder = (Button) findViewById(R.id.cancel_order);
		
		if(!(order_status.equalsIgnoreCase("Active")) && !(order_status.equalsIgnoreCase("Submitted")) ) // if order status is not "Active" then, no submit
		{
			cancelOrder.setVisibility(View.GONE);
			
			
		}
		
		cancelOrder.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				/* Alert Dialog Code Start*/ 	
				AlertDialog.Builder alert = new AlertDialog.Builder(context);
				alert.setTitle("Cancel the Order"); //Set Alert dialog title here
				alert.setMessage("Do you want to Cancel the Order? "); //Message here
				
				alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
				
					if(order_status.equalsIgnoreCase("Submitted")){
					//------------1     chnange order status
					order.put("order_status","Cancelled");
					order.saveInBackground();
					
					//------------------2 release the quantity
					for(ParseObject ordered_prd: ordered_product_list)
					{
						final ParseObject product = ordered_prd.getParseObject("product_id");

						//------------2---------------  Release quntity
						release_quantity(ordered_prd, product,   ordered_prd.getInt("order_color")  );
						
					}//end for
					
					//-3 
					  finish();
					  
					} ///-----------------------------end if order is submitted
					
					if(order_status.equalsIgnoreCase("Active")){

						//1 release the quantity
						for(ParseObject ordered_prd: ordered_product_list)
						{
							final ParseObject product = ordered_prd.getParseObject("product_id");

							//------------1---------------  Release quntity
							release_quantity(ordered_prd, product, ordered_prd.getInt("order_color") );
							//delete ordered prd
							ordered_prd.deleteInBackground();
							
						}//end for
						
						//-3 
						order.deleteInBackground();
						  finish();
						  
						} ///end if order is Active 
					
					
					
					}	
				}); //End of alert.setPositiveButton
				
				alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
				// Canceled.
				dialog.cancel();
				}
				}); //End of alert.setNegativeButton
				AlertDialog alertDialog = alert.create();
				alertDialog.show();
							
			
			}
			});
		
		
		
	}
	
		
		 // A method to build the data in a list 
		public void getDataForListView1()
	    {
	       
	    	Intent intent= getIntent();
	  		order_id = intent.getStringExtra("orderID");
	  		shop_id = intent.getStringExtra("shopId");
	  		
	       ParseUser currentUser = ParseUser.getCurrentUser();
	       
	       //------------------------Start Query
	       //-------- Order Query
	      try{
	       ParseQuery<ParseObject> orderQuery = ParseQuery.getQuery("Order");
       		orderQuery.whereEqualTo("objectId", order_id);
       		List<ParseObject>orders =  orderQuery.find();
       		
       		for(ParseObject order_object :orders){
       			order= order_object;
       			Log.i(TAG, "order is not  null");
   		    	Log.d(TAG, "order is not  null");
       		}
       		
	      } catch (ParseException e1) {

				e1.printStackTrace();
			}
       /*
       		orderQuery.getFirstInBackground(new GetCallback<ParseObject>() {
       		  public void done(ParseObject object, ParseException e) {
       		    if (object == null) {
       		     Log.d(TAG, "order is null");
       		     //Log.i(TAG, "order is null");
       		    } else {
       		      // Log.d("score", "Retrieved the object.");
       		    	order = object;
       		    //	Log.i(TAG, "order is not  null");
       		    	Log.d(TAG, "order is not  null");
       		    }
       		  }
       		});
       		*/
 	       //-------- END Order Query
       		
 	       //-------- Ordered Product Query
       		Double order_total_cost=0.0;
       		try { 
   	    	
       		// //order_objects_list = orderQuery.find();
       		ParseQuery<ParseObject> ordered_productQuery = ParseQuery.getQuery("Ordered_Product");
			ordered_productQuery.whereEqualTo("order_id", order);
			ordered_productQuery.orderByDescending("createdAt");
			ordered_productQuery.include("product_id");
			ordered_product_list = ordered_productQuery.find();
	 	     //-------- END Ordered Product Query
			int i= 0;
			
       		for(ParseObject ordered_prd_object :ordered_product_list){
   
       			//Calcualte the order total cost , get each ordered prd and get its quantity and unit price
       			order_total_cost += ordered_prd_object.getInt("product_quantity")*ordered_prd_object.getDouble("unit_cost");
       		
       		   //ParseObject product = ordered_prd_object.getParseObject("product_id");
       		   	//Log.i(TAG,i+ " prd is not  null"+ product.getString("ProductName"));
       		   	//Log.d(TAG,i+ " prd is not  null"+ product.getString("ProductName"));
       		  // product_list.add(product);
       		   	//Log.d(TAG,i+ " OH pass the prblm !! (:"+ product.getString("ProductName"));
       		   	//i++;
       			
       			/*
      	       //--------  Product Query
       			//For each ordered product object --> find the product object (:
       			       			
       			String prdouct_id = ordered_prd_object.getParseObject("product_id").getObjectId();
       		//	ParseObject productObject = ParseObject.createWithoutData("Product",prdouct_id); //--> CzECnGlGWM
       			
       			
       			ParseQuery<ParseObject> productQuery = ParseQuery.getQuery("Product");
       			productQuery.whereEqualTo("objectId", prdouct_id);
       			
       			List<ParseObject>  products= productQuery.find();
       			Log.i(TAG, "product size"+ products.size());
       			Log.d(TAG, "product size"+ products.size());
       			//product_list.add(products.get(0));
       			ParseObject product = products.get(0);
       			product_list.add(i, product);
       			
       			
       			i++;
       			/*
       			for(ParseObject prd_object :products){
           			product_list.add(prd_object.getParseObject("objectId"));
           			Log.i(TAG, "product is not  null");
       		    	Log.d(TAG, "prd is not  null");
           		}
           		*/
       			
       			/*
    			productQuery.getFirstInBackground(new GetCallback<ParseObject>() {
    	       		  public void done(ParseObject object, ParseException e) {
    	         		    if (object == null) {
    	         		    
    	         		    } else {
    	         		    
    	         		    	product_list.add(object);
    	         		    }
    	         		  }
    	         		});
       			
 			*/
										
			}//End for
       		
  	       //-------- Shop  Query
       		
       		
   			//ParseObject shopObject = ParseObject.createWithoutData("shop", shop_id);
   			
   			ParseQuery<ParseObject> shopQuery = ParseQuery.getQuery("shop");
   			shopQuery.whereEqualTo("objectId", shop_id);

   			List<ParseObject>  shops= shopQuery.find();
   			//shop= shops.get(0);
   			for(ParseObject shopObject1 :shops){
       			shop= shopObject1;
       			Log.i(TAG, "shop is not  null");
   		    	Log.d(TAG, "shop is not  null");
       		}
   			
			/*
   			shopQuery.getFirstInBackground(new GetCallback<ParseObject>() {
	       		  public void done(ParseObject object, ParseException e) {
	         		    if (object == null) {
	         		    
	         		    } else {
	         		    
	         		    	shop =object;
	         		    }
	         		  }
	         		});
				*/
       		
       		//--------- END Shop Query
       		
	            } catch (ParseException e1) {

    				e1.printStackTrace();
    			}
 	          
	    //Now fill the Order info  in the screen 
       	TextView orderStatus = (TextView)findViewById(R.id.order_status_of_shop);
       	TextView totalCost = (TextView)findViewById(R.id.order_cost);
       	TextView PaymentMethod = (TextView)findViewById(R.id.order_payment_type);
       	
		TextView shopName = (TextView)findViewById(R.id.shop_name_of_order);
	   
		// --------------Prepare the order status
		order_status = order.getString("order_status");
		String user_order_status ="";
	    if(order_status.equalsIgnoreCase("Active"))
	    	user_order_status = " (Waiting for Submit)";
	    else if(order_status.equalsIgnoreCase("Submitted"))
	    	user_order_status = " (Waiting for Confirm)" ;
	    else if(order_status.equalsIgnoreCase("Confirmed"))
	    	user_order_status = " (Waiting for Payment)" ;  
	    else if(order_status.equalsIgnoreCase("Payed"))
	    	user_order_status = " (Waiting for Delivery)" ;
	    else  //if delivered or cancelled or not paid
	    	user_order_status ="";
		// -------------- END   Prepare the order status
	    
		orderStatus.setText("Status: "+  order_status + user_order_status);
		PaymentMethod.setText("Payment Method : "+ order.getString("payment_type"));
		totalCost.setText("Order Total Cost: "+ order_total_cost);
		///* --------------->>>
		shopName.setText(" "+shop.get("shop_name").toString()+"");
		
		 final ParseImageView ShopImg = (ParseImageView)findViewById(R.id.order_shop_image);
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
		
		
	    } 
	
		
	 // ----------------------A method to build the data in a list
		 
	    //----------------------------Adapter Class
	    public class UserOrderAdapter extends BaseAdapter {

	    	
	    	private LayoutInflater layoutInflater;
	   
	    	public UserOrderAdapter(User_Order_Page activity) {
	            
	            layoutInflater = (LayoutInflater) activity
	                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        //    Toast.makeText(getApplicationContext(), "Adapter Constructor", Toast.LENGTH_LONG).show();
	        	
	        }
			@Override
			public int getCount() {
			
				//return userOrderList.size();
				return ordered_product_list.size();
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
					LayoutInflater inflater = (LayoutInflater) User_Order_Page.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					arg1 = inflater.inflate(R.layout.user_order_row2, null);
				}
				//---------------------------------------------------------------------------------
				TextView productQuantity = (TextView)arg1.findViewById(R.id.ordered_prd_quantity);
				TextView productName = (TextView)arg1.findViewById(R.id.ordered_prd_name);
				TextView productCost = (TextView)arg1.findViewById(R.id.ordered_prd_cost4);
				TextView productColor = (TextView)arg1.findViewById(R.id.ordered_prd_color);
				
				ImageView imageColor = (ImageView)arg1.findViewById(R.id.order_prd_color);
				
				
				LayoutParams params = (LayoutParams) imageColor.getLayoutParams();
				params.width = 20;
				params.height=20;
				// existing height is ok as is, no need to edit it
				imageColor.setLayoutParams(params);
	     
				imageColor.setBackgroundColor(0xffffffff);
				imageColor.setClickable(false);
			    
				
				Button edit_ordered_prd = (Button) arg1.findViewById(R.id.ordered_product_edit);
				Button remove_ordered_prd = (Button)arg1.findViewById(R.id.ordered_product_remove);
				
				
				//---------------------------------------------------------------------------------
				
				//ParseObject product = product_list.get(pos);
				
				final ParseObject ordered_prd = ordered_product_list.get(pos);
				final ParseObject product = ordered_prd.getParseObject("product_id");
				
				 
		    	productName.setText(""+  product.getString("ProductName").toString()+"");
		    	
		    	productQuantity.setText("Quantity: "+ordered_prd.getInt("product_quantity")+"");
		    	productCost.setText("Total Cost: "+ordered_prd.getInt("product_quantity")*ordered_prd.getDouble("unit_cost"));
		    	productColor.setText("Color: "+"");	    		
		    		    	
				imageColor.setBackgroundColor(ordered_prd.getInt("order_color"));
				
				//---------------> find the color
						    	///*
		    	//---------> img
		    	final ParseImageView prdImg = (ParseImageView)arg1.findViewById(R.id.ordered_prd_img);
				ParseFile imageFile = product.getParseFile("product_pic");
		 		if (imageFile != null) {
		 			prdImg.setParseFile(imageFile);
		 			prdImg.loadInBackground();
		 		}
		 		else
		 		{
		 				Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
		 						R.drawable.ic_launcher);
		 				prdImg.setImageBitmap(bitmap); // for pevieeeewwww
		 						
		 		}
						
		    	//---------> END img

		 		//---------- Buttons (edit && remove )
		 		if(!(order_status.equalsIgnoreCase("Active"))) // if order status is not "Active" then, no edit or remove
				{
					remove_ordered_prd.setVisibility(View.GONE);
					edit_ordered_prd.setVisibility(View.GONE);
				}
		 		// Remove product and Edit
				
				remove_ordered_prd.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						
						//String order_id = order.getObjectId();
						
						/* Alert Dialog Code Start*/ 	
						AlertDialog.Builder alert = new AlertDialog.Builder(context);
						alert.setTitle("Remove Product"); //Set Alert dialog title here
						alert.setMessage("Do you want to Remove this Product from the Order? "); //Message here
						
						alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
						
						//---1----Delete from the DB from Ordered_Prdouct tabel  
							ordered_prd.deleteInBackground();
							/*
							ParseQuery<ParseObject> query = ParseQuery.getQuery("Ordered_Product");
						query.getInBackground(ordered_prd_id, new GetCallback<ParseObject>() {
						public void done(ParseObject object, ParseException e) {
						if (e == null) {
						
						object.deleteInBackground();
					
						} else {
							
							Toast.makeText(getApplicationContext(), 
								"not detelted",
								Toast.LENGTH_SHORT).show();
							e.getStackTrace();
							}
							}
							});
								//----- END delete from DB
						
						*/
						
							//------------2---------------  Release quntity
							
							release_quantity(ordered_prd, product , ordered_prd.getInt("order_color") );
							
						//----3----Delete from list adapter && the ordered prd list
						// main code on after clicking yes
	                    ordered_product_list.remove(pos);
	                    notifyDataSetChanged();
	                    notifyDataSetInvalidated();
	                    
						
						Toast.makeText(getApplicationContext(), 
						"detelted",
						Toast.LENGTH_SHORT).show();
						
						//----4- Optional-------------------------------->> delete the order itself if it is empty and before submitting it 
						if (ordered_product_list.size() ==0)
						{
							// delete the order itself order
							//Delete from the DB from Order tabel  
							order.deleteInBackground();
							
							/*
							ParseQuery<ParseObject> query_order = ParseQuery.getQuery("Order");
							query_order.getInBackground(order_id, new GetCallback<ParseObject>() {
								public void done(ParseObject object, ParseException e) {
								if (e == null) {
								
								object.deleteInBackground();
								//----- END delete from DB
								
								} else {
								
								 e.getStackTrace();
								}
								}
								});
								*/
							//----- END delete from DB
							
							/*
							Intent intent = new Intent(User_Order_Page.this,User_All_Orders.class);
							finish();
							startActivity(intent);
		                    */
							
						}//end if order is empty because of removing all the products
	                    
						
						
						} // End of onClick(DialogInterface dialog, int whichButton)
						}); //End of alert.setPositiveButton
						alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
						// Canceled.
						dialog.cancel();
						}
						}); //End of alert.setNegativeButton
						AlertDialog alertDialog = alert.create();
						alertDialog.show();
									
					
					}
					});
				
				//--------->>>
					
				
				edit_ordered_prd.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						
						
					}
				});
				
				
		 		
		 		//*/
		    	/*
	    		arg1.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(User_Order_Page.this, User_All_Orders.class);
						//intent.putExtra("SHOP_ID", object.getObjectId());
						
						//intent.putExtra("SHOP_DESC", object.getString("shop_desc"));
						startActivity(intent);
						//Toast.makeText(getContext(), object.getObjectId()+"", Toast.LENGTH_SHORT).show();
						
					}
				});
				*/
				return arg1;
			}
			
		

	    }
	    //----------------------------- END Adapter Class
	
	
	
	    protected void delete_ordered_product(ParseObject ordered_prd) {
			
		}
	    
	    protected void hold_quantity(ParseObject ordered_prd, ParseObject product) {
	    	
	    	int ordered_prd_quantity= ordered_prd.getInt("product_quantity") ;
	    	product.increment("product_quantity", -1*ordered_prd_quantity);//decrease
	    	product.increment("hold_quantity", ordered_prd_quantity); //decrease
			product.saveInBackground();
			
		}
	    
	    protected void hold_quantity(ParseObject ordered_prd, ParseObject product , int color_no) {
	    	
	    	int ordered_prd_quantity= ordered_prd.getInt("product_quantity") ;
	    	product.increment("product_quantity", -1*ordered_prd_quantity);//decrease
	    	product.increment("hold_quantity", ordered_prd_quantity); //increase
			product.saveInBackground();
			
			
			//------------------------------color
			ParseObject color = null;
			try{

			ParseQuery<ParseObject> colorQuery = ParseQuery.getQuery("color");
   			colorQuery.whereEqualTo("color_number",color_no );
   			colorQuery.whereEqualTo("productId", product );

   			List<ParseObject>  colors= colorQuery.find();
   			
   			for(ParseObject color_object :colors){
       			color = color_object;
       			Log.i(TAG, "color is not  null");
   		    	Log.d(TAG, "color is not  null");
       		}
   			
		      } catch (ParseException e1) {

					e1.printStackTrace();
				}

			
			color.increment("unit_Quantity", -1*ordered_prd_quantity); //decrease
			
			color.saveInBackground();
		}
	    protected void release_quantity(ParseObject ordered_prd, ParseObject product) {
	    	int ordered_prd_quantity= ordered_prd.getInt("product_quantity") ;
	    	product.increment("product_quantity", ordered_prd_quantity);//increase
	    	product.increment("hold_quantity", -1*ordered_prd_quantity); //decrease
			product.saveInBackground();
	    	
		}
	    protected void release_quantity(ParseObject ordered_prd, ParseObject product, int color_no) {
	    	int ordered_prd_quantity= ordered_prd.getInt("product_quantity") ;
	    	product.increment("product_quantity", ordered_prd_quantity);//increase
	    	product.increment("hold_quantity", -1*ordered_prd_quantity); //decrease
			product.saveInBackground();
			
			//------------------------------color
			ParseObject color = null;
			try{

			ParseQuery<ParseObject> colorQuery = ParseQuery.getQuery("color");
   			colorQuery.whereEqualTo("color_number",color_no );
   			colorQuery.whereEqualTo("productId", product );

   			List<ParseObject>  colors= colorQuery.find();
   			
   			for(ParseObject color_object :colors){
       			color = color_object;
       			Log.i(TAG, "color is not  null");
   		    	Log.d(TAG, "color is not  null");
       		}
   			
		      } catch (ParseException e1) {

					e1.printStackTrace();
				}

			
			
			color.increment("unit_Quantity", ordered_prd_quantity); //increase
			color.saveInBackground();
		}
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
}
