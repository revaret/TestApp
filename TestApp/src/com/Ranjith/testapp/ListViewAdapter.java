package com.Ranjith.testapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ListViewAdapter extends BaseAdapter {
	
	Context mContext;
	LayoutInflater inflater;
	private List<Items> itemsList=null;
	private ArrayList<Items> arrayList;
	
	

	public ListViewAdapter(Context context,List<Items> itemsList ){
		mContext = context;
		this.itemsList = itemsList;
		inflater = LayoutInflater.from(mContext);
		this.arrayList = new ArrayList<Items>();
		this.arrayList.addAll(itemsList);
	}
	
	public class ViewHolder{
		TextView name;
		TextView price;
		TextView qty_in_stock;
		ImageView add_to_cart;
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return itemsList.size();
	}

	@Override
	public Items getItem(int position) {
		// TODO Auto-generated method stub
		return itemsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		if(view==null)
		{
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.item_row, null);
			//locate the textview in  item_row
			
			holder.name = (TextView) view.findViewById(R.id.item_name);
			holder.price = (TextView) view.findViewById(R.id.item_price);
			holder.qty_in_stock = (TextView) view.findViewById(R.id.avail_qty);
			holder.add_to_cart = (ImageView) view.findViewById(R.id.add_to_cart);
			view.setTag(holder);
			
		}
		else
		{
			holder = (ViewHolder) view.getTag();
		}
		holder.name.setText(itemsList.get(position).getName());
		holder.price.setText(String.valueOf(itemsList.get(position).getPrice())+" Rs");
		holder.qty_in_stock.setText(String.valueOf(itemsList.get(position).getQty_in_stock())+" kg");
		holder.add_to_cart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast toast=Toast.makeText(mContext, itemsList.get(position).getName()+" is clicked", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.TOP|Gravity.RIGHT, 0,80);
				toast.show();
				GlobalClass globe = new GlobalClass();
				globe.myVal.add(itemsList.get(position).getName());
				
			}
		});
		
		return view;
	}
	
	
	//Filter class
	public void filter(String chartext){
		chartext = chartext.toLowerCase(Locale.getDefault());
		itemsList.clear();
		if(chartext.length()==0){
			itemsList.addAll(arrayList);
		}
		else{
			for(Items item:arrayList){
				if(item.getName().toLowerCase(Locale.getDefault()).contains(chartext)){
					itemsList.add(item);
				}
			}
		}
		notifyDataSetChanged();
	}

}
