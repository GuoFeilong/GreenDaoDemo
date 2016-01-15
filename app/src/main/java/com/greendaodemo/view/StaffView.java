package com.greendaodemo.view;

import com.socks.greendao.StaffTable;

import java.util.List;

public interface StaffView {
    void showError();

    void bindQueryAllStaffs(List<StaffTable> queryStaffs);

    void addQueryStaffByName(List<StaffTable> queryStaffs);

    void addQueryStaffByNum(List<StaffTable> queryStaffs);

    void addQueryStaffByAge(List<StaffTable> queryStaffs);

    void insertStaff(StaffTable insertStaff);

    void deleteStaffByID();
}
