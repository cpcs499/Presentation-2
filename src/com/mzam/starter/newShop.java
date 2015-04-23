package com.mzam.starter;

import java.io.ByteArrayOutputStream;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class newShop extends Activity {
	/** Called when the activity is first created. */
	
	Button btn;
	ImageView shopPic;
	ParseObject myshop;
	final int PHOTO_SELECTED = 1;
	ParseFile file;
	ParseFile photoFile;
	final Context context = this;
	String picturePath,fileName;
	final int SELECT_PHOTO = 1;
	
	EditText edi ;
	Spinner CatagorySpin;
	
	String CatagorySelected ="";
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.openshop);	
		getActionBar().setDisplayHomeAsUpEnabled(true);
		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6adbd9"))); // set your desired color
		
		CatagorySpin = (Spinner) findViewById(R.id.CatagorySpin);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.CategoryItems, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		CatagorySpin.setAdapter(adapter);
		
		btn = (Button) findViewById(R.id.CreateShop);
		shopPic = (ImageView) findViewById(R.id.imageView1);
		
		 edi = (EditText) findViewById(R.id.DescEdit);
		 final EditText shopna = (EditText) findViewById(R.id.shopname);
			
		 
		 
		shopPic.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
					  
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("image/*");
				startActivityForResult(Intent.createChooser(intent, "Select Picture"),SELECT_PHOTO);
				
			}
		});
		

		btn.setOnClickListener(new View.OnClickListener() {
 			public void onClick(View arg0) {
 						
 				myshop = new ParseObject("shop");
				
 				// Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
 				//shopPic.setImageBitmap(bitmap);
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
					ParseFile prsFile = new ParseFile(shopna.getText().toString()+".jpg", scaledData);
					// Create the ParseFile
				    //ParseFile file = new ParseFile(shopna.getText().toString()+".png", image);
					
					// Upload the image into Parse Cloud
					prsFile.saveInBackground();
					myshop.put("shopImage",prsFile);
					myshop.saveInBackground();
				}
				
				//Must enter the shop name
				if(!(shopna.getText().toString().equals(""))){
					CatagorySelected = String.valueOf(CatagorySpin.getSelectedItem());   
				//Create the Shop
				myshop.put("shop_name", shopna.getText().toString());
				myshop.put("UserOpen",ParseUser.getCurrentUser());
				myshop.put("shop_desc",edi.getText().toString());
				myshop.put("Category", CatagorySelected);
				
				//Add a relation between the Post and Comment
				//myshop.put("parent", myPost);
				myshop.saveInBackground();
				
				Toast.makeText(getApplicationContext(), "Shop Created",
						Toast.LENGTH_SHORT).show();
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Please fill the Shop information", Toast.LENGTH_SHORT).show();
				}
	
 			}
 			
         });
		
		
	} 
	
	@Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	     super.onActivityResult(requestCode, resultCode, data);
	      
	     if (requestCode == PHOTO_SELECTED && resultCode == RESULT_OK && null != data) {
	         Uri selectedImage = data.getData();
	         String[] filePathColumn = { MediaStore.Images.Media.DATA };
	 
	         Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
	         cursor.moveToFirst();
	 
	         int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	         picturePath = cursor.getString(columnIndex);
	         cursor.close();
	         
	        // ImageView imageView = (ImageView) findViewById(R.id.imgView);
	        // final Bitmap bitmap = BitmapFactory.decodeByteArray(picturePath.getBytes(),0,picturePath.getBytes().length);
	         Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
	         //picturePath.getBytes();
	        //if(bitmap.getByteCount()<=5242880)
	         shopPic.setImageBitmap(bitmap); // for pevieeeewwww
	        // else
	        // Toast.makeText(getApplicationContext(), "Big Size, Choose Another Pic", Toast.LENGTH_SHORT).show();
	        //Toast.makeText(getApplicationContext(), picturePath, Toast.LENGTH_SHORT).show();
	        
	        // Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
	        
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
	


//-----------------





	