package com.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.WritableMap

class BroadcastReceiverReceiver : BroadcastReceiver() {
  override fun onReceive(context: Context?, intent: Intent?) {
    // Check if the intent is not null
    if (intent != null) {
      // Get data from intent
      val data = getDataFromIntent(intent)
      if (data != null) {
        val params = Arguments.createMap()
        params.putString(Constants.Action, intent.action)

        when (data) {
          is ResultData.StringData -> {
            // Handle the String case
            params.putString(Constants.DataProp, data.value)
          }

          is ResultData.MapData -> {
            // Handle the WritableMap case
            params.putMap(Constants.DataProp, data.map)
          }
        }

        BroadcastReceiverImpl.sendEvent(Constants.BroadcastEventName, params)
      }
    }
  }

  sealed class ResultData {
    data class StringData(val value: String?) : ResultData()
    data class MapData(val map: WritableMap) : ResultData()
  }

  private fun getDataFromIntent(intent: Intent): ResultData? {
    val action = intent.action
    val intentFilterMap = IntentConfiguration.getMap()

    Log.d(TAG, intentFilterMap.toString())
    if (intentFilterMap.containsKey(action)) {
      val dataKey = intentFilterMap[action]

      // If a data key is given, only the corresponding string will be returned; otherwise, the entire object will be returned.
      if (!dataKey.isNullOrEmpty() && intent.extras?.containsKey(dataKey) == true) {
        val barcodeData = intent.extras?.get(dataKey)?.toString()?.trim() ?: ""
        return ResultData.StringData(barcodeData)
      } else {
        // Create a WritableMap if there are no specific string requirements
        val dataMap = Arguments.createMap()
        intent.extras?.let { extras ->
          for (key in extras.keySet()) {
            dataMap.putString(key, extras.get(key).toString())
          }
        }
        return ResultData.MapData(dataMap)
      }
    }

    return null
  }

  companion object {
    const val TAG = "Receiver"
  }
}
