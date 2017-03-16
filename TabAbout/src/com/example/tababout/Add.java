package com.example.tababout;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.location.Geocoder;

public class Add extends Activity implements LocationListener{
	Button btnAdd, btnCancel, btnGenerate;
	private LocationManager locman;
	private Criteria criteria;
	private String provider;
	private Location location;
	String lat, lng;
	EditText txtLat, txtLng;
	Geocoder geocoder;
	Address thisAddress;
	TextView t1, t2, t3, t4;
	LocationDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add);
		geocoder = new Geocoder(this);
		
		db = new LocationDatabase(this);
		
		
		this.txtLat = (EditText) this.findViewById(R.id.editText1);
		this.txtLng = (EditText) this.findViewById(R.id.editText2);
		this.btnGenerate = (Button) this.findViewById(R.id.button3);
		this.btnAdd = (Button) this.findViewById(R.id.button1);
		
		this.t1 = (TextView) this.findViewById(R.id.textView1);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    StrictMode.setThreadPolicy(policy);
		

		btnGenerate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				locman = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
				criteria = new Criteria();
				
				criteria.setAccuracy(Criteria.ACCURACY_MEDIUM);
				criteria.setPowerRequirement(Criteria.ACCURACY_MEDIUM);
					
				provider = locman.getBestProvider(criteria, false);
				locman.requestLocationUpdates(provider, 1, 10,Add.this);
				
				location = locman.getLastKnownLocation(provider);
				lat = String.format("%.5f",location.getLatitude());
				lng = String.format("%.5f",location.getLongitude());
				Toast.makeText(Add.this, lat + "  " +lng, Toast.LENGTH_LONG).show();

			
				txtLat.setText(lat);
				txtLng.setText(lng);
			
//				try {
//					URL url = new URL("http://maps.googleapis.com/maps/api/geocode/json?latlng="+lat+","+lng+"&sensor=true");
//					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//					
//					StringBuffer sb = new StringBuffer();
//					InputStream is = conn.getInputStream();
//					
//					int c = 0;
//					
//					while((c = is.read()) != -1){
//						sb.append((char)c);
//					}
//					
//					JSONObject json = new JSONObject(sb.toString());
//					JSONArray results = json.getJSONArray("results");
//					JSONObject r = results.getJSONObject(0);
//					String str1 = r.getString("formatted_address");
//				
//					is.close();
//					conn.disconnect();
//					t1.setText("Complete Address: "+str1);
//					
//				} catch (IOException e) {
//					Toast.makeText(Add.this, "error", Toast.LENGTH_LONG).show();
//					e.printStackTrace();
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}	
			}
		});
		
		btnAdd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String lat = txtLat.getText().toString();
				String lng = txtLng.getText().toString();
				if(!lat.equals("") && (!lng.equals(""))){
					db.addLocation(lat, lng);
					Toast.makeText(Add.this, "Added Successfully", Toast.LENGTH_LONG).show();
					overridePendingTransition(0,0);
					Intent intent = new Intent(Add.this,MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					overridePendingTransition(0,0);
					startActivity(intent);
					
				}
			}
		});	
	}
	  
	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}

}
