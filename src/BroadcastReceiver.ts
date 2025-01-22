import { NativeEventEmitter, NativeModule, Platform } from 'react-native';
import { Constants, defaultConfig } from './config';
import type * as t from './types';
import { transformConfig } from './util';
import RNBroadcastReceiver from './js/NativeBroadcastReceiver';
import { LogBox } from 'react-native';

// Ignore log notification by message
LogBox.ignoreLogs([
  '`new NativeEventEmitter()` was called with a non-null argument without the required `addListener` method.',
  '`new NativeEventEmitter()` was called with a non-null argument without the required `removeListeners` method.',
]);

const nativeEventEmitter = new NativeEventEmitter(
  RNBroadcastReceiver as unknown as NativeModule
);
class BroadcastReceiver implements t.BroadcastReceiverInterface {
  private _nativeModule: t.NativeModuleType | undefined;
  constructor(args: t.IntentActionConfig = defaultConfig) {
    this._nativeModule = this.getNativeModule();
    this.setIntentActionConfig(args);
  }

  private getNativeModule = (): t.NativeModuleType | undefined => {
    return RNBroadcastReceiver;
  };

  addEventListner(listener: t.BroadcastEventCallback) {
    return nativeEventEmitter.addListener(
      Constants.BROADCAST_EVENT_NAME,
      listener
    );
  }

  sendEvent(payload: unknown) {
    return nativeEventEmitter.emit(Constants.BROADCAST_EVENT_NAME, {
      [Constants.DATA_PROP]: payload,
    });
  }

  // Start Region :: Native modules methods
  setIntentActionConfig(args: t.IntentActionConfig) {
    if (Platform.OS === 'ios') return Promise.resolve(false);
    return (
      this._nativeModule?.setIntentConfig(transformConfig(args)) ??
      Promise.resolve(false)
    );
  }
  getPhoneID() {
    if (Platform.OS === 'ios') return Promise.resolve(['']);
    return this._nativeModule?.getPhoneID() ?? Promise.resolve(['']);
  }

  // Start Region :: Native modules methods
}
const receiver = new BroadcastReceiver([
  { action: 'com.string.intent', datakey: 'data' },
  { action: 'com.record.intent', datakey: '' },
]);
export { receiver };
