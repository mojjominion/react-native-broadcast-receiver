export interface BarcodeScannerNativeModuleType {
  /**
   *
   * @param a number
   * @param b  number
   */
  multiply(a: number, b: number): Promise<number>;
  getPhoneID(): Promise<string[]>;
}

export interface BarcodeEventData {
  /**
   * Scanned barcode data from harware scanners
   */
  barcode: string;
}
