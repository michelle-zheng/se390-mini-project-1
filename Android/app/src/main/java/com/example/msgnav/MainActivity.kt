package com.example.msgnav

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import java.time.Duration


class MainActivity : Activity() {
    private val SMS_PERMISSION_CODE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkforSmsPermission()
    }

    fun onButtonClick(view: View) {
        val intent = Intent(this, SearchActivity::class.java)
        startActivity(intent)
    }

    private fun checkforSmsPermission() {
        val sendPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
        val receivePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
        if (sendPermission != PackageManager.PERMISSION_GRANTED || receivePermission != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "PERMISSION NOT GRANTED", Toast.LENGTH_SHORT).show()
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS), SMS_PERMISSION_CODE);
        }
    }
}
