package android.os;

import android.content.pm.UserInfo;

public interface IUserManager extends IInterface {
    void setUserRestriction(String key, boolean value, int userHandle);
    Bundle getApplicationRestrictionsForUser(String packageName, int userId);
    void setApplicationRestrictions(String packageName, Bundle restrictions, int userId);
    UserInfo getUserInfo(int userId);

    abstract class Stub extends Binder implements IUserManager {
        public static IUserManager asInterface(IBinder obj) {
            throw new UnsupportedOperationException();
        }
    }
}
