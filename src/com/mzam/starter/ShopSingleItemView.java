package com.mzam.starter;


import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
 
public class ShopSingleItemView extends Activity {
	// Declare Variables
	Intent io;
	String shopId,shopna,shopdes;
	ParseImageView img1;
	ImageView deleteDialog,addProduct;
	TextView txtname,txtdesc,shopOrder;
	ParseFile imageFile;
	GridView gridview;
	Context context = this;
	ArrayList<String> obList;
	TextView productnumber;
	Button query;
	Bundle bundl=new Bundle();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop_singleitemview);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6adbd9")));
		io = getIntent();
        shopId = io.getStringExtra("SHOP_ID");

        img1 = (ParseImageView) findViewById(R.id.imageView1);
		txtname = (TextView) findViewById(R.id.name);
		txtdesc = (TextView) findViewById(R.id.desc);
		gridview = (GridView) findViewById(R.id.gridProd);
		shopOrder= (TextView)findViewById(R.id.textView4);
        query = (Button)findViewById(R.id.query);
        
		
		
		       // Clickable Image to allow you to select images for product 
				addProduct= (ImageView)findViewById(R.id.AddProduct);
				addProduct.setOnClickListener(new OnClickListener(){
			        public void onClick(View view) {
			        	Intent intent= new Intent(ShopSingleItemView.this,AddProduct.class);
			        	intent.putExtra("shop_id",shopId);
		    			finish();
		    		   startActivity(intent);
			          }
			        });
				
			
				// Clickable Image to allow you to select images for product 
				
				shopOrder.setOnClickListener(new OnClickListener(){
			        public void onClick(View view) {
		    		   final CharSequence[] items = {"inProgress Orders", "Complete Orders"};

		    	        AlertDialog.Builder builder = new AlertDialog.Builder(context);
		    	        builder.setTitle("Make your selection");
		    	        builder.setItems(items, new DialogInterface.OnClickListener() {
		    	            public void onClick(DialogInterface dialog, int item) {
		    	                // Do something with the selection
		    	                if(item == 0){
		    	                	Intent intent= new Intent(ShopSingleItemView.this,inProgressOneShopComingOrder.class);
		    			        	intent.putExtra("SHOP_ID", shopId);
		    		    			//finish();
		    		    		    startActivity(intent);
		    	                }
		    	                else if(item == 1){
		    	                	Intent intent= new Intent(ShopSingleItemView.this,PayedOneShopComingOrder.class);
		    			        	intent.putExtra("SHOP_ID", shopId);
		    		    			//finish();
		    		    		    startActivity(intent);
		    	                }
		    	            }
		    	        });
		    	        AlertDialog alert = builder.create();
		    	        alert.show();
			          }
			        });
			        
        
	}	
	
	
	public void onResume(){
		super.onResume();{
			ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("shop");
			query.getInBackground(shopId, new GetCallback<ParseObject>(){
	        	@Override
				public void done(ParseObject object, ParseException e) {
					// TODO Auto-generated method stub
	        		
	        		txtname.setText(object.getString("shop_name"));
	        		txtdesc.setText(object.getString("shop_desc"));
					imageFile = object.getParseFile("shopImage");
					if (imageFile != null) {
						img1.setParseFile(imageFile);
						img1.loadInBackground();
					}
					else
					{
						Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.inspiration_6);
						img1.setImageBitmap(bitmap); // for pevieeeewwww		
					}
					
				}
			});
			
			TextView editDialog = (TextView) findViewById(R.id.edit);
			editDialog.setOnClickListener(new OnClickListener() {	 
				public void onClick(View arg0) {
					
					Intent intent = new Intent (ShopSingleItemView.this,EditShopInfo.class);
					// Start SingleItemView Class
				    intent.putExtra("SHOP_ID",shopId);
					finish();
					startActivity(intent);
				
	}
		});
			
			deleteDialog = (ImageView) findViewById(R.id.imageView3);
			deleteDialog.setOnClickListener(new OnClickListener() {	 
				public void onClick(View arg0) {
					
					 /* Alert Dialog Code Start*/ 	
		        	AlertDialog.Builder alert = new AlertDialog.Builder(context);
		        	alert.setTitle("Delete a  shop"); //Set Alert dialog title here
		        	alert.setMessage("Do you want to delete this shop, if so all products will be deleted?"); //Message here
		      
		        	alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		            	public void onClick(DialogInterface dialog, int whichButton) {
		            	
		            		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("shop");
					          query.getInBackground(shopId,new GetCallback<ParseObject>() {
										  public void done(ParseObject object, ParseException e) {
										    if (e == null) {
										    	object.deleteInBackground();
										    	finish();
							        		   Toast.makeText(context, 
														"detelted",
														Toast.LENGTH_SHORT).show();
										    } else {
	                 	Toast.makeText(context, 
								"not detelted",
								Toast.LENGTH_SHORT).show();
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
		}
			});
			
			gridview.setAdapter(new VersiAdapter(this));
			
			
			

			try {
	            
	        	ParseQuery<ParseObject> query2 = new ParseQuery<ParseObject>("Product");
	        	ParseObject obj = ParseObject.createWithoutData("shop",shopId);
	        	query2.whereEqualTo("shopId", obj);
	        	int count = query2.count();
	    		productnumber = (TextView) findViewById(R.id.textView3);
	    		productnumber.setText(""+count+" Products");
	    		productnumber.setGravity(Gravity.CENTER);
	        	
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
				
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
	
	// Create an Adapter Class extending the BaseAdapter
    class VersiAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;
        
        public VersiAdapter(ShopSingleItemView activity) {
            // TODO Auto-generated constructor stub
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	
            obList = new ArrayList<String>();
            try {
            
            	ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Product");
            	ParseObject obj = ParseObject.createWithoutData("shop",shopId);
            	query.whereEqualTo("shopId", obj);
            	List<ParseObject>ob = query.find();
            	for (ParseObject num : ob) {
            		obList.add(num.getObjectId());
    			}
    			} catch (ParseException e1) {
    				// TODO Auto-generated catch block
    				e1.printStackTrace();
    			}
 	
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
                listItem = layoutInflater.inflate(R.layout.gridview_item, null);   
            }
            
            // Initialize the views in the layout
            final ParseImageView iv = (ParseImageView) listItem.findViewById(R.id.phone);
            LayoutParams params = new LayoutParams(360, 360);
            iv.setLayoutParams(params);
            ParseQuery<ParseObject> cc = ParseQuery.getQuery("Product");
            cc.getInBackground(obList.get(pos), new GetCallback<ParseObject>(){

				@Override
				public void done(final ParseObject object, ParseException e) {
					// TODO Auto-generated method stub
					
					ParseFile fileObject = (ParseFile) object.get("product_pic");
					if(fileObject!=null){
						iv.setParseFile(fileObject);
						iv.loadInBackground();
					}
					else
					{
							Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
									R.drawable.inspiration_6);
							iv.setImageBitmap(bitmap); // for pevieeeewwww
		
					}
				}
				
            	
            });
            
            gridview.setOnItemClickListener(new OnItemClickListener() {
    			public void onItemClick(AdapterView<?> parent, View v,int position, long id) {
    			   Toast.makeText(getApplicationContext(),position+"", Toast.LENGTH_SHORT).show();
    			   Intent in = new Intent(ShopSingleItemView.this,ProductPage.class);
    			   in.putExtra("productid", obList.get(position).toString());
    			//Toast.makeText(getApplicationContext(),obList.get(position), Toast.LENGTH_SHORT).show();
    				//object.getObjectId().toString();
    			   startActivity(in);
    			   
    			}
    		});

            
            
            
            return listItem; 
            
            
            
        }
        
        

    }
	
}