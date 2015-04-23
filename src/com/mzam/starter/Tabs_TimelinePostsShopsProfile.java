package com.mzam.starter;

import android.app.ActionBar;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

public class Tabs_TimelinePostsShopsProfile extends TabActivity {
	/** Called when the activity is first created. */
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_main);
		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6adbd9"))); // set your desired color
		createCutomActionBarTitle();
		setTabs();
	}
	
	private void setTabs()
	{
		addTab("Home", R.drawable.tab_home, Timeline.class);
		addTab("Search", R.drawable.tab_home, searchtry.class);
		addTab("Post", R.drawable.tab_post, Posts.class);
		
		addTab("My Shops", R.drawable.tab_shop, ShopsAm.class);
		addTab("Me", R.drawable.tab_profile, UserProfile_ME.class);
		
	}
	
	private void addTab(String labelId, int drawableId, Class<?> c)
	{
		Typeface font = Typeface.createFromAsset(getAssets(),"Fonts/Rosemary.ttf");
		
		TabHost tabHost = getTabHost();
		
		tabHost.setBackgroundResource(R.drawable.tb2); //background
		Intent intent = new Intent(this, c);
		TabHost.TabSpec spec = tabHost.newTabSpec("tab" + labelId);	
		
		View tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
		TextView title = (TextView) tabIndicator.findViewById(R.id.title);
		title.setText(labelId);
		title.setTextColor(Color.parseColor("#6adbd9"));
		title.setTypeface(font);
		title.setTextSize(16);
		ImageView icon = (ImageView) tabIndicator.findViewById(R.id.icon);
		icon.setImageResource(drawableId);
		
		spec.setIndicator(tabIndicator);
		spec.setContent(intent);
		tabHost.addTab(spec);
		for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
		{
			tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
		}
		
		
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