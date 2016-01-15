package com.greendaodemo.presenter.impl;

import android.content.Context;

import com.greendaodemo.Model.StaffModel;
import com.greendaodemo.presenter.StaffPresenter;
import com.greendaodemo.view.StaffView;
import com.socks.greendao.StaffTable;
import com.socks.greendao.StaffTableDao;

import java.util.List;

/**
 * Created by jsion on 16/1/14.
 */
public class StaffPresenterImpl implements StaffPresenter {
    private StaffModel staffModel;
    private StaffView staffView;
    private Context context;
    private StaffTableDao staffTableDao;


    public StaffPresenterImpl(StaffView staffView, Context context) {
        this.staffView = staffView;
        this.context = context;
        staffModel = new StaffModel(context);
        staffTableDao = staffModel.getStaffDaoSession().getStaffTableDao();
    }

    @Override
    public void queryAllStaffs() {
        List<StaffTable> qStaffs = staffTableDao.queryBuilder().list();
        staffView.bindQueryAllStaffs(qStaffs);
    }

    @Override
    public void queryStaffByName(String staffName) {
        /**
         * greendao的条件查询语句
         */
        List<StaffTable> qStaffs = staffTableDao.queryBuilder().where(StaffTableDao.Properties.StaffName.eq(staffName)).list();
        staffView.addQueryStaffByName(qStaffs);
    }

    @Override
    public void queryStaffByNum(String staffNum) {
        List<StaffTable> qStaffs = staffTableDao.queryBuilder().where(StaffTableDao.Properties.StaffNum.eq(staffNum)).list();
        staffView.addQueryStaffByNum(qStaffs);
    }

    @Override
    public void queryStaffByAge(String staffAge) {
        List<StaffTable> qStaffs = staffTableDao.queryBuilder().where(StaffTableDao.Properties.StaffAge.eq(staffAge)).list();
        staffView.addQueryStaffByAge(qStaffs);
    }

    @Override
    public void insertStaff(StaffTable insertStaff) {
        staffTableDao.insert(insertStaff);
        staffView.insertStaff(insertStaff);
    }

    @Override
    public void deleteStaffByID(Long id) {
        staffTableDao.deleteByKey(id);
        staffView.deleteStaffByID();
    }
}
