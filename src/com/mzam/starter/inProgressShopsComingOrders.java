package com.mzam.starter;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

public class inProgressShopsComingOrders extends Fragment {
	ListView listView ;
	ImageView userpic;
	Switch switchT1;
	List<String> obList ,obList2,obList3,obList4,obList5;
	List<ParseObject> ob,os;
	List<ParseUser> ou;
	ParseUser fl = ParseUser.getCurrentUser();
	ParseQueryAdapter<ParseObject> adapter ;
	TextView profileusername;
    
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		  View myFragmentView = inflater.inflate(R.layout.shopcomingorders_main, container, false);
		  listView = (ListView) myFragmentView.findViewById(R.id.listView1);
		  
		  //listView.setAdapter(new VersiAdapter(this));
		  
			return myFragmentView;
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
        
        public VersiAdapter(inProgressShopsComingOrders activity) {
            // TODO Auto-generated constructor stub
            layoutInflater = (LayoutInflater) activity.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	
            obList = new ArrayList<String>();
            obList2 = new ArrayList<String>();
            obList3 = new ArrayList<String>();
            obList4 = new ArrayList<String>();
            obList5 = new ArrayList<String>();
            
            try {
                ParseQuery<ParseObject> innerQuery = ParseQuery.getQuery("Shop");
            	innerQuery.whereEqualTo("userOpen", ParseUser.getCurrentUser());
            	//List<ParseObject> ss = innerQuery.find();
            	ob = innerQuery.find();
    			for(ParseObject m:ob){
    				ParseQuery<ParseObject> y = ParseQuery.getQuery("Order");
    				ParseObject obj = ParseObject.createWithoutData("Shop",m.getObjectId());
    				y.whereEqualTo("order_status", "inProgress");
    				y.whereEqualTo("ShopId", obj);
    				List<ParseObject> gg = y.find();
    				for(ParseObject c:gg){
    					//Toast.makeText(getApplicationContext(), c.getObjectId()+"", Toast.LENGTH_LONG).show(); 	
    					obList.add(c.getParseObject("ShopId").getObjectId());
    					obList2.add(c.getParseObject("user_id").getObjectId());
    					obList3.add(c.getParseObject("productId").getObjectId());
    					obList4.add(c.getObjectId());
    					obList5.add(c.getString("PaymentMethod"));
    				}
    			
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
                listItem = layoutInflater.inflate(R.layout.all_orders_row, null);   
            }
            
            // Initialize the views in the layout
            final ImageView iv = (ImageView) listItem.findViewById(R.id.imageView1);
             profileusername = (TextView) listItem.findViewById(R.id.textView1);
            final TextView Productname = (TextView) listItem.findViewById(R.id.textView2);
            final TextView ProductQty = (TextView) listItem.findViewById(R.id.textView3);
            final TextView TotalCost = (TextView) listItem.findViewById(R.id.textView4);
            final TextView shopNAme = (TextView) listItem.findViewById(R.id.textView6);
            final TextView paymentMethod = (TextView) listItem.findViewById(R.id.textView7);

            
            
            switchT1 = (Switch)listItem.findViewById(R.id.switch1);
            
            
          //----------------------------- Start Shop &username name Query ------------------------------------------------ 

            
            paymentMethod.setText("Payment Type: "+obList5.get(pos)+"");
           
            
            try{
                ParseQuery<ParseObject> cc = ParseQuery.getQuery("Shop");
            	cc.whereEqualTo("objectId", obList.get(pos));
            	
            	List<ParseObject> oo = cc.find();
    			//for(ParseObject m:oo){
            	// Or like this...
                for(int i = 0; i < oo.size(); i++)
                   shopNAme.setText("Shop name: "+oo.get(i).get("Shop_name").toString()+"");
    					
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
    			
            switchT1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            	   @Override
            	   public void onCheckedChanged(CompoundButton buttonView,
            	     boolean isChecked) {
            	    
            	    if(isChecked){
            	    	//switchT1.setVisibility(View.INVISIBLE);
            	    	ParseQuery<ParseObject> query = ParseQuery.getQuery("Order");
            	    	
            	    	query.getInBackground(obList4.get(pos), new GetCallback<ParseObject>() {
            	    		  public void done(ParseObject ord, ParseException e) {
            	    		    if (e == null) {
            	    		    	onResume();
            	    		    	//switchT1.setVisibility();
            	    		    		ord.put("order_status", "Payed");
       					    	    	ord.saveInBackground();
       					    	    	Toast.makeText(getActivity(),
       					        				String.valueOf("Your Selected is On"),
       					        					Toast.LENGTH_SHORT).show();
       					    	    	//onResume();
       					    	    	//listView.invalidateViews();
       					    	    	
       					    	    	//listView.removeViewInLayout(ProductQty);
       					    	    	
            	    		    }
            	    		  }
            	    	});
            	    	
            	    	
            	    }else{
            	    	Toast.makeText(getActivity(),
                				String.valueOf("Your Selected is Off"),
                					Toast.LENGTH_SHORT).show();
            	    }

            	   }
            	
            	   
            	  }); 
            
         profileusername.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					String v = obList2.get(pos);
					Intent in = new Intent(getActivity(),UserProfile_ME.class);
					in.putExtra("userProfile", v);
					//setResult(in,0);
					getActivity().startActivity(in);
					//Toast.makeText(getActivity(), obList2.get(pos)+"", Toast.LENGTH_LONG).show();
				}
			});
            
            return listItem; 
            
        }
        
        

    }
	
}