package com.example.msgnav;

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.widget.Toast

class SmsReceiver : BroadcastReceiver() {
    private var locations = arrayListOf<String>()
    private var directions= arrayListOf<String>()

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
                receiveAll = parseDirection(msgBody)
                Toast.makeText(context, phoneNumber + ": " + msgBody, Toast.LENGTH_LONG).show()
            }
        }

        if (receiveAll) {
            SearchActivity.displayDirections(locations, directions)
        }
    }

    private fun parseDirection(msg: String): Boolean {
        if (locations.isEmpty()) {
            locations.addAll(msg.split(";").map { i -> i.trim() }.toTypedArray())
            return false;
        }
        val dirSeq = msg.split("|").map { i -> i.trim() }.toTypedArray()
        (0..dirSeq.size - 2).map{i -> directions.addAll(
            dirSeq[i].split(";").map{ d -> d.trim()}.toTypedArray())}

        val sec = dirSeq.last();
        val div = sec.substring(1, sec.indexOf("/"))
        val tot = sec.substring(sec.indexOf("/") + 1, sec.indexOf(")"))
        return div.equals(tot)
    }
}