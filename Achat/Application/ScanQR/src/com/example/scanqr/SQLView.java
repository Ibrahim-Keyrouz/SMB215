package com.example.scanqr;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SQLView extends Activity{
	TextView sqlinfo ;
	TextView sqlqty;
	
	public void init() {
		sqlinfo = (TextView)findViewById(R.id.tvSQLinfo);
		sqlqty = (TextView)findViewById(R.id.tvsqlQty);
		sqlinfo.setSingleLine(false);
		sqlqty.setSingleLine(false);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.sqlview);
		init();
		SQLProducts o = new SQLProducts(this);
		o.open();
		List<String> data = o.getData();
		
		o.close();
		 for (int i = 0 ; i< data.size();i+=2){
			sqlinfo.setText(sqlinfo.getText()+" \n"+ data.get(i));
			sqlqty.setText(sqlqty.getText()+" \n"+data.get(i+1));
			
		}
		
		
		
	}
	
	

}
