import { receiver } from './BroadcastReceiver';
import { type Event as BroadcastEventType, Constants } from './config';

const BroadcastEvent = Constants.BROADCAST_EVENT_NAME;
const ModuleName = Constants.MODULE_NAME;
const DataKey = Constants.DATA_PROP;

export type { BroadcastEventCallback, BroadcastEventData } from './types';
export { receiver as BroadcastReceiver, BroadcastEvent, ModuleName, DataKey };
export type { BroadcastEventType };
