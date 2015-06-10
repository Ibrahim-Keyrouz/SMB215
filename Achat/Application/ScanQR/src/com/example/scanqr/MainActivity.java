package com.example.scanqr;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
	Button bscan;
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

		if (requestCode == 0) {

			if (resultCode == RESULT_OK) {
				String contents = intent.getStringExtra("SCAN_RESULT");
				String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
				this.etBarcode.setText(contents);
				Toast toast = Toast.makeText(this, "Content:" + contents
						+ " Format:" + format, Toast.LENGTH_LONG);
				toast.show();

			}

		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bScan :
			scanBar(v);
			break;
		}

	}

}
