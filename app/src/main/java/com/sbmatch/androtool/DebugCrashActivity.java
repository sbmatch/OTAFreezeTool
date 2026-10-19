package com.sbmatch.androtool;

import com.kongzue.baseframework.BaseActivity;
import com.kongzue.baseframework.util.JumpParameter;
import java.util.Map;
import java.util.function.Consumer;
import android.widget.TextView;
import android.content.Intent;
import android.graphics.Color;
import com.tencent.mmkv.MMKV;
import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONException;
import java.util.Iterator;
import com.sbmatch.androtool.R;

public class DebugCrashActivity extends BaseActivity {

    MMKV xCrashkv;
    
    public DebugCrashActivity() {
        setLayout(R.layout.debug_crash);
        setDarkStatusBarTheme(true);
        setNavigationBarBackgroundColor(Color.TRANSPARENT);
    }

    private TextView logTextView;
    private androidx.appcompat.widget.Toolbar toolbar;
    private com.google.android.material.appbar.CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    // 此处用于绑定布局、View初始化等操作
    public void initViews() {
        logTextView = (TextView) findViewById(R.id.logText);
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbarLayout);
        // 大标题/小标题由 CollapsingToolbarLayout 绘制（布局里已有默认 title），
        // Toolbar 自身的标题设为透明，避免重复显示。
        setSupportActionBar(toolbar);
    }

    @Override
    // 请在此编写初始化操作，例如读取数据等，以及对 UI 组件进行赋值
    public void initDatas(JumpParameter parameter) {

        Intent intent = getIntent();
        if (intent != null) {

            xCrashkv = MMKV.mmkvWithID("xcrash");

            String title = intent.getStringExtra("title");
            if (title != null) collapsingToolbarLayout.setTitle(title);
            String logKey = intent.getStringExtra("logKey");
            String logPath = intent.getStringExtra("logPath");
            String emergency = intent.getStringExtra("emergency");

            if (logPath != null) {
                logTextView.setHint("正在解析日志: " + logPath);
                tombstoneParser(logPath, emergency, result -> {
                    runOnMain(() -> logTextView.append(result));
                });
            } else {
                String errorStackTrace = xCrashkv.decodeString(logKey);
                errorLog(errorStackTrace);
                logTextView.append(errorStackTrace);
            }
        } else {
            finish();
        }
    }

    @Override
    // 此处为组件绑定功能事件、回调等方法
    public void setEvents() {
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });
    }

    void tombstoneParser(String logPath, String emergency, Consumer<String> callback) {
        com.sbmatch.androtool.utils.ExecutorUtils.submit(() -> {
            try {
                Map<String, String> map = xcrash.TombstoneParser.parse(logPath, emergency);
                StringBuilder sb = new StringBuilder();
                sb.append("*** *** *** *** *** *** *** *** *** *** *** *** *** *** *** ***\n");

                String[] headKeys = {
                        "Tombstone maker", "Crash type", "Start time", "Crash time",
                        "App ID", "App version", "Rooted", "API level", "OS version",
                        "Kernel version", "ABI list", "Manufacturer", "Brand", "Model",
                        "Build fingerprint", "ABI"
                };
                for (String key : headKeys) {
                    String v = map.get(key);
                    if (v != null && !v.isEmpty()) {
                        sb.append(key).append(": ").append(v).append("\n");
                    }
                }

                // pid / tid / name 特殊行
                String pid = map.get("pid");
                if (pid != null) {
                    sb.append("pid: ").append(pid);
                    String tid = map.get("tid");
                    if (tid != null) sb.append(", tid: ").append(tid);
                    String tname = map.get("tname");
                    if (tname != null) sb.append(", name: ").append(tname);
                    String pname = map.get("pname");
                    if (pname != null) sb.append("  >>> ").append(pname).append(" <<<");
                    sb.append('\n');
                }

                // signal / code / fault addr 特殊行
                String signal = map.get("signal");
                if (signal != null) {
                    sb.append("signal ").append(signal);
                    String code = map.get("code");
                    if (code != null) sb.append(", code ").append(code);
                    String fault = map.get("fault addr");
                    if (fault != null) sb.append(", fault addr ").append(fault);
                    sb.append('\n');
                }
                if (callback != null) callback.accept(sb.toString());

                // Abort message 带单引号
                String abort = map.get("Abort message");
                if (abort != null && !abort.isEmpty()) {
                    if (callback != null) callback.accept("Abort message: " + abort + "\n");
                }

                String[] sectionKeys = {
                        "stack", "java stacktrace", "logcat"
                };
                for (String key : sectionKeys) {
                    String v = map.get(key);
                    if (v != null && !v.isEmpty()) {
                        if (callback != null)
                            callback.accept("----" + key + "----" + "\n" + v + "\n");
                    }
                }
                
                xCrashkv.encode("logPath", (String) null);
            } catch (Exception e) {
                errorLog(e);
            }
        });
    }

}