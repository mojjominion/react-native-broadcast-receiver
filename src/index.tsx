import { BarcodeScanner } from './barcode-scanner';

export function multiply(a: number, b: number): Promise<number> {
  return BarcodeScanner.multiply(a, b);
}
export { BarcodeScanner };
