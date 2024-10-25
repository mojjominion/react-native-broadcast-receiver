import { NativeEventEmitter } from 'react-native';
import { Constants, defaultConfig } from './config';
import type * as t from './types';
import { transformConfig } from './util';
import RNBroadcastReceiver from './js/NativeBroadcastReceiver';

const nativeEventEmitter = new NativeEventEmitter();
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
    return (
      this._nativeModule?.setIntentConfig(transformConfig(args)) ??
      Promise.resolve(false)
    );
  }
  getPhoneID() {
    return this._nativeModule?.getPhoneID() ?? Promise.resolve(['']);
  }

  // Start Region :: Native modules methods
}
const receiver = new BroadcastReceiver([
  { action: 'com.string.intent', datakey: 'data' },
  { action: 'com.record.intent', datakey: '' },
]);
export { receiver };
