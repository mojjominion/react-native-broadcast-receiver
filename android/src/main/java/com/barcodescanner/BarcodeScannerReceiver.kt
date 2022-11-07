import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.barcodescanner.BarcodeScannerModule
import com.barcodescanner.IntentFilterMap
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.WritableMap

class BarcodeScannerReceiver : BroadcastReceiver() {
  private var intentFilterMap = IntentFilterMap().getMap()

  override fun onReceive(context: Context?, intent: Intent?) {
    var data = getBarcode(intent)
    if (data != null && data.isNotEmpty()) {
      sendBarCode(data)
    }
  }

  private fun getBarcode(intent: Intent?): String? {
    var action = intent?.action
    var bundle = intent?.extras
    var barcodedata: String? = null

    if (intentFilterMap.containsKey(action)) {
      var dataKey = intentFilterMap[action]
      barcodedata = bundle?.get(dataKey) as String?
    }

    return barcodedata?.trim()
  }

  private fun sendBarCode(scannedData: String) {
    val params: WritableMap = Arguments.createMap();
    params.putString("barcode", scannedData);
    BarcodeScannerModule.sendEvent("BarcodeEvent", params);
  }

}
