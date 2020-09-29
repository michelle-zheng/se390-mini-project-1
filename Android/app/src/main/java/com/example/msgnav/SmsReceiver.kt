package com.example.msgnav;

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.widget.Toast

class SmsReceiver : BroadcastReceiver() {
    var locations = arrayListOf<String>()
    var directions = arrayListOf<Direction>()

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
            //locations = Array<String> = arrayOf("c", "b")
//            val directions = Array(20) { Direction(R.drawable.search_icon, "temp", "100m") }
            SearchActivity.displayDirections(locations.toTypedArray(), directions.toTypedArray())
        }
    }

    private fun parseDirection(msg: String): Boolean {
        if (locations.isEmpty()) {
            locations.addAll(msg.split(";").map { i -> i.trim() }.toTypedArray())
            return false;
        }
        var directionString =arrayListOf<String>()
        val dirSeq = msg.split("|").map { i -> i.trim() }.toTypedArray()
        (0..dirSeq.size - 2).map{i -> directionString.addAll(
            dirSeq[i].split(";").map{ d -> d.trim()}.toTypedArray())}
        
        for(i in 0 until directionString.size step 3){
            directions.add(Direction(R.drawable.search_icon, directionString[i], directionString[i + 1]))
        }

        val sec = dirSeq.last();
        val div = sec.substring(1, sec.indexOf("/"))
        val tot = sec.substring(sec.indexOf("/") + 1, sec.indexOf(")"))

        return div.equals(tot)
    }
}