package com.jesen.cod.usetinker;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.jesen.cod.usetinker.tinker.Log.TinkerLg;
import com.jesen.cod.usetinker.tinker.util.SampleApplicationContext;
import com.jesen.cod.usetinker.tinker.util.TinkerManager;
import com.tencent.tinker.entry.DefaultApplicationLike;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.shareutil.ShareConstants;

@DefaultLifeCycle(application = "com.jesen.cod.usetinker.TinApplication",// application类名。只能用字符串，这个MyApplication文件是不存在的，但可以在AndroidManifest.xml的application标签上使用（name）
        flags = ShareConstants.TINKER_ENABLE_ALL,// tinkerFlags
        loaderClass = "com.tencent.tinker.loader.TinkerLoader",//loaderClassName, 我们这里使用默认即可!（可不写）
        loadVerifyFlag = false) //tinkerLoadVerifyFlag
)
public class TinkerApplicationLike extends DefaultApplicationLike {
    private static final String TAG = "TinkerApplicationLike";


    public TinkerApplicationLike(Application application, int tinkerFlags,
                                 boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime,
                                 long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        //you must install multiDex whatever tinker is installed!
        MultiDex.install(base);

        SampleApplicationContext.application = getApplication();
        SampleApplicationContext.context = getApplication();
        TinkerManager.setTinkerApplicationLike(this);

        TinkerManager.initFastCrashProtect();
        //should set before tinker is installed
        TinkerManager.setUpgradeRetryEnable(true);

        //optional set logIml, or you can use default debug log
        TinkerInstaller.setLogIml(new TinkerLg());

        //installTinker after load multiDex
        //or you can put com.tencent.tinker.** to main dex
        TinkerManager.installTinker(this);
        Tinker tinker = Tinker.with(getApplication());
        // 可以将之前自定义的Application中onCreate()方法所执行的操作搬到这里...
    }

    public void registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks callback) {
        getApplication().registerActivityLifecycleCallbacks(callback);
    }

}
