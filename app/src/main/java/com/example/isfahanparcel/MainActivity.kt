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
        
        // فعال‌سازی جاوااسکریپت
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        
        // باز شدن لینک‌ها درون خود اپلیکیشن
        webView.webViewClient = WebViewClient()

        // لینک جدید سامانه گردشگری/نقشه اصفهان
        val url = "https://tourismapp.isfahan.ir/map?token=B9q4m68vAJmit6Rt2RTUoIz6ibmxS9"
        
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
