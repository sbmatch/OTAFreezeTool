package android.bluetooth.le;

import android.os.IInterface;
import java.util.List;

public interface IScannerCallback extends IInterface {
    void onScannerRegistered(int status, int scannerId);
    void onScanResult(ScanResult scanResult);
    void onBatchScanResults(List<ScanResult> batchResults);
    void onFoundOrLost(boolean onFound, ScanResult scanResult);
    void onScanManagerErrorCallback(int errorCode);

}
