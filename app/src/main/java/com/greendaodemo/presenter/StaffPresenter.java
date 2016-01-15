package com.greendaodemo.presenter;

import com.socks.greendao.StaffTable;

/**
 * Created by jsion on 16/1/14.
 */
public interface StaffPresenter {
    /**
     * 查询所有的员工
     */
    void queryAllStaffs();

    /**
     * 通过姓名查找
     *
     * @param staffName
     */
    void queryStaffByName(String staffName);

    /**
     * 通过工号查找
     *
     * @param staffNum
     */
    void queryStaffByNum(String staffNum);

    /**
     * 通过年龄查找
     *
     * @param staffAge
     */
    void queryStaffByAge(String staffAge);

    /**
     * 插入一个员工
     *
     * @param insertStaff
     */
    void insertStaff(StaffTable insertStaff);

    /**
     * 通过ID 删除人员
     * @param id
     */
    void deleteStaffByID(Long id);
}
