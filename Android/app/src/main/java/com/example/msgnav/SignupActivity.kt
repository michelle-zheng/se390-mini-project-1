package com.example.msgnav

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.edit

class SignupActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
    }

    fun onClickRegisterButton(view: View) {
        // TODO: HIT THE API TO REGISTER

        getSharedPreferences("shared_prefs", Context.MODE_PRIVATE).edit {
            putBoolean("is_registered", true)
            commit()
        }

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}