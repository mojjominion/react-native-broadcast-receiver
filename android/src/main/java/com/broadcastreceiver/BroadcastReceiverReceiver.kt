package com.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.facebook.react.bridge.Arguments

private data class Data(val key: String?, val data: String?)

class BroadcastReceiverReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val (key, data) = getDataFromIntent(intent)   
        if (key != null && key.isNotEmpty() && data != null && data.isNotEmpty()) {
            val params = Arguments.createMap()
            params.putString(key, data)
            BroadcastReceiverModule.sendEvent(Constants.BroadcastEventName, params)
        }
    }

    private fun getDataFromIntent(intent: Intent?): Data {
        val action = intent?.action
        var dataKey: String? = null
        var barcodedata: String? = null
        val intentFilterMap = IntentConfiguration.getMap()

        Log.d(TAG, intentFilterMap.toString())
        if (intentFilterMap.containsKey(action)) {
            dataKey = intentFilterMap[action]
            barcodedata = intent?.extras?.get(dataKey).toString()
        }

        return Data(dataKey, barcodedata?.trim())
    }

    companion object {
        const val TAG = "Receiver"
    }
}
