package android.bluetooth;

import android.app.PendingIntent;
import android.content.AttributionSource;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.ParcelUuid;

import com.android.modules.utils.SynchronousResultReceiver;

import java.util.Map;

public interface IBluetooth extends IInterface {
    void getState(SynchronousResultReceiver receiver);
    void enable(boolean quietMode, AttributionSource attributionSource, SynchronousResultReceiver receiver);
    void disable(AttributionSource attributionSource, SynchronousResultReceiver receiver);
    void getAddress(AttributionSource attributionSource, SynchronousResultReceiver receiver);
    boolean isLogRedactionEnabled();
    void getUuids(AttributionSource attributionSource, SynchronousResultReceiver receiver);
    void setName(String name, AttributionSource attributionSource, SynchronousResultReceiver receiver);
    void getIdentityAddress(String address, SynchronousResultReceiver receiver);
    void getName(AttributionSource attributionSource, SynchronousResultReceiver receiver);
    void getNameLengthForAdvertise(AttributionSource attributionSource, SynchronousResultReceiver receiver);
    void getIoCapability(AttributionSource attributionSource, SynchronousResultReceiver receiver);
    void setIoCapability(int capability, AttributionSource attributionSource, SynchronousResultReceiver receiver);
    void getScanMode(AttributionSource attributionSource, SynchronousResultReceiver receiver);
    void setScanMode(int mode, AttributionSource attributionSource, SynchronousResultReceiver receiver);
    void getDiscoverableTimeout(AttributionSource attributionSource, SynchronousResultReceiver receiver);
    void setDiscoverableTimeout(long timeout, AttributionSource attributionSource, SynchronousResultReceiver receiver);
    void startDiscovery(AttributionSource attributionSource, SynchronousResultReceiver receiver);
    void cancelDiscovery(AttributionSource attributionSource, SynchronousResultReceiver receiver);
    void isDiscovering(AttributionSource attributionSource, SynchronousResultReceiver receiver);


    void getDiscoveryEndMillis(AttributionSource attributionSource, SynchronousResultReceiver receiver);


    void getAdapterConnectionState(SynchronousResultReceiver receiver);


    void getProfileConnectionState(int profile, AttributionSource source, SynchronousResultReceiver receiver);


    void getBondedDevices(AttributionSource attributionSource, SynchronousResultReceiver receiver);
    //void createBond(BluetoothDevice device, int transport, OobData p192Data, OobData p256Data, AttributionSource attributionSource, SynchronousResultReceiver receiver);
    void cancelBondProcess(BluetoothDevice device, AttributionSource attributionSource, SynchronousResultReceiver receiver);
    void removeBond(BluetoothDevice device, AttributionSource attributionSource, SynchronousResultReceiver receiver);
    void getBondState(BluetoothDevice device, AttributionSource attributionSource, SynchronousResultReceiver receiver);


    void isBondingInitiatedLocally(BluetoothDevice device, AttributionSource attributionSource, SynchronousResultReceiver receiver);


    void getSupportedProfiles(AttributionSource attributionSource, SynchronousResultReceiver receiver);
    void getConnectionState(BluetoothDevice device, AttributionSource attributionSource, SynchronousResultReceiver receiver);
    void getConnectionHandle(BluetoothDevice device, int transport, AttributionSource attributionSource, SynchronousResultReceiver receiver);
    void getRemoteName(BluetoothDevice device, AttributionSource attributionSource, SynchronousResultReceiver receiver);
    void getRemoteType(BluetoothDevice device, AttributionSource attributionSource, SynchronousResultReceiver receiver);
    void getRemoteAlias(BluetoothDevice device, AttributionSource attributionSource, SynchronousResultReceiver receiver);
    void setRemoteAlias(BluetoothDevice device, String name, AttributionSource attributionSource, SynchronousResultReceiver receiver);
    void getRemoteClass(BluetoothDevice device, AttributionSource attributionSource, SynchronousResultReceiver receiver);
    void getRemoteEirManuFactureData(BluetoothDevice device, AttributionSource attributionSource, SynchronousResultReceiver receiver);
    void getRemoteUuids(BluetoothDevice device, AttributionSource attributionSource, SynchronousResultReceiver receiver);
    void fetchRemoteUuids(BluetoothDevice device, int transport, AttributionSource attributionSource, SynchronousResultReceiver receiver);
    void sdpSearch(BluetoothDevice device, ParcelUuid uuid, AttributionSource attributionSource, SynchronousResultReceiver receiver);
    void getBatteryLevel(BluetoothDevice device, AttributionSource attributionSource, SynchronousResultReceiver receiver);
    void getMaxConnectedAudioDevices(AttributionSource attributionSource, SynchronousResultReceiver receiver);
    void setPin(BluetoothDevice device, boolean accept, int len, byte[] pinCode, AttributionSource attributionSource, SynchronousResultReceiver receiver);
    void setPasskey(BluetoothDevice device, boolean accept, int len, byte[] passkey, AttributionSource attributionSource, SynchronousResultReceiver receiver);
    void setPairingConfirmation(BluetoothDevice device, boolean accept, AttributionSource attributionSource, SynchronousResultReceiver receiver);

