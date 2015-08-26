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
		protected String doInBackground(String... urls) {

			InputStream is = null;
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			input_services = String.valueOf(spServices.getSelectedItem());
			input_range = String.valueOf(spRange.getSelectedItem());
			input_rating = String.valueOf(spRating.getSelectedItem());
			input_cuisine = String.valueOf(spCuisine.getSelectedItem());
			input_min = etMin.getText().toString();
			input_max = etMax.getText().toString();

			// ipaddress = etServer.getText().toString();
			if (input_services.equals("") || input_range.equals("")
					|| input_rating.equals("") || input_cuisine.equals("")
					|| input_min.equals("") || input_max.equals("")) {
				return null;
			} else {
				params.add(new BasicNameValuePair("catalog", input_services));
				params.add(new BasicNameValuePair("rating", input_rating));
				params.add(new BasicNameValuePair("p1", input_min));
				params.add(new BasicNameValuePair("p2", input_max));
				params.add(new BasicNameValuePair("cuisine", input_cuisine));
				params.add(new BasicNameValuePair("range", input_range));

				// getting JSON Object
				try {
					// json = jsonParser.makeHttpRequest(url_register,
					// "POST",params);
					url_search = "http://" + ipaddress1 + "/esdb/search2.php";
					DefaultHttpClient httpClient = new DefaultHttpClient();
					String paramString = URLEncodedUtils
							.format(params, "utf-8");
					url_search += "?" + paramString;
					Log.d("json url", url_search);
					HttpGet httpGet = new HttpGet(url_search);
					// //////////////////////
					HttpResponse httpResponse = httpClient.execute(httpGet);
					HttpEntity httpEntity = httpResponse.getEntity();
					is = httpEntity.getContent();
					if (is.toString().equals(null)) {
						Log.d("JSON is string", "is is null");
					} else {
						Log.d("JSON is string", is.toString());
					}

					BufferedReader reader = new BufferedReader(
							new InputStreamReader(is, "iso-8859-1"), 8);
					StringBuilder sb = new StringBuilder();
					String line = null;

					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
					Log.d("JSON", sb.toString());
					is.close();
					sjson = sb.toString();

				} catch (Exception e) {
					Log.e("parser error", e.toString());
				}
				Log.d("Create Response", sjson);

				// //////////////////////////////////////
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
				Log.d("jsonstring", sjson);
				// return neull;
				return sjson;
				// return POST(urls[0],person);
			}
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			try {
				if (result.equals(null)) {
					Toast.makeText(getBaseContext(), "'" + result + "'",
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(getBaseContext(),
							"Data successfully send to server!!!",
							Toast.LENGTH_LONG).show();

				}

			} catch (Exception e) {
				Log.e("JSON postExe", e.toString());
				Toast.makeText(
						getBaseContext(),
						"Data cannot be send! Please fill the required fields !!!",
						Toast.LENGTH_LONG).show();
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

	public void funSearchNow(View v) {
		// new registerJSONdbTask().execute(url_register);
		new HttpAsyncTaskSearch().execute(url_search);
	}

}
