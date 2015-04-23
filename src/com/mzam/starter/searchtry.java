package com.mzam.starter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.TreeMap;

import com.mzam.starter.ShopSingleItemView.VersiAdapter;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout.LayoutParams;

public class searchtry extends Activity {
	/** Called when the activity is first created. */
	Typeface ft;
	//ArrayAdapter<String> myAdapter;
	ListView listView;
    String[] dataArray = new String[] {"India","Androidhub4you", "Pakistan", "Srilanka", "Nepal", "Japan"};
    AutoCompleteAdapter myAdapter;
    ArrayList<String> picList,picList2,picList3,picList4;
    List<String> productsIds;
    List<String> userIds;
    HashMap<String,Double> map = new  HashMap<String,Double>();
    Map sortedMap ;
    int[][] simArray = new int [6][7];
    List<ParseObject> userParseIds;
    List<ParseObject> productParseIds;
    List<ParseObject>userUser;
    double [] sim = new double [6];
   // Recommendation variables 
   TextView reco;
    String BasicOrderId, BasicProductId;
    RadioGroup radio;
    GridView gridview;
    
////////////////////////////////////////////////////////////
    
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trysearch);	
		//getActionBar().setDisplayHomeAsUpEnabled(true);
		//==========================
		
		picList = new ArrayList<String>();
		picList4 = new ArrayList<String>();
		picList2 = new ArrayList<String>();
		ft = Typeface.createFromAsset(getAssets(),"Fonts/Rosemary.ttf");
		reco = (TextView) findViewById(R.id.recommend);
		reco.setTypeface(ft);
		
		gridview = (GridView) findViewById(R.id.gridProd);
		radio= (RadioGroup) findViewById(R.id.radGrp);

		
	/*	reco.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {*/
				productParseIds= new ArrayList<ParseObject>();
				userUser= new ArrayList<ParseObject>();
	
				
