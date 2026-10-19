// package com.sbmatch.androtool.utils;
// 
// import android.accounts.IAccountManager;
// import android.app.IActivityManager;
// import android.app.INotificationManager;
// import android.app.IThermalService;
// import android.app.admin.IDevicePolicyManager;
// import android.app.role.IRoleManager;
// import android.content.Context;
// import android.content.IContentService;
// import android.content.pm.IPackageManager;
// import android.net.wifi.IWifiManager;
// import android.os.IBinder;
// import android.os.IInterface;
// import android.os.IUserManager;
// import android.os.ServiceManager;
// import android.permission.IPermissionManager;
// import android.net.IConnectivityManager;
// import android.util.Singleton;
// import java.lang.reflect.Method;
// 
// import rikka.shizuku.ShizukuBinderWrapper;
// import rikka.shizuku.SystemServiceHelper;
// 
// public class ShizukuSystemServerApi {
// 
//     public static final Singleton<IActivityManager> ACTIVITY_MANAGER = new Singleton<>() {
//         @Override
//         protected IActivityManager create() {
//             return IActivityManager.Stub.asInterface(new ShizukuBinderWrapper(SystemServiceHelper.getSystemService("activity")));
//         }
//     };
// 
//     public static final Singleton<IPackageManager> PACKAGE_MANAGER = new Singleton<>() {
//         @Override
//         protected IPackageManager create() {
//             return IPackageManager.Stub.asInterface(new ShizukuBinderWrapper(SystemServiceHelper.getSystemService("package")));
//         }
//     };
// 
//     public static final Singleton<IUserManager> USER_MANAGER = new Singleton<>() {
//         @Override
//         protected IUserManager create() {
//             return IUserManager.Stub.asInterface(new ShizukuBinderWrapper(SystemServiceHelper.getSystemService(Context.USER_SERVICE)));
//         }
//     };
// 
//     public static final Singleton<IDevicePolicyManager> DEVICE_POLICY_MANAGER = new Singleton<>() {
//         @Override
//         protected IDevicePolicyManager create() {
//             return IDevicePolicyManager.Stub.asInterface(new ShizukuBinderWrapper(SystemServiceHelper.getSystemService(Context.DEVICE_POLICY_SERVICE)));
//         }
//     };
// 
//     public static final Singleton<IPermissionManager> PERMISSION_MANAGER = new Singleton<>() {
//         @Override
//         protected IPermissionManager create() {
//             return IPermissionManager.Stub.asInterface(new ShizukuBinderWrapper(SystemServiceHelper.getSystemService("permissionmgr")));
//         }
//     };
// 
//     public static final Singleton<INotificationManager> NOTIFICATION_MANAGER = new Singleton<>() {
//         @Override
//         protected INotificationManager create() {
//             return INotificationManager.Stub.asInterface(new ShizukuBinderWrapper(SystemServiceHelper.getSystemService(Context.NOTIFICATION_SERVICE)));
//         }
//     };
// 
//     public static final Singleton<IThermalService> THERMAL_SERVICE = new Singleton<>() {
//         @Override
//         protected IThermalService create() {
//             return IThermalService.Stub.asInterface(new ShizukuBinderWrapper(SystemServiceHelper.getSystemService("thermalservice")));
//         }
//     };
// 
//     public static final Singleton<IContentService> CONTENT_SERVICE = new Singleton<
//             IContentService>() {
//         @Override
//         protected IContentService create() {
//             return IContentService.Stub.asInterface(new ShizukuBinderWrapper(SystemServiceHelper.getSystemService("content")));
//         }
//     };
// 
//     public static final Singleton<IWifiManager> WIFI_MANAGER = new Singleton<>() {
//         @Override
//         protected IWifiManager create() {
//             return IWifiManager.Stub.asInterface(new ShizukuBinderWrapper(SystemServiceHelper.getSystemService(Context.WIFI_SERVICE)));
//         }
//     };
// 
//     public static final Singleton<IAccountManager> ACCOUNT_MANAGER = new Singleton<>() {
//         @Override
//         protected IAccountManager create() {
//             return IAccountManager.Stub.asInterface(new ShizukuBinderWrapper(SystemServiceHelper.getSystemService("account")));
//         }
//     };
// 
//     public static final Singleton<IRoleManager> ROLE_SERVICE = new Singleton<IRoleManager>() {
//         @Override
//         protected IRoleManager create() {
//             return IRoleManager.Stub.asInterface(new ShizukuBinderWrapper(SystemServiceHelper.getSystemService(Context.ROLE_SERVICE)));
//         }
//     };
// 
//     public static final Singleton<IConnectivityManager> CONNECTIVITY_SERVICE = new Singleton<
//             IConnectivityManager>() {
//         @Override
//         protected IConnectivityManager create() {
//             return IConnectivityManager.Stub.asInterface(new ShizukuBinderWrapper(SystemServiceHelper.getSystemService("connectivity")));
//         }
//     };
// 
//     public static Singleton<IInterface> getSystemServiceInterface(String name, String type) {
//         return new Singleton<IInterface>() {
//             @Override
//             protected IInterface create() {
//                 return getServiceInterface(new ShizukuBinderWrapper(SystemServiceHelper.getSystemService(name)), type);
//             }
//         };
//     }
// 
//     public static IInterface getServiceInterface(IBinder binder, String type) {
//         try {
//             Method asInterfaceMethod = Class.forName(type + "$Stub").getMethod("asInterface", IBinder.class);
//             return (IInterface) asInterfaceMethod.invoke(null, binder);
//         } catch (Exception e) {
//             throw new RuntimeException(e);
//         }
//     }
// 
// }
