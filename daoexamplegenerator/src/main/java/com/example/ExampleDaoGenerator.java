package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class ExampleDaoGenerator {

    public static void main(String[] args) throws Exception {
        // 相当于建表语句
        Schema schema = new Schema(GreenDaoConstant.DATA_VERSION_CODE, GreenDaoConstant.PACKAGE_NAME);
        creatTABLE(schema, GreenDaoTableName.STAFF_TABLE_NAME);
        //生成Dao文件路径
        new DaoGenerator().generateAll(schema, GreenDaoConstant.DAO_PATH);

    }

    /**
     * greenDao建表
     *
     * @param schema
     * @param tableName
     */
    private static void creatTABLE(Schema schema, String tableName) {
        Entity joke = schema.addEntity(tableName);

        //主键id自增长
        joke.addIdProperty().primaryKey().autoincrement();
        //姓名
        joke.addStringProperty(StaffProperties.STAFF_NAME);
        //工号
        joke.addStringProperty(StaffProperties.STAFF_NUM);
        //年龄
        joke.addIntProperty(StaffProperties.STAFF_AGE);
        //座右铭
        joke.addStringProperty(StaffProperties.STAFF_MOTTO);
        //插入时间
        joke.addLongProperty(StaffProperties.STAFF_INSERT_TIME);

    }
}
