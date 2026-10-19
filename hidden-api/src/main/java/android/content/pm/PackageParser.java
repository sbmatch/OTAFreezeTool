package android.content.pm;

import android.content.ComponentName;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import java.io.File;
import java.util.ArrayList;

public class PackageParser {

    public PackageParser() {
        throw new RuntimeException("Stub!");
    }

    public Package parsePackage(File packageFile, int flags) throws PackageParserException {
        throw new RuntimeException("Stub!");
    }

    public Package parsePackage(File packageFile, int flags, boolean useCaches)
            throws PackageParserException {
        throw new RuntimeException("Stub!");
    }


    public static class PackageParserException extends Exception {

    }

    public interface Callback {
        boolean hasFeature(String str);
    }

    public void setCallback(Callback cb) {
        throw new RuntimeException("Stub!");
    }

    public static final class CallbackImpl implements Callback {

        public CallbackImpl(PackageManager pm) {
            throw new RuntimeException("Stub!");
        }

        @Override
        public boolean hasFeature(String feature) {
            throw new RuntimeException("Stub!");
        }
    }

    public static abstract class IntentInfo extends IntentFilter {
    }

    public static final class ActivityIntentInfo extends IntentInfo {
        public Activity activity;
    }

    public final static class ServiceIntentInfo extends IntentInfo {

        public Service service;
        public ServiceIntentInfo(Service _service) {
            throw new RuntimeException("Stub!");
        }
        public String toString() {
            throw new RuntimeException("Stub!");
        }
        public ServiceIntentInfo(Parcel in) {
            throw new RuntimeException("Stub!");
        }
    }

    public static abstract class Component<II extends IntentInfo> {
        public final String className;
        public final ArrayList<II> intents;
        public Package owner;
        public Bundle metaData;
        ComponentName componentName;

        public Component(Package owner, ArrayList<II> intents, String className){
            throw new RuntimeException("Stub!");
        }

        public Component(Package owner) {
            throw new RuntimeException("Stub!");
        }

        public Component(Component<II> clone) {
            throw new RuntimeException("Stub!");
        }

        protected Component(Parcel in) {
            throw new RuntimeException("Stub!");
        }
    }

    public static final class ProviderIntentInfo extends IntentInfo {

        public Provider provider;
        public ProviderIntentInfo(Provider provider) {
            throw new RuntimeException("Stub!");
        }
        public String toString() {
            throw new RuntimeException("Stub!");
        }

    }

    public final static class Provider extends Component<ProviderIntentInfo> implements Parcelable {

        public final ProviderInfo info;
        public boolean syncable;

        public Provider(Provider existingProvider) {
            super(existingProvider);
            throw new RuntimeException("Stub!");
        }
        public void setPackageName(String packageName) {
            throw new RuntimeException("Stub!");
        }
        public String toString() {
            throw new RuntimeException("Stub!");
        }
        @Override
        public int describeContents() {
            return 0;
        }
        @Override
        public void writeToParcel(Parcel dest, int flags) {
            throw new RuntimeException("Stub!");
        }

        public static final Creator<Provider> CREATOR = new Parcelable.Creator<Provider>() {
            public Provider createFromParcel(Parcel in) {
                throw new RuntimeException("Stub!");
            }
            public Provider[] newArray(int size) {
                throw new RuntimeException("Stub!");
            }
        };
    }



    public static final class Activity extends Component<ActivityIntentInfo> implements Parcelable {

        public final ActivityInfo info;

        public static final Creator<Activity> CREATOR = new Creator<Activity>() {
            @Override
            public Activity createFromParcel(Parcel in) {
                throw new RuntimeException("Stub!");
            }

            @Override
            public Activity[] newArray(int size) {
                throw new RuntimeException("Stub!");
            }
        };

        Activity(Package owner, ArrayList<ActivityIntentInfo> intents, String className) {
            super(owner, intents, className);
            throw new RuntimeException("Stub!");
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            throw new RuntimeException("Stub!");
        }
    }

    public final static class Package implements Parcelable {
        public ApplicationInfo applicationInfo = new ApplicationInfo();
        
        public final ArrayList<Permission> permissions = new ArrayList<>(0);
        public final ArrayList<PermissionGroup> permissionGroups = new ArrayList<>(0);
        public final ArrayList<Activity> activities = new ArrayList<>(0);
        public final ArrayList<Activity> receivers = new ArrayList<>(0);
        public final ArrayList<Provider> providers = new ArrayList<>(0);
        
