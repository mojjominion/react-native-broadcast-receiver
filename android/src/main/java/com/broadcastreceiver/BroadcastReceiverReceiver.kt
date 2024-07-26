package com.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.facebook.react.bridge.Arguments

class BroadcastReceiverReceiver : BroadcastReceiver() {
  override fun onReceive(context: Context?, intent: Intent?) {
    val data = getDataFromIntent(intent)
    if (!data.isNullOrEmpty()) {
      val params = Arguments.createMap()
      params.putString(Constants.DataProp, data)
      BroadcastReceiverImpl.sendEvent(Constants.BroadcastEventName, params)
    }
  }

  private fun getDataFromIntent(intent: Intent?): String? {
    val action = intent?.action
    var barcodedata: String? = null
    val intentFilterMap = IntentConfiguration.getMap()

    Log.d(TAG, intentFilterMap.toString())
    if (intentFilterMap.containsKey(action)) {
      val dataKey = intentFilterMap[action]
      barcodedata = intent?.extras?.get(dataKey).toString()
    }

    return barcodedata?.trim()
  }

  companion object {
    const val TAG = "Receiver"
  }
}
