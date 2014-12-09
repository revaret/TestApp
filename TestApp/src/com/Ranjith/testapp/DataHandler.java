package com.Ranjith.testapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



public class DataHandler {

	public static final String DATA_BASE_NAME ="TestApp.db";
	public static final int DATA_BASE_VERSION = 2;
	//Item Table
	public static final String ITEM_ID = "id";
	public static final String ITEM_NAME="item_name";
	public static final String ITEM_PRICE="item_price";
	public static final String ITEM_QUANTITY_AVAIL = "qty_avail";
	public static final String ITEM_TABLE_NAME ="item_table";
	public static final String ITEM_TABLE_CREATE = "CREATE TABLE item_table(id INTEGER PRIMARY KEY AUTOINCREMENT,item_name TEXT NOT NULL,item_price REAL NOT NULL,qty_avail REAL NOT NULL );";

	DataBaseHelper dbhelper;
	Context ctx;
	SQLiteDatabase db;

	public DataHandler(Context ctx){

		this.ctx = ctx;
		dbhelper = new DataBaseHelper(ctx);
	}

	public static class DataBaseHelper extends SQLiteOpenHelper{

		public DataBaseHelper(Context ctx) {
			super(ctx,DATA_BASE_NAME , null, DATA_BASE_VERSION);

		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			try {
				db.execSQL(ITEM_TABLE_CREATE);

			} catch (Exception e) {
				Log.d("datasbase", "error");
				e.printStackTrace();
			}

		}
		@Override
		public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
			db.execSQL("DROP TABLE IF EXISTS "+ITEM_TABLE_NAME);
			onCreate(db);

		}
	}
	public DataHandler open()
	{
		db =dbhelper.getWritableDatabase();
		return this;
	}

	public void close(){
		dbhelper.close();
	}
	
	//INSERT ITEMS INTO ITEM_TABLE
	public long insertItem(String item_name,Float item_price,Float qty_avail){
		
		ContentValues cv = new ContentValues();
		cv.put(ITEM_NAME, item_name);
		cv.put(ITEM_PRICE, item_price);
		cv.put(ITEM_QUANTITY_AVAIL, qty_avail);
		return db.insert(ITEM_TABLE_NAME, null, cv);
		
	}
	
	//RETRIEVE FROM TABLE
	public Cursor returnItem()
	{
		return db.query(ITEM_TABLE_NAME, new String[]{ITEM_NAME, ITEM_PRICE,ITEM_QUANTITY_AVAIL},null, null, null, null, null);
	}
	
	//Make Array
	public String[] makeArray(Cursor crs,String coloumn){
		String[] array = new String[crs.getCount()];
		int i = 0;
		while(crs.moveToNext()){
		    String uname = crs.getString(crs.getColumnIndex(coloumn));
		    array[i] = uname;
		    i++;
		}
	    return array;
	}
}
