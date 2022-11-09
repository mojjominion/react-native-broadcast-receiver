package com.barcodescanner

object IntentConfiguration {
    private val filterMap: MutableMap<String, String> = mutableMapOf(
        "com.cipherlab.barcodebaseapi.PASS_DATA_2_APP" to "Decoder_Data",
        "com.barcodeservice.broadcast.string" to "barcodedata",
        "BROADCAST_BARCODE" to "BROADCAST_BARCODE_STRING",
        "zebra.barcode" to "com.motorolasolutions.emdk.datawedge.data_string",
        "com.honeywell.scantointent.intent.action.BARCODE" to "data"
    )

    fun getMap(): MutableMap<String, String> {
        return filterMap
    }

    fun addToMap(args: List<Pair<String, String>>) {
        args.forEach {
            filterMap[it.first] = it.second
        }
    }
}
