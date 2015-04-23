package com.mzam.starter;

import java.util.List;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class OrderProduct extends Activity {
	private RadioGroup radioGroup;
	private RadioButton radioButton;
	private static final  String TAG= "OrderProduct"; 
	String productId;
	String shopId;
	Double productPrice;
	Double productDiscount;
	int    productQuantity;
	String order_id;
	final String order_status ="Active";
	ParseObject productObject, openedOrder;
	int no_ofColors=0;
	int quantity_entered=0;
	boolean color_is_choosen= false;
	boolean order_open_exist=false;
	int selected_color_no;
	final Context context = this;
	//....................................Color
	
	//////////////////////////////
	//Parse color objects.... !! take a look zozo
	ParseObject color1;
	ParseObject color2;
	ParseObject color3;
	ParseObject color4;
	ParseObject color5;
	List<ParseObject> colors;
	//---------------------
	
	int[]  colorNumber = new int[5];
	int[] colorQuantity= new int[5];
	
	
	//-----------------------
	// Color Image Views .....
		ImageView imageColor1;
		ImageView imageColor2;
		ImageView imageColor3;
		ImageView imageColor4;
		ImageView imageColor5;
	//-----------------------
	// Colors Selected  .....
	int colorSelected1;
	
	//-----------------------
	// Colors Quantities .....
	int colorQuantity1;
	int colorQuantity2;
	int colorQuantity3;
	int colorQuantity4;
	int colorQuantity5;

	//-----------------------
	//////////////////////////////
	int [] mColor;
	/////////////////////////////
	// Implement listener to get selected color value
	/*
	ColorPickerSwatch.OnColorSelectedListener colorcalendarListener1 = new ColorPickerSwatch.OnColorSelectedListener(){

		@Override
		public void onColorSelected(int color) {
	

			colorSelected1=color;	
			imageColor1.setBackgroundColor(colorSelected1);
			}

	
	};
*/
	
	//....................................END Color
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_product);
		
		
		fillProductSpcae();
		onColor();
		onQuantityEdit();
		OnSubmit();
		
		
	}//end on create
	
	private void onColor() {
		final TextView colorQuantity = (TextView)findViewById(R.id.color_quantity_availabe);
		
		colorQuantity.setText("");
		//if(no_ofColors ==0)--> visabilty gone
		// Declare Image views for colors .....
				//--------- Color1
				if(no_ofColors>=1)
				{
		 		color1 = colors.get(0);
		 		
		 		colorQuantity1=color1.getInt("unit_Quantity"); 
		 		
				imageColor1 = (ImageView) findViewById(R.id.order_color1);
				LayoutParams params = (LayoutParams) imageColor1.getLayoutParams();
				params.width = 80;
				params.height=80;
				// existing height is ok as is, no need to edit it
				imageColor1.setLayoutParams(params);
				imageColor1.setBackgroundColor(0xffffffff);
				//String color1_no = "0x"+""+color1.getInt("color_number");
				imageColor1.setBackgroundColor(color1.getInt("color_number"));
				imageColor1.setClickable(true);
				imageColor1.setOnClickListener(new OnClickListener() {
		            @Override
		            public void onClick(View v) {
		            	color_is_choosen=true;
		            	//////////////////////////////////////////////////
		            	selected_color_no=1;
		            	colorSelected1 = color1.getInt("color_number");
		            	colorQuantity.setText(colorQuantity1 +" Availabe of Choosen Color"+"");
		            }
		        });
				}//end if 
				//---------END Color1
				
				//--------- Color2
				if(no_ofColors>=2)
				{
		 		color2 = colors.get(1);
		 		colorQuantity2=color2.getInt("unit_Quantity");
				imageColor2 = (ImageView) findViewById(R.id.order_color2);
				LayoutParams params = (LayoutParams) imageColor2.getLayoutParams();
				params.width = 80;
				params.height=80;
				// existing height is ok as is, no need to edit it
				imageColor2.setLayoutParams(params);
				imageColor2.setBackgroundColor(0xffffffff);
				//String color1_no = "0x"+""+color1.getInt("color_number");
				imageColor2.setBackgroundColor(color2.getInt("color_number"));
				imageColor2.setClickable(true);
				imageColor2.setOnClickListener(new OnClickListener() {
		            @Override
		            public void onClick(View v) {
		            	color_is_choosen=true;
		            	selected_color_no=2;
		            	colorSelected1 = color2.getInt("color_number");
		            	colorQuantity.setText(colorQuantity2 +" Availabe of Choosen Color"+"");
		            }
		        });
				}//end if
				//---------END Color2

				//--------- Color3
				if(no_ofColors>=3)
				{
		 		color3 = colors.get(2);
		 		colorQuantity3=color3.getInt("unit_Quantity");
				imageColor3 = (ImageView) findViewById(R.id.order_color3);
				LayoutParams params = (LayoutParams) imageColor3.getLayoutParams();
				params.width = 80;
				params.height=80;
				// existing height is ok as is, no need to edit it
				imageColor3.setLayoutParams(params);
				imageColor3.setBackgroundColor(0xffffffff);
				//String color1_no = "0x"+""+color1.getInt("color_number");
				imageColor3.setBackgroundColor(color3.getInt("color_number"));
				imageColor3.setClickable(true);
				imageColor3.setOnClickListener(new OnClickListener() {
		            @Override
		            public void onClick(View v) {
		            	color_is_choosen=true;
		            	selected_color_no=3;
		            	colorSelected1 = color3.getInt("color_number");
		            	colorQuantity.setText(colorQuantity3 +" Availabe of Choosen Color"+"");
		            }
		        });
				}//end if
				//---------END Color3
				//--------- Color4
				if(no_ofColors>=4)
				{
		 		color4 = colors.get(3);
		 		colorQuantity4=color4.getInt("unit_Quantity");
				imageColor4 = (ImageView) findViewById(R.id.order_color4);
				LayoutParams params = (LayoutParams) imageColor4.getLayoutParams();
				params.width = 80;
				params.height=80;
				// existing height is ok as is, no need to edit it
				imageColor4.setLayoutParams(params);
				imageColor4.setBackgroundColor(0xffffffff);
				//String color1_no = "0x"+""+color1.getInt("color_number");
				imageColor4.setBackgroundColor(color4.getInt("color_number"));
				imageColor4.setClickable(true);
				imageColor4.setOnClickListener(new OnClickListener() {
		            @Override
		            public void onClick(View v) {
		            	color_is_choosen=true;
		            	selected_color_no=4;
		            	colorSelected1 = color4.getInt("color_number");
		            	colorQuantity.setText(colorQuantity4 +" Availabe of Choosen Color"+"");
		            }
		        });
				}//end if
				//---------END Color4
				//--------- Color4
				if(no_ofColors==5)
				{
		 		color5 = colors.get(4);
		 		colorQuantity5=color5.getInt("unit_Quantity");
		 		
				imageColor5 = (ImageView) findViewById(R.id.order_color5);
				LayoutParams params = (LayoutParams) imageColor5.getLayoutParams();
				params.width = 80;
				params.height=80;
				// existing height is ok as is, no need to edit it
				imageColor5.setLayoutParams(params);
				imageColor5.setBackgroundColor(0xffffffff);
				//String color1_no = "0x"+""+color1.getInt("color_number");
				imageColor5.setBackgroundColor(color5.getInt("color_number"));
				imageColor5.setClickable(true);
				imageColor5.setOnClickListener(new OnClickListener() {
		            @Override
		            public void onClick(View v) {
		            	color_is_choosen=true;
		            	selected_color_no=5;
		            	colorSelected1 = color5.getInt("color_number");
		            	colorQuantity.setText(colorQuantity5 +" Availabe of Choosen Color"+"");
		            }
		        });
				}//end if
				//---------END Color5
	
	}//-----End OnColor
	private void onQuantityEdit() {
		final EditText order_quantity = (EditText)findViewById(R.id.order_quantity);
		final TextView total_cost = (TextView) findViewById(R.id.calculated_total_cost);
		 order_quantity.addTextChangedListener(new TextWatcher() {
			 
			   public void afterTextChanged(Editable s) {
				   String empty = order_quantity.getText().toString();
				   if (empty.matches("")|| (productPrice==0.0)) {
					   total_cost.setText("" );
					   quantity_entered =0;
				   }else
				   {
				   Double unitCost=productPrice;
				   String discount_exist ="";
					if(productDiscount >0)
					{
						Double x= productPrice*(productDiscount/100.0);
						unitCost = productPrice -(productPrice*(productDiscount/100.0));
						discount_exist ="  After "+productDiscount+"% Discount" ;
						Toast.makeText(OrderProduct.this,
								"X:  "+ x+" unit cost "+unitCost, Toast.LENGTH_SHORT).show();
						
					}
					
					Double totalCost =Double.parseDouble(order_quantity.getText().toString())* unitCost; 
				   total_cost.setText(totalCost +discount_exist );
				   
				   quantity_entered =Integer.parseInt(order_quantity.getText().toString());
				   }//end else
				   
				   
			   }
			 
			   public void beforeTextChanged(CharSequence s, int start, 
			     int count, int after) {
			   }
			 
			   public void onTextChanged(CharSequence s, int start, 
			     int before, int count) {
				   String empty = order_quantity.getText().toString();
				   if (empty.matches("")|| (productPrice==0.0)) {
					   total_cost.setText("" );
					   quantity_entered =0;
					   
				   }
			   }
			  });
		
	} 
	
	private void OnSubmit() {
		final Context context = this;
		//final EditText payment_type = (EditText)findViewById(R.id.payment_type);
		final EditText order_quantity = (EditText)findViewById(R.id.order_quantity);
		Button  submitOrder= (Button) findViewById(R.id.orderProduct);
		
		
		radioGroup = (RadioGroup) findViewById(R.id.rbGroup);
		submitOrder.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			
			
				
		//---------------------------------------------------------
				//if quantity not entered 
				if(	(quantity_entered ==0 ))
				{
					/* Alert Dialog Code Start*/ 	
		        	AlertDialog.Builder alert = new AlertDialog.Builder(context);
		        	alert.setTitle("No Choosen Quantity"); //Set Alert dialog title here
		        	alert.setMessage("Please Enter The Quantity ! "); //Message here
		      	        	
		        	// if pressing OK, nothing happen ...
		        	alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		            	public void onClick(DialogInterface dialog, int whichButton) {
		            		// Canceled.
		            		  dialog.cancel();
		        			  }
		        			});

		            	AlertDialog alertDialog = alert.create();
		            	alertDialog.show();
					
				}else if( (!color_is_choosen) && (no_ofColors>0))
				{
					/* Alert Dialog Code Start*/ 	
		        	AlertDialog.Builder alert = new AlertDialog.Builder(context);
		        	alert.setTitle("No Choosen Color"); //Set Alert dialog title here
		        	alert.setMessage("Please Choose The Color ! "); //Message here
		      	        	
		        	// if pressing OK, nothing happen ...
		        	alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		            	public void onClick(DialogInterface dialog, int whichButton) {
		            		// Canceled.
		            		  dialog.cancel();
		        			  }
		        			});

		            	AlertDialog alertDialog = alert.create();
		            	alertDialog.show();
					
				}else{ //Now both quantity and color are entered
				// if quantity is over
						 
				int selected_color_quantity= colors.get(selected_color_no-1).getInt("unit_Quantity");		
				if(Integer.parseInt(order_quantity.getText().toString()) > selected_color_quantity){
					/* Alert Dialog Code Start*/ 	
		        	AlertDialog.Builder alert = new AlertDialog.Builder(context);
		        	alert.setTitle("Over Quantity"); //Set Alert dialog title here
		        	alert.setMessage("The Quantity you enter is over than the availabel! "); //Message here
		      	        	
		        	// if pressing OK, nothing happen ...
		        	alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		            	public void onClick(DialogInterface dialog, int whichButton) {
		            		// Canceled.
		            		  dialog.cancel();
		        			  }
		        			});

		            	AlertDialog alertDialog = alert.create();
		            	alertDialog.show();
					
					
		            	// END if quantity is over
				}else{
					 String payment_type= "Not Specified";
					
					if (radioGroup.getCheckedRadioButtonId() == -1)
					{
					  // no radio buttons are checked
					
						
					}
					else
					{
					  //one of the radio buttons is checked
					  //the Payment Type: Radio Button 
					  //Find the selected 
						
						int selectedId = radioGroup.getCheckedRadioButtonId();
						 
						// find the radiobutton by returned id
					    radioButton = (RadioButton) findViewById(selectedId);
					    payment_type =radioButton.getText().toString();
						Toast.makeText(OrderProduct.this,
							radioButton.getText(), Toast.LENGTH_SHORT).show();
					}
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~`
					Toast.makeText(OrderProduct.this,
							order_quantity.getText().toString(), Toast.LENGTH_SHORT).show();
					
					////// NOW Start Add to DB
					
					//check if there is an open order
					
					try{
					ParseQuery<ParseObject> orderExistQuery = ParseQuery.getQuery("Order");
					
					ParseUser currentUser = ParseUser.getCurrentUser();
					ParseObject shopObject = ParseObject.createWithoutData("shop",shopId);
					orderExistQuery.whereEqualTo("ShopId", shopObject);
					orderExistQuery.whereEqualTo("user_id", currentUser);
					orderExistQuery.whereEqualTo("order_status", order_status);
					
					 List<ParseObject> ordersOpen = orderExistQuery.find();
			         if(ordersOpen.size()>0)
			         {
			        	 order_open_exist= true;
			        	 Log.d(TAG," open order = true");
			         }
						for(ParseObject order :ordersOpen){
							openedOrder =order;
			        		Log.d(TAG,openedOrder.getObjectId()+" the opened order id");
						
			        		
						}//end for
					}catch(ParseException e1){
						e1.printStackTrace();
					}
					/*
					orderExistQuery.findInBackground(new FindCallback<ParseObject>() {
					    public void done(List<ParseObject> ordersOpen, ParseException e) {
					        if (e == null) { //>>>there is an open order
					        
					        	order_open_exist= true;
					        	openedOrder= ordersOpen.get(0);
					        	Log.d(TAG,"there is an open order "+openedOrder.getObjectId()+" the opened order id");
					        	//Log.d(TAG,openedOrder.getObjectId()+" the opened order id");
					           // Log.d("score", "Retrieved " + scoreList.size() + " scores");
					        	for(ParseObject order :ordersOpen){
					        		openedOrder =order;
					        		Log.d(TAG,openedOrder.getObjectId()+" the opened order id");
					        						        		
					        	}
					        } else {
					        	Log.d(TAG,"no open order!!"+e.toString());
					           // Log.d("score", "Error: " + e.getMessage());
					        	order_open_exist= false;
					        }
					    }
					});
						*/				
					
						//------------------------------------------------------------------
					//------------------------------------------------------------------------
					if(!order_open_exist){
					final ParseObject order = new ParseObject("Order");
					//final Double unitCost=0.0;
					//Create the Order
					order.put("ShopId", ParseObject.createWithoutData("shop", shopId));
					//---> next line should be take off
					order.put("productId", ParseObject.createWithoutData("Product", productId));
					order.put("user_id",ParseUser.getCurrentUser());
					order.put("order_status",order_status);
					order.put("payment_type",payment_type);
					
					order.saveInBackground(new SaveCallback() {
						
						@Override
						public void done(ParseException e) {
							 order_id= order.getObjectId();
							//-------->>>>Test
							Toast.makeText(OrderProduct.this,
									"the new order id "+order_id, Toast.LENGTH_SHORT).show();
							//-------->>>>Test
							
							///////////////////////////////////////////////////////////////////////
							
							Double unitCost=0.0;
							if(productDiscount >0)
							{
								Double x= productPrice*(productDiscount/100);
								unitCost = productPrice-(productPrice*(productDiscount/100));
								Toast.makeText(OrderProduct.this,
										 "X:: " + x+ " in parse unit cost  "+unitCost, Toast.LENGTH_SHORT).show();
								//-------->>>>Test
							}else
								unitCost = productPrice;
							
							// after create an order in the DB, create Ordered_product object and add it to the DB
							final ParseObject  ordered_product = new ParseObject("Ordered_Product");
												
							ordered_product.put("product_id", ParseObject.createWithoutData("Product", productId));
							ordered_product.put("order_id", ParseObject.createWithoutData("Order", order_id));
							ordered_product.put("unit_cost",unitCost);
							ordered_product.put("product_quantity",Integer.parseInt(order_quantity.getText().toString()));
							ordered_product.put("order_color",colorSelected1);
							
							ordered_product.saveInBackground(new SaveCallback() {
								 public void done(ParseException e) {
						    	    	String ordered_prod_id= ordered_product.getObjectId();
						    	    	//-------->>>>Test
									if(e ==null){
						    	    	Toast.makeText(OrderProduct.this,
											"Success (: orderedprod id= "+ordered_prod_id, Toast.LENGTH_SHORT).show();
						    	    	
						    	    	
						    	    	//-------------> hold the quantity
						    	    	ParseObject product= ParseObject.createWithoutData("Product", productId);
						    	    	
						    	    	hold_quantity(ordered_product, product, colors.get(selected_color_no-1));
						    	    	
						    	    	
						    	    	// now close this activity 
						    	    	//---- Now to update the order page
										Intent intent = new Intent(OrderProduct.this,
						                        User_Order_Page.class);
									
								  		intent.putExtra("orderID", order_id);
										intent.putExtra("shopId", shopId);
										
						                startActivity(intent);
						                finish();
						    	    	
									//-------->>>>Test
									} else
									{
										Log.i(TAG,e.toString());
							
										Toast.makeText(OrderProduct.this,
												"Not Success !!! orderedprod id= "+ordered_prod_id, Toast.LENGTH_SHORT).show();
									}//end else
									}
		 			    	    
							});
							
							
						}
					});
					
					 //end if order is not open before
					}else
					{	//if an Active order is open from this shop for current user
						 order_id= openedOrder.getObjectId();
						 Double unitCost=0.0;
							if(productDiscount >0)
							{
								Double x= productPrice*(productDiscount/100);
								unitCost = productPrice-(productPrice*(productDiscount/100));
								Toast.makeText(OrderProduct.this,
										 "Opened X:: " + x+ " in parse unit cost  "+unitCost, Toast.LENGTH_SHORT).show();
								//-------->>>>Test
							}else
								unitCost = productPrice;
							
							//-------------------------------------------
							// create Ordered_product object and add it to the DB
							final ParseObject  ordered_product = new ParseObject("Ordered_Product");
												
							ordered_product.put("product_id", ParseObject.createWithoutData("Product", productId));
							ordered_product.put("order_id", ParseObject.createWithoutData("Order", order_id));
							ordered_product.put("unit_cost",unitCost);
							ordered_product.put("product_quantity",Integer.parseInt(order_quantity.getText().toString()));
							ordered_product.put("order_color",colorSelected1);
							
							
							ordered_product.saveInBackground(new SaveCallback() {
								 public void done(ParseException e) {
						    	    	String ordered_prod_id= ordered_product.getObjectId();
						    	    	//-------->>>>Test
									if(e ==null){
						    	    	Toast.makeText(OrderProduct.this,
											" opened >>Success (: orderedprod id= "+ordered_prod_id, Toast.LENGTH_SHORT).show();
									//-------->>>>Test
						    	    	
						    	    	//-------------> hold the quantity
						    	    	ParseObject product= ParseObject.createWithoutData("Product", productId);
						    	    	hold_quantity(ordered_product, product, colors.get(selected_color_no-1));
						    	    	
						    	    	
						    	    	// now close this activity 
						    	    	//---- Now to update the order page
										Intent intent = new Intent(OrderProduct.this,
						                        User_Order_Page.class);
									
								  		intent.putExtra("orderID", order_id);
										intent.putExtra("shopId", shopId);
										
						                startActivity(intent);
						                finish();
						    	    	
									} else
									{
										Log.i(TAG,e.toString());
										Toast.makeText(OrderProduct.this,
												" opened >> Not Success !!! orderedprod id= "+ordered_prod_id, Toast.LENGTH_SHORT).show();
									}//end else
									}
		 			    	    
							});
					}
					
					
					
					
					
/*
 					Double unitCost=0.0;
					if(productDiscount >0)
					{
						Double x= productPrice*(productDiscount/100);
						unitCost = productPrice-(productPrice*(productDiscount/100));
						Toast.makeText(OrderProduct.this,
								 "X:: " + x+ " in parse unit cost  "+unitCost, Toast.LENGTH_SHORT).show();
						//-------->>>>Test
					}
					else
						unitCost = productPrice;
					
					// after create an order in the DB, create Ordered_product object and add it to the DB
					final ParseObject  ordered_product = new ParseObject("Ordered_Product");
										
					ordered_product.put("product_id", ParseObject.createWithoutData("Product", productId));
					ordered_product.put("order_id", ParseObject.createWithoutData("Order", order_id));
					ordered_product.put("unit_cost",unitCost);
					ordered_product.put("product_quantity",order_quantity.getText().toString());
					
					ordered_product.saveInBackground(new SaveCallback() {
 			    	    public void done(ParseException e) {
 			    	    	String ordered_prod_id= ordered_product.getObjectId();
 			    	    	//-------->>>>Test
							Toast.makeText(OrderProduct.this,
									"the new ordered prod id "+ordered_prod_id, Toast.LENGTH_SHORT).show();
							//-------->>>>Test
 			    	    }});
					/*
					  ordered_product.saveInBackground(new SaveCallback() {
					 
						
						@Override
						public void done(ParseException e) {

							String ordered_prod_id= ordered_product.getObjectId();
							//-------->>>>Test
							Toast.makeText(OrderProduct.this,
									"the new ordered prod id "+ordered_prod_id, Toast.LENGTH_SHORT).show();
							//-------->>>>Test
						}
						
					
					});;
					*/
		
			
		}
				}//end else of no choosen quantity
		} //end on click for submit order
		
			
		
					
				});
							
				
	}
	
	private void fillProductSpcae() {
		
		//productId= "CzECnGlGWM";
		//--->
		Intent intent= getIntent();
		productId = intent.getStringExtra("productID");
		shopId = intent.getStringExtra("shopId");
		 
		try{
			
			TextView prodQuantity = (TextView)findViewById(R.id.prod_quantity);
			TextView prodPrice = (TextView)findViewById(R.id.prod_price);
			TextView prodName = (TextView)findViewById(R.id.prod_name);
			
			
		ParseQuery<ParseObject> productQuery = ParseQuery.getQuery("Product");
         productQuery.whereEqualTo("objectId", productId);
        
     	 List<ParseObject> product = productQuery.find();
     
     	 
			for(ParseObject prod :product){
				//Set the product name
				productObject =prod;
				
				shopId= prod.getParseObject("shopId").getObjectId().toString();
				productPrice = prod.getDouble("product_price");
				productQuantity = prod.getInt("product_quantity");
				productDiscount =prod.getDouble("product_discount");
				
				prodName.setText("Product: "+prod.get("ProductName").toString()+"");
				prodQuantity.setText("Availabe Quantity: "+prod.getInt("product_quantity")+"");
				prodPrice.setText("Price: "+prod.getDouble("product_price")+"");
				
				
				//set the product pic
				
				
				//---------> img
		    	final ParseImageView prdImg = (ParseImageView)findViewById(R.id.productIMG);
				ParseFile imageFile = prod.getParseFile("product_pic");
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

				
			} //end for
			
			//------------------------------------------------------------------
			// get the product colours
			ParseQuery<ParseObject> colorQuery = ParseQuery.getQuery("color");
			colorQuery.whereEqualTo("productId",  productObject);
	     	colors = colorQuery.find();
	     	no_ofColors=colors.size();
	     	  
			
			//------------------------------------------------------------------
			
			
			
			
		} catch (ParseException e1) {

			e1.printStackTrace();
		}
	}
	
	
    protected void hold_quantity(ParseObject ordered_prd, ParseObject product , ParseObject color) {
    	
    	int ordered_prd_quantity= ordered_prd.getInt("product_quantity") ;
    	product.increment("product_quantity", -1*ordered_prd_quantity);//decrease
    	product.increment("hold_quantity", ordered_prd_quantity); //increase
		product.saveInBackground();
		
		color.increment("unit_Quantity", -1*ordered_prd_quantity); //decrease
		color.saveInBackground();
		
	}
    protected void release_quantity(ParseObject ordered_prd, ParseObject product) {
    	int ordered_prd_quantity= ordered_prd.getInt("product_quantity") ;
    	product.increment("product_quantity", ordered_prd_quantity);//increase
    	product.increment("hold_quantity", -1*ordered_prd_quantity); //decrease
		product.saveInBackground();
    	
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
