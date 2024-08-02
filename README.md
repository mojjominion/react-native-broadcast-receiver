# react-native-broadcast-receiver

:sparkles: :rocket: Supports React-Native Bridgeless mode

This package allows you to register custom intent to listen in react native applications.
The idea here is to reduce the number app builds that are required if you change the native code in react native. With this you can inject custom intent config at runtime.


## Installation

### npm
```sh
npm install react-native-broadcast-receiver
```

yarn

```sh
yarn add react-native-broadcast-receiver
```

### Usage

#### Listen to broadcast events with custom intent actions and extraDataKey

```js
import { BroadcastReceiver } from 'react-native-broadcast-receiver';

BroadcastReceiver.setIntentActionConfig([
  { action: 'com.zzzz.yyyy.action1', datakey: '<data_key1>' },
  { action: 'com.zzzz.yyyy.action2', datakey: '<data_key2>' },
  { action: 'com.zzzz.yyyy.action3', datakey: '<data_key3>' },
]);

React.useEffect(() => {
  const sub = BroadcastReceiver.addEventListner((d) =>
    setScanned((x) => [...x, d.data])
  );
  return () => sub.remove();
}, []);
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)
