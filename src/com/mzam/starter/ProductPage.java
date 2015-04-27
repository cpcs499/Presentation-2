package com.mzam.starter;

import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
public class ProductPage extends Activity {
	
	String phone;
	String prodId;
    ParseObject product;
    String shopId;
	//////////////////////////////
	//Parse color objects....
	ParseObject color1;
	ParseObject color2;
	ParseObject color3;
	ParseObject color4;
	ParseObject color5;

	//---------------------
	// Color Image Views .....
	ImageView imageColor1;
	ImageView imageColor2;
	ImageView imageColor3;
	ImageView imageColor4;
	ImageView imageColor5;
    //-----------------------
	// Colors Selected  .....
	int colorSelected1;
	int colorSelected2;
	int colorSelected3;
	int colorSelected4;
	int colorSelected5;
	
	//-----------------------
	// Colors Quantities .....
	int colorQuantity1;
	int colorQuantity2;
	int colorQuantity3;
	int colorQuantity4;
	int colorQuantity5;
	
	TextView ColorQnt1;
	TextView ColorQnt2;
	TextView ColorQnt3;
	TextView ColorQnt4;
	TextView ColorQnt5;
	//-----------------------
	TextView name;
	TextView Description;
	TextView TotalQnt;
	TextView Price;
	//////////////////////
	int [] colors =new int[5];
	int [] quantities=new int[5];
	int  totalQnt;
	
