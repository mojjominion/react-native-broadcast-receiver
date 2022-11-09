import { BroadcastReceiver } from './BroadcastReceiver';
import { Constants } from './config';

const receiver = new BroadcastReceiver();

const ModuleName = Constants.MODULE_NAME;
const BroadcastEvent = Constants.BROADCAST_EVENT_NAME;
const DataKey = Constants.DATA_PROP;

export { receiver as BroadcastReceiver, ModuleName, BroadcastEvent, DataKey };
