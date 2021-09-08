package com.demo.sql

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * <p>Title: DatabaseHelper </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2021/1/28 10:14
 * @version : 1.0
 */
class DatabaseHelper(context: Context?) :
        SQLiteOpenHelper(context, "demo.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}