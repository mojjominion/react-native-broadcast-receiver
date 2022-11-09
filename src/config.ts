import type { IntentActionConfig } from './types';

const defaultConfig: IntentActionConfig = [
  //   {
  //     action: 'com.cipherlab.barcodebaseapi.PASS_DATA_2_APP',
  //     datakey: 'Decoder_Data',
  //   },
  //   {
  //     action: 'com.barcodeservice.broadcast.string',
  //     datakey: 'barcodedata',
  //   },
  //   {
  //     action: 'BROADCAST_BARCODE',
  //     datakey: 'BROADCAST_BARCODE_STRING',
  //   },
  //   {
  //     action: 'zebra.barcode',
  //     datakey: 'com.motorolasolutions.emdk.datawedge.data_string',
  //   },
  //   {
  //     action: 'com.honeywell.scantointent.intent.action.BARCODE',
  //     datakey: 'data',
  //   },
];

const Constants = {
  BROADCAST_EVENT_NAME: 'BROADCAST_EVENT',
  MODULE_NAME: 'BARCODE_BROADCAST_RECEIVER',
  DATA_PROP: 'data',
};

export { defaultConfig, Constants };
