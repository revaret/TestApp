package com.Ranjith.testapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.Ranjith.testapp.ListViewAdapter.ViewHolder;


import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.content.ClipData.Item;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;



public class HomeActivity extends ActionBarActivity {

	EditText search;
	ListView list;
	ListViewAdapter adapter;
	DataHandler handler;
	ImageView home_button,co_button;
	String [] item_names;
	double[] item_prices,item_avail_qtys;
	ArrayList<Items> arrayList = new ArrayList<Items>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		item_names =new String[]{"Onions","Mango","Coconut","Tomato","Brinjal","Carrot","Orange","Apple","Banana","JackFruit"};
		item_prices = new double[]{100,40,70,40,35,30,40,100,30,20};
		item_avail_qtys = new double[]{1,1,1,1,1,1,1,1,1,1};
		


		search = (EditText)findViewById(R.id.search_field);
		home_button = (ImageView) findViewById(R.id.home_button);
		co_button = (ImageView) findViewById(R.id.check_out_button);
		list = (ListView) findViewById(R.id.listview);
		handler = new DataHandler(getBaseContext());
		
		boolean firstrun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("firstrun", true);
        if (firstrun){
        	getSharedPreferences("PREFERENCE", MODE_PRIVATE)
            .edit()
            .putBoolean("firstrun", false)
            .commit();
        	
        	//insert into table
        	
        	long[] id = new long[item_names.length];
        	handler.open();
        	for(int i=0;i<item_names.length;i++)
        		id[i]= handler.insertItem(item_names[i], item_prices[i], item_avail_qtys[i]);
        	handler.close();
        }
		
		for(int i = 0;i<item_names.length;i++){
			Items item = new Items(item_names[i], item_prices[i], item_avail_qtys[i]);
			arrayList.add(item);
		}
		
		adapter = new ListViewAdapter(this, arrayList);
		
		list.setAdapter(adapter);
		
		search.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				String text = search.getText().toString().toLowerCase(Locale.getDefault());
				adapter.filter(text);
				
			}
		});
		
		
		co_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent (HomeActivity.this,CheckOutActivity.class);
				startActivity(intent);
				
			}
		});
		
}
	
	

	
	
	
	
	



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
