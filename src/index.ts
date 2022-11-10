import { receiver } from './BroadcastReceiver';
import { Event as BroadcastEventType, Constants } from './config';

const BroadcastEvent = Constants.BROADCAST_EVENT_NAME;
const ModuleName = Constants.MODULE_NAME;
const DataKey = Constants.DATA_PROP;

export { BroadcastEventCallback, BroadcastEventData } from './types';
export {
  receiver as BroadcastReceiver,
  BroadcastEventType,
  BroadcastEvent,
  ModuleName,
  DataKey,
};
