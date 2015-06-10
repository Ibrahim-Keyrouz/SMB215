package com.example.scanqr;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLProducts {

	// SQLiteDatabase.openOrCreateDatabase(String path,
	// SQLiteDatabase.CursorFactory factory)
	
	  
	public static final String KEY_BARCODE = "BARCODE";
	public static final String KEY_SITE = "SITEID";
	public static final String KEY_QTY = "QTY";
	public static final String KEY_QTY_NOTIFICATION = "QTY_NOTIFICATION";
	
	
	
	//public static final String KEY_PRODUCT = "Description";
	public static final String DATABASE_NAME = "PRODUCT_ADD";
	public static final String DATABASE_TABLE = "PRODUCT_ADD";
	public static final int DATABASE_VERSION = 1;

	private DbHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;

	public SQLProducts(Context c) {
		ourContext = c;
	}

	public SQLProducts open() throws SQLException {
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		ourHelper.close();
	}

				private static class DbHelper extends SQLiteOpenHelper {
			
					public DbHelper(Context context) {
						super(context, DATABASE_NAME, null, DATABASE_VERSION);
						// TODO Auto-generated constructor stub
					}
			
					@Override
					public void onCreate(SQLiteDatabase db) {
						// TODO Auto-generated method stub
						db.execSQL("CREATE TABLE " + DATABASE_TABLE + " ( " + KEY_BARCODE
								+ " varchar2(16) PRIMARY KEY "
								+ ", " + KEY_SITE + " varchar2(2) "
								+ ", " + KEY_QTY + " NUMBER(5) "
								+ ", " + KEY_QTY_NOTIFICATION + " NUMBER(5) "
								
								+ ");");
			
					}
			
					@Override
					public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
						// TODO Auto-generated method stub
			
					}
			
				}

	public long createEntry(String vBarcode,String vSite,Integer vQty,Integer vQty_not) throws SQLException {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(KEY_BARCODE, vBarcode);
		cv.put(KEY_SITE, vSite);
		cv.put(KEY_QTY, vQty);
		cv.put(KEY_QTY_NOTIFICATION, vQty_not);
		
		//cv.put(KEY_PRODUCT, vProd);
		return ourDatabase.insert(DATABASE_TABLE, null, cv);
	}

	public List<String> getData() {
		
		int i = 0 ;
		// TODO Auto-generated method stub
		String[] columns = new String[] {KEY_BARCODE};
		Cursor c = ourDatabase.query( DATABASE_TABLE, columns, null, null, null, null, null, null);
		
		//String[] result = new String[12];
		
		List<String> result = new ArrayList<String>();
		
		int iRow = c.getColumnIndex(KEY_BARCODE);
		//int iName = c.getColumnIndex(KEY_PRODUCT);
		
		for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
			
			//result[i] =c.getString(iRow) ;
			
			//result.set(i, c.getString(iRow));
			result.add(c.getString(iRow));
			i++;
			
		}
		
		return result;
	}
	
	public Integer getQty(String info)  throws SQLException {
		// TODO Auto-generated method stub
		String[] columns = new String[] {KEY_QTY};
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_BARCODE + "='" + info+"'",null, null, null, null);
		if (c != null ) {
			c.moveToFirst();
			
			int iName = c.getColumnIndex(KEY_QTY);
			
			return c.getInt(iName);
		}
		
		return null;
	}
	

	public String getProduct(String info)  throws SQLException {
		// TODO Auto-generated method stub
		String[] columns = new String[] {KEY_BARCODE};
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_BARCODE + "='" + info+"'",null, null, null, null);
		if (c != null ) {
			c.moveToFirst();
			
			int iName = c.getColumnIndex(KEY_BARCODE);
			
			return c.getString(iName);
		}
		
		return null;
	}

	public long updateColumns(Integer vQty, String vBarcode)  throws SQLException {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		
		cv.put(KEY_QTY, getQty(vBarcode)+vQty);
	
		return ourDatabase.update(DATABASE_TABLE, cv, KEY_BARCODE + "='" + vBarcode+"'", null);
		
	}

	/*public long deleteColumns(String pID)  throws SQLException{
		// TODO Auto-generated method stub
		
		return ourDatabase.delete(DATABASE_TABLE, KEY_ID + "='" + pID+"'", null);
		
	}*/

}
