import { NativeModules, Platform, NativeEventEmitter } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-barcode-scanner' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

const BarcodeScanner = NativeModules.BarcodeScanner
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
export function multiply(a: number, b: number): Promise<number> {
  return BarcodeScanner.multiply(a, b);
}

interface BarcodeEventData {
  barcode: string;
}
export function addEventListner(listener: (event: BarcodeEventData) => void) {
  return nativeEventEmitter.addListener('BarcodeEvent', listener);
}
