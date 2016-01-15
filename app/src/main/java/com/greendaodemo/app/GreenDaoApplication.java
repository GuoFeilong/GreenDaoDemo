package com.greendaodemo.app;

import android.app.Application;
import android.content.Context;

import com.socks.greendao.DaoMaster;
import com.socks.greendao.DaoSession;

/**
 * Created by jsion on 16/1/14.
 */
public class GreenDaoApplication extends Application {
    private static Context mContext;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static Context getContext() {
        return mContext;
    }

    public static DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context, GreenDaoMyConstant.DB_NAME, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    public static DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }
}
