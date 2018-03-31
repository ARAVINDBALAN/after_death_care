package com.code_blooded.mrithyu_care.afterdeathcare;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.R.id.home;
import static android.R.id.progress;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    ProgressDialog dialog;
    private WebView mWebView;
    boolean connected = false;
    private ViewPager mViewPager;
    private static final String TAG = "FragmentActivity";
    String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mWebView = (WebView) findViewById(R.id.web_main);
        mWebView.setWebViewClient(new WebViewClient() {

            // This method will be triggered when the Page Started Loading

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                dialog = ProgressDialog.show(MainActivity.this, null,
                        "Please Wait...Page is Loading...");
                dialog.setCancelable(true);
                super.onPageStarted(view, url, favicon);
            }

            // This method will be triggered when the Page loading is completed

            public void onPageFinished(WebView view, String url) {
                dialog.dismiss();
                super.onPageFinished(view, url);
            }

            // This method will be triggered when error page appear

            /* public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                dialog.dismiss();
                // You can redirect to your own page instead getting the default
                // error page
                Toast.makeText(MainActivity.this,
                        "The Requested Page Does Not Exist", Toast.LENGTH_LONG).show();
                mWebView.loadUrl("");
                super.onReceivedError(view, errorCode, description, failingUrl);
            }*/
        });
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setOnTouchListener(new View.OnTouchListener() {
            float m_downX;
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getPointerCount() > 1) {
                    //Multi touch detected
                    return true;
                }

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_UP:{
                        // save the x
                        m_downX = event.getX();
                        break;
                    }
                    case MotionEvent.ACTION_MOVE:
                    case MotionEvent.ACTION_CANCEL:
                     {
                        // set x so that it doesn't move
                        event.setLocation(m_downX, event.getY());
                        break;
                    }

                }
                return false;
            }
        });
        webSettings.setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath());
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        /*     mWebView.setWebViewClient(new MyBrowser()); */
        setNavigationViewListener();

        /*boolean flag = haveNetworkConnection();
        if(!flag){
            mWebView.loadUrl("https://google.com");
        }
        mWebView.loadUrl("https://facebook.com");*/

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else
            connected = false;
        if (connected == false) {
            s="https://adcsih18.herokuapp.com";
            mWebView.loadUrl("file:///android_asset/page.html");
        } else {
            mWebView.loadUrl("http://adcsih18.herokuapp.com");

        }







    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    public void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*public void gotologin(View view ){
        mWebView.loadUrl("www.google.com");}
    public void gotosignup(View view){
        mWebView.loadUrl("www.yahoo.in");

    }
    public void settings(View view) {
        mWebView.loadUrl("https://www.facebook.com");
    }*/
    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Login:
                if (connected == true) {
                    mWebView.loadUrl("https://adcsih18.herokuapp.com");
                } else{
                    s="https://adcsih18.herokuapp.com";
                    mWebView.loadUrl("file:///android_asset/page.html");}
                break;
            /*case R.id.signup:
                if (connected == true) {
                    mWebView.loadUrl("https://www.youtube.com");
                } else{
                    s="https://www.youtube.com";
                    mWebView.loadUrl("file:///android_asset/page.html");}
                break; */
            case R.id.settings:
                if (connected == true) {
                    mWebView.loadUrl("https://innovate.mygov.in/sih2018/");

                } else{
                    s="https://innovate.mygov.in/sih2018/";
                    mWebView.loadUrl("file:///android_asset/page.html");}
                break;
            case R.id.help:get_help();
                            break;
            case R.id.refresh : ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    //we are connected to a network
                    connected = true;}
                    else
                        connected= false;
                if(connected==true){
                   mWebView.loadUrl(s);
                }
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
           /* progressbar.setVisibility(View.VISIBLE);*/
            view.loadUrl(url);
            return true;
        }


    }

    /*public  boolean hasInternetAccess(Context context) {
        if (isNetworkAvailable(context)) {
            try {
                HttpURLConnection urlc = (HttpURLConnection)
                        (new URL("http://clients3.google.com/generate_204")
                                .openConnection());
                urlc.setRequestProperty("User-Agent", "Android");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();
                return (urlc.getResponseCode() == 204 &&
                        urlc.getContentLength() == 0);
            } catch (IOException e) {
                Log.e(TAG, "Error checking internet connection", e);
            }
        } else {
            Log.d(TAG, "No network available!");
        }
        return false;
    }

    public  boolean isNetworkAvailable(Context context) {

        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }*/




   /* public void progress(WebView view) {
        final ProgressBar progressbar = (ProgressBar) findViewById(R.id.progress_bar);
        view.setWebViewClient(new WebViewClient() {
   this         public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (progressbar.getVisibility() == View.INVISIBLE)
                    progressbar.setVisibility(View.VISIBLE);
            }

            public void onPageFinished(WebView view, String url) {
                progressbar.setVisibility(View.GONE);
            }
        });
    }*/
    public void get_help(){
        Intent i = new Intent(this,help.class);
        startActivity(i);
    }

}



