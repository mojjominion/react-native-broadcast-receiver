import * as React from 'react';

import { StyleSheet, View, Text } from 'react-native';
import { addEventListner, multiply } from 'react-native-barcode-scanner';

export default function App() {
  const [result, setResult] = React.useState<number | undefined>();
  const [scanned, setScanned] = React.useState<string[]>(['']);

  React.useEffect(() => {
    multiply(3, 7).then(setResult);
  }, []);

  React.useEffect(() => {
    const sub = addEventListner((d) => setScanned((x) => [...x, d.barcode]));
    return () => sub.remove();
  }, []);

  return (
    <View style={styles.container}>
      <Text>Result: {result}</Text>
      {scanned.map((t, i) => (
        <Text key={i}>Scanned: {t}</Text>
      ))}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
