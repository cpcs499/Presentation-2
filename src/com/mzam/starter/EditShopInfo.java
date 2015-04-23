package com.mzam.starter;

import java.io.ByteArrayOutputStream;
import java.util.List;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;

public class EditShopInfo extends Activity {

	public static final int SELECT_PHOTO = 1;
	ParseImageView shopPic;
	Button EditInfoBtn;
	EditText shopName,shopDesc;
	Spinner CatagorySpin;
	String shopId;
	String picturePath;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_shop);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6adbd9"))); // set your desired color
		
		// Views Decleration 
					EditInfoBtn = (Button) findViewById(R.id.UpdateButton);
					shopPic = (ParseImageView) findViewById(R.id.shopEditImage);
					shopName = (EditText) findViewById(R.id.nameShopEdit);
					shopDesc =(EditText) findViewById(R.id.DescEditShop);
					CatagorySpin = (Spinner) findViewById(R.id.CatagorySpin);
					
					LayoutParams params = new LayoutParams(300, 300);
					shopPic.setLayoutParams(params);
		            
					Intent i = getIntent();
					shopId= i.getStringExtra("SHOP_ID");
					
					shopPic.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
							intent.setType("image/*");
							startActivityForResult(Intent.createChooser(intent, "Select Picture"),SELECT_PHOTO);
						}
					});
					
					
					ParseQuery<ParseObject> query = ParseQuery.getQuery("shop");  
					query.getInBackground(shopId, new GetCallback<ParseObject>() {
					  public void done(final ParseObject object, ParseException e) {
					    if (e == null) {
					    	int indexSpinner = 0;
					    	// Set the values that coming from the database .....
					    	shopName.setText(object.getString("shop_name"));
					    	shopDesc.setText(object.getString("shop_desc"));
					    	String CatagorySelected= object.getString("Category");
					    	
					    	for(int i=0;i<CatagorySpin.getCount();i++)
							{
					    	CatagorySpin.getItemAtPosition(i).equals(CatagorySelected);
					    	indexSpinner=i;
							}
					    	CatagorySpin.setSelection(indexSpinner);
					    	
					    	ParseFile imageFile = object.getParseFile("shopImage");
							if (imageFile != null) {
								shopPic.setParseFile(imageFile);
								shopPic.loadInBackground();
							}
							else
							{
									Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.default_shoppic);
									shopPic.setImageBitmap(bitmap); // for pevieeeewwww		
							}
					        
					    }
					    EditInfoBtn.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View arg0) {
						    	object.put("shop_name",shopName.getText().toString());
						    	object.put("shop_desc",shopDesc.getText().toString());
						    	object.put("Category",String.valueOf(CatagorySpin.getSelectedItem()));
						    	
						    	Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
						    	if(bitmap!=null){
						    	 Bitmap prsImgScaled = Bitmap.createScaledBitmap(bitmap, 110, 110* bitmap.getHeight() / bitmap.getWidth(), false);
							     Matrix matrix = new Matrix();
							     Bitmap prsImgScaledRotated = Bitmap.createBitmap(prsImgScaled, 0,0, prsImgScaled.getWidth(), prsImgScaled.getHeight(),matrix, true);
						         ByteArrayOutputStream bos = new ByteArrayOutputStream();
								 prsImgScaledRotated.compress(Bitmap.CompressFormat.JPEG, 100, bos);
								 byte[] scaledData = bos.toByteArray();
								 ParseFile prsFile = new ParseFile("ShopPic.jpg", scaledData);
								 object.put("shopImage", prsFile);
								 object.saveInBackground();
						    	}
						    	object.saveInBackground(new SaveCallback() {
							  public void done(ParseException e) {
							    if (e == null) {
							      // Now let's update it with some new data. In this case, only cheatMode and score
							      // will get sent to the Parse Cloud. playerName hasn't changed.
							    	//object.put("Phonenumber",phone.getText().toString());
							    	Toast.makeText(getApplicationContext(),
							    			"Succesfully Updated!" , Toast.LENGTH_LONG)
						                     .show();
							    	
							        finish();
							        Intent intent = new Intent(EditShopInfo.this, ShopSingleItemView.class); 
							        intent.putExtra("SHOP_ID",shopId);
							    	startActivity(intent);
					
							    }
							    }
							  });
							}});
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
	         shopPic.setImageBitmap(bitmap); // for pevieeeewwww


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
