package com.example.msgnav

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.edit
import okhttp3.*
import okio.BufferedSink
import java.io.IOException


class SignupActivity: Activity() {
    // api call client
    private val client = OkHttpClient()
    private val registerApi = "http://localhost:8080/register"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
    }

    fun runPost(url: String) {
        val body = object : RequestBody(){
            override fun contentType(): MediaType? {
                TODO("Not needed")
            }

            override fun writeTo(sink: BufferedSink) {
                TODO("Not needed")
            }
        }
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) = println(response.body?.string())
        })
    }

    fun onClickRegisterButton(view: View) {
        // TODO: HIT THE API TO REGISTER
        val phoneNumber: TextView = findViewById(R.id.pnumber);
        // TODO: add format check before api call
        runPost("$registerApi?number=$phoneNumber")

        getSharedPreferences("shared_prefs", Context.MODE_PRIVATE).edit {
            putBoolean("is_registered", true)
            commit()
        }

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}