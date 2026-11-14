package com.example.isfahanparcel

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.webkit.GeolocationPermissions
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
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

        // تنظیمات ضروری برای اجرای نقشه و جاوااسکریپت در وب‌ویو
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.databaseEnabled = true

        // فعال‌سازی دسترسی به موقعیت مکانی (GPS) برای کدهای داخل WebView
        webView.webChromeClient = object : WebChromeClient() {
            override fun onGeolocationPermissionsShowPrompt(
                origin: String?,
                callback: GeolocationPermissions.Callback?
            ) {
                // تایید خودکار درخواست موقعیت مکانی از طرف صفحه وب
                callback?.invoke(origin, true, false)
            }
        }

        webView.webViewClient = WebViewClient()

        // بررسی و گرفتن پرمیشن‌های GPS از اندروید
        checkLocationPermission()

        // TODO: لینک وب‌سایت یا نقشه خودتان را اینجا قرار دهید (مثلاً آدرس پنل یا فایل نقشه آنلاین)
        // webView.loadUrl("https://example.com/map.html")
    }

    private fun checkLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                // پرمیشن قبلاً داده شده است
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
                // کاربر دسترسی مکان را تایید کرد
            } else {
                Toast.makeText(this, "دسترسی به مکان رد شد.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
