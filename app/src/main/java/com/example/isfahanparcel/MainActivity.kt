package com.example.isfahanparcel

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webView)
        
        // فعال‌سازی جاوااسکریپت برای بارگذاری کامل سامانه شهرداری
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        
        // باز شدن لینک‌ها درون خود اپلیکیشن
        webView.webViewClient = WebViewClient()

        // لینک استعلام عوارض نوسازی اصفهان
        val url = "https://srv.isfahan.ir/citizen/?formName=UBedehiAvaarezNosazi&cf=App&token=6YDXj3C780Q6Uy780D88Dhs0u6ynUq&cf=App&token=43R9TmmVq9Vzc242QdX4KQL7EWzBz4"
        
        webView.loadUrl(url)
    }

    // مدیریت دکمه بازگشت گوشی برای مرور صفحات قبلی در وب‌ویو
    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
