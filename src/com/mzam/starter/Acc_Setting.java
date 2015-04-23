package com.mzam.starter;

import java.util.regex.Pattern;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Acc_Setting extends Activity {
	/** Called when the activity is first created. */
	
	ListView listView ;
	final Context context = this;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acc_setting);	
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6adbd9"))); // set your desired color
		createCutomActionBarTitle();
		final Typeface tf = Typeface.createFromAsset(getAssets(),"Fonts/Rosemary.ttf");
		listView = (ListView) findViewById(R.id.listView1);
		
		// Defined Array values to show in ListView
        String[] values = new String[] { "My Order History", "Favorite List","Edit Profile", "Change Pass","Private Account","Follow Request","Logout" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, values);
       
        // Assign adapter to ListView
        listView.setAdapter(adapter);
      
     // ListView Item Click Listener
        
        Switch sw = (Switch) findViewById(R.id.switch1);
        
        sw.setOnCheckedChangeListener(new OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
            		ParseQuery<ParseUser> privacyStatues = ParseQuery.getUserQuery();
            		privacyStatues.getInBackground(ParseUser.getCurrentUser().getObjectId(),new GetCallback<ParseUser>(){
						@Override
						public void done(ParseUser object, ParseException e) {
							// TODO Auto-generated method stub
							if(isChecked){
								object.put("profile_privacy", "Private");
								object.saveInBackground();
								Toast.makeText(getApplicationContext(),object.getObjectId()+"On", Toast.LENGTH_SHORT).show();}
							else{
								object.put("profile_privacy", "Public");
								object.saveInBackground();
								Toast.makeText(getApplicationContext(),object.getObjectId()+"Off", Toast.LENGTH_SHORT).show();
							}
						}
            			
            		});
			        
            	}
			
        });
        
        listView.setOnItemClickListener(new OnItemClickListener() {
            
              @SuppressLint("NewApi")
			@Override
              public void onItemClick(AdapterView<?> parent, View view,
                 int position, long id) {
               int itemPosition = position;
               
               listView.getChildAt(4).setPressed(false);
              // String itemVa = (String) listView.getItemAtPosition(position);
               
               if(itemPosition == 0){
            	   Intent in = new Intent (Acc_Setting.this , User_All_Orders.class);
				startActivity(in);
               }
               
               if(itemPosition == 2){
            	   Intent in = new Intent (Acc_Setting.this , EditProfile.class);
				startActivity(in);
               }
               
               
               else if(itemPosition == 3){
            	   
            	final String PASSWORD_PATTERN = "((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[?=.*[@#$%]]*.{6,20})";
   				
            	final AlertDialog.Builder Builder = new AlertDialog.Builder(context);
            	LayoutInflater li = LayoutInflater.from(context);
   				final View passReset = li.inflate(R.layout.change_pass, null);
   				Builder.setView(passReset);
   				
   				//final Dialog myDialog = new Dialog(context);
   			  Button submit = (Button)passReset.findViewById(R.id.textPost);
   			  submit.setTypeface(tf);
              submit.setOnClickListener(new View.OnClickListener()
              {
                  @Override
                  public void onClick(View v) {
                	  
                	    final EditText newestPass = (EditText)passReset.findViewById(R.id.newPass);
         				final String newestPassString = newestPass.getText().toString();
         				final EditText confirmPass = (EditText)passReset.findViewById(R.id.confirmNewPass);
         				newestPass.setTypeface(tf);
         				confirmPass.setTypeface(tf);
   										if(Pattern.compile(PASSWORD_PATTERN).matcher(newestPassString).matches() == false ||
   						   						newestPassString.length()<6 || newestPassString.length()>20 ||
   						   						newestPassString.contains("*"))
   						   				{
   						   					if (newestPassString.equals("")) { newestPass.setError("Empty");}
   						   					else if (newestPassString.length()<6 || newestPassString.length()>20) { newestPass.setError("The Password length is between 6 and 20");}
   						   					else if (newestPassString.contains("*")){newestPass.setError("Password must doesn't Contain “*” ");}
   						   					else if (Pattern.compile(PASSWORD_PATTERN).matcher(newestPassString).matches() == false)
   						   					{newestPass.setError("The password Must Contain numbers ,Uppercase and Lowercase letters");}
   						   					
   						   				}
   										
   										else if (!(newestPassString.equals(confirmPass.getText().toString()))){
   											//Toast.makeText(getApplicationContext(),"Password Doesn't Match" , Toast.LENGTH_LONG).show();
   											new AlertDialog.Builder(context)
   									    .setTitle("ERROR")
   									    .setMessage("Password Doesn't Match")
   									    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
   									        public void onClick(DialogInterface dialog, int which) { 
   									            // continue with delete
   									        }
   									     })
   									    .setIcon(android.R.drawable.ic_dialog_alert)
   									     .show();
   										}
   										
   										else if(newestPass.getText().toString().equals(confirmPass.getText().toString())){
   											ParseUser curr = ParseUser.getCurrentUser();
   											curr.setPassword(newestPass.getText().toString());
   											curr.saveInBackground();
   											Toast.makeText(getApplicationContext(),"Password Changed" , Toast.LENGTH_LONG).show();
   											//myDialog.cancel();
   										}
   									}
                  
   								});

   				// create
   				Builder.show();
               }
               
               else if(itemPosition == 5){
            	   Intent in = new Intent(Acc_Setting.this, FollowRequest.class);
            	  
            	   startActivity(in);
               }
               
               else if(itemPosition == 6){
            	   ParseUser.logOut();
            	   finish();
            	   Intent in = new Intent(Acc_Setting.this , ParseStarterProjectActivity.class);
            	   startActivity(in);
            	   
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
	
	private void createCutomActionBarTitle(){
        this.getActionBar().setDisplayShowCustomEnabled(true);
        this.getActionBar().setDisplayShowTitleEnabled(false);

        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.custom_action_bar, null);
        Typeface tf = Typeface.createFromAsset(getAssets(),"Fonts/Rosemary.ttf");
        ((TextView)v.findViewById(R.id.titleFragment1)).setTypeface(tf);
        ((TextView)v.findViewById(R.id.titleFragment2)).setTypeface(tf);
        //assign the view to the actionbar
        this.getActionBar().setCustomView(v);
    }
	
}