    void getPhonebookAccessPermission(BluetoothDevice device, AttributionSource attributionSource, SynchronousResultReceiver receiver);
    void setSilenceMode(BluetoothDevice device, boolean silence, AttributionSource attributionSource, SynchronousResultReceiver receiver);
    void getSilenceMode(BluetoothDevice device, AttributionSource attributionSource, SynchronousResultReceiver receiver);

    void setPhonebookAccessPermission(BluetoothDevice device, int value, AttributionSource attributionSource, SynchronousResultReceiver receiver);

    void getMessageAccessPermission(BluetoothDevice device, AttributionSource attributionSource, SynchronousResultReceiver receiver);

    void setMessageAccessPermission(BluetoothDevice device, int value, AttributionSource attributionSource, SynchronousResultReceiver receiver);
    void getSimAccessPermission(BluetoothDevice device, AttributionSource attributionSource, SynchronousResultReceiver receiver);
    void setSimAccessPermission(BluetoothDevice device, int value, AttributionSource attributionSource, SynchronousResultReceiver receiver);

    void registerCallback(IBluetoothCallback callback, AttributionSource attributionSource, SynchronousResultReceiver receiver);

    void unregisterCallback(IBluetoothCallback callback, AttributionSource attributionSource, SynchronousResultReceiver receiver);

    void logL2capcocServerConnection(BluetoothDevice device, int port, boolean isSecured, int result, long socketCreationTimeMillis, long socketCreationLatencyMillis, long socketConnectionTimeMillis, long timeoutMillis, SynchronousResultReceiver receiver);

    IBluetoothSocketManager getSocketManager();

    void logL2capcocClientConnection(BluetoothDevice device, int port, boolean isSecured, int result, long socketCreationTimeMillis, long socketCreationLatencyMillis, long socketConnectionTimeMillis, SynchronousResultReceiver receiver);

    void factoryReset(AttributionSource attributionSource, SynchronousResultReceiver receiver);

    void isMultiAdvertisementSupported(SynchronousResultReceiver receiver);

    void isOffloadedFilteringSupported(SynchronousResultReceiver receiver);


    void isOffloadedScanBatchingSupported(SynchronousResultReceiver receiver);


    void isActivityAndEnergyReportingSupported(SynchronousResultReceiver receiver);


    void isLe2MPhySupported(SynchronousResultReceiver receiver);


    void isLeCodedPhySupported(SynchronousResultReceiver receiver);


    void isLeExtendedAdvertisingSupported(SynchronousResultReceiver receiver);


    void isLePeriodicAdvertisingSupported(SynchronousResultReceiver receiver);


    void isLeAudioSupported(SynchronousResultReceiver receiver);


    void isLeAudioBroadcastSourceSupported(SynchronousResultReceiver receiver);


    void isLeAudioBroadcastAssistantSupported(SynchronousResultReceiver receiver);


    void isDistanceMeasurementSupported(AttributionSource attributionSource, SynchronousResultReceiver receiver);


    void getLeMaximumAdvertisingDataLength(SynchronousResultReceiver receiver);

    void getEncKeyMaterialValue(SynchronousResultReceiver receiver);

    void reportActivityInfo(AttributionSource attributionSource, SynchronousResultReceiver receiver);

    void registerMetadataListener(IBluetoothMetadataListener listener, BluetoothDevice device, AttributionSource attributionSource, SynchronousResultReceiver receiver);

    void unregisterMetadataListener(BluetoothDevice device, AttributionSource attributionSource, SynchronousResultReceiver receiver);

    void setMetadata(BluetoothDevice device, int key, byte[] value, AttributionSource attributionSource, SynchronousResultReceiver receiver);

    void getMetadata(BluetoothDevice device, int key, AttributionSource attributionSource, SynchronousResultReceiver receiver);

    void requestActivityInfo(IBluetoothActivityEnergyInfoListener listener, AttributionSource attributionSource);

    void onLeServiceUp(AttributionSource attributionSource, SynchronousResultReceiver receiver);

    void onBrEdrDown(AttributionSource attributionSource, SynchronousResultReceiver receiver);

    void connectAllEnabledProfiles(BluetoothDevice device, AttributionSource attributionSource, SynchronousResultReceiver receiver);

    void disconnectAllEnabledProfiles(BluetoothDevice device, AttributionSource attributionSource, SynchronousResultReceiver receiver);

