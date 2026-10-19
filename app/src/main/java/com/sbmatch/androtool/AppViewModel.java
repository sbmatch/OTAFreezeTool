package com.sbmatch.androtool;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class AppViewModel extends AndroidViewModel {

    private final MutableLiveData<String> someData = new MutableLiveData<>();
    
    public AppViewModel(Application application) {
        super(application);
        // 初始化数据
    }
    
    // 获取数据的 LiveData
    public LiveData<String> getSomeData() {
        return someData;
    }
    
    // 更新数据
    public void setSomeData(String data) {
        someData.setValue(data);
    }
}