package com.demo.bean

import android.database.Cursor
import android.text.TextUtils
import com.blankj.utilcode.util.TimeUtils
import hos.sqlite.table.TableDao
import hos.utils.CloseUtils
import java.lang.NullPointerException
import java.util.*
import kotlin.collections.HashMap

/**
 * <p>Title: TableInfoDao </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2021/1/28 9:51
 * @version : 1.0
 */
class TableInfoDao : TableDao<TableInfo> {

    override fun getTableName(): String {
        return "TableInfo"
    }

    override fun getPrimaryKey(): String {
        return "id"
    }

    override fun createTable(createTableSql: String, createTablePrimaryKeySql: String) {
        val sqlCreate = createTableSql + " ( " + createTablePrimaryKeySql + " , " + " 'tableName' TEXT" + " , " +
                " 'servicePrimaryKey' TEXT" + " , " + " 'dataUpdateTime' TEXT" + " , " +
                " 'updateTime' TEXT" + " , " + " 'createTime' TEXT" + " , " + " 'keyUpdateTime' TEXT" +
                " ) "
        database.execSQL(sqlCreate)
    }

    override fun saveOrUpdate(map: MutableMap<String, Any?>): Boolean {
        val tableName = map["tableName"].toString()
        if (TextUtils.isEmpty(tableName)) {
            throw NullPointerException("tableName is null")
        }
        return saveOrUpdate(map, " tableName=? ", arrayOf(tableName))
    }

    override fun toTableList(cursor: Cursor?): MutableList<TableInfo>? {
        if (cursor == null) {
           return null
        }
        try {
            val resultList = ArrayList<TableInfo>()
            while (cursor.moveToNext()) {
                val tableInfo = TableInfo()

                tableInfo.id = cursor.getLong(cursor.getColumnIndex("id"))
                tableInfo.tableName = cursor.getString(cursor.getColumnIndex("tableName"))
                tableInfo.servicePrimaryKey = cursor.getString(cursor.getColumnIndex("servicePrimaryKey"))
                tableInfo.dataUpdateTime = cursor.getString(cursor.getColumnIndex("dataUpdateTime"))
                tableInfo.updateTime = cursor.getString(cursor.getColumnIndex("updateTime"))
                tableInfo.createTime = cursor.getString(cursor.getColumnIndex("createTime"))
                tableInfo.keyUpdateTime = cursor.getString(cursor.getColumnIndex("keyUpdateTime"))
                resultList.add(tableInfo)
            }
            return  resultList
        } finally {
            CloseUtils.closeQuietly(cursor)
        }
    }

    override fun toMap(tableInfo: TableInfo): MutableMap<String, Any?> {
        val map = HashMap<String, Any?>()
        map["id"] = tableInfo.id
        map["tableName"] = tableInfo.tableName
        map["servicePrimaryKey"] = tableInfo.servicePrimaryKey
        map["dataUpdateTime"] = tableInfo.dataUpdateTime
        map["updateTime"] = tableInfo.updateTime
        map["keyUpdateTime"] = tableInfo.keyUpdateTime
        return map
    }

    override fun beforeInsert(mapValues: MutableMap<String, Any?>) {
        val nowString: String = TimeUtils.getNowString()
        mapValues["createTime"] = nowString
        mapValues["updateTime"] = nowString
    }

    override fun beforeUpdate(mapValues: MutableMap<String, Any?>, oldMap: MutableMap<String, Any>) {
        mapValues["updateTime"] = TimeUtils.getNowString()
        mapValues["createTime"] = oldMap["createTime"].toString()
        mapValues["id"] = oldMap["id"].toString().toLong()
    }

    fun transactionInsert(){
        database.transaction {
            return@transaction -1L;
        }
    }
}