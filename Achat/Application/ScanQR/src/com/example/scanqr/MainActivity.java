package com.example.scanqr;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import android.database.Cursor;
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
	Button bnew;
	// Button btest;
	Button b1;
	Button bview;
	EditText etBarcode;
	String contents;
	int counter = 0;

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
		bnew = (Button) findViewById(R.id.bNew);
		bnew.setOnClickListener(this);
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
				contents = intent.getStringExtra("SCAN_RESULT");
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

		case R.id.bNew:
			SQLProducts x = new SQLProducts(getBaseContext());
			x.open();
			x.deleteColumns();
			x.close();

			break;

		}

	}

	class LongRunningGetIO extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			List<String[]> data = new ArrayList<String[]>();
			String[] row;

			JSONObject jsonUser = new JSONObject();
			JSONObject jsonUser1 = new JSONObject();
			JSONObject jsonUser2 = new JSONObject();
			JSONObject jsonUser3 = new JSONObject();
			JSONObject jsonUser4 = new JSONObject();
			JSONObject jsonUser5 = new JSONObject();
			JSONObject jsonUser6 = new JSONObject();
			JSONObject jsonUser7 = new JSONObject();

			JSONArray jsonOrderExtraDetailsList = new JSONArray();
			JSONArray jsonOrderExtraDetailsList1 = new JSONArray();

			DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd");
			Date date = new Date();
			String vDocid = "123456" + dateFormat.format(date);

			SQLProducts o = new SQLProducts(getBaseContext());
			o.open();
			Cursor c = o.getDataCursor();

			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
				row = new String[4];
				for (int i = 0; i < 4; i++) {

					row[i] = c.getString(i);
					System.out.println(row[i]);

				}

				data.add(row);

			}

			o.close();

			// To create a Recept Header

			try {
				jsonUser1.put("siteid", "08");
				jsonUser2.put("docid", "123456");
				jsonUser3.put("docid", vDocid);
				jsonUser3.put("invoiceDiscount", 0);
				jsonUser3.put("relatedDocid", jsonUser2);
				jsonUser3.put("siteid", jsonUser1);
				// jsonUser5.put("trsdate",trsdateFormat.format(date1));
				dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				date = new Date();
				jsonUser3.put("trsdate", dateFormat.format(date)
						+ "T00:00:00+03:00");

				jsonOrderExtraDetailsList.put(jsonUser3);

			}

			catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			HttpClient httpClient = new DefaultHttpClient();
			HttpPost request;
			StringEntity params1;
			HttpResponse response;
			try {
				request = new HttpPost(
						"http://192.168.10.111:8080/STK_PRD_WS/webresources/entities.recept/insrt_recept");
				params1 = new StringEntity(jsonOrderExtraDetailsList.toString());
				System.out.println(jsonOrderExtraDetailsList.toString());

				request.addHeader("Content-Type", "application/json");
				request.setHeader("Accept", "application/json");
				request.setHeader("Content-Type", "application/json");

				request.setHeader("Cache-Control", "no-cache");
				request.setHeader("Cache-Control", "no-store");
				request.setEntity(params1);

				response = httpClient.execute(request);

			} catch (Exception ex) {
				// handle exception here
			}

			jsonUser1 = new JSONObject();
			jsonUser2 = new JSONObject();
			jsonUser3 = new JSONObject();

			jsonOrderExtraDetailsList = new JSONArray();

			try {
				// to update the qty in stk_prd

				for (String[] rows : data) {
					System.out.println(rows[0] + "," + rows[1] + "," + rows[2]
							+ "," + rows[3]);

					jsonUser3.put("barcode", rows[0]);
					jsonUser3.put("siteid", rows[1]);

					jsonUser2.put("siteid", rows[1]);
					jsonUser.put("barcode", rows[0]);
					jsonUser1.put("product", jsonUser);
					jsonUser1.put("qty", rows[2]);
					jsonUser1.put("qtyNotification", rows[3]);
					jsonUser1.put("sites", jsonUser2);
					jsonUser1.put("stkPrdPK", jsonUser3);

					jsonOrderExtraDetailsList.put(jsonUser1);
					jsonUser1 = new JSONObject();
					jsonUser2 = new JSONObject();
					jsonUser3 = new JSONObject();

					// to insert in recept_dtl

					jsonUser4.put("barcode", rows[0]);
					jsonUser5.put("docid", vDocid);
					jsonUser6.put("barcode", rows[0]);
					jsonUser6.put("docid", vDocid);

					jsonUser7.put("itemDiscount", 0);
					jsonUser7.put("product", jsonUser4);
					jsonUser7.put("purchasePrice", 0);
					jsonUser7.put("qty", rows[2]);
					jsonUser7.put("recept", jsonUser5);
					jsonUser7.put("receptDtlPK", jsonUser6);

					jsonOrderExtraDetailsList1.put(jsonUser7);
					jsonUser4 = new JSONObject();
					jsonUser5 = new JSONObject();
					jsonUser6 = new JSONObject();
					jsonUser7 = new JSONObject();

				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			httpClient = new DefaultHttpClient();
			try {

				request = new HttpPost("http://192.168.10.111:8080/STK_PRD_WS/webresources/entities.stkprd/insrt");

				params1 = new StringEntity(jsonOrderExtraDetailsList.toString());
				System.out.println(jsonOrderExtraDetailsList.toString());

				request.addHeader("Content-Type", "application/json");
				request.setHeader("Accept", "application/json");
				request.setHeader("Content-Type", "application/json");

				request.setHeader("Cache-Control", "no-cache");
				request.setHeader("Cache-Control", "no-store");
				request.setEntity(params1);

				response = httpClient.execute(request);

				httpClient = new DefaultHttpClient();
				request = new HttpPost("http://192.168.10.111:8080/STK_PRD_WS/webresources/entities.receptdtl/insrt_receptdtl");

				params1 = new StringEntity(
						jsonOrderExtraDetailsList1.toString());
				System.out.println(jsonOrderExtraDetailsList1.toString());

				request.addHeader("Content-Type", "application/json");
				request.setHeader("Accept", "application/json");
				request.setHeader("Content-Type", "application/json");

				request.setHeader("Cache-Control", "no-cache");
				request.setHeader("Cache-Control", "no-store");
				request.setEntity(params1);

				response = httpClient.execute(request);

				// handle response here...
			} catch (Exception ex) {
				// handle exception here
				Toast.makeText(getBaseContext(), ex.toString(),
						Toast.LENGTH_SHORT).show();
			} finally {
				httpClient.getConnectionManager().shutdown();

				// / Delete all Sqlite Rows

				SQLProducts x = new SQLProducts(getBaseContext());
				x.open();
				x.deleteColumns();
				x.close();

			}
			return null;
		}

	}

}
