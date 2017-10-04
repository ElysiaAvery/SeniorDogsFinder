package com.example.guest.seniordogsfinder.util;

import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebViewClient extends WebViewClient {
    @Override
//Implement shouldOverrideUrlLoading//
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        if(request.equals("https://sap.petfinderfoundation.com/sponsor-a-pet/checkout")) {
//            view.loadUrl(request.getUrl().toString());
            return false;
        }
        view.loadUrl(request.getUrl().toString());
        return true;
    }

}
