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
import androidx.appcompat.app.AlertDialog;
import com.sbmatch.androtool.R;

public class EntryDiaogActivity extends BaseActivity {

    private AlertDialog dialog;

    public EntryDiaogActivity() {
        setDarkStatusBarTheme(true);
        setNavigationBarBackgroundColor(Color.TRANSPARENT);
    }
 
    @Override
    // 此处用于绑定布局、View初始化等操作
    public void initViews() {
        
    }

    @Override
    // 请在此编写初始化操作，例如读取数据等，以及对 UI 组件进行赋值
    public void initDatas(JumpParameter parameter) {
        
        Intent intent = getIntent();
        if (intent != null) {
            String title = intent.getStringExtra("title");
            String message = intent.getStringExtra("message");
            if (title == null) return;
            
        }
    }

    @Override
    // 此处为组件绑定功能事件、回调等方法
    public void setEvents() {
        
    }

}