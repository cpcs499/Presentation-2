package com.mzam.starter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class ShopsAm extends Activity {
	/** Called when the activity is first created. */
	
	Button btn;
	ListView listView ;
	final Context context = this;
	ParseUser fl = ParseUser.getCurrentUser();
	List<String> obList,obList2 ;
	List<ParseFile> obList3;
	List<ParseObject> ob;
	TextView allShoporder;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop_tab);	
		
		btn = (Button) findViewById(R.id.textPost);
		allShoporder = (TextView)findViewById(R.id.textView2);
		
		listView = (ListView) findViewById(R.id.listView1);
	
	}
	
	public void onResume(){
		super.onResume();
		{
			listView.setAdapter(new ShopCustomAdapter(this));
				
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(ShopsAm.this, newShop.class);
				startActivity(intent);
				
				//Toast.makeText(getApplicationContext(),"hii", Toast.LENGTH_LONG).show();
				   
									}
								});
		
		allShoporder.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent in = new Intent(ShopsAm.this, AllShopOrderTabsFragment.class);
				startActivity(in);
				
			}
		});
		
		}
	}

}
