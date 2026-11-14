package com.example.isfahanparcel

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import org.neshan.mapsengine.MapView

class MainActivity : AppCompatActivity() {

    private var map: MapView? = null
    private val LOCATION_PERMISSION_REQUEST_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // پیدا کردن امن کامپوننت نقشه با فرض اینکه id آن در لایوت map است
        map = findViewById(R.id.map)

        // بررسی و درخواست دسترسی GPS
        checkLocationPermission()
    }

    private fun checkLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                enableUserLocation()
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
                enableUserLocation()
            } else {
                Toast.makeText(this, "دسترسی به مکان رد شد.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun enableUserLocation() {
        try {
            // فعال‌سازی لایه مکان روی نقشه به صورت ایمن
            map?.let { mapView ->
                val settings = mapView.settings
                settings?.isMyLocationLayerEnabled = true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onStart() {
        super.onStart()
        map?.onStart()
    }

    override fun onStop() {
        super.onStop()
        map?.onStop()
    }
}
