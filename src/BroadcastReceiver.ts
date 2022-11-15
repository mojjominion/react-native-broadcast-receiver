import { NativeEventEmitter, NativeModules } from 'react-native';
import { Constants, defaultConfig } from './config';
import { LINKING_ERROR } from './error';
import type * as t from './types';
import { transformConfig } from './util';

const nativeEventEmitter = new NativeEventEmitter();
class BroadcastReceiver implements t.BroadcastReceiverInterface {
  private _nativeModule: t.NativeModuleType;
  constructor(args: t.IntentActionConfig = defaultConfig) {
    this._nativeModule = this.getNativeModule();
    this.setIntentActionConfig(args);
  }

  private getNativeModule = (): t.NativeModuleType => {
    return Constants.MODULE_NAME in NativeModules
      ? NativeModules[Constants.MODULE_NAME]
      : new Proxy(
          {},
          {
            get() {
              throw new Error(LINKING_ERROR);
            },
          }
        );
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
    return this._nativeModule.setIntentConfig(transformConfig(args));
  }
  getPhoneID() {
    return this._nativeModule.getPhoneID();
  }

  // Start Region :: Native modules methods
}
const receiver = new BroadcastReceiver();
export { receiver };
