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
        val permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "PERMISSION NOT GRANTED", Toast.LENGTH_SHORT).show()
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS), SMS_PERMISSION_CODE);
        }
        val smsListener = ReceiveSms()
        val intentFilter = IntentFilter()
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED")
        registerReceiver(smsListener, intentFilter)
        Toast.makeText(this, "make text", Toast.LENGTH_LONG).show()
    }
}
