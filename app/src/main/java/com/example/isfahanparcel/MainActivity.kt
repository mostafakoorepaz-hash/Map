package com.example.isfahanparcel

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.webkit.GeolocationPermissions
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private val LOCATION_PERMISSION_REQUEST_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webView)

        // تنظیمات ضروری برای وب‌ویو
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.databaseEnabled = true

        // پاک کردن کش و تاریخچه هنگام شروع برنامه
        webView.clearCache(true)
        webView.clearHistory()

        // اعطای دسترسی GPS به نقشه
        webView.webChromeClient = object : WebChromeClient() {
            override fun onGeolocationPermissionsShowPrompt(
                origin: String?,
                callback: GeolocationPermissions.Callback?
            ) {
                callback?.invoke(origin, true, false)
            }
        }

        // تزریق اسکریپت پیشرفته برای افزایش کنتراست و برجسته کردن خطوط نقشه
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                
                // این اسکریپت هم کنتراست را بالا می‌برد و هم رنگ‌های روشن نقشه را تیره و پررنگ می‌کند
                val enhancedContrastScript = "javascript:(function() {" +
                        "var style = document.createElement('style');" +
                        "style.innerHTML = 'html, body, div, canvas, img {" +
                        "   filter: contrast(170%) brightness(90%) !important;" +
                        "}' ;" +
                        "document.head.appendChild(style);" +
                        "})();"
                view?.evaluateJavascript(enhancedContrastScript, null)
            }
        }

        // بررسی و درخواست پرمیشن مکان
        checkLocationPermission()

        // بارگذاری لینک نقشه اصفهان
        webView.loadUrl("https://tourismapp.isfahan.ir/map")

        // مدیریت دکمه Back
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (webView.canGoBack()) {
                    webView.goBack()
                } else {
                    webView.clearCache(true)
                    finish()
                }
            }
        })
    }

    private fun checkLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                // دسترسی مکان تایید شده است
            }
            else -> {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // دسترسی مکان داده شد
            } else {
                Toast.makeText(this, "دسترسی به مکان رد شد.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
