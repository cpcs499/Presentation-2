package com.mzam.starter;

import java.util.List;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EditProduct extends Activity {

	
	
	String name;
	String desc;
	String image;
	String prodId;
	double Price;
	
	final Context context = this;
	
	Button Update;

	//---------------------
	// Color Image Views .....
	ImageView imageColor1;
	ImageView imageColor2;
	ImageView imageColor3;
	ImageView imageColor4;
	ImageView imageColor5;
	
	EditText name1;
	EditText Description;
	EditText price;
	
	
	EditText ColorQnt1;
	EditText ColorQnt2;
	EditText ColorQnt3;
	EditText ColorQnt4;
	EditText ColorQnt5;
	
	// Colors Selected  .....
		int colorSelected1;
		int colorSelected2;
		int colorSelected3;
		int colorSelected4;
		int colorSelected5;
	////////////////////////////////////////////////
		int[] colorsSe = new int [5];
		int[] quanSe = new int [5];
		///////////////////////////////////
		int [] mColor;
 	// Implement listener to get selected color value
	ColorPickerSwatch.OnColorSelectedListener colorcalendarListener1 = new ColorPickerSwatch.OnColorSelectedListener(){

		@Override
		public void onColorSelected(int color) {
	

			colorSelected1=color;	
			imageColor1.setBackgroundColor(colorSelected1);
			}

		
	};

	
	// Implement listener to get selected color value
		ColorPickerSwatch.OnColorSelectedListener colorcalendarListener2 = new ColorPickerSwatch.OnColorSelectedListener(){

			@Override
			public void onColorSelected(int color) {
		

				colorSelected2=color;	
				imageColor2.setBackgroundColor(colorSelected2);
				}

			
		};

		
		// Implement listener to get selected color value
				ColorPickerSwatch.OnColorSelectedListener colorcalendarListener3 = new ColorPickerSwatch.OnColorSelectedListener(){

					@Override
					public void onColorSelected(int color) {
				

						colorSelected3=color;	
						imageColor3.setBackgroundColor(colorSelected3);
						}

					
				};
				
				
				// Implement listener to get selected color value
				ColorPickerSwatch.OnColorSelectedListener colorcalendarListener4 = new ColorPickerSwatch.OnColorSelectedListener(){

					@Override
					public void onColorSelected(int color) {
				

						colorSelected4=color;	
						imageColor4.setBackgroundColor(colorSelected4);
						}

					
				};
				
				
				// Implement listener to get selected color value
				ColorPickerSwatch.OnColorSelectedListener colorcalendarListener5= new ColorPickerSwatch.OnColorSelectedListener(){

					@Override
					public void onColorSelected(int color) {
				

						colorSelected5=color;	
						imageColor5.setBackgroundColor(colorSelected5);
						}

					
				};
				
				
				
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_product);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6adbd9"))); // set your desired color
		
		
		Intent i = getIntent();
		name=i.getStringExtra("name");
        name1 = (EditText)findViewById(R.id.NameSet);
        name1.setText(name);
       
		
		desc=i.getStringExtra("desc");
		Description = (EditText)findViewById(R.id.DescSet);
		Description .setText(desc);
		
		ImageView imgphone = (ImageView) findViewById(R.id.phone);
         
		Price=i.getDoubleExtra("price", 0.0);
		price = (EditText)findViewById(R.id.price);
		price.setText(String.valueOf(Price));
		
		
		
		prodId = i.getStringExtra("prod_id");
		
		// Colorssss 
					imageColor1 =(ImageView)findViewById(R.id.color1);
					LayoutParams params = (LayoutParams) imageColor1.getLayoutParams();
					params.width = 80;
					params.height=80;
					// existing height is ok as is, no need to edit it
					imageColor1.setLayoutParams(params);
					
					imageColor1.setBackgroundColor(i.getIntExtra("color1",0));
					
					imageColor2 =(ImageView)findViewById(R.id.color2);
					params = (LayoutParams) imageColor2.getLayoutParams();
					params.width = 80;
					params.height=80;
					// existing height is ok as is, no need to edit it
					imageColor2.setLayoutParams(params);
					imageColor2.setBackgroundColor(i.getIntExtra("color2",0));
					
					imageColor3 =(ImageView)findViewById(R.id.color3);
					params = (LayoutParams) imageColor3.getLayoutParams();
					params.width = 80;
					params.height=80;
					// existing height is ok as is, no need to edit it
					imageColor3.setLayoutParams(params);
					imageColor3.setBackgroundColor(i.getIntExtra("color3",0));
					
					imageColor4 =(ImageView)findViewById(R.id.color4);
					params = (LayoutParams) imageColor4.getLayoutParams();
					params.width = 80;
					params.height=80;
					// existing height is ok as is, no need to edit it
					imageColor4.setLayoutParams(params);
					imageColor4.setBackgroundColor(i.getIntExtra("color4",0));
					
					imageColor5=(ImageView)findViewById(R.id.color5);
					params = (LayoutParams) imageColor5.getLayoutParams();
					params.width = 80;
					params.height=80;
					// existing height is ok as is, no need to edit it
					imageColor5.setLayoutParams(params);
					imageColor5.setBackgroundColor(i.getIntExtra("color5",0));
					
					
		//=================================================
					
					// Qunatities
					ColorQnt1= (EditText) findViewById(R.id.qnt1);
					ColorQnt2= (EditText) findViewById(R.id.qnt2);
					ColorQnt3= (EditText) findViewById(R.id.qnt3);
					ColorQnt4= (EditText) findViewById(R.id.qnt4);
					ColorQnt5= (EditText) findViewById(R.id.qnt5);
					
					ColorQnt1.setText(String.valueOf(i.getIntExtra("qnt1",0)));
					ColorQnt2.setText(String.valueOf(i.getIntExtra("qnt2",0)));
					ColorQnt3.setText(String.valueOf(i.getIntExtra("qnt3",0)));
					ColorQnt4.setText(String.valueOf(i.getIntExtra("qnt4",0)));
					ColorQnt5.setText(String.valueOf(i.getIntExtra("qnt5",0)));
					
		
					
		////////////////////////////////////////////////////
					// Take New Values //
					
					// Declare Image views for colors .....
					 mColor = Utils.ColorUtils.colorChoice(this);
					imageColor1.setClickable(true);
					imageColor1.setOnClickListener(new OnClickListener() {
			            @Override
			            public void onClick(View v) {
			            	
			            	//////////////////////////////////////////////////
			            	// Original Stock Calendar
			    			ColorPickerDialog colorcalendar = ColorPickerDialog.newInstance(
			    					R.string.color_picker_default_title, mColor,
			    					colorSelected1, 5,
			    					Utils.isTablet(context) ? ColorPickerDialog.SIZE_LARGE
			    							: ColorPickerDialog.SIZE_SMALL);
			    						
			    			colorcalendar.setOnColorSelectedListener(colorcalendarListener1);
			    			colorcalendar.show(getFragmentManager(), "cal");

			            }
			        });
					
					
					
					imageColor2.setClickable(true);
					imageColor2.setOnClickListener(new OnClickListener() {
			            @Override
			            public void onClick(View v) {
			            	
			            	//////////////////////////////////////////////////
			            	// Original Stock Calendar
			    			ColorPickerDialog colorcalendar = ColorPickerDialog.newInstance(
			    					R.string.color_picker_default_title, mColor,
			    					colorSelected2, 5,
			    					Utils.isTablet(context) ? ColorPickerDialog.SIZE_LARGE
			    							: ColorPickerDialog.SIZE_SMALL);
			    						
			    			colorcalendar.setOnColorSelectedListener(colorcalendarListener2);
			    			colorcalendar.show(getFragmentManager(), "cal");

			            }
			        });
					
					
					
					
					imageColor3.setClickable(true);
					imageColor3.setOnClickListener(new OnClickListener() {
			            @Override
			            public void onClick(View v) {
			            	
			            	//////////////////////////////////////////////////
			            	// Original Stock Calendar
			    			ColorPickerDialog colorcalendar = ColorPickerDialog.newInstance(
			    					R.string.color_picker_default_title, mColor,
			    					colorSelected3, 5,
			    					Utils.isTablet(context) ? ColorPickerDialog.SIZE_LARGE
			    							: ColorPickerDialog.SIZE_SMALL);
			    						
			    			colorcalendar.setOnColorSelectedListener(colorcalendarListener3);
			    			colorcalendar.show(getFragmentManager(), "cal");

			            }
			        });
					
					imageColor4.setClickable(true);
					imageColor4.setOnClickListener(new OnClickListener() {
			            @Override
			            public void onClick(View v) {
			            	
			            	//////////////////////////////////////////////////
			            	// Original Stock Calendar
			    			ColorPickerDialog colorcalendar = ColorPickerDialog.newInstance(
			    					R.string.color_picker_default_title, mColor,
			    					colorSelected4, 5,
			    					Utils.isTablet(context) ? ColorPickerDialog.SIZE_LARGE
			    							: ColorPickerDialog.SIZE_SMALL);
			    						
			    			colorcalendar.setOnColorSelectedListener(colorcalendarListener4);
			    			colorcalendar.show(getFragmentManager(), "cal");

			            }
			        });
					
				
					imageColor5.setClickable(true);
					imageColor5.setOnClickListener(new OnClickListener() {
			            @Override
			            public void onClick(View v) {
			            	
			            	//////////////////////////////////////////////////
			            	// Original Stock Calendar
			    			ColorPickerDialog colorcalendar = ColorPickerDialog.newInstance(
			    					R.string.color_picker_default_title, mColor,
			    					colorSelected5, 5,
			    					Utils.isTablet(context) ? ColorPickerDialog.SIZE_LARGE
			    							: ColorPickerDialog.SIZE_SMALL);
			    						
			    			colorcalendar.setOnColorSelectedListener(colorcalendarListener5);
			    			colorcalendar.show(getFragmentManager(), "cal");

			            }
			        });
					
					
					Update = (Button)findViewById(R.id.update);
				    Update.setOnClickListener(new View.OnClickListener() {
			 			@SuppressLint("NewApi")
						public void onClick(View arg0) {
			 				
			 				
			 				DataFill();
		
			 				finish();
					
			 			}});
				    
				    
								
	}

	private void DataFill()
	{
		// Query .................
		
					ParseQuery<ParseObject> query = ParseQuery.getQuery("Product");
					query.getInBackground(prodId, new GetCallback<ParseObject>() {
					  public void done(ParseObject object, ParseException e) {
					    if (e == null) {

						     object.put("ProductName",name1.getText().toString());
						     object.put("product_description",Description.getText().toString());
						     object.put("product_price",Double.parseDouble(price.getText().toString()));

						     
						   
				    	        
						     object.saveInBackground(new SaveCallback() {
						    	    public void done(ParseException e) {
						    	    	
						    			colorsSe[0]=colorSelected1;
						 				colorsSe[1]=colorSelected2;					 			
						 				colorsSe[2]=colorSelected3;
						 				colorsSe[3]=colorSelected4;
						 				colorsSe[4]=colorSelected5;
						 				
						 				
						 				quanSe[0]= Integer.parseInt( ColorQnt1.getText().toString());
						 				quanSe[1]= Integer.parseInt( ColorQnt2.getText().toString());
						 				quanSe[2]= Integer.parseInt( ColorQnt3.getText().toString());
						 				quanSe[3]= Integer.parseInt( ColorQnt4.getText().toString());
						 				quanSe[4]= Integer.parseInt( ColorQnt5.getText().toString());
						    	    	
	ParseQuery<ParseObject> query1 = ParseQuery.getQuery("color");
		ParseObject prod = ParseObject.createWithoutData("Product", prodId);
	query1.whereEqualTo("productId", prod);
						    	    	
query1.findInBackground(new FindCallback<ParseObject>() {
    public void done(List<ParseObject> Objs, ParseException e) {
    	int t=0;
        if (e == null) {
        	for(ParseObject i :Objs)
        	{
        		
        		/*if(colorsSe[t]==i.getInt("color_number")){
        		}
        		else if (colorsSe[t]!=i.getInt("color_number")){*/
        		i.put("color_number", colorsSe[t]);
        	/*	}
        		else if (colorsSe[t]==0)
        		{
        			
        		}*/
        		i.put("unit_Quantity",quanSe[t]);
        		i.saveInBackground(new SaveCallback() {
		    	    public void done(ParseException e) {}});
        		t++;
        	}//for
        } //if
    }
});
						    	    	
						    	    }});//outside query 
					    } else {
					    	
					    	e.getStackTrace();
		  			    }
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
	
}