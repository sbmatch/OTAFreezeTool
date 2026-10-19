package com.sbmatch.androtool;

import com.kongzue.baseframework.BaseActivity;
import com.kongzue.baseframework.util.JumpParameter;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;
import android.graphics.Color;
import android.widget.LinearLayout;
import java.io.*;
import com.sbmatch.androtool.R;

public class EntryActivity extends BaseActivity {

    public EntryActivity() {
        setLayout(R.layout.entry_layout);
        setDarkStatusBarTheme(true);
        setNavigationBarBackgroundColor(Color.TRANSPARENT);
    }

    public androidx.appcompat.widget.Toolbar toolbar;
    private com.google.android.material.appbar.CollapsingToolbarLayout collapsingToolbarLayout;
    public LinearLayout content_layout;
    //private LuaState L;

    @Override
    // 此处用于绑定布局、View初始化等操作
    public void initViews() {
        content_layout = (LinearLayout) findViewById(R.id.content_layout);
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
            // 从 Intent 里获取要执行的 Lua 脚本路径或代码
            String luaScript = intent.getStringExtra("LUA_SCRIPT");
            String title = intent.getStringExtra("title");
            if (title != null) collapsingToolbarLayout.setTitle(title);
            
        }
    }

    @Override
    // 此处为组件绑定功能事件、回调等方法
    public void setEvents() {
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });
    }



    private static byte[] readAll(InputStream input) throws Exception {
        ByteArrayOutputStream output = new ByteArrayOutputStream(4096);
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }

}