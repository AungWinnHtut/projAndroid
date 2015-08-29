package com.engineer4myanmar.json;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class GPSActivity extends Activity implements LocationListener {
  private TextView latituteField;
  private TextView longitudeField;
  private LocationManager locationManager;
  private String provider;

  
/** Called when the activity is first created. */

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_gps);
    latituteField = (TextView) findViewById(R.id.TextView02);
    longitudeField = (TextView) findViewById(R.id.TextView04);

    LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
	boolean enabled = service
			.isProviderEnabled(LocationManager.GPS_PROVIDER);

	// check if enabled and if not send user to the GSP settings
	// Better solution would be to display a dialog and suggesting to
	// go to the settings
	if (!enabled) {
		Toast.makeText(getApplicationContext(),
				"you must enable gps to use this app", Toast.LENGTH_LONG)
				.show();
		Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		startActivity(intent);
	}
    // Get the location manager
    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    // Define the criteria how to select the locatioin provider -> use
    // default
    Criteria criteria = new Criteria();
    provider = locationManager.getBestProvider(criteria, false);
    Location location = locationManager.getLastKnownLocation(provider);

    // Initialize the location fields
    if (location != null) {
      System.out.println("Provider " + provider + " has been selected.");
      onLocationChanged(location);
    } else {
      latituteField.setText("Location not available");
      longitudeField.setText("Location not available");
    }
  }

  /* Request updates at startup */
  @Override
  protected void onResume() {
    super.onResume();
    locationManager.requestLocationUpdates(provider, 400, 1, this);
  }

  /* Remove the locationlistener updates when Activity is paused */
  @Override
  protected void onPause() {
    super.onPause();
    locationManager.removeUpdates(this);
  }

  public void onLocationChanged(Location location) {
    double lat =  (location.getLatitude());
    double lng =  (location.getLongitude());
    latituteField.setText(String.valueOf(lat));
    longitudeField.setText(String.valueOf(lng));
  }

  public void onStatusChanged(String provider, int status, Bundle extras) {
    // TODO Auto-generated method stub

  }

  public void onProviderEnabled(String provider) {
    Toast.makeText(this, "Enabled new provider " + provider,
        Toast.LENGTH_SHORT).show();

  }

  public void onProviderDisabled(String provider) {
    Toast.makeText(this, "Disabled provider " + provider,
        Toast.LENGTH_SHORT).show();
  }
  
  public void funGetLoc(View v)
  {
	  String value;
	  	Geocoder geocoder;
	  	String bestProvider;
	  	double lat;
	  	double lng;
	  	Context context = null;
	  	LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	  	Criteria criteria = new Criteria();
	  	bestProvider = lm.getBestProvider(criteria, false);
	  	Location location = lm.getLastKnownLocation(bestProvider);
	  	if (location == null){
	  		value="0,0";
	  	}
	  	else{
	  		geocoder = new Geocoder(context);
	  		try {
				List<Address> user = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(), 1);
				lat=(double)user.get(0).getLatitude();
				lng=(double)user.get(0).getLongitude();
				TextView etlat=(TextView)findViewById(R.id.TextView02);
				TextView etlng=(TextView)findViewById(R.id.TextView04);
				etlat.setText(String.valueOf(lat));
				etlng.setText(String.valueOf(lng));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	  		
	  	}
	  
  }
  
  
  
  
} 
