package com.engineer4myanmar.gpio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

public class MainActivity extends Activity {
	String URL="http://192.168.1.2/";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void funOn1(View v)
    {
    	String url="http://192.168.1.2/index3.php";
		
    	try {
    		WebView webview = (WebView)findViewById(R.id.web1 );
    		webview.loadUrl(url);
    	   
    	  } catch (Exception e) {
    	    
    	   Log.e("eeee",e.toString());
    	  }
    }

    public void funOff1(View v)
    {
    	String url="http://192.168.1.2/index4.php";
		
    	try {
    		WebView webview = (WebView)findViewById(R.id.web1 );
    		webview.loadUrl(url);
    	   
    	  } catch (Exception e) {
    	    
    	   Log.e("eeee",e.toString());
    	  }
    }
    
    public void funOn2(View v)
    {
    	String url="http://192.168.1.2/index5.php";
		
    	try {
    		WebView webview = (WebView)findViewById(R.id.web1 );
    		webview.loadUrl(url);
    	   
    	  } catch (Exception e) {
    	    
    	   Log.e("eeee",e.toString());
    	  }
    }

    public void funOff2(View v)
    {
    	String url="http://192.168.1.2/index6.php";
		
    	try {
    		WebView webview = (WebView)findViewById(R.id.web1 );
    		webview.loadUrl(url);
    	   
    	  } catch (Exception e) {
    	    
    	   Log.e("eeee",e.toString());
    	  }
    }
    
    public void funOn3(View v)
    {
    	String url="http://192.168.1.2/index7.php";
		
    	try {
    		WebView webview = (WebView)findViewById(R.id.web1 );
    		webview.loadUrl(url);
    	   
    	  } catch (Exception e) {
    	    
    	   Log.e("eeee",e.toString());
    	  }
    }

    public void funOff3(View v)
    {
    	String url="http://192.168.1.2/index8.php";
		
    	try {
    		WebView webview = (WebView)findViewById(R.id.web1 );
    		webview.loadUrl(url);
    	   
    	  } catch (Exception e) {
    	    
    	   Log.e("eeee",e.toString());
    	  }
    }
    
    public void funOn4(View v)
    {
    	String url="http://192.168.1.2/index9.php";
		
    	try {
    		WebView webview = (WebView)findViewById(R.id.web1 );
    		webview.loadUrl(url);
    	   
    	  } catch (Exception e) {
    	    
    	   Log.e("eeee",e.toString());
    	  }
    }

    public void funOff4(View v)
    {
    	String url="http://192.168.1.2/index10.php";
		
    	try {
    		WebView webview = (WebView)findViewById(R.id.web1 );
    		webview.loadUrl(url);
    	   
    	  } catch (Exception e) {
    	    
    	   Log.e("eeee",e.toString());
    	  }
    }
    
    
	
	
}
