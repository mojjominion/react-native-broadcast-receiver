package com.broadcastreceiver

import android.annotation.SuppressLint
import android.content.IntentFilter
import android.provider.Settings
import android.util.Log
import android.os.Build;
import android.content.Context;
import com.facebook.react.BuildConfig
import com.facebook.react.bridge.*
import com.facebook.react.modules.core.DeviceEventManagerModule


class BroadcastReceiverImpl(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext){
  private var broadcastReceiver = BroadcastReceiverReceiver()

  init {
    cReactContext = reactContext
  }

  override fun getName(): String {
    return Constants.ModuleName
  }

  private fun getReactContext(): ReactApplicationContext {
    return cReactContext
  }

  // Example method
  // See https://reactnative.dev/docs/native-modules-android
  fun multiply(a: Double, b: Double, promise: Promise) {
    promise.resolve(a * b)
  }

  fun setIntentConfig(args: ReadableArray, promise: Promise) {
    if (BuildConfig.DEBUG) {
      Log.d(name, args.toString())
      Log.d(name, "addActions: register receiver")
    }
    val argsArray = args.toArrayList().filterIsInstance<List<String>>()
    val data = argsArray.map {
      Pair(it[0], it[1])
    }
    IntentConfiguration.addToMap(data)
    registerBroadcastReceiver()
    promise.resolve(true)
  }

  @SuppressLint("HardwareIds")
  fun getPhoneID(promise: Promise) {
    try {
      val secureId: String = Settings.Secure.getString(
        getReactContext().contentResolver, Settings.Secure.ANDROID_ID
      )
      promise.resolve(Arguments.fromJavaArgs(arrayOf(secureId)))
    } catch (e: Exception) {
      promise.reject(RuntimeException("Could not get unique Id"))
    }
  }


  companion object {
    lateinit var cReactContext: ReactApplicationContext

    fun sendEvent(eventName: String, params: WritableMap) {
      cReactContext.emitDeviceEvent(eventName, params)
    }
  }

  fun registerBroadcastReceiver() {
    if (BuildConfig.DEBUG) Log.d(name, "register receiver")

    val filter = IntentFilter()
    val filterMap = IntentConfiguration.getMap()

    filterMap.forEach { filter.addAction(it.key) }
    filter.addCategory("android.intent.category.DEFAULT")

    val context = getReactContext()
    if (Build.VERSION.SDK_INT >= 34 && context.applicationInfo.targetSdkVersion >= 34) {
      context.registerReceiver(broadcastReceiver, filter, Context.RECEIVER_EXPORTED);
    } else {
      context.registerReceiver(broadcastReceiver, filter);
    }
  }

  fun unregisterBroadcastReceiver() {
    getReactContext().unregisterReceiver(broadcastReceiver)
  }
}
