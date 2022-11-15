package com.broadcastreceiver

import android.annotation.SuppressLint
import android.content.IntentFilter
import android.provider.Settings
import android.util.Log
import com.facebook.react.BuildConfig
import com.facebook.react.bridge.*
import com.facebook.react.modules.core.DeviceEventManagerModule


class BroadcastReceiverModule(reactContext: ReactApplicationContext) :
    ReactContextBaseJavaModule(reactContext), LifecycleEventListener {
    private var broadcastReceiver = BroadcastReceiverReceiver()

    init {
        cReactContext = reactContext
        cReactContext.addLifecycleEventListener(
            this
        )
    }

    override fun getName(): String {
        return Constants.ModuleName
    }

    private fun getReactContext(): ReactApplicationContext {
        return cReactContext
    }

    // Example method
    // See https://reactnative.dev/docs/native-modules-android
    @ReactMethod
    fun multiply(a: Double, b: Double, promise: Promise) {
        promise.resolve(a * b)
    }

    @ReactMethod
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
    @ReactMethod
    fun getPhoneID(promise: Promise) {
        try {
            val secureId: String =
                Settings.Secure.getString(
                    getReactContext().contentResolver,
                    Settings.Secure.ANDROID_ID
                )
            promise.resolve(Arguments.fromJavaArgs(arrayOf(secureId)))
        } catch (e: Exception) {
            promise.reject(RuntimeException("Could not get unique Id"))
        }
    }


    companion object {
        lateinit var cReactContext: ReactApplicationContext

        fun sendEvent(eventName: String, params: WritableMap) {
            cReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
                .emit(eventName, params)
        }
    }

    private fun registerBroadcastReceiver() {
        if (BuildConfig.DEBUG)
            Log.d(name, "register receiver")

        val filter = IntentFilter()
        val filterMap = IntentConfiguration.getMap()

        filterMap.forEach { filter.addAction(it.key) }
        filter.addCategory("android.intent.category.DEFAULT")

        getReactContext().registerReceiver(broadcastReceiver, filter)
    }

    private fun unregisterBroadcastReceiver() {
        getReactContext().unregisterReceiver(broadcastReceiver)
    }

    //region: LifecycleEventListener
    override fun onHostResume() {
        if (BuildConfig.DEBUG) Log.d(name, "onHostResume: register Application receivers")
        registerBroadcastReceiver()
    }

    override fun onHostPause() {
        if (BuildConfig.DEBUG) Log.d(name, "onHostPause: unregister receivers")
        unregisterBroadcastReceiver()
    }

    override fun onHostDestroy() {
        if (BuildConfig.DEBUG) Log.d(name, "onHostDestroy: Destroy host")
    }
    //endregion

}
