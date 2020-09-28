package com.example.msgnav;

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast

class SmsReceiver : BroadcastReceiver() {
    private lateinit var locations: Array<String>

    override fun onReceive(context: Context, intent: Intent) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

//        val bundle = intent.getExtras();

        Toast.makeText(context, "Received a message", Toast.LENGTH_LONG).show()

        val intent = Intent(context, FullDirectionsActivity::class.java)

        // TODO: Update this location value to the proper addresses returned by the server
        intent.putExtra("locations", locations)
        // TODO: Pass Array<Direction> as an extra to this activity (use name "directions")
        intent.putExtra("directions", Array(20){ i -> Direction(R.drawable.search_icon,  "Turn left at Thomas Street Middle School", "100 m") })

//        if (context.intent.getBooleanExtra("new_activity", true)) {
            if (context != null) {
                context.startActivity(intent)
            }
//        } else {
//            setResult(RESULT_OK, intent)
//        }

//        finish()
    }


}

