package com.engineer4myanmar.json;

import com.engineer4myanmar.json.Person;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;

public class SearchActivity extends Activity {
	public static String ipaddress1 = "192.168.1.100";

	Person person = null;
	Spinner spServices;
	Spinner spRange;
	Spinner spRating;
	EditText etMin;
	EditText etMax;
	Spinner spCuisine;

	String input_services = "";
	String input_range = "";
	String input_rating = "";
	String input_min = "";
	String input_max = "";
	String input_cuisine;
	String sjson = "";

	JSONObject jObj;
	JSONParser jsonParser = new JSONParser();

	// change here your ip/folder/php
	private static String url_search = "http://" + ipaddress1
			+ "/esdb/search2.php";

	private ProgressDialog pDialog;
	final ArrayList<String> Alist = new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		spServices = (Spinner) findViewById(R.id.spServices);
		spRange = (Spinner) findViewById(R.id.spRange);
		spRating = (Spinner) findViewById(R.id.spRating);
		etMin = (EditText) findViewById(R.id.etMin);
		etMax = (EditText) findViewById(R.id.etMax);
		spCuisine = (Spinner) findViewById(R.id.spCuisine);
	}

	private class HttpAsyncTaskSearch extends AsyncTask<String, String, String> {
		JSONObject json = null;
		
		@Override
		protected void onPreExecute() {
			
		}
		
		
		
		@Override
		protected String doInBackground(String... params) {

			// updating UI from Background Thread
			
					// Check for success tag
					int success;
					Log.d("test run","running");
					
					try {
						// Building Parameters
						List<NameValuePair> params1 = new ArrayList<NameValuePair>();
						params1.add(new BasicNameValuePair("catalog", input_services));
						params1.add(new BasicNameValuePair("rating", input_rating));
						params1.add(new BasicNameValuePair("p1", input_min));
						params1.add(new BasicNameValuePair("p2", input_max));
						params1.add(new BasicNameValuePair("cui", input_cuisine));
						// getting product details by making HTTP request
						// Note that product details url will use GET request
						Log.d("checking",url_search+" "+params1.toString());
						JSONObject json = jsonParser.makeHttpRequest(
								url_search, "GET", params1);

						// check your log for json response
						Log.d("Single Product Details", json.toString());
						
						// json success tag
						success = json.getInt("success");
						if (success == 1) {
							// successfully received product details
							JSONArray resultObjA = json
									.getJSONArray("result"); // JSON Array
							
							// get first product object from JSON Array
							JSONObject resultObj = resultObjA.getJSONObject(0);

							// product with this pid found
							// Edit Text
							//txtName = (EditText) findViewById(R.id.inputName);
							//txtPrice = (EditText) findViewById(R.id.inputPrice);
							//txtDesc = (EditText) findViewById(R.id.inputDesc);

							// display product data in EditText
							Toast.makeText(getApplicationContext(), resultObj.getString("info_name"), Toast.LENGTH_SHORT).show();
							Toast.makeText(getApplicationContext(), resultObj.getString("address"), Toast.LENGTH_SHORT).show();
							Toast.makeText(getApplicationContext(), resultObj.getString("phone_no"), Toast.LENGTH_SHORT).show();


						}else{
							// product with pid not found
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
			

			return null;
		}


		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once got all details
			
		}
	}
	@SuppressWarnings("unused")
	private static String convertInputStreamToString(InputStream inputStream)
			throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while ((line = bufferedReader.readLine()) != null)
			result += line;

		inputStream.close();
		return result;

	}

	public void funSearchNow(View v) {
		// new registerJSONdbTask().execute(url_register);
		input_services = String.valueOf(spServices.getSelectedItem());
		Toast.makeText(getApplicationContext(), input_services, Toast.LENGTH_SHORT).toString();
		input_range = String.valueOf(spRange.getSelectedItem());
		input_rating = String.valueOf(spRating.getSelectedItem());
		input_cuisine = String.valueOf(spCuisine.getSelectedItem());
		input_min = etMin.getText().toString();
		input_max = etMax.getText().toString();
		Log.d("test fun","fun running");
		new HttpAsyncTaskSearch().execute(url_search);
	}

}
