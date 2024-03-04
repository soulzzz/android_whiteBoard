package com.goke.whiteboard.utils;

import android.content.Context;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.util.Log;

import java.io.File;

/**
 * Created by gpy on 2015/10/20.
 */
public class SdCardStatus {

    public static String getDefaulstCacheDirInSdCard(Context context) throws IllegalStateException {
        String sdCardPath = null;
        sdCardPath =   context.getExternalFilesDir(null).getPath();
        Log.d(SdCardStatus.class.getSimpleName(), "getDefaulstCacheDirInSdCard: "+sdCardPath  );
        return sdCardPath;
    }
}
