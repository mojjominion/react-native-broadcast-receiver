package com.broadcastreceiver

import android.util.Log
import com.facebook.react.BuildConfig
import com.facebook.react.bridge.*

class BroadcastReceiverModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext), LifecycleEventListener {
  private var implementtation = BroadcastReceiverImpl(reactContext)
  private var name = implementtation.name

  init {
    reactContext.addLifecycleEventListener(
      this
    )
  }

  @ReactMethod
  override fun getName(): String {
    return implementtation.name
  }

  // Example method
  // See https://reactnative.dev/docs/native-modules-android
  @ReactMethod
  fun multiply(a: Double, b: Double, promise: Promise) {
    implementtation.multiply(a, b, promise)
  }

  @ReactMethod
  fun setIntentConfig(args: ReadableArray, promise: Promise) {
    implementtation.setIntentConfig(args, promise)
  }

  @ReactMethod
  fun getPhoneID(promise: Promise) {
    implementtation.getPhoneID(promise)
  }

  //region: LifecycleEventListener
  override fun onHostResume() {
    if (BuildConfig.DEBUG) Log.d(name, "onHostResume: register Application receivers")
    implementtation.registerBroadcastReceiver()
  }

  override fun onHostPause() {
    if (BuildConfig.DEBUG) Log.d(name, "onHostPause: unregister receivers")
    implementtation.unregisterBroadcastReceiver()
  }

  override fun onHostDestroy() {
    if (BuildConfig.DEBUG) Log.d(name, "onHostDestroy: Destroy host")
  }
  //endregion
}
