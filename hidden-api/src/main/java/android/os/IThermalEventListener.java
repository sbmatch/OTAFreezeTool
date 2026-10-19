package android.os;

public interface IThermalEventListener extends IInterface {
    void notifyThrottling(Temperature temperature);

    abstract class Stub extends Binder implements IThermalEventListener {
        @Override
        public IBinder asBinder() {
            throw new RuntimeException("Stub!");
        }
    }
}
