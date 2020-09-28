package com.example.msgnav;

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle

class SendSms : Activity(){
    private lateinit var locations: Array<String>

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        locations = getLocations(intent)
        setContentView(R.layout.activity_loading)

        val uri = Uri.parse("smsto:+13852090925")
        val intent = Intent(Intent.ACTION_SENDTO, uri)
        intent.putExtra("sms_body", "from ")
        intent.putExtra("exit_on_sent", true);
//            startActivity(intent)
        startActivityForResult(intent, 1);
    }

    override fun onActivityResult(requestCode:Int,resultCode:Int,data:Intent?){
        super.onActivityResult(requestCode,resultCode,data)

        if(requestCode == 0 && resultCode == RESULT_OK){
            locations = getLocations(data)
        }
    }

    private fun getLocations(intent:Intent?):Array<String> {
        return intent?.getStringArrayExtra("locations")?:emptyArray()
    }


}

