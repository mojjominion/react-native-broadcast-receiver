# barcode-broadcast-receiver

Native module for harware based scanners

## Installation

```sh
npm install barcode-broadcast-receiver
```

### Usage

#### 1. Listen to broadcast events

```js
import { BroadcastReceiver } from 'barcode-broadcast-receiver';

React.useEffect(() => {
  const sub = BroadcastReceiver.addEventListner((d) =>
    setScanned((x) => [...x, d.data])
  );
  return () => sub.remove();
}, []);
```

#### 2. Listen to broadcast events `(with custom intent actions and extraDataKey)`

```js
import { BroadcastReceiver } from 'barcode-broadcast-receiver';

BroadcastReceiver.setIntentActionConfig([
  { action: 'com.zzzz.yyyy.action', datakey: '<data_key>' },
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
