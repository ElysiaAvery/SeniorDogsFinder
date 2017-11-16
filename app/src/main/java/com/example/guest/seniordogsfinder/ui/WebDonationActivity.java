package com.example.guest.seniordogsfinder.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.guest.seniordogsfinder.R;
import com.example.guest.seniordogsfinder.util.MyWebViewClient;

import org.parceler.Parcels;

public class WebDonationActivity extends AppCompatActivity {
    private WebView webView = null;
    String webDonationUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_donation);
        webDonationUrl = Parcels.unwrap(getIntent().getParcelableExtra("webDonationUrl"));
        Log.v("daontaion ", webDonationUrl);
        this.webView = (WebView) findViewById(R.id.webview);
        webView.setWebChromeClient(new WebChromeClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webView.setInitialScale(1);
        webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        MyWebViewClient webViewClient = new MyWebViewClient();
        webView.setWebViewClient(webViewClient);
        webView.loadUrl(webDonationUrl);
    }

    @Override

    public boolean onKeyDown(final int keyCode, final KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
//If there is history, then the canGoBack method will return ‘true’//
            onStop();
            return true;
        }

//If the button that’s been pressed wasn’t the ‘Back’ button, or there’s currently no
//WebView history, then the system should resort to its default behavior and return
//the user to the previous Activity//
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
