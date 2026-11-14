package com.example.isfahanparcel // پکیج اپلیکیشن خودتان را اینجا قرار دهید

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.carto.styles.Color
import com.carto.styles.MarkerStyleBuilder
import com.carto.utils.BitmapUtils
import org.neshan.mapsengine.MapView
import org.neshan.common.model.LatLng

class MainActivity : AppCompatActivity() {

    private lateinit var map: MapView
    private val LOCATION_PERMISSION_REQUEST_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // لایوت حاوی MapView

        // مقداردهی اولیه نقشه
        map = findViewById(R.id.map) // مطمئن شوید آی‌دی نقشه در xml همین است

        // بررسی و درخواست دسترسی GPS در زمان اجرا
        checkLocationPermission()
    }

    private fun checkLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                // اگر دسترسی قبلاً داده شده بود، موقعیت را روی نقشه فعال کن
                enableUserLocation()
            }
            else -> {
                // درخواست دسترسی از کاربر
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

    // دریافت نتیجه پاسخ کاربر به دیالوگ پرمیشن
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // کاربر دسترسی را داد
                enableUserLocation()
            } else {
                // کاربر دسترسی را رد کرد
                Toast.makeText(this, "دسترسی به مکان رد شد. نمایش موقعیت امکان‌پذیر نیست.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun enableUserLocation() {
        // فعال‌سازی لایه موقعیت کاربر در نقشه نشان
        try {
            // در SDK نشان معمولاً با فعال کردن لایه لوکیشن، موقعیت و دایره آبی مدیریت می‌شود
            map.isMyLocationEnabled = true
            map.isMyLocationZoomEnabled = true
            
            // اگر خواستید روی موقعیت کاربر زوم کنید یا لایه را ردیابی کنید:
            // map.settings.isMyLocationLayerEnabled = true
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onStart() {
        super.onStart()
        map.onStart()
    }

    override fun onStop() {
        super.onStop()
        map.onStop()
    }
}
