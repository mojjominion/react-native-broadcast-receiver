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

type Event = 'BROADCAST_EVENT';
const BROADCAST_EVENT_NAME: Event = 'BROADCAST_EVENT';

const Constants = {
  MODULE_NAME: 'RNBroadcastReceiver' as const,
  DATA_PROP: 'data' as const,
  BROADCAST_EVENT_NAME,
};

export { defaultConfig, Constants };
export type { Event };
