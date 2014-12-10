package com.Ranjith.testapp;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;



import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CheckOutActivity extends ActionBarActivity {
	
	ImageView home_button,co_button;
	DataHandler handler;
	String[] item_names;
	double[] item_prices;
	int []item_qtys;
	double unit_price;
	LinearLayout Container;
	double total_price;
	int newvalue =0;
	TextView total_price_text;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_out);
		
		
		GlobalClass globe = new GlobalClass();
		item_names = new String[globe.myVal.size()];
		item_qtys = new int[globe.myVal.size()];
		item_prices = new double[globe.myVal.size()];
		Set<String> uniqueSet = new HashSet<String>(globe.myVal);
		int count =0;
		for (String temp : uniqueSet) {
			item_names[count]=temp;
			item_qtys[count] = Collections.frequency(globe.myVal, temp);
			count++;
			//Log.d("values",temp + ": " + Collections.frequency(globe.myVal, temp));
		}
		
		for(int i=0;i<count;i++)
			Log.d("item_name", item_names[i]);
		
		home_button = (ImageView) findViewById(R.id.home_check);
		home_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(CheckOutActivity.this,HomeActivity.class);
				startActivity(intent);
				
			}
		});
		
		Container = (LinearLayout) findViewById(R.id.show_item);
		handler = new DataHandler(getBaseContext());
		handler.open();
		for( int i=0;i<count;i++){
			
			LayoutInflater layoutInflaternotice = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final View addview = layoutInflaternotice.inflate(R.layout.item_check_row, null);
			TextView item_name_check = (TextView) addview.findViewById(R.id.item_check);
			TextView item_price =(TextView)addview.findViewById(R.id.price_check);
			ImageView add_item = (ImageView) addview.findViewById(R.id.add);
			ImageView remove_item = (ImageView) addview.findViewById(R.id.remove);
			
			Log.d("name", item_names[i]);
			
			item_name_check.setText(item_names[i]);
			//retrieve unit price
			
			Cursor crs = handler.returnItembyName(item_names[i]);
			if(crs.moveToFirst()){
				do{
					unit_price = Double.parseDouble(crs.getString(1));
				}while(crs.moveToNext());
			}
			
			//calculate total price
			Log.d("unit price", String.valueOf(unit_price));
			Log.d("qty",String.valueOf( item_qtys[i]));
			final int oldvalue = item_qtys[i];
			final String getname = item_names[i];
			add_item.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					GlobalClass global = new GlobalClass();
					global.myVal.add(getname);
					finish();
					startActivity(getIntent());
					//Toast.makeText(getBaseContext(),getname+" is clicked" , Toast.LENGTH_LONG).show();
					
				}
			});
			remove_item.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					GlobalClass globe = new GlobalClass();
					globe.myVal.remove(getname);
					finish();
					startActivity(getIntent());
				}
			});
			double total_price = (unit_price)*(double)item_qtys[i];
			Log.d("total",String.valueOf(total_price));
			item_price.setText(String.valueOf(total_price)+" Rs");
			item_prices[i] = total_price;
			Container.addView(addview);
		}
		handler.close();
		total_price_text = (TextView) findViewById(R.id.total_check);
		double total = 0;
		for(int i=0;i<count;i++)
			total = total+item_prices[i];
		total_price_text.setText(String.valueOf(total));
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.check_out, menu);
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
