package com.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.TimeUtils
import com.demo.bean.TableInfo
import com.demo.bean.TableInfoDao
import com.demo.sql.DatabaseHelper
import hos.sqlite.DatabaseConfig
import hos.sqlite.DatabaseManger
import kotlinx.android.synthetic.main.activity_main.*

/**
 * <p>Title: MainActivity </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2021/1/28 9:42
 * @version : 1.0
 */
class MainActivity: AppCompatActivity(R.layout.activity_main) {

    private val mTableInfoDao by lazy { TableInfoDao() }

    private val mDatabaseHelper by lazy {
        DatabaseHelper(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 配置数据库
        DatabaseManger.setConfig(DatabaseConfig.newBuilder()
                .setSqLiteDatabase(mDatabaseHelper.writableDatabase)
                .build())
        // 监听
        mBtnInsert.setOnClickListener {
            // 插入一条数据
            val tableInfo = TableInfo("Common_Type","updateTime","updateTime")
            tableInfo.dataUpdateTime = TimeUtils.getNowString()
            mTableInfoDao.saveOrUpdate(tableInfo)
        }
        mBtnQuery.setOnClickListener {
            // 请求所有数据
            val queryTable = mTableInfoDao.queryTableAll()
            mTvContent.text = queryTable.toString()
        }
        mBtnUpdate.setOnClickListener {
            // 更新一条数据
            val tableInfo = TableInfo("Common_Type","updateTime","updateTime")
            tableInfo.dataUpdateTime = TimeUtils.getNowString()
            mTableInfoDao.saveOrUpdate(tableInfo)
        }
    }
}