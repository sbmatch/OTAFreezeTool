package android.bluetooth;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public final class BluetoothQualityReport implements Parcelable {

    public static final Parcelable.Creator<BluetoothQualityReport> CREATOR = new Parcelable.Creator<BluetoothQualityReport>() { // from class: android.bluetooth.BluetoothQualityReport.1
        @Override
        public BluetoothQualityReport createFromParcel(Parcel in) {
            throw new RuntimeException("Stub!");
        }

        @Override
        public BluetoothQualityReport[] newArray(int size) {
            throw new RuntimeException("Stub!");
        }
    };

    public static final int QUALITY_REPORT_ID_A2DP_CHOPPY = 3;
    public static final int QUALITY_REPORT_ID_APPROACH_LSTO = 2;
    public static final int QUALITY_REPORT_ID_CONN_FAIL = 8;
    public static final int QUALITY_REPORT_ID_MONITOR = 1;
    public static final int QUALITY_REPORT_ID_SCO_CHOPPY = 4;
    private static final String TAG = "BluetoothQualityReport";
    private String mAddr;
    private BluetoothClass mBluetoothClass;
    //private BqrCommon mBqrCommon;
    //private BqrConnectFail mBqrConnectFail;
    //private BqrVsA2dpChoppy mBqrVsA2dpChoppy;
    //private BqrVsLsto mBqrVsLsto;
    //private BqrVsScoChoppy mBqrVsScoChoppy;
    private int mLmpSubVer;
    private int mLmpVer;
    private int mManufacturerId;
    private String mName;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        throw new RuntimeException("Stub!");
    }
}
