import type { TurboModule } from 'react-native/Libraries/TurboModule/RCTExport';
import { TurboModuleRegistry } from 'react-native';

export interface Spec extends TurboModule {
  getPhoneID(): Promise<Array<string>>;
  setIntentConfig(args: Array<Array<string>>): Promise<boolean>;
}

export default TurboModuleRegistry.get<Spec>('RNBroadcastReceiver') as
  | Spec
  | undefined;
