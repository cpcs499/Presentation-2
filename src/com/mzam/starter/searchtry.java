package com.mzam.starter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.DataFormatException;
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
import android.text.format.DateFormat;
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
import android.widget.ImageView;
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
    int discount =0;
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
    Context context = this;
    List<ParseObject> recommenda;
    ParseUser user ; 
    String searchtype;
    ArrayList<String> picListshop,picListprod,picListuser;
////////////////////////////////////////////////////////////
    
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trysearch);	
		//getActionBar().setDisplayHomeAsUpEnabled(true);
		//==========================
		
		// develeration .................
		picListshop = new ArrayList<String>();
		picListprod = new ArrayList<String>();
		picListuser = new ArrayList<String>();
		ft = Typeface.createFromAsset(getAssets(),"Fonts/Rosemary.ttf");
		reco = (TextView) findViewById(R.id.recommend);
		reco.setTypeface(ft);
		gridview = (GridView) findViewById(R.id.gridProd);
		radio= (RadioGroup) findViewById(R.id.myRadioGroup);
		productParseIds= new ArrayList<ParseObject>();
		userUser= new ArrayList<ParseObject>();
	   //recommenda = new ArrayList<ParseObject>(0);
			
		user= ParseUser.getCurrentUser();

      gridview.setAdapter(new VersiAdapter(this));

      
  	Date recoAt =  user.getDate("RecommendAt");
	if (recoAt==null){
	       onRecommendation();
	}
	else if (recoAt!=null ){

	
    Date cur = Calendar.getInstance().getTime();
    long t = cur.getTime() - recoAt.getTime();
    /////////////////////////////////////////////////
  
    int days = (int) (t / (1000*60*60*24));  
    int hours = (int) ((t - (1000*60*60*24*days)) / (1000*60*60)); 
    int min = (int) (t - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
   
    /////////////////////////////////////////////////////
    if (days==0 && hours==12){

    	 onRecommendation();
    }
	
	else 
	{

	}
	}
	
       onChooseAddress();	
	 

}// END OF ON-CREATE () .....

	

	
	



	private void onChooseAddress() {

		
	
			radio.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		 @Override
			 public void onCheckedChanged(RadioGroup group, int checkedId) {

			 
			 if(checkedId == R.id.shop) {
				 searchtype ="shop";
				 onSearchShop();
			} else  if(checkedId == R.id.user) {
				searchtype ="user";
				 onSearchUser();
			} else  if(checkedId == R.id.product) {
				searchtype="product";
				 onSearchProduct();
			} 

		 }

	

		});		
		
	}


	
private void onSearchProduct() {


try {
ParseQuery<ParseObject> yy =ParseQuery.getQuery("Product");
List<ParseObject> g = yy.find();
for(ParseObject cc:g){
picListprod.add(cc.getString("ProductName"));
}


} catch (ParseException e1) {
//TODO Auto-generated catch block
e1.printStackTrace();
}




AutoCompleteTextView acTextView = (AutoCompleteTextView)findViewById(R.id.AndroidBooks);
acTextView.setTypeface(ft);
myAdapter = new AutoCompleteAdapter(this,R.layout.searchresult,picListprod);
//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,androidBooks);

acTextView.setThreshold(2);
acTextView.setAdapter(myAdapter);


acTextView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
@Override
public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
long arg3) {
//TODO Auto-generated method stub
try{
ParseQuery<ParseObject> yy =ParseQuery.getQuery("Product");
yy.whereEqualTo("ProductName", picListprod.get(arg2));
ParseObject gg = yy.getFirst();
Intent intent = new Intent(searchtry.this, ProductPage.class);
intent.putExtra("productid", gg.getObjectId());
startActivity(intent);


}catch(ParseException ee){
ee.printStackTrace();
   }
}});
}
	
