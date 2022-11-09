import type { EmitterSubscription } from 'react-native';

type IntentActionConfigTuple = [string, string][];
type IntentActionConfig = { action: string; datakey: string }[];

interface BarcodeEventData {
  /**
   * Scanned barcode data from harware scanners
   */
  data: string;
}

interface NativeModuleType {
  getPhoneID(): Promise<string[]>;
  setIntentConfig(args: IntentActionConfigTuple): Promise<boolean>;
}

interface BroadcastReceiverInterface {
  /**
   * Get device harware id
   */
  getPhoneID(): Promise<string[]>;
  setIntentActionConfig(args: IntentActionConfig): Promise<boolean>;
  /**
   *
   * @param args ``` [intentAction: string, intentExtrasDataKey: string][] ```
   * @description
   *  - `intentAction` is the actions name that'd be registered for `android.BroadcastReceiver`
   *  - `intentExtrasDataKey` will be used to extract data from the intent
   */
  addEventListner(cb: (d: BarcodeEventData) => void): EmitterSubscription;
}

export {
  NativeModuleType as NativeModuleType,
  BroadcastReceiverInterface,
  IntentActionConfigTuple,
  IntentActionConfig,
  BarcodeEventData,
};
