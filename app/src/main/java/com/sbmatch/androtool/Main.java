package com.sbmatch.androtool;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.lang.reflect.Method;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.util.Optional;
import java.util.Arrays;
import java.util.HashSet;
import com.kongzue.baseframework.BaseActivity;
import com.kongzue.baseframework.util.JumpParameter;
import com.kongzue.baseframework.util.Permission;
import com.kongzue.baseframework.util.OnActivityPermissionCallBack;
import android.graphics.*;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.content.res.AssetManager;
import android.util.*;
import android.os.*;
import android.text.method.ScrollingMovementMethod;
import android.view.*;
import android.widget.*;
import android.content.Intent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.ListPreference;
import androidx.preference.MultiSelectListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import com.android.settingslib.widget.*;
import com.sbmatch.androtool.utils.*;
import miui.util.*;
import android.miui.*;
import android.view.View.OnClickListener;

public class Main extends BaseActivity implements OnClickListener {

    Toolbar toolbar;
    AppViewModel appViewModel;
    IInterface vendor_perfservice;
    final StringBuilder output = new StringBuilder();

    public Main() {
        setLayout(R.layout.main);
        setDarkStatusBarTheme(true);
        setNavigationBarBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    // 此处用于绑定布局、View初始化等操作
    public void initViews() {

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportFragmentManager().findFragmentById(R.id.preferenceContainer) == null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.preferenceContainer, new PreferenceMainFragment())
                        .commit();
        }
    }

    @Override
    // 请在此编写初始化操作，例如读取数据等，以及对 UI 组件进行赋值
    public void initDatas(JumpParameter parameter) {
        
        try {
            //Object securitymgr = getServiceInterface(ServiceManager.getService("security"), "miui.security.ISecurityManager");
            //Boolean blocked = ReflectionUtils.callMethod(securitymgr, "checkSmsBlocked", Boolean.class, (Intent) new Intent("android.provider.Telephony.SMS_DELIVER"));
        } catch (Throwable e) {
            LogUtils.e("AndroidRuntime", Log.getStackTraceString(e));
        }
        
    }

    @Override
    // 此处为组件绑定功能事件、回调等方法
    public void setEvents() {

        /**
         * perfSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> { if (isChecked) { // 使用
         * 0xFFF 表示最高频率，强制跑满 int[] resources = new int[]{ 0x40C00000, 0x1, // SCHEDBOOST 开启
         * 0x40800200, 0xFFF, // PRIME 核心 → 最高频 0x40800300, 0xFFF, // TITANIUM 核心 → 最高频 0x40800000,
         * 0xFFF, // BIG 核心 → 最高频 0x40800100, 0xFFF, // LITTLE 核心 → 最高频 0x41844000, 0x927C0, // CPU
         * LLCC 带宽 0x4303C000, 0x1A0FE0, // LLCC DDR 带宽 0x43458000, 0x1B8A00 // L3 缓存频率 };
         *
         * <p>int lockAcquireResult = ReflectionUtils.tryCallMethod( vendor_perfservice,
         * "perfLockAcquire", Integer.class, 0, // handle 0, // 0 = 无限期锁定 resources ).get();
         * toast("锁定性能: " + lockAcquireResult); } else { int releaseResult =
         * ReflectionUtils.tryCallMethod( vendor_perfservice, "perfLockRelease",
         * Integer.class).get(); toast("释放性能锁: " + releaseResult); } });
         */
         
         // vendor_perfservice =
        // getServiceInterface(android.os.ServiceManager.getService("vendor.perfservice"),
        // "com.qualcomm.qti.IPerfManager");
        
    }

    @Override
    public void onClick(View view) {}

    public static IInterface getServiceInterface(IBinder binder, String type) {
        try {
            Method asInterfaceMethod = Class.forName(type + "$Stub").getMethod("asInterface", IBinder.class);
            return (IInterface) asInterfaceMethod.invoke(null, binder);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getIdString() {
        PackageManager pm = getPackageManager();

        int uid = android.os.Process.myUid();

        // 读 /proc/self/status 拿 gid / groups
        String gid = "?";
        String groups = "";
        String status = FilesUtils.readFromFile("/proc/self/status");
        if (status != null) {
            for (String line : status.split("\n")) {
                if (line.startsWith("Gid:")) {
                    String[] p = line.trim().split("\\s+");
                    if (p.length >= 2) gid = p[1];
                } else if (line.startsWith("Groups:")) {
                    String[] p = line.trim().split("\\s+");
                    StringBuilder g = new StringBuilder();
                    for (int i = 1; i < p.length; i++) {
                        if (i > 1) g.append(',');
                        g.append(p[i]);
                    }
                    groups = g.toString();
                }
            }
        }

        return String.format(
                "uid=%d(%s) gid=%s(%s) groups=%s context=%s",
                uid, pm.getNameForUid(uid).split(":")[0],
                gid, pm.getNameForUid(Integer.parseInt(gid.trim())).split(":")[0],
                groups,
                FilesUtils.readFromFile("/proc/self/attr/current").trim()
        );
    }


}