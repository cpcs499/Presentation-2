package com.mzam.starter;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class AddProductComment extends Activity {
	Context context = this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addcomment);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6adbd9"))); // set your desired color
		
		final Typeface ft = Typeface.createFromAsset(getAssets(),"Fonts/Rosemary.ttf");
		//final EditText userInput = (EditText)textPPost.findViewById(R.id.editText1);
		final EditText postdetail = (EditText) findViewById(R.id.editText1);
		//final String post = postdetail.getText().toString();
		final ParseImageView profilepic = (ParseImageView) findViewById(R.id.imageView1);
		
		ParseUser curr = ParseUser.getCurrentUser();
		TextView firstlastname = (TextView) findViewById(R.id.textView1);
		firstlastname.setText(curr.getString("firstName")+" "+curr.getString("LastName"));
		firstlastname.setAllCaps(true);
		firstlastname.setTypeface(ft);
		TextView username = (TextView) findViewById(R.id.textView2);
		username.setText("@"+curr.getUsername());
		username.setTypeface(ft);
		
		ParseFile imageFile = curr.getParseFile("ProfilePic");
		if (imageFile != null) {
			profilepic.setParseFile(imageFile);
			profilepic.loadInBackground();
		}
		
		else
		{
				Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
						R.drawable.inspiration_6);
				profilepic.setImageBitmap(bitmap); // for pevieeeewwww
		}
		
		 final TextView LetterCounter = (TextView)findViewById(R.id.textView3);
		 LetterCounter.setTypeface(ft);
		postdetail.addTextChangedListener(new TextWatcher() {
			 
			   public void afterTextChanged(Editable s) {
				// LetterCounter.setText(post.length());
					  int c = 200 - s.length();  // counter
					  String counter = String.valueOf(c);
					  LetterCounter.setText(counter);
					  LetterCounter.setTextColor(Color.RED);
			          
			   }
			 
			   public void beforeTextChanged(CharSequence s, int start, 
			     int count, int after) {
			   }
			 
			   public void onTextChanged(CharSequence s, int start, 
			     int before, int count) {
			   //TextView myOutputBox = (TextView) findViewById(R.id.myOutputBox);
			   //myOutputBox.setText(s);
			
			   }
		});
		
		final String value = getIntent().getExtras().getString("PRODUCT_ID");
		Button add = (Button) findViewById(R.id.button1);
		add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ParseObject MyComment = new ParseObject("Product_User_Comment");
				//Create the Shop
				MyComment.put("comment_text", postdetail.getText().toString());
				MyComment.put("user_id",ParseUser.getCurrentUser());
				MyComment.put("product_id",value);
				MyComment.saveInBackground(); 
				finish();
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
