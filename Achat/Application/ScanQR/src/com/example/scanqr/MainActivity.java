package com.example.scanqr;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
	Button bscan;
	// Button btest;
	Button b1;
	Button bview;
	EditText etBarcode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		bscan = (Button) findViewById(R.id.bScan);
		etBarcode = (EditText) findViewById(R.id.etBarcode);
		bscan.setOnClickListener(this);
		b1 = (Button) findViewById(R.id.button1);
		b1.setOnClickListener(this);
		bview = (Button) findViewById(R.id.bView);
		bview.setOnClickListener(this);

		// btest = (Button) findViewById(R.id.bTest);
		// btest.setOnClickListener(this);
	}

	public void scanBar(View v) {

		try {

			// start the scanning activity from the
			// com.google.zxing.client.android.SCAN intent
			Intent intent = new Intent(ACTION_SCAN);
			intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
			startActivityForResult(intent, 0);

		} catch (ActivityNotFoundException anfe) {

			// on catch, show the download dialog

			showDialog(MainActivity.this, "No Scanner Found",
					"Download a scanner code activity?", "Yes", "No").show();

		}

	}

	private static AlertDialog showDialog(final Activity act,
			CharSequence title, CharSequence message, CharSequence buttonYes,
			CharSequence buttonNo) {

		AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);

		downloadDialog.setTitle(title);

		downloadDialog.setMessage(message);

		downloadDialog.setPositiveButton(buttonYes,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialogInterface, int i) {

						Uri uri = Uri.parse("market://search?q=pname:"
								+ "com.google.zxing.client.android");

						Intent intent = new Intent(Intent.ACTION_VIEW, uri);

						try {

							act.startActivity(intent);

						} catch (ActivityNotFoundException anfe) {

						}

					}

				});

		downloadDialog.setNegativeButton(buttonNo,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialogInterface, int i) {

					}

				});

		return downloadDialog.show();

	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		long b = 0;
		SQLProducts entry = new SQLProducts(MainActivity.this);
		if (requestCode == 0) {

			if (resultCode == RESULT_OK) {
				String contents = intent.getStringExtra("SCAN_RESULT");
				String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
				this.etBarcode.setText(contents);

				Toast toast = Toast.makeText(this, "Content:" + contents
						+ " Format:" + format, Toast.LENGTH_LONG);
				toast.show();

				// / Here I insert/update the data in SQLite
				try {
					
					entry.open();

					b = entry.createEntry(contents, "08", 1, 4);

					entry.close();
				} catch (Exception e) {
					Dialog d = new Dialog(this);
					d.setTitle("We have it");
					TextView tv = new TextView(this);
					tv.setText("not New Product");
					d.setContentView(tv);
					d.show();
				} finally {
					if (b > 0) {
						Dialog d = new Dialog(this);
						d.setTitle("Heck Yea");
						TextView tv = new TextView(this);
						tv.setText("New Product Created");
						d.setContentView(tv);
						d.show();
					} else {
						
						entry.open();
						entry.updateColumns(etBarcode.getText().toString());
						entry.close();
					}
				}

			}

		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bScan:
			scanBar(v);
			break;

		case R.id.button1:

			new LongRunningGetIO().execute();
			break;

		case R.id.bView:
			Intent i = new Intent(MainActivity.this, SQLView.class);
			startActivity(i);
			break;

		
		}

	}

	class LongRunningGetIO extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			JSONObject jsonUser = new JSONObject();

			JSONObject jsonUser1 = new JSONObject();
			JSONObject jsonUser2 = new JSONObject();
			JSONObject jsonUser3 = new JSONObject();
			JSONArray jsonOrderExtraDetailsList = new JSONArray();
			try {
				//[{"product":{"barcode":"123546"} ,"qty":5,"qtyNotification":5,"sites":{"siteid":"09"},"stkPrdPK":{"barcode":"123546","siteid":"08"}}]

				jsonUser3.put("barcode", "123546");
				jsonUser3.put("siteid", "08");

				jsonUser2.put("siteid", "08");
				jsonUser.put("barcode", "123546");
				jsonUser1.put("product", jsonUser);
				jsonUser1.put("qty", 5);
				jsonUser1.put("qtyNotification", 5);
				jsonUser1.put("sites", jsonUser2);
				jsonUser1.put("stkPrdPK", jsonUser3);

				jsonOrderExtraDetailsList.put(jsonUser1);
				// jsonOrderExtraDetailsList.put(jsonUser1);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			HttpClient httpClient = new DefaultHttpClient();
			try {
				
				HttpPost request = new HttpPost(
						"http://192.168.0.102:8080/STK_PRD_WS/webresources/entities.stkprd/insrt");

				

				StringEntity params1 = new StringEntity(
						jsonOrderExtraDetailsList.toString());
				System.out.println(jsonOrderExtraDetailsList.toString());

				request.addHeader("Content-Type", "application/json");
				request.setHeader("Accept", "application/json");
				request.setHeader("Content-Type", "application/json");
				request.setEntity(params1);

				HttpResponse response = httpClient.execute(request);

				// handle response here...
			} catch (Exception ex) {
				// handle exception here
				Toast.makeText(getBaseContext(), ex.toString(),
						Toast.LENGTH_SHORT).show();
			} finally {
				httpClient.getConnectionManager().shutdown();
			}
			return null;
		}

	}

}
