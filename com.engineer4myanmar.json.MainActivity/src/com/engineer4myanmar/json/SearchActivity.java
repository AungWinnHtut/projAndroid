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

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

public class SearchActivity extends Activity {
	public static  String ipaddress1= "192.168.1.100";

	Person person = null;
	//////////////////////////
	// to delete
	/////////////////////////
	EditText etUsername;
	EditText etPassword;
	EditText etEmail;
	EditText etPhoneno;
	EditText etDob;
	EditText etProfession;
	EditText etCity;
	EditText etServer;

	String input_username = "";
	String input_password = "";
	String input_email = "";
	String input_phoneno = "";
	String input_dob = "";
	String input_profession = "";
	String input_city = "";	
	//////////////////////////////////
	Spinner spServices;
	Spinner spRange;
	Spinner spRating;
	EditText etMin;
	EditText etMax;
	Spinner spCuisine;
	
	String input_services="";
	String input_range="";
	String input_rating="";
	String input_min="";
	String input_max="";
	String input_cuisine;

	JSONObject jObj;
	JSONParser jsonParser = new JSONParser();
	
	// change here your ip/folder/php
	private static String url_search = "http://"+ipaddress1+"/esdb/search2.php";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		spServices=(Spinner)findViewById(R.id.spServices);
		spRange=(Spinner)findViewById(R.id.spRange);
		spRating=(Spinner)findViewById(R.id.spRating);
		etMin=(EditText)findViewById(R.id.etMin);
		etMax=(EditText)findViewById(R.id.etMax);
		spCuisine=(Spinner)findViewById(R.id.spCuisine);
	}

	private class HttpAsyncTask extends AsyncTask<String, String, String> {
		JSONObject json = null;

		@Override
		protected String doInBackground(String... urls) {

			InputStream is = null;
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			
			input_services=String.valueOf(spServices.getSelectedItem());
			input_range=String.valueOf(spRange.getSelectedItem());
			input_rating=String.valueOf(spRating.getSelectedItem());
			input_cuisine=String.valueOf(spCuisine.getSelectedItem());
			input_min=etMin.getText().toString();
			input_max=etMax.getText().toString();
			
			//ipaddress = etServer.getText().toString();
			if (input_services.equals("") || input_range.equals("")||input_rating.equals("") || 
					input_cuisine.equals("") || input_min.equals("")||input_max.equals("")) {				
				return null;
			} else {
				params.add(new BasicNameValuePair("catalog", input_services));
				params.add(new BasicNameValuePair("rating", input_rating));
				params.add(new BasicNameValuePair("p1",
						input_min));
				params.add(new BasicNameValuePair("p2", input_max));
				params.add(new BasicNameValuePair("cuisine", input_cuisine));
				params.add(new BasicNameValuePair("range",
						input_range));
				
				// getting JSON Object
				try {
					// json = jsonParser.makeHttpRequest(url_register,
					// "POST",params);
					url_search="http://"+ipaddress1+"/esdb/search2.php";
					DefaultHttpClient httpClient = new DefaultHttpClient();
					HttpPost httpPost = new HttpPost(url_search);
					httpPost.setEntity(new UrlEncodedFormEntity(params));
					HttpResponse httpResponse = httpClient.execute(httpPost);
					HttpEntity httpEntity = httpResponse.getEntity();
					is = httpEntity.getContent();
					Log.d("JSON", is.toString());
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(is, "iso-8859-1"), 8);
					StringBuilder sb = new StringBuilder();
					String line = null;

					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
					Log.d("JSON", sb.toString());
					is.close();
					String sjson = sb.toString();

					try {
						jObj = new JSONObject(sjson);
						json = jObj;
						
					} catch (JSONException e) {
						Log.e("JSON Parser",
								"Error parsing data " + e.toString() + "   "
										+ params.toString() + "+++ " + sjson);
					}

				} catch (Exception e) {
					Log.e("parser error", e.toString());
				}
				Log.d("Create Response", json.toString());

				// check for success tag
				try {
					int success = json.getInt("success");
					if (success == 1) {
						Log.d("Success", "OK");
					} else {
						Log.d("Success", "not OK");
					}
				} catch (Exception e) {
					Log.d("Success", "not OK " + e.toString());
				}

				// return neull;
				return jObj.toString();
				// return POST(urls[0],person);
			}
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
		
			try{
				if(result.equals(null))
				{
					Toast.makeText(getBaseContext(), "'"+ result+"'",Toast.LENGTH_LONG).show();
				}
				else
				{
					Toast.makeText(getBaseContext(), "Data successfully send to server!!!",Toast.LENGTH_LONG).show();
				}
					
			}catch(Exception e)
			{
				Log.e("JSON",e.toString());
				Toast.makeText(getBaseContext(), "Data cannot be send! Please fill the required fields !!!",Toast.LENGTH_LONG).show();
			}
			
			
			
			
			
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

	public void funRegister(View v) {
		// new registerJSONdbTask().execute(url_register);
		new HttpAsyncTask().execute(url_search);
	}
}

