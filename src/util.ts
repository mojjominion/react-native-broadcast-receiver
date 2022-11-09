import type { IntentActionConfig, IntentActionConfigTuple } from './types';

function transformConfig(config: IntentActionConfig): IntentActionConfigTuple {
  return config.map((x) => [x.action, x.datakey]);
}

export { transformConfig };
