package com.example.msgnav;

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.widget.Toast

class SmsReceiver : BroadcastReceiver() {
    private lateinit var locations: Array<String>
    private lateinit var directions: ArrayList<String>

    private final val pdu_type = "pdus"

    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "Received a message", Toast.LENGTH_LONG).show()

        val bundle = intent.getExtras();
        var receiveAll = false;

        if (bundle != null) {
            val format = bundle.getString("format")
            val pdusObj = bundle[pdu_type] as Array<Object>
            for (pdus in pdusObj) {
                val currMsg = SmsMessage.createFromPdu(pdus as ByteArray, format)
                val phoneNumber = currMsg.getDisplayOriginatingAddress();
                val msgBody = currMsg.getDisplayMessageBody();
                parseDirection(msgBody);
                Toast.makeText(context, phoneNumber + ": " + msgBody, Toast.LENGTH_LONG).show()
            }
        }

        if (receiveAll && context != null) {
            SearchActivity.displayDirect(locations, directions)
        }
    }

    private fun parseDirection(msg: String) {
        if (locations == null) {
            locations = msg.split(";").map { i -> i.trim() }.toTypedArray()
            return;
        }
        directions.addAll(msg.split(";").map { i -> i.trim() }.toTypedArray())
    }

}