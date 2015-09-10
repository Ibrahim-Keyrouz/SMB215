package com.example.scanqr;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class MyNotificationService extends Service{
	
	
	HttpClient client;
	String ip ;
	 String URL;
	
	JSONArray json;
	SharedPreferences getPrefs ;
	//String site;
	

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		 ip = this.getString(R.string.ip);
		  URL = "http://"+ip+":8080/STK_PRD_WS/webresources/entities.stkprd/notifications/";
	//	Toast.makeText(getBaseContext(), "On create Service", Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onStart(Intent intent, int startId) {
		
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		//Toast.makeText(getBaseContext(), "On start Service", Toast.LENGTH_SHORT).show();
		getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		//site = getPrefs.getString("Siteid", "09");
		client = new DefaultHttpClient();
		new LongRunningGetIO().execute(); 
		
	}

	private boolean SQLWSequal() throws JSONException {
		// TODO Auto-generated method stub
		
		try{
		if (json.length()!=0){
			return true;
		}
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return false;


	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Toast.makeText(getBaseContext(), "Service Destroyed", Toast.LENGTH_SHORT).show();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
public JSONArray lastNotifications() throws ClientProtocolException,IOException,JSONException{
		
		StringBuilder url = new StringBuilder(URL+getPrefs.getString("Siteid", "09"));
		
		
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
		Toast.makeText(MyNotificationService.this, "error", Toast.LENGTH_SHORT).show();
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
			if (SQLWSequal()) {

			
			NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
			
			Intent notiIntent = new Intent(getBaseContext(),Stock_Alert.class);
			PendingIntent pi = PendingIntent.getActivity(getBaseContext(), 0, notiIntent, 0);
			
			int icon = R.drawable.ic_app;
			long when = System.currentTimeMillis();
			String body = "Check your Stock ";
			
			Notification n = new Notification(icon,body,when);
			n.defaults=Notification.DEFAULT_ALL;
			n.flags =  Notification.FLAG_AUTO_CANCEL;
			n.setLatestEventInfo(getBaseContext(), "Check your Stock ", "You must recheck your stock", pi);
			nm.cancelAll();
			nm.notify(123, n);
			
			}
		} catch (JSONException e) {
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
