package com.greendaodemo.Model;

import android.content.Context;

import com.greendaodemo.app.GreenDaoApplication;
import com.socks.greendao.DaoSession;

/**
 * Created by jsion on 16/1/14.
 */
public class StaffModel {
    private Context context;

    public StaffModel(Context context) {
        this.context = context;
    }

    public DaoSession getStaffDaoSession() {
        return GreenDaoApplication.getDaoSession(context);
    }
}
