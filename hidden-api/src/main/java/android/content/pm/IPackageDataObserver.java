package android.content.pm;

import android.os.IInterface;

public interface IPackageDataObserver extends IInterface {
    void onRemoveCompleted(String packageName, boolean succeeded);
}
