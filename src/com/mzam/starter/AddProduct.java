package com.mzam.starter;


import java.io.ByteArrayOutputStream;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class AddProduct extends Activity {

	// Variables deceleration ... 
	// button .....
	Button upload;
	// parse ..... 
	ParseObject product;
	ParseUser user;
	// photo..... 
	ImageView productPic;
	final int PHOTO_SELECTED = 1;
	ParseFile file;
	// context ....
	ParseFile photoFile;
	final Context context = this;
	String prodId ;
   
	
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
	
	EditText ColorQnt1;
	EditText ColorQnt2;
	EditText ColorQnt3;
	EditText ColorQnt4;
	EditText ColorQnt5;
	//-----------------------
	//////////////////////////////
	int [] mColor;
	/////////////////////////////
	
	
	
	//String ColorSelected;
	// Radio Button ..... 
	 /*MultiSelectionSpinner ColorSpin;*/ 
	RadioGroup groupRadio;
/*	RadioButton priceButton ;
	RadioButton colorButton ;*/
	
	
	//////////////////////////////
	// Edit Texts......
	EditText price ;
	EditText name ;
	//EditText TotalQuanitiy ;
	EditText desc;
	/////////////////////////////
	
	////////////////////////////
	// Text Views ...
	TextView TotalQuantity;
	int totalQnt;
	///////////////////////////
	
	///////////////////////////
	// stored values & constants ....
	String nameProd ="";
	String descProd ;
	String shopId;
	int TotalQuantityProd; 
	double priceProd;
	
	////////////////////////////
	// Image View for Product Picture 
	private static final int SELECT_PHOTO = 1;
	String picturePath,fileName;
	Bitmap bitmap ;
	////////////////////////////
	
	
	
	
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
				
				
				
	
	
	//@SuppressLint("CutPasteId")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_product);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6adbd9"))); // set your desired color
		
		///////////////////////////////////////////////////
		//[1]  Views setting .....
		upload = (Button) findViewById(R.id.upload);
		price = (EditText) findViewById(R.id.priceEdit);
		name = (EditText) findViewById(R.id.nameEdit);
		desc =(EditText) findViewById(R.id.descEdit);
		//TotalQuanitiy= (EditText) findViewById(R.id.QunatityEdit);
		TotalQuantity =(TextView) findViewById(R.id.textView4);
		
		// Qunatities
		ColorQnt1= (EditText) findViewById(R.id.qnt1);
		ColorQnt2= (EditText) findViewById(R.id.qnt2);
		ColorQnt3= (EditText) findViewById(R.id.qnt3);
		ColorQnt4= (EditText) findViewById(R.id.qnt4);
		ColorQnt5= (EditText) findViewById(R.id.qnt5);
		

				
		//////////////////////////////////////////////////
		
		////////////////////////////////////////////////////
		//[2] Variables inputs .....
     	/*	DecimalFormat format = new DecimalFormat(".##");
		name.setText(format.format(name.getText().toString()));*/
        //nameProd = name.getText().toString();
        //descProd = desc.getText().toString();
        ///////////////////////////////////////////////////
        
        
		//////////////////////////////////////////////////
		// Parse Decleration 
		user = ParseUser.getCurrentUser();
		//////////////////////////////////////////////////
		
		
		
		//////////////////////////////////////////////////
		// Product Image View 
		productPic = (ImageView) findViewById(R.id.imageProduct);
		productPic.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("image/*");
				startActivityForResult(Intent.createChooser(intent, "Select Picture"),SELECT_PHOTO);
				
			}
		});

		
		
		/////////////////////////////////////////////////
		
		
		
		
		////////////////////////////////////////////////////
		// Color Image Views 
		// Declare Image views for colors .....
		 mColor = Utils.ColorUtils.colorChoice(this);
		 
		 
		imageColor1 = (ImageView) findViewById(R.id.color1);
		
		LayoutParams params = (LayoutParams) imageColor1.getLayoutParams();
		params.width = 80;
		params.height=80;
		// existing height is ok as is, no need to edit it
		imageColor1.setLayoutParams(params);
		imageColor1.setBackgroundColor(0xffffffff);
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
		
		
		imageColor2 = (ImageView) findViewById(R.id.color2);
		params = (LayoutParams) imageColor2.getLayoutParams();
		params.width = 80;
		params.height=80;
		imageColor2.setLayoutParams(params);
		imageColor2.setBackgroundColor(0xffffffff);
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
		
		
		imageColor3 = (ImageView) findViewById(R.id.color3);
		params = (LayoutParams) imageColor3.getLayoutParams();
		params.width = 80;
		params.height=80;
		imageColor3.setLayoutParams(params);
		imageColor3.setBackgroundColor(0xffffffff);
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
		
		
		imageColor4 = (ImageView) findViewById(R.id.color4);
		params = (LayoutParams) imageColor4.getLayoutParams();
		params.width = 80;
		params.height=80;
		imageColor4.setLayoutParams(params);
		imageColor4.setBackgroundColor(0xffffffff);
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
		
		
		imageColor5 = (ImageView) findViewById(R.id.color5);
		params = (LayoutParams) imageColor2.getLayoutParams();
		params.width = 80;
		params.height=80;
		imageColor5.setLayoutParams(params);
		imageColor5.setBackgroundColor(0xffffffff);
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
		
		//////////////////////////////////////////////////
		
		 
		 
        //////////////////////////////////////////////////
		//[3] Upload Button 	
        upload.setOnClickListener(new View.OnClickListener() {
 			@SuppressLint("NewApi")
			public void onClick(View arg0) {
 				
 				/////////////////////////////////////
 				// CHECK Validity of Data 
 				
 				if (Double.parseDouble(price.getText().toString()) <0.0)
 						{
 					price.setError("Enter valid Price more than 0");
 						}

		       ///////////////////////////////////////
 				
 				Intent i = getIntent();
 				shopId= i.getStringExtra("shop_id");
 				
 				
    DataFill();
    finish();

 			}

		
			
		
        });
      // END of Upload Button ....
    //////////////////////////////////////////////////////////////////
        
	}

	

	
	////////////////////////////////////////////////////////////
	
	private void DataFill() {
		 
		 product = new ParseObject("Product");
	     product.put("ProductName",name.getText().toString());
	     product.put("product_description",desc.getText().toString());
	     product.put("product_price",Double.parseDouble(price.getText().toString()));
	     product.put("shopId",ParseObject.createWithoutData("shop",shopId));
         //ParseObject objectId= ParseObject.createWithoutData("Product",product.getObjectId());
	   
	    	Bitmap bitmap = BitmapFactory.decodeFile(picturePath);//////////
			if(bitmap!= null){
			Bitmap prsImgScaled = Bitmap.createScaledBitmap(bitmap, 110, 110* bitmap.getHeight() / bitmap.getWidth(), false);
	        Matrix matrix = new Matrix();
	        Bitmap prsImgScaledRotated = Bitmap.createBitmap(prsImgScaled, 0,
						0, prsImgScaled.getWidth(), prsImgScaled.getHeight(),
						matrix, true);
	        ByteArrayOutputStream bos = new ByteArrayOutputStream();//
	        prsImgScaledRotated.compress(Bitmap.CompressFormat.JPEG, 100, bos);
				byte[] scaledData = bos.toByteArray();
				ParseFile prsFile = new ParseFile(name.getText().toString()+".jpg", scaledData);
				// Create the ParseFile
			    //ParseFile file = new ParseFile(shopna.getText().toString()+".png", image);
				
				// Upload the image into Parse Cloud
				prsFile.saveInBackground();
				product.put("product_pic",prsFile);
				product.saveInBackground();
			}

		   product.saveInBackground(new SaveCallback() {
	    	    public void done(ParseException e) {
	    	        // Now you can do whatever you want with the object ID, like save it in a variable
	    	    	prodId = product.getObjectId();
	    	        Toast.makeText(getApplicationContext(),"fff "+prodId,
	    	      			Toast.LENGTH_LONG).show();
	    	        
	    	        
	    	        
	    	    ///////////////////////// Color 1 
	    	  	  color1 = new ParseObject("color");
 			      color1.put("color_number", colorSelected1);
 			      
 			      if (ColorQnt1.getText().toString()!=null )
 			      {colorQuantity1 =Integer.parseInt( ColorQnt1.getText().toString());  
 			      }else{colorQuantity1=0;}
 			      
 				  color1.put("unit_Quantity",colorQuantity1);
 		          color1.put("productId",ParseObject.createWithoutData("Product",prodId));
 		        
 				  color1.saveInBackground(new SaveCallback() {
 			    	    public void done(ParseException e) {
 			    	   
 			    	    }});
 				  
 				  
 				 /////////////////////////////////////////////
 				  
 				  
 				   ///////////////////////// Color 2
	    	  	  color2 = new ParseObject("color");
 			      color2.put("color_number", colorSelected2);
 			      
 			      if (ColorQnt2.getText().toString()!=null )
 			      {colorQuantity2 =Integer.parseInt( ColorQnt2.getText().toString());  
 			      }else{colorQuantity2=0;}
 			      
 				  color2.put("unit_Quantity",colorQuantity2);
 		          color2.put("productId",ParseObject.createWithoutData("Product",prodId));
 		        
 				  color2.saveInBackground(new SaveCallback() {
 			    	    public void done(ParseException e) {
 			    	   
 			    	    }});
 				  
 				  /////////////////////////////////////
 				  
                 ///////////////////////// Color 3
  	  	  color3 = new ParseObject("color");
		      color3.put("color_number", colorSelected3);
		      
		      if (ColorQnt3.getText().toString()!=null )
		      {colorQuantity3 =Integer.parseInt( ColorQnt3.getText().toString());  
		      }else{colorQuantity3=0;}
		      
			  color3.put("unit_Quantity",colorQuantity3);
	          color3.put("productId",ParseObject.createWithoutData("Product",prodId));
	        
			  color3.saveInBackground(new SaveCallback() {
		    	    public void done(ParseException e) {
		    	   
		    	    }});
			  
			  /////////////////////////////////////
			  
			  
			  ///////////////////////// Color 4
	  	  	  color4 = new ParseObject("color");
			      color4.put("color_number", colorSelected4);
			      
			      if (ColorQnt4.getText().toString()!=null )
			      {colorQuantity4 =Integer.parseInt( ColorQnt4.getText().toString());  
			      }else{colorQuantity4=0;}
			      
				  color4.put("unit_Quantity",colorQuantity4);
		          color4.put("productId",ParseObject.createWithoutData("Product",prodId));
		        
				  color4.saveInBackground(new SaveCallback() {
			    	    public void done(ParseException e) {
			    	   
			    	    }});
				  
				  /////////////////////////////////////
				  
				  
				  ///////////////////////// Color 5
		  	  	  color5 = new ParseObject("color");
				      color5.put("color_number", colorSelected5);
				      
				      if (ColorQnt5.getText().toString()!=null )
				      {colorQuantity5 =Integer.parseInt( ColorQnt5.getText().toString());  
				      }else{colorQuantity5=0;}
				      
					  color5.put("unit_Quantity",colorQuantity5);
			          color5.put("productId",ParseObject.createWithoutData("Product",prodId));
			        
					  color5.saveInBackground(new SaveCallback() {
				    	    public void done(ParseException e) {
				    	   
				    	    }});
					  
			      /////////////////////////////////////
					  
					  
					  totalQnt = (Integer.parseInt( ColorQnt1.getText().toString()))
				  				 +(Integer.parseInt( ColorQnt2.getText().toString()))
				  				+(Integer.parseInt( ColorQnt3.getText().toString()))
				  				+(Integer.parseInt( ColorQnt4.getText().toString()))
				  				+(Integer.parseInt( ColorQnt5.getText().toString()));
				  		 TotalQuantity.setText(String.valueOf(totalQnt));
				  
				  product.put("product_quantity",totalQnt );
					
		  	  	 // product.saveInBackground();
               ////////////////// END of Save Colors 
	    	    }
	    	    
	    	    
	    	    
		
	    	});		
	}
	
	
	
	@Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	     super.onActivityResult(requestCode, resultCode, data);
	      
	     if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && null != data) {
	         Uri selectedImage = data.getData();
	         String[] filePathColumn = { MediaStore.Images.Media.DATA };
	 
	         Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
	         cursor.moveToFirst();
	 
	         int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	         picturePath = cursor.getString(columnIndex);
	         cursor.close();
	         Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
	         productPic.setImageBitmap(bitmap); // for pevieeeewwww

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

}