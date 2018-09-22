package com.pavankumarpatruni.githubapp.webview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.pavankumarpatruni.githubapp.R
import com.pavankumarpatruni.githubapp.api.Constants
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val repoUrl = intent.getStringExtra(Constants.REPO_URL)
        val repoName = intent.getStringExtra(Constants.REPO_NAME)

        supportActionBar?.title = repoName

        webView.webViewClient = MyWebClient()
        webView.settings.setSupportZoom(true)
        webView.settings.supportMultipleWindows()

        webView.loadUrl(repoUrl)

    }

    class MyWebClient : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {

            view.loadUrl(url)
            return true

        }
    }
}
