package com.example.scanqr;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.widget.Toast;

public class RedirectClass extends Activity {
	HttpClient client;
	String findEmail;
	String ip ;
	 String URL ;
	
	JSONArray json;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_redirect_class);
		
		 ip = getResources().getString(R.string.ip);
		 URL = "http://"+ip+":8080/STK_PRD_WS/webresources/entities.usersachat/email_exist/";
		
		client = new DefaultHttpClient();
		Intent a = this.getIntent();
		findEmail = a.getStringExtra("email_id");
		new LongRunningGetIO().execute();
		
		
	}
	
	
	
public JSONArray lastNotifications() throws ClientProtocolException,IOException,JSONException{
	findEmail = "bob.keyrouz@gmail.com";
		StringBuilder url = new StringBuilder(URL+findEmail);
		
		
		HttpGet get = new HttpGet(url.toString());
		
		try {
		HttpResponse r = client.execute(get);
		
		
	
		int status = r.getStatusLine().getStatusCode();
		
		if (status == 200) {
			HttpEntity entity = r.getEntity();
			String data = EntityUtils.toString(entity);
			JSONArray timeline = new JSONArray(data); // return all the result into a JSON Array
			//JSONObject last = timeline.getJSONObject(0); // return the first record of the JSON Array 
			return timeline;
		}
	
	else{
		
		//Toast.makeText(RedirectClass.this, "error", Toast.LENGTH_SHORT).show();
	}
			}catch(IOException e){
				
		}
		
		return null;
	}
	
	
	class LongRunningGetIO extends  AsyncTask <Void, Void, String>{

		

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		
			
			try {
				
					
				if (json.length() != 0 ){
				
					finish();
					Intent i = new Intent(RedirectClass.this, MainActivity.class);
					startActivity(i);
				}
				else{
					
					finish();
					Intent i = new Intent(RedirectClass.this, ErrorActivity.class);
					startActivity(i);
					
				}
				
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		
			
		}

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			try {
				
				json = lastNotifications();
				
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		
		
		

	}
		
		
	
}