	final Context context = this;
	////////////////////
	TextView editBtn,ProductOrder;
	TextView deleteBtn;
	Button rate ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_page);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6adbd9"))); // set your desired color

			Intent i = getIntent();
			// Get the intent from ListViewAdapter
			
			prodId =i.getStringExtra("productid");
            name = (TextView)findViewById(R.id.NameSet);
			Description = (TextView)findViewById(R.id.DescSet);
			TotalQnt = (TextView)findViewById(R.id.QuantSet);
			Price = (TextView)findViewById(R.id.PriceSet);
			ProductOrder = (TextView)findViewById(R.id.textView4);
			// Locate the ImageView in singleitemview.xml
			final ParseImageView imgphone = (ParseImageView) findViewById(R.id.phone);
			shopId= i.getStringExtra("shopId");
			// Load image into the ImageView
						
			TextView order_this_prodcut;
            order_this_prodcut = (TextView)findViewById(R.id.order_the_product);
            

            order_this_prodcut.setOnClickListener(new View.OnClickListener() {
                
                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    Intent in = new Intent (ProductPage.this,OrderProduct.class);
                    in.putExtra("productID", prodId);
                      //-------------------------->>heeeeeeeeeeeeere>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                    in.putExtra("shopId", shopId);
                    
                    startActivity(in);
                }
            });
            
						
						
			// Colorssss 
			imageColor1 =(ImageView)findViewById(R.id.color1);
			LayoutParams params = (LayoutParams) imageColor1.getLayoutParams();
			params.width = 80;
			params.height=80;
			// existing height is ok as is, no need to edit it
			imageColor1.setLayoutParams(params);
			//imageColor1.setBackgroundColor(0xffffffff);
			
			imageColor2 =(ImageView)findViewById(R.id.color2);
			params = (LayoutParams) imageColor2.getLayoutParams();
			params.width = 80;
			params.height=80;
			// existing height is ok as is, no need to edit it
			imageColor2.setLayoutParams(params);
			//imageColor2.setBackgroundColor(0xffffffff);
			
			imageColor3 =(ImageView)findViewById(R.id.color3);
			params = (LayoutParams) imageColor3.getLayoutParams();
			params.width = 80;
			params.height=80;
			// existing height is ok as is, no need to edit it
			imageColor3.setLayoutParams(params);
			//imageColor3.setBackgroundColor(0xffffffff);
			
			imageColor4 =(ImageView)findViewById(R.id.color4);
			params = (LayoutParams) imageColor4.getLayoutParams();
			params.width = 80;
			params.height=80;
			// existing height is ok as is, no need to edit it
			imageColor4.setLayoutParams(params);
			//imageColor4.setBackgroundColor(0xffffffff);
			
			imageColor5=(ImageView)findViewById(R.id.color5);
			params = (LayoutParams) imageColor5.getLayoutParams();
			params.width = 80;
			params.height=80;
			// existing height is ok as is, no need to edit it
			imageColor5.setLayoutParams(params);
			//imageColor5.setBackgroundColor(0xffffffff);
			
			
			
			// Qunatities
			ColorQnt1= (TextView) findViewById(R.id.qnt1);
			ColorQnt2= (TextView) findViewById(R.id.qnt2);
			ColorQnt3= (TextView) findViewById(R.id.qnt3);
			ColorQnt4= (TextView) findViewById(R.id.qnt4);
			ColorQnt5= (TextView) findViewById(R.id.qnt5);
			
			
			// Query .................
			
			ParseQuery<ParseObject> query = ParseQuery.getQuery("Product");
			query.getInBackground(prodId, new GetCallback<ParseObject>() {
			  public void done(ParseObject object, ParseException e) {
			    if (e == null) {

                name.setText(object.getString("ProductName"));
                Description.setText(object.getString("product_description"));
                Price.setText(String.valueOf(object.getDouble("product_price")));
                ParseFile imageFile = object.getParseFile("product_pic");
                
                imgphone.setParseFile(imageFile);
                imgphone.loadInBackground();
        		
			    } else {
			    	
			    	e.getStackTrace();
  			    }
			  }
			});
			
			ParseQuery<ParseObject> query1 = ParseQuery.getQuery("color");
			ParseObject prod= ParseObject.createWithoutData("Product", prodId);
			query1.whereEqualTo("productId", prod);
			query1.findInBackground(new FindCallback<ParseObject>() {
			    public void done(List<ParseObject> color, ParseException e) {
			    	
			    	int t=0;
			        if (e == null) {
   
			       for (ParseObject co :color)
			       {
			    	  colors[t]=co.getInt("color_number");
			    	  quantities[t]=co.getInt("unit_Quantity");
			    	  t++;
			    	  
			       }
			       imageColor1.setBackgroundColor(colors[0]);
					imageColor2.setBackgroundColor(colors[1]);
					imageColor3.setBackgroundColor(colors[2]);
					imageColor4.setBackgroundColor(colors[3]);
					imageColor5.setBackgroundColor(colors[4]);
					
					ColorQnt1.setText(String.valueOf(quantities[0]));
					ColorQnt2.setText(String.valueOf(quantities[1]));
					ColorQnt3.setText(String.valueOf(quantities[2]));
					ColorQnt4.setText(String.valueOf(quantities[3]));
					ColorQnt5.setText(String.valueOf(quantities[4]));
					
					
					totalQnt = quantities[0]+quantities[1]+quantities[2]+quantities[3]+quantities[4];
					
					TotalQnt.setText(String.valueOf(totalQnt)); 
			        } else {
              e.getStackTrace();
               }
			        
			        
			    }
			});
			
			
				/////////////////////////////////////////////////////
							
				/// EDIT AND DELETE // ------------------------
				
				// DELETE 
				
				deleteBtn = (TextView) findViewById(R.id.delete);
				deleteBtn.setOnClickListener(new OnClickListener() {	 
					public void onClick(View arg0) {
					
						/* Alert Dialog Code Start*/ 	
						AlertDialog.Builder alert = new AlertDialog.Builder(context);
						alert.setTitle("Delete a  product"); //Set Alert dialog title here
						alert.setMessage("Do you want to delete the product? "); //Message here
						
						alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
						
						
						ParseQuery<ParseObject> query = ParseQuery.getQuery("Product");
						query.getInBackground(prodId, new GetCallback<ParseObject>() {
						public void done(ParseObject object, ParseException e) {
						if (e == null) {
						
						object.deleteInBackground();
						//Intent intent = new Intent(ProductPage.this,ShopSingleItemView.class);
						finish();
						//startActivity(intent);
						
						Toast.makeText(getApplicationContext(), 
						"detelted",
						Toast.LENGTH_SHORT).show();
						} else {
						
						Toast.makeText(getApplicationContext(), 
							"not detelted",
							Toast.LENGTH_SHORT).show();
						e.getStackTrace();
						}
						}
						});
						
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
						
						/*Intent intent = new Intent(Sign_In.this, Sign_Up.class);
						startActivity(intent);*/
					
					
					}
					});
				
				editBtn = (TextView) findViewById(R.id.edit);
				editBtn.setOnClickListener(new OnClickListener() {	 
					public void onClick(View arg0) {
						
						Intent n = new Intent (ProductPage.this,EditProduct.class);
						n.putExtra("prod_id", prodId);
						n.putExtra("name", name.getText().toString());
						n.putExtra("desc", Description.getText().toString());
						n.putExtra("price",Double.parseDouble(Price.getText().toString()));
						n.putExtra("color1", colors[0]);
						n.putExtra("color2", colors[1]);
						n.putExtra("color3", colors[2]);
						n.putExtra("color4", colors[3]);
						n.putExtra("color5", colors[4]);
						
						n.putExtra("qnt1", quantities[0]);
						n.putExtra("qnt2", quantities[1]);
						n.putExtra("qnt3", quantities[2]);
						n.putExtra("qnt4", quantities[3]);
						n.putExtra("qnt5", quantities[4]);
						
						finish();
						startActivity(n);
						
					}});
					
				
				ProductOrder.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Intent in = new Intent (ProductPage.this,ViewProductOrders.class);
						in.putExtra("productID", prodId);
						startActivity(in);
					}
				});
				
				TextView comment = (TextView)findViewById(R.id.textView5);
				comment.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Intent in = new Intent (ProductPage.this,ProductComments.class);
						in.putExtra("PRODUCT_ID", prodId);
						startActivity(in);
					}
				});
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
	
	public void onResume(){
		super.onResume();{
			Button wow = (Button)findViewById(R.id.button1);
			Button wtf = (Button)findViewById(R.id.button2);
			Button good = (Button)findViewById(R.id.button3);
			
			final TextView wowcount = (TextView)findViewById(R.id.textView7);
			final TextView goodcount = (TextView)findViewById(R.id.textView6);
			final TextView wtfcount = (TextView)findViewById(R.id.TextView01);
			
			final ParseQuery<ParseObject> ratec = ParseQuery.getQuery("Product_User_Rate");
			ratec.whereEqualTo("product_id", prodId);
			try{
			ratec.whereEqualTo("rate_type", "1");
			wowcount.setText(ratec.count()+"");
			ratec.whereEqualTo("rate_type", "2");
			goodcount.setText(ratec.count()+"");
			ratec.whereEqualTo("rate_type", "3");
			wtfcount.setText(ratec.count()+"");
			}catch(ParseException e){
				e.printStackTrace();
			}
			
			final ParseQuery<ParseObject> rate = ParseQuery.getQuery("Product_User_Rate");
			rate.whereEqualTo("user_id", ParseUser.getCurrentUser());
			rate.whereEqualTo("product_id", prodId);
			final ParseObject productRate = new ParseObject("Product_User_Rate");
			
			wow.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					rate.getFirstInBackground(new GetCallback<ParseObject>(){
						
						
						@Override
						public void done(ParseObject object, ParseException e) {
							// TODO Auto-generated method stub
							if(object!=null && object.getString("rate_type").equals("1"))
							{
								Toast.makeText(getApplicationContext(), "you chhose it before", Toast.LENGTH_SHORT).show();
							}
							else if(object!=null&&(object.getString("rate_type").equals("2")||object.getString("rate_type").equals("3")))
							{
								productRate.put("rate_type", "1");
								productRate.saveInBackground();
								Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
							}
							else if (object==null)
							{
								Toast.makeText(getApplicationContext(), "Not Exist", Toast.LENGTH_SHORT).show();
								productRate.put("product_id", prodId);
								productRate.put("user_id", ParseUser.getCurrentUser());
								productRate.put("rate_type", "1");
								productRate.saveInBackground();
							}	
							
							try
							{
							rate.whereEqualTo("rate_type", "1");
							wowcount.setText(rate.count()+"");
							rate.whereEqualTo("rate_type", "2");
							goodcount.setText(rate.count()+"");
							rate.whereEqualTo("rate_type", "3");
							wtfcount.setText(rate.count()+"");
							}catch(ParseException ee){
								ee.printStackTrace();
							}
						}
						
					});
					
				}
				
			});
			
			good.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					rate.getFirstInBackground(new GetCallback<ParseObject>(){
						@Override
						public void done(ParseObject object, ParseException e) {
							// TODO Auto-generated method stub
							if(object!=null && object.getString("rate_type").equals("2"))
							{
								Toast.makeText(getApplicationContext(), "you chhose it before", Toast.LENGTH_SHORT).show();
							}
							else if(object!=null&&(object.getString("rate_type").equals("1")||object.getString("rate_type").equals("3")))
							{
								productRate.put("rate_type", "2");
								productRate.saveInBackground();
								Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
							}
							else if (object==null)
							{
								Toast.makeText(getApplicationContext(), "Not Exist", Toast.LENGTH_SHORT).show();
								productRate.put("product_id", prodId);
								productRate.put("user_id", ParseUser.getCurrentUser());
								productRate.put("rate_type", "2");
								productRate.saveInBackground();
							}
							try
							{
							rate.whereEqualTo("rate_type", "1");
							wowcount.setText(rate.count()+"");
							rate.whereEqualTo("rate_type", "2");
							goodcount.setText(rate.count()+"");
							rate.whereEqualTo("rate_type", "3");
							wtfcount.setText(rate.count()+"");
							}catch(ParseException ee){
								ee.printStackTrace();
							}
							
						}
					});
					
				}
			});
			
			wtf.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					rate.getFirstInBackground(new GetCallback<ParseObject>(){
						@Override
						public void done(ParseObject object, ParseException e) {
							// TODO Auto-generated method stub
							if(object!=null && object.getString("rate_type").equals("3"))
							{
								Toast.makeText(getApplicationContext(), "you chhose it before", Toast.LENGTH_SHORT).show();
							}
							else if(object!=null&&(object.getString("rate_type").equals("1")||object.getString("rate_type").equals("2")))
							{
								productRate.put("rate_type", "3");
								productRate.saveInBackground();
								
								
								Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
							
							}
							else if (object==null)
							{
								Toast.makeText(getApplicationContext(), "Not Exist", Toast.LENGTH_SHORT).show();
								productRate.put("product_id", prodId);
								productRate.put("user_id", ParseUser.getCurrentUser());
								productRate.put("rate_type", "3");
								productRate.saveInBackground();
								
							}
							try
							{
							rate.whereEqualTo("rate_type", "1");
							wowcount.setText(rate.count()+"");
							rate.whereEqualTo("rate_type", "2");
							goodcount.setText(rate.count()+"");
							rate.whereEqualTo("rate_type", "3");
							wtfcount.setText(rate.count()+"");
							}catch(ParseException ee){
								ee.printStackTrace();
							}
						}
					});
					
					
				}
			});
		}
	}				
}
		            					    
		
