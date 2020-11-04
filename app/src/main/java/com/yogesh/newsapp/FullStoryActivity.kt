package com.yogesh.newsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_full_story.*
import kotlinx.android.synthetic.main.activity_full_story.view.*

class FullStoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_story)
        val intent:Intent= getIntent()
        val openUrl: String? =intent.getStringExtra("STORY")
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, openUrl: String?): Boolean {
                if (openUrl != null) {
                    view?.loadUrl(openUrl)
                }
                return true
            }
        }
        if (openUrl != null) {
            webView.settings.loadsImagesAutomatically
            webView.settings.javaScriptEnabled
            webView.scrollBarStyle
            webView.loadUrl(openUrl)
        }
    }
}