private void onSearchUser() {
			
///////////////////////////////////////////////////////////////////
try {
ParseQuery<ParseUser> y = ParseQuery.getUserQuery();

List<ParseUser> gg = y.find();
for(ParseObject c:gg){
picListuser.add(c.getString("username"));
}
} catch (ParseException e1) {
//TODO Auto-generated catch block
e1.printStackTrace();
}


//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,androidBooks);
AutoCompleteTextView acTextView = (AutoCompleteTextView)findViewById(R.id.AndroidBooks);
acTextView.setTypeface(ft);
myAdapter = new AutoCompleteAdapter(this,R.layout.searchresult,picListuser);
//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,androidBooks);

acTextView.setThreshold(2);
acTextView.setAdapter(myAdapter);


acTextView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
@Override
public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
long arg3) {
//TODO Auto-generated method stub
try{
ParseQuery<ParseUser> y = ParseQuery.getUserQuery();
y.whereEqualTo("username", picListuser.get(arg2));
List<ParseUser> gg = y.find();
for(ParseObject c:gg){
//Toast.makeText(getApplicationContext(), "" +c.getObjectId() , Toast.LENGTH_SHORT).show();
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





	private void onRecommendation() {

		
	
        	  ParseQuery<ParseObject> cc = ParseQuery.getQuery("RecommendedProduct");
              cc.whereEqualTo("user_id", ParseUser.getCurrentUser());
              
              try {
            	  List<ParseObject> todelete = cc.find();
            	  for (ParseObject  li:todelete)
            	  {
            		 li.deleteInBackground(); 
            	  }
  			} catch (ParseException e1) {
  				// TODO Auto-generated catch block
  				e1.printStackTrace();
  			}
              
        	
		ParseObject  lastorder=null,  lastproduct=null,orderorder;
		List<ParseObject> orderproducts,orderusers,finalproducts;
		orderusers=new ArrayList<ParseObject>();
		finalproducts=new ArrayList<ParseObject>();
		//.............................................
		// [1] Find the last ordered product ........
		ParseQuery<ParseObject> orders= ParseQuery.getQuery("Ordered_Product");
		orders.orderByDescending("createdAt");
		orders.include("product_id");
		orders.include("order_id");
			try {
				lastorder= orders.getFirst();
			    lastproduct= lastorder.getParseObject("product_id");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    // .............................................
		// [2] Find who order this ...... 
	    ParseQuery<ParseObject> products= ParseQuery.getQuery("Ordered_Product");
	    products.whereEqualTo("product_id", lastproduct);
	    products.include("order_id.user_id");
	    products.setLimit(6);
	    
	    
	    try {
	    orderproducts = products.find();	    
	    for (ParseObject orderproduct :orderproducts){
	    	orderorder = orderproduct.getParseObject("order_id");
	    	orderusers.add(orderorder.getParseObject("user_id"));
	    }//end for 
		} catch (ParseException e) {
			e.printStackTrace();
		} 
	    
	    // .............................................
		// [3] find each user what order else ...... 
	    for (ParseObject user : orderusers)
	    {   	    // [3] Find any product they also order it other than that one
		    Toast.makeText(context, "this "+user.getObjectId()+lastorder.getObjectId(), Toast.LENGTH_SHORT).show(); 
		    ParseQuery<ParseObject> quorders1= ParseQuery.getQuery("Order");
		    quorders1.whereEqualTo("user_id", user);
		    ParseQuery<ParseObject> quorders2= ParseQuery.getQuery("Ordered_Product");
		    quorders2.whereMatchesQuery("order_id", quorders1);//.whereMatchesKeyInQuery("order_id", "objectId",quorders1);
		    quorders2.whereNotEqualTo("product_id", lastproduct);
		    quorders2.include("product_id");
		    try {
				finalproducts.add(quorders2.getFirst().getParseObject("product_id"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }// end for 
	    
	    List<Integer> sum = new ArrayList<Integer>();
	    //Find the similarity matrix 
	    for (ParseObject pro : finalproducts)
	    {
	    	for (ParseObject user : orderusers)
		    {   
	    		ParseQuery<ParseObject> quorders1= ParseQuery.getQuery("Order");
	   		    quorders1.whereEqualTo("user_id", user);
	   		    ParseQuery<ParseObject> quorders2= ParseQuery.getQuery("Ordered_Product");
			    quorders2.whereMatchesQuery("order_id", quorders1);
			    quorders2.whereEqualTo("product_id", pro);
			    
			    try {
					if (quorders2.count()>0)
					{
						sum.add(1);
						
					}// end if 
					else 
					{
						sum.add(0);
				
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}// end catch 
	   		    
			  
		    }// end inner for
	    	double sumB=0;
	    	for (int i:sum)
	    	{
	    		sumB+=i;
	    	}
	    	 double similiraity = sumB/((Math.sqrt(sum.size()))*(Math.sqrt(sumB)));
	    	ParseObject recommend = new ParseObject("RecommendedProduct");
	    	recommend.put("user_id", ParseUser.getCurrentUser());
	    	recommend.put("product_id", pro);
	    	recommend.put("similarity",similiraity); 	
	    	recommend.saveInBackground();
	    	 Date cur_ = Calendar.getInstance().getTime();
	    	user.put("RecommendAt",cur_ );
	    	user.saveInBackground();
	    	
	  //  	recommenda.add(pro);
	    	similiraity=0;
	    	sumB=0;
	    	sum.clear();
	    }// end out for 
	    
	    // ...................................................
               
      
		
	    
	    
}

	
	// Create an Adapter Class extending the BaseAdapter
    class VersiAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;
        
        public VersiAdapter(searchtry activity) {
            // TODO Auto-generated constructor stub
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            ParseQuery<ParseObject> cc = ParseQuery.getQuery("RecommendedProduct");
            cc.include("product_id");
            cc.whereEqualTo("user_id", ParseUser.getCurrentUser());
            try {
				recommenda = cc.find();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            
        }
        
        @Override
		public int getCount() {
			// TODO Auto-generated method stub
			return recommenda.size();
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
            final TextView disnumber = (TextView) listItem.findViewById(R.id.disnumber);
	        disnumber.setVisibility(View.GONE);
            final ImageView soldout = (ImageView) listItem.findViewById(R.id.soldout);
            // Initialize the views in the layout
            final ParseImageView iv = (ParseImageView) listItem.findViewById(R.id.phone);
            LayoutParams params = new LayoutParams(360, 360);
            iv.setLayoutParams(params);
				
            if (recommenda.get(pos).getParseObject("product_id").getInt("product_quantity")>0)
			{
			soldout.setVisibility(View.GONE);
			}
            
            discount = recommenda.get(pos).getParseObject("product_id").getInt("product_discount");
            if(recommenda.get(pos).getParseObject("product_id").getInt("product_quantity")>0 &&recommenda.get(pos).getParseObject("product_id").getInt("product_discount")>0)
			{
			//	sale.setVisibility(View.VISIBLE);
				disnumber.setText(" "+String.valueOf(recommenda.get(pos).getParseObject("product_id").getInt("product_discount"))+" % off");
				disnumber.setVisibility(View.VISIBLE);

			}
            
            ParseFile fileObject = (ParseFile) recommenda.get(pos).getParseObject("product_id").get("product_pic");
					if(fileObject!=null){
						iv.setParseFile(fileObject);
						iv.loadInBackground();
					}
					else
					{
							Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
									R.drawable.inspiration_6);
							iv.setImageBitmap(bitmap);
		
					}

            gridview.setOnItemClickListener(new OnItemClickListener() {
    			public void onItemClick(AdapterView<?> parent, View v,int position, long id) {
    			   Toast.makeText(getApplicationContext(),position+"", Toast.LENGTH_SHORT).show();
    			   Intent in = new Intent(searchtry.this,ProductPage.class);
    			   in.putExtra("productid", recommenda.get(position).getParseObject("product_id").getObjectId());
    			   in.putExtra("discount", discount);
    			   startActivity(in);
    			   
    			}
    		});

            return listItem; 
            
            
            
        }
        
        

    }
    
    
private void onSearchShop() {


try {
ParseQuery<ParseObject> yy =ParseQuery.getQuery("shop");
List<ParseObject> g = yy.find();
for(ParseObject cc:g){
picListshop.add(cc.getString("shop_name"));
}


} catch (ParseException e1) {
//TODO Auto-generated catch block
e1.printStackTrace();
}




AutoCompleteTextView acTextView = (AutoCompleteTextView)findViewById(R.id.AndroidBooks);
acTextView.setTypeface(ft);
myAdapter = new AutoCompleteAdapter(this,R.layout.searchresult,picListshop);
//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,androidBooks);

acTextView.setThreshold(2);
acTextView.setAdapter(myAdapter);


acTextView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
@Override
public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
long arg3) {
//TODO Auto-generated method stub
try{
ParseQuery<ParseObject> yy =ParseQuery.getQuery("shop");
yy.whereEqualTo("shop_name", picListshop.get(arg2));
ParseObject gg = yy.getFirst();
Intent intent = new Intent(searchtry.this, ShopSingleItemView.class);
intent.putExtra("SHOP_ID", gg.getObjectId());
startActivity(intent);


}catch(ParseException ee){
ee.printStackTrace();
   }
}});
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
            
            TextView profileusername = (TextView) listItem.findViewById(R.id.textView1);
            
           profileusername.setText(items.get(pos)+"");
            
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

}

