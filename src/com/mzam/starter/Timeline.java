package com.mzam.starter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Timeline extends Activity {

			
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timeline);	
		// TODO Auto-generated method stub

		
	
		
	}	
	
	public void onResume(){
		super.onResume();{
			ListView nn = (ListView) findViewById(R.id.listView1);
			nn.setAdapter(new TimlineAdapter(this));
			/*
			ParseQuery<ParseObject> innerQuery = ParseQuery.getQuery("Follow");
			innerQuery.whereEqualTo("from", ParseUser.getCurrentUser());

			ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
			userQuery.whereMatchesKeyInQuery("objectId","tostring", innerQuery);
			
			userQuery.findInBackground(new FindCallback<ParseUser>() {
			@Override
			public void done(List<ParseUser> objects, ParseException e) {
				// TODO Auto-generated method stub
				
				for (ParseObject i : objects) {
					
				Toast.makeText(getApplicationContext(), i.getObjectId()+"", Toast.LENGTH_SHORT).show();
				}
			}
			});
			*/
			
		}
	}
}