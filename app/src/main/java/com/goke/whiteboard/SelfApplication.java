package com.goke.whiteboard;

import android.app.Application;

import com.goke.wblib.utils.AppContextUtil;
import com.goke.wblib.utils.OperationUtils;
import com.goke.whiteboard.utils.SdCardStatus;
import com.goke.whiteboard.utils.StoreUtil;


/**
 * Created by gpy on 2015/8/17.
 */
public class SelfApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        AppContextUtil.init(this);
        OperationUtils.getInstance().init();
    }
}
