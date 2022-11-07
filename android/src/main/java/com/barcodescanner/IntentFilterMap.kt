package com.barcodescanner

class  IntentFilterMap {
  private val filterMap = mapOf(
    "com.cipherlab.barcodebaseapi.PASS_DATA_2_APP" to "Decoder_Data",
    "com.barcodeservice.broadcast.string" to "barcodedata",
    "BROADCAST_BARCODE" to "BROADCAST_BARCODE_STRING",
    "zebra.barcode" to "com.motorolasolutions.emdk.datawedge.data_string",
    "com.honeywell.scantointent.intent.action.BARCODE" to "data"
  )
  fun getMap(): Map<String, String> {
    return filterMap
  }
}