    void setActiveDevice(BluetoothDevice device, int profiles, AttributionSource attributionSource, SynchronousResultReceiver receiver);

    void getActiveDevices(int profile, AttributionSource attributionSource, SynchronousResultReceiver receiver);

    void getMostRecentlyConnectedDevices(AttributionSource attributionSource, SynchronousResultReceiver receiver);

    void removeActiveDevice(int profiles, AttributionSource attributionSource, SynchronousResultReceiver receiver);

    void registerBluetoothConnectionCallback(IBluetoothConnectionCallback callback, AttributionSource attributionSource, SynchronousResultReceiver receiver);

    void unregisterBluetoothConnectionCallback(IBluetoothConnectionCallback callback, AttributionSource attributionSource, SynchronousResultReceiver receiver);

    void canBondWithoutDialog(BluetoothDevice device, AttributionSource attributionSource, SynchronousResultReceiver receiver);

    void getPackageNameOfBondingApplication(BluetoothDevice device, SynchronousResultReceiver receiver);

    //void generateLocalOobData(int transport, IBluetoothOobDataCallback callback, AttributionSource attributionSource, SynchronousResultReceiver receiver);

    void allowLowLatencyAudio(boolean allowed, BluetoothDevice device, SynchronousResultReceiver receiver);

    void isRequestAudioPolicyAsSinkSupported(BluetoothDevice device, AttributionSource attributionSource, SynchronousResultReceiver receiver);

    void requestAudioPolicyAsSink(BluetoothDevice device, BluetoothSinkAudioPolicy policies, AttributionSource attributionSource, SynchronousResultReceiver receiver);

    void getRequestedAudioPolicyAsSink(BluetoothDevice device, AttributionSource attributionSource, SynchronousResultReceiver receiver);

    void setBondingInitiatedLocally(BluetoothDevice device, boolean localInitiated, AttributionSource attributionSource);

    int getDeviceType(BluetoothDevice device, AttributionSource attributionSource);

    boolean isTwsPlusDevice(BluetoothDevice device, AttributionSource attributionSource);

    String getTwsPlusPeerAddress(BluetoothDevice device, AttributionSource attributionSource);

    void updateQuietModeStatus(boolean quietMode, AttributionSource attributionSource);

    int setSocketOpt(int type, int port, int optionName, byte[] optionVal, int optionLen);

    int getSocketOpt(int type, int port, int optionName, byte[] optionVal);

    boolean isBroadcastActive(AttributionSource attributionSource);

    void startRfcommListener(String name, ParcelUuid uuid, PendingIntent intent, AttributionSource attributionSource, SynchronousResultReceiver receiver);

    void stopRfcommListener(ParcelUuid uuid, AttributionSource attributionSource, SynchronousResultReceiver receiver);

    void retrievePendingSocketForServiceRecord(ParcelUuid uuid, AttributionSource attributionSource, SynchronousResultReceiver receiver);

    void setForegroundUserId(int userId, AttributionSource attributionSource);

    void setPreferredAudioProfiles(BluetoothDevice device, Bundle modeToProfileBundle, AttributionSource source, SynchronousResultReceiver receiver);

    void getPreferredAudioProfiles(BluetoothDevice device, AttributionSource source, SynchronousResultReceiver receiver);

    void registerPreferredAudioProfilesChangedCallback(IBluetoothPreferredAudioProfilesCallback callback, AttributionSource attributionSource, SynchronousResultReceiver receiver);

    void unregisterPreferredAudioProfilesChangedCallback(IBluetoothPreferredAudioProfilesCallback callback, AttributionSource attributionSource, SynchronousResultReceiver receiver);

    void notifyActiveDeviceChangeApplied(BluetoothDevice device, AttributionSource attributionSource, SynchronousResultReceiver receiver);

    void getOffloadedTransportDiscoveryDataScanSupported(AttributionSource attributionSource, SynchronousResultReceiver receiver);

    int getSpecificCodecStatus(BluetoothDevice device, String codec);

    boolean setSpecificCodecStatus(BluetoothDevice device, String codec, int value);

    void setSARLimit(int set);

    Map<String, String> getRestrictControl(int type);

    void doOneTrack(String type);

    void registerBluetoothQualityReportReadyCallback(IBluetoothQualityReportReadyCallback callback, AttributionSource attributionSource, SynchronousResultReceiver receiver);

    void unregisterBluetoothQualityReportReadyCallback(IBluetoothQualityReportReadyCallback callback, AttributionSource attributionSource, SynchronousResultReceiver receiver);

    abstract class Stub extends Binder implements IBluetooth {
        public static IBluetooth asInterface(IBinder obj) {
            throw new UnsupportedOperationException();
        }
    }

}
