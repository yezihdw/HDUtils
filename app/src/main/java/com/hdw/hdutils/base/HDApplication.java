package com.hdw.hdutils.base;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Created by hdw on 2017/6/6.
 */

@SuppressWarnings("unused")
@DefaultLifeCycle(application = "com.hdw.hdutils.HDApplication",
        flags = ShareConstants.TINKER_ENABLE_ALL,
        loadVerifyFlag = false)
public class HDApplication extends TinkerApplication{


    protected HDApplication(int tinkerFlags) {
        super(
                //tinkerFlags, which types is supported
                //dex only, library only, all support
                ShareConstants.TINKER_ENABLE_ALL,
                // This is passed as a string so the shell application does not
                // have a binary dependency on your ApplicationLifeCycle class.
                "tinker.sample.android.app.SampleApplicationLike");
    }

    /**
     * install multiDex before install tinker
     * so we don't need to put the tinker lib classes in the main dex
     *
     * @param base
     */
//    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
//    @Override
    public void onBaseContextAttached(Context base) {
//        super.onBaseContextAttached(base);

        //you must install multiDex whatever tinker is installed!
        MultiDex.install(base);

//        TinkerInstaller.install(this);

//        SampleApplicationContext.application = getApplicationContext();
//        SampleApplicationContext.context = getApplicationContext();
//        TinkerManager.setTinkerApplicationLike(this);

//        TinkerManager.initFastCrashProtect();
        //should set before tinker is installed
//        TinkerManager.setUpgradeRetryEnable(true);

        //optional set logIml, or you can use default debug log
//        TinkerInstaller.setLogIml(new MyLogImp());

        //installTinker after load multiDex
        //or you can put com.tencent.tinker.** to main dex
//        TinkerManager.installTinker(this);
//        Tinker tinker = Tinker.with(getApplication());
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks callback) {
//        getApplication().registerActivityLifecycleCallbacks(callback);
    }
}
