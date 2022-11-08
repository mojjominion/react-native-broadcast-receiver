package com.barcodescanner

import BarcodeScannerReceiver
import android.annotation.SuppressLint
import android.content.IntentFilter
import android.provider.Settings
import android.util.Log
import com.facebook.react.bridge.*
import com.facebook.react.modules.core.DeviceEventManagerModule


class BarcodeScannerModule(reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext), LifecycleEventListener {
  var intentFilterMap = IntentFilterMap().getMap()
  var broadcastReceiver = BarcodeScannerReceiver()

  init {
    cReactContext = reactContext
    cReactContext.addLifecycleEventListener(
      this
    )
  }

  override fun getName(): String {
    return NAME
  }

  fun getReactContext(): ReactApplicationContext {
    return cReactContext
  }

  // Example method
  // See https://reactnative.dev/docs/native-modules-android
  @ReactMethod
  fun multiply(a: Double, b: Double, promise: Promise) {
    promise.resolve(a * b)
  }

  @SuppressLint("HardwareIds")
  @ReactMethod
  fun getPhoneID(promise: Promise) {
    try {
      val secureId: String =
        Settings.Secure.getString(
          getReactContext().getContentResolver(),
          Settings.Secure.ANDROID_ID
        )
      promise.resolve(Arguments.fromJavaArgs(arrayOf(secureId)))
    } catch (e: Exception) {
      promise.reject(RuntimeException("Could not get unique Id"))
    }
  }


  companion object {
    lateinit var cReactContext: ReactApplicationContext
    const val NAME = "BarcodeScanner"

    fun sendEvent(eventName: String, params: WritableMap) {
      cReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
        .emit(eventName, params)
    }
  }

  private fun registerBroadcastReceiver() {
    var filter = IntentFilter()
    for (entry in intentFilterMap) {
      filter.addAction(entry.key)
    }
    println(filter)
    filter.addCategory("android.intent.category.DEFAULT");
    getReactContext().registerReceiver(broadcastReceiver, filter)

  }

  fun unregisterBroadcastReceiver() {
    getReactContext().unregisterReceiver(broadcastReceiver)
  }

  //region: LifecycleEventListener
  override fun onHostResume() {
    if (BuildConfig.DEBUG) Log.d(NAME, "onHostResume: register Application receivers")
    registerBroadcastReceiver()
  }

  override fun onHostPause() {
    if (BuildConfig.DEBUG) Log.d(NAME, "onHostPause: unregister receivers")
    unregisterBroadcastReceiver()
  }

  override fun onHostDestroy() {
    if (BuildConfig.DEBUG) Log.d(
      NAME,
      "onHostDestroy: unregister receivers"
    )
    unregisterBroadcastReceiver()
  }
  //endregion

}
