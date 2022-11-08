import { NativeEventEmitter, NativeModules, Platform } from 'react-native';
import type * as types from './types';

const LINKING_ERROR =
  `The package 'barcode-broadcast-receiver' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

const BarcodeScannerNativeModule: types.BarcodeScannerNativeModuleType =
  NativeModules.BarcodeScanner
    ? NativeModules.BarcodeScanner
    : new Proxy(
        {},
        {
          get() {
            throw new Error(LINKING_ERROR);
          },
        }
      );

const nativeEventEmitter = new NativeEventEmitter();
export function addEventListner(listener: (d: types.BarcodeEventData) => void) {
  return nativeEventEmitter.addListener('BarcodeEvent', listener);
}
export const BarcodeScanner = {
  ...BarcodeScannerNativeModule,
  addEventListner,
};
