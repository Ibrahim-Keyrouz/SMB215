package com.example.scanqr;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class SQLProducts {

	// SQLiteDatabase.openOrCreateDatabase(String path,
	// SQLiteDatabase.CursorFactory factory)
	
	  
	public static final String KEY_BARCODE = "BARCODE";
	public static final String KEY_SITE = "SITEID";
	public static final String KEY_QTY = "QTY";
	public static final String KEY_QTY_NOTIFICATION = "QTY_NOTIFICATION";
	
	
	
	//public static final String KEY_PRODUCT = "Description";
	public static final String DATABASE_NAME = "PRODUCT_ADD_TEST1";
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
		// TODO Auto-generated method stub
		List<String> results = new ArrayList<String>();
		
		int i = 0 ;
		String[] columns = new String[] {KEY_BARCODE,KEY_SITE,KEY_QTY,KEY_QTY_NOTIFICATION};
		Cursor c = ourDatabase.query( DATABASE_TABLE, columns, null, null, null, null, null, null);
		
		int iRow = c.getColumnIndex(KEY_BARCODE);
		int iName = c.getColumnIndex(KEY_QTY);
	
		
		
		for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
			results.add(c.getString(iRow));
			results.add(c.getString(iName));
		
			
		}
		
		
		return results;
	}
	
	
	public Cursor getDataCursor() {
		// TODO Auto-generated method stub
		String[] columns = new String[] {KEY_BARCODE,KEY_SITE,KEY_QTY,KEY_QTY_NOTIFICATION};
		Cursor c = ourDatabase.query( DATABASE_TABLE, columns, null, null, null, null, null, null);
		//String result = "";
		//int iRow = c.getColumnIndex(KEY_BARCODE);
		//int iName = c.getColumnIndex(KEY_QTY);
		
		//for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
		//	result = result +" "+ c.getString(iRow) +"          "+c.getString(iName)+"\n";
			
		//}
		
		return c;
	}
	
	public int getQty(String info)  throws SQLException {
		// TODO Auto-generated method stub
		try {
		String[] columns = new String[] {KEY_QTY};
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_BARCODE + "='" + info+"'",null, null, null, null);
		for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
			
			int iName = c.getColumnIndex(KEY_QTY);
			
			return c.getInt(iName);
		}
		
		return 0;
		
		}catch(SQLException e) {
			return 0;
		}
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

	public long updateColumns( String vBarcode)  throws SQLException {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		
		cv.put(KEY_QTY, getQty(vBarcode)+1);
	
		return ourDatabase.update(DATABASE_TABLE, cv, KEY_BARCODE + "='" + vBarcode+"'", null);
		
	}

	public long deleteColumns()  throws SQLException{
		// TODO Auto-generated method stub
		
		return ourDatabase.delete(DATABASE_TABLE, null, null);
		
	}
	
	
	public long deleteColumn(String vBarcode)  throws SQLException{
		// TODO Auto-generated method stub
		
		return ourDatabase.delete(DATABASE_TABLE, KEY_BARCODE + "='" + vBarcode+"'", null);
		
	}

}