//	[1]  Query the order table for last ordered product. 				
ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Order");
query1.orderByDescending("createdAt");
try {
ParseObject precoProduct = query1.getFirst();
BasicOrderId = precoProduct.getObjectId();
BasicProductId=precoProduct.getParseObject("productId").getObjectId(); // *** Book Id *** // 
ParseObject BasicObj = ParseObject.createWithoutData("Product",BasicProductId);
//productParseIds.add(BasicObj);
 //Toast.makeText(getApplicationContext(),  BasicProductId, Toast.LENGTH_LONG).show(); 
////////////////////////////////////////////////////////////////////////////////////////////			

       // [2] Query about users who order this product . 
	      ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Order");	 
	      query1.orderByDescending("createdAt");
	      query1.whereEqualTo("productId", BasicObj);
	      query1.setLimit(6);
	      userParseIds=query1.find();
/*	Toast.makeText(getApplicationContext(),  userParseIds.get(0).getObjectId()+" "+userParseIds.get(1).getObjectId()+userParseIds.get(2).getObjectId(), Toast.LENGTH_LONG).show(); 
*/	      
		  for(ParseObject c: userParseIds){
          // * Done: Toast.makeText(getApplicationContext(),  c.getParseObject("user_id").getObjectId(), Toast.LENGTH_LONG).show(); 			
	      ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			  
		           // [3] Query from order table for each user what is the last products ordered 
			            ParseQuery<ParseObject> query3 = ParseQuery.getQuery("Order");
			            ParseObject user = ParseObject.createWithoutData("_User",c.getParseObject("user_id").getObjectId());
			            userUser.add(user);
                        //	Toast.makeText(getApplicationContext(),  user.getObjectId(), Toast.LENGTH_LONG).show(); 			
			            query3.whereEqualTo("user_id", user);
		
                        // Toast.makeText(getApplicationContext(),  String.valueOf(query3.count()), Toast.LENGTH_LONG).show(); 
			            query3.whereNotEqualTo("productId", BasicObj);
			            ParseObject otherOrder = query3.getFirst();
			            ParseObject otherProduct = ParseObject.createWithoutData("Product",otherOrder.getParseObject("productId").getObjectId());
			            productParseIds.add(otherProduct);
                        //Toast.makeText(getApplicationContext(), user.getObjectId () +" prod: "+otherProduct.getParseObject("productId").getObjectId(), Toast.LENGTH_LONG).show(); 						
		                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			            
		  }//end for each user objects
		  
/*		  Toast.makeText(getApplicationContext(),  userUser.get(0).getObjectId()+" "+userUser.get(1).getObjectId()+" "+userUser.get(2).getObjectId(), Toast.LENGTH_LONG).show(); 
*/
/*		  Toast.makeText(getApplicationContext(),  "0-"+productParseIds.get(0).getObjectId()+" 1-"+ productParseIds.get(1).getObjectId()+" 2-"+ productParseIds.get(2).getObjectId()+" 3-"+productParseIds.get(3).getObjectId(), Toast.LENGTH_LONG).show(); 
*/
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

///////////////////////////////////////////////////////////////////////////////////////////////////




ParseQuery<ParseObject> query4= ParseQuery.getQuery("Order");

// [4] loop to fill the 2D array: that is compare between users and products. 
      for(int i=0;i<simArray.length;i++)
        {
        	for(int j=0;j<6;j++)
        	{
        	 query4.whereEqualTo("user_id",userUser.get(i));//ParseObject.createWithoutData("_User","5ZblnAArki"));//
        	 query4.whereEqualTo("productId", productParseIds.get(j));//ParseObject.createWithoutData("Product","NBv3UX2oAa"));//
        	 
        	 
        	 try {
				ParseObject o = query4.getFirst();
				if(o.getObjectId()!="")
				{
					simArray[i][j]=1;
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	 
        	 
/*  	 Toast.makeText(getApplicationContext(),  "i= "+i+" "+userUser.get(i).getObjectId()+" j="+j+" "+productParseIds.get(j).getObjectId(), Toast.LENGTH_LONG).show(); 
        	 Toast.makeText(getApplicationContext(),  "i= "+i+" j= "+j+" "+simArray[i][j], Toast.LENGTH_LONG).show(); */
        	}
        }// for 

      
  
      int Sum=0;
      double simi=0.0;
      int j=0;
      do{
      for (int i=0;i<simArray.length;i++)
      {
    	  Sum=(Sum+simArray[i][j]);
      }
      simi= (Sum/((Math.sqrt(simArray.length))*(Math.sqrt(Sum))));
      
      map.put(productParseIds.get(j).getObjectId(), simi);
	  //sim[0]=simi;
      Sum=0;
      simi=0;
      
      j++;
      }while(j<6);
      
   /*   map.put(productParseIds.get(0).getObjectId(), .4);
      map.put(productParseIds.get(1).getObjectId(), .5);*/
     /* sim[0]=simi;
      sim[1]=.5;
      sim[2]=.4;*/
      System.out.println(map);
    sortedMap = sortByValue(map);
		System.out.println(sortedMap);
		
      
      
gridview.setAdapter(new VersiAdapter(this));


				
				
				
	/*			
				
			}});*/
			
///////////////////////////////////////////////////////////////////
try {
ParseQuery<ParseUser> y = ParseQuery.getUserQuery();
//innerQuery.whereEqualTo("username", newText);

List<ParseUser> gg = y.find();
for(ParseObject c:gg){
//Toast.makeText(getApplicationContext(), c.getObjectId()+"", Toast.LENGTH_LONG).show(); 	
picList.add(c.getString("username"));
}
} catch (ParseException e1) {
//TODO Auto-generated catch block
e1.printStackTrace();
}

 
try {
ParseQuery<ParseObject> yy =ParseQuery.getQuery("shop");
List<ParseObject> g = yy.find();
for(ParseObject cc:g){
picList.add(cc.getString("shop_name"));
}


} catch (ParseException e1) {
//TODO Auto-generated catch block
e1.printStackTrace();
}

	
	
	    
		//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,androidBooks);
        AutoCompleteTextView acTextView = (AutoCompleteTextView)findViewById(R.id.AndroidBooks);
        acTextView.setTypeface(ft);
        myAdapter = new AutoCompleteAdapter(this,R.layout.searchresult,picList);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,androidBooks);
        
        acTextView.setThreshold(2);
        acTextView.setAdapter(myAdapter);

        
        acTextView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				try{
				ParseQuery<ParseUser> y = ParseQuery.getUserQuery();
	        	y.whereEqualTo("username", picList.get(arg2));
	        	List<ParseUser> gg = y.find();
				for(ParseObject c:gg){
	        	   // Toast.makeText(getApplicationContext(), "" +c.getObjectId() , Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(searchtry.this, ProfileOtherUser.class);
					intent.putExtra("USER_ID", c.getObjectId());
					startActivity(intent);
				
			}
				}catch(ParseException ee){
					ee.printStackTrace();
				}
			}
        });
        
        
			    

}

	

	
	public class AutoCompleteAdapter extends ArrayAdapter<String> implements Filterable
	{
		private ArrayList<String> items;
		private ArrayList<String> suggestions;
		private ArrayList<String> itemsAll;
		//private ArrayList<String> id;
		private LayoutInflater layoutInflater;
	    private int viewResourceId;
	    
	    public AutoCompleteAdapter(Context context, int viewResourceId, ArrayList<String> items)
	    {
	    	
	    	super(context, viewResourceId, items);
	    	this.viewResourceId = viewResourceId;
	    	this.suggestions = new ArrayList<String>();
	    	this.items = items;
	    	//this.id = id;
	    	this.itemsAll = (ArrayList<String>) items.clone();
	    	
	    	
	    }
	    
	    @
        Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // Inflate the item layout and set the views
            View listItem = convertView;
            final int pos = position;
            if (listItem == null) {
            	layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            	listItem = layoutInflater.inflate(viewResourceId, null);
                //listItem = layoutInflater.inflate(R.layout.searchresult, null);   
            }
            
            //listItem.findViewById(R.id.imageView1);
            TextView profileusername = (TextView) listItem.findViewById(R.id.textView1);
            //final TextView firstlast = (TextView)listItem.findViewById(R.id.textView2);
            
           profileusername.setText(items.get(pos)+"");
            //firstlast.setText(id.get(pos)+"");
             
            
            
            
            return listItem;
        }
	    
	    @Override
	    public Filter getFilter() {
	        return nameFilter;
	    }

	    Filter nameFilter = new Filter() {
	    	
	    	public String convertResultToString(Object resultValue) {
	            String str = (String)resultValue; 
	            return str;
	        }
	    	
	        @Override
	        protected FilterResults performFiltering(CharSequence constraint) {
	            if(constraint != null) {
	                suggestions.clear();
	                for (String new_suggest : itemsAll) {
	                    if(new_suggest.toLowerCase().startsWith(constraint.toString().toLowerCase())){
	                        suggestions.add(new_suggest);
	                    }
	                }
	                FilterResults filterResults = new FilterResults();
	                filterResults.values = suggestions;
	                filterResults.count = suggestions.size();
	                return filterResults;
	            } else {
	                return new FilterResults();
	            }
	        }
	        
	        @SuppressWarnings("unchecked")
			@Override
	        protected void publishResults(CharSequence constraint, FilterResults results) {
	            ArrayList<String> filteredList = (ArrayList<String>) results.values;
	            if(results != null && results.count > 0) {
	                clear();
	                for (String c : filteredList) {
	                    add(c);
	                }
	                notifyDataSetChanged();
	            }
	        }
	    };


		

    }

	
	public static Map sortByValue(Map unsortMap) {	 
		List list = new LinkedList(unsortMap.entrySet());
	 
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return (((Comparable) ((Map.Entry) (o2)).getValue())
							.compareTo(((Map.Entry) (o1)).getValue()));
			}
		});
	 
		Map sortedMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}
	
	@SuppressLint("NewApi")
	public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
	    for (Entry<T, E> entry : map.entrySet()) {
	        if (Objects.equals(value, entry.getValue())) {
	            return entry.getKey();
	        }
	    }
	    return null;
	}
	
	
	// Create an Adapter Class extending the BaseAdapter
    class VersiAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;
        
        public VersiAdapter(searchtry activity) {
            // TODO Auto-generated constructor stub
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        	
//            obList = new ArrayList<String>();
//            try {
//            
//            	ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Product");
//            	ParseObject obj = ParseObject.createWithoutData("shop",shopId);
//            	query.whereEqualTo("shopId", obj);
//            	List<ParseObject>ob = query.find();
//            	for (ParseObject num : ob) {
//            		obList.add(num.getObjectId());
//    			}
//    			} catch (ParseException e1) {
//    				// TODO Auto-generated catch block
//    				e1.printStackTrace();
//    			}
 	
        }
        
        @Override
		public int getCount() {
			// TODO Auto-generated method stub
			return productParseIds.size();
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
            //String Id = getKeyByValue(sortedMap,sim[pos]);
      	    // Toast.makeText(getApplicationContext(),  String.valueOf(sim[pos]), Toast.LENGTH_LONG).show(); 

            cc.getInBackground(productParseIds.get(pos).getObjectId(), new GetCallback<ParseObject>(){

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
    			   Intent in = new Intent(searchtry.this,ProductPage.class);
    			   in.putExtra("productid", productParseIds.get(position).getObjectId());
    			//Here Error !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    			  // in.putExtra("shopId", productParseIds.get(position).getParseObject("shopId").getObjectId());
    			//Toast.makeText(getApplicationContext(),obList.get(position), Toast.LENGTH_SHORT).show();
    				//object.getObjectId().toString();
    			   startActivity(in);
    			   
    			}
    		});

            
            
            
            return listItem; 
            
            
            
        }
        
        

    }
}

