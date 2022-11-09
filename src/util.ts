import { LINKING_ERROR } from './error';
import type { IntentActionConfig, IntentActionConfigTuple } from './types';

function transformConfig(config: IntentActionConfig): IntentActionConfigTuple {
  return config.map((x) => [x.action, x.datakey]);
}

const proxyModule = new Proxy(
  {},
  {
    get() {
      throw new Error(LINKING_ERROR);
    },
  }
);

export { proxyModule, transformConfig };
