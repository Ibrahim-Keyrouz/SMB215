package com.example.scanqr;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SQLView extends Activity{
	TextView sqlinfo ;
	
	public void init() {
		sqlinfo = (TextView)findViewById(R.id.tvSQLinfo);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.sqlview);
		init();
		SQLProducts o = new SQLProducts(this);
		o.open();
		String data = o.getData();
		o.close();
		sqlinfo.setText(data);
	}
	
	

}
