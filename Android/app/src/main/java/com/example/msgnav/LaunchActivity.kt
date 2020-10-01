package com.example.msgnav

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle

class LaunchActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (getSharedPreferences("shared_prefs", Context.MODE_PRIVATE).getBoolean("is_registered", false)) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}