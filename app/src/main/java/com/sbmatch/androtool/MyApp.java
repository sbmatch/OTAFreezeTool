package com.sbmatch.androtool;

import com.kongzue.baseframework.BaseApp;
import com.kongzue.baseframework.interfaces.OnBugReportListener;
import java.util.Random;
import android.graphics.Bitmap;
import android.util.Log;
import com.google.android.material.color.DynamicColors;
import com.google.android.material.color.DynamicColorsOptions;
import com.rosan.dhizuku.api.Dhizuku;
import java.io.File;
import android.content.Intent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.FileUtils;
import xcrash.XCrash;
import xcrash.ICrashCallback;
import com.tencent.mmkv.MMKV;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.nio.channels.FileChannel;
import java.nio.ByteBuffer;
import java.io.FileInputStream;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import com.sbmatch.androtool.utils.*;

public class MyApp extends BaseApp<MyApp> implements ViewModelStoreOwner {

    static AppViewModel viewModel;
    private ViewModelStore viewModelStore;

    @Override
    public void init() {
        // 参考 InstallerX-Revived：用系统从壁纸提取的 accent1 作为种子，
        // 生成整套 Material3 配色，让 surface / surfaceContainer 等背景色跟随壁纸主题色。
        applyContentBasedDynamicColors();
        // 初始化 Dhizuku（未安装/未激活时返回 false，不影响应用启动）
        try {
            Dhizuku.init(this);
        } catch (Throwable ignored) {
        }
        // 加载 native 库（hidden_api_bypass）较慢，放到后台线程，避免阻塞首帧
        ExecutorUtils.submit(() -> com.sbmatch.androtool.utils.MagicHelper.init());

        MMKV.initialize(this);
        MMKV xCrashkv = MMKV.mmkvWithID("xcrash");

        viewModelStore = new ViewModelStore();
        ViewModelProvider.AndroidViewModelFactory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(this);
        viewModel = new ViewModelProvider(viewModelStore, factory).get(AppViewModel.class);

        ICrashCallback callback = new ICrashCallback() {
            @Override
            public void onCrash(String logPath, String emergency) {
                xCrashkv.encode("logPath", logPath);
                xCrashkv.encode("emergency", emergency);
            }
        };

        String nativeCrashPath = xCrashkv.decodeString("logPath", null);
        if (nativeCrashPath != null) {
            openCrashActivity("nativeCrashLog", nativeCrashPath, xCrashkv.decodeString("emergency", null), "上次崩溃日志");
        }

        XCrash.init(this, new XCrash.InitParameters()
                .setNativeLogCountMax(1)
                .setNativeDumpAllThreadsWhiteList(new String[]{"^xcrash\\.sample$", "^Signal Catcher$", "^Jit thread pool$", ".*(R|r)ender.*", ".*Chrome.*"})
                .setNativeDumpAllThreadsCountMax(32)
                .setNativeCallback(callback)
                .setPlaceholderSizeKb(512)
                .setLogDir(getExternalFilesDir("xcrash").toString())
                // .setLogFileMaintainDelayMs(1000))
        );

        setOnCrashListener(new OnBugReportListener() {
            @Override
            public boolean onCrash(Exception e, final File crashLogFile) {
                // TODO: 请在这里处理异常信息
                // return true时，会在执行完上述代码后关闭 App，但 return false，则会拦截此错误，App 不会闪退，继续运行
                xCrashkv.encode("javaCrashLog", android.util.Log.getStackTraceString(e));
                openCrashActivity("javaCrashLog", null, null, null);
                return false;
            }
        });

        
    }

    /**
     * 和 InstallerX-Revived 一样，以系统的 {@code system_accent1_500}（Android 12+ 由壁纸生成）
     * 作为种子色，生成整套 Material3 配色。这样 Activity 里的
     * {@code ?attr/colorSurfaceContainer} / {@code ?attr/colorPrimaryContainer} 等
     * 背景色都会跟随壁纸主题色，而不是固定的 Material 默认色。
     */
    private void applyContentBasedDynamicColors() {
        try {
            int seedColor = getColor(android.R.color.system_accent1_500);
            Bitmap seed = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            seed.setPixel(0, 0, seedColor);
            DynamicColors.applyToActivitiesIfAvailable(this,
                    new DynamicColorsOptions.Builder()
                            .setContentBasedSource(seed)
                            .build());
            return;
        } catch (Exception e) {
            Log.w("MyApp", "Failed to read system accent color", e);
        }
        // 读取失败时退回系统自带的动态取色
        DynamicColors.applyToActivitiesIfAvailable(this);
    }

    @Override
    public ViewModelStore getViewModelStore() {
        return viewModelStore;
    }

    public static AppViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public void initSDKs() {
        MMKV applistCache = MMKV.mmkvWithID("applist_cache");
        ExecutorUtils.submit(() -> {
            
        });
    }

    void openCrashActivity(String logKey, String logPath, String emergency, String title) {
        Intent intent = new Intent(me, DebugCrashActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("logKey", logKey);
        intent.putExtra("logPath", logPath);
        intent.putExtra("emergency", emergency);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    String readFileString(String path) throws IOException {
        try (FileInputStream fis = new FileInputStream(path);
                FileChannel channel = fis.getChannel()) {
            long size = channel.size();
            ByteBuffer buffer = ByteBuffer.allocate((int) size);
            channel.read(buffer);
            buffer.flip();
            return StandardCharsets.UTF_8.decode(buffer).toString();
        }
    }
}