        public final ArrayList<Service> services = new ArrayList<Service>(0);
        public final ArrayList<String> requestedPermissions = new ArrayList<String>();
        /** Permissions requested but not in the manifest. */
        public final ArrayList<String> implicitPermissions = new ArrayList<>();
        
        public ArrayList<String> protectedBroadcasts;

        public Package(String packageName) {
            throw new RuntimeException("Stub!");
        }

        public static final Creator<Package> CREATOR = new Creator<Package>() {
            @Override
            public Package createFromParcel(Parcel in) {
                throw new RuntimeException("Stub!");
            }

            @Override
            public Package[] newArray(int size) {
                throw new RuntimeException("Stub!");
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            throw new RuntimeException("Stub!");
        }
    }

    public final static class Permission extends Component<IntentInfo> implements Parcelable {

        public final PermissionInfo info;

        public boolean tree;

        public PermissionGroup group;

        public Permission(Package owner, String backgroundPermission) {
            super(owner);
            throw new RuntimeException("Stub!");
        }

        public Permission(Package _owner, PermissionInfo _info) {
            super(_owner);
            throw new RuntimeException("Stub!");
        }
        public void setPackageName(String packageName) {
            throw new RuntimeException("Stub!");
        }
        public String toString() {
            throw new RuntimeException("Stub!");
        }
        @Override
        public int describeContents() {
            throw new RuntimeException("Stub!");
        }
        @Override
        public void writeToParcel(Parcel dest, int flags) {
            throw new RuntimeException("Stub!");
        }

        public boolean isAppOp() {
            throw new RuntimeException("Stub!");
        }
        private Permission(Parcel in) {
            super(in);
            throw new RuntimeException("Stub!");
        }
        public static final Creator<Permission> CREATOR = new Parcelable.Creator<Permission>() {
            public Permission createFromParcel(Parcel in) {
                return new Permission(in);
            }
            public Permission[] newArray(int size) {
                return new Permission[size];
            }
        };
    }


    public final static class PermissionGroup extends Component<IntentInfo> implements Parcelable {

        public final PermissionGroupInfo info;
        public PermissionGroup(Package owner, @StringRes int requestDetailResourceId,
                               @StringRes int backgroundRequestResourceId,
                               @StringRes int backgroundRequestDetailResourceId) {
            super(owner);
            throw new RuntimeException("Stub!");
        }
        public PermissionGroup(Package _owner, PermissionGroupInfo _info) {
            super(_owner);
            throw new RuntimeException("Stub!");
        }
        public void setPackageName(String packageName) {
            throw new RuntimeException("Stub!");
        }
        public String toString() {
            throw new RuntimeException("Stub!");
        }
        @Override
        public int describeContents() {
            throw new RuntimeException("Stub!");
        }
        @Override
        public void writeToParcel(Parcel dest, int flags) {
            throw new RuntimeException("Stub!");
        }
        private PermissionGroup(Parcel in) {
            super(in);
            throw new RuntimeException("Stub!");
        }
        public static final Creator<PermissionGroup> CREATOR = new Parcelable.Creator<PermissionGroup>() {
            public PermissionGroup createFromParcel(Parcel in) {
                return new PermissionGroup(in);
            }
            public PermissionGroup[] newArray(int size) {
                return new PermissionGroup[size];
            }
        };
    }

    public final static class Service extends Component<ServiceIntentInfo> implements Parcelable {

        public final ServiceInfo info;

        public void setPackageName(String packageName) {
            throw new RuntimeException("Stub!");
        }
        public String toString() {
            throw new RuntimeException("Stub!");
        }
        @Override
        public int describeContents() {
            return 0;
        }
        @Override
        public void writeToParcel(Parcel dest, int flags) {
            throw new RuntimeException("Stub!");
        }
        private Service(Parcel in) {
            super(in);
            throw new RuntimeException("Stub!");
        }
        public static final Creator<Service> CREATOR = new Parcelable.Creator<Service>() {
            public Service createFromParcel(Parcel in) {
                return new Service(in);
            }
            public Service[] newArray(int size) {
                return new Service[size];
            }
        };
    }

}
