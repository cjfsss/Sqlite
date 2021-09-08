package com.demo.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * <p>Title: TableInfo </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/6/6 14:11
 */
public class TableInfo implements Serializable, Parcelable {

    private static final long serialVersionUID = 5588633541212156274L;
    private long id;
    // 表名称
    private String tableName;
    // 服务端表主键
    private String servicePrimaryKey;
    // 第几页
    private long pageIndex = 0;
    /**
     * 每次请求多少条
     */
    private long pageSize;

    // 更新时间
    private String dataUpdateTime;
    /**
     * 创建时间
     */
    private String updateTime;
    /**
     * 创建时间
     */
    private String createTime;

    private String keyUpdateTime;
    // 表同步描述
    private String tableSyncDesc;

    private boolean isAsyncAll = false;

    private boolean isSuccess = false;
    // 同步数据的总数
    private long totalSize;
    // 同步成功的数量
    private long successSize;
    // 同步失败的数量
    private long failedSize;

    public TableInfo() {
        updateAllData();
    }

    public TableInfo(String tableName, String servicePrimaryKey, String keyUpdateTime) {
        this.tableName = tableName;
        this.servicePrimaryKey = servicePrimaryKey;
        this.keyUpdateTime = keyUpdateTime;
        updateAllData();
    }

    public TableInfo(String tableName, String servicePrimaryKey) {
        this.tableName = tableName;
        this.servicePrimaryKey = servicePrimaryKey;
        updateAllData();
    }

    public void updateAllData() {
        this.dataUpdateTime = "1900-01-01";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getServicePrimaryKey() {
        return servicePrimaryKey;
    }

    public void setServicePrimaryKey(String servicePrimaryKey) {
        this.servicePrimaryKey = servicePrimaryKey;
    }

    public long getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(long pageIndex) {
        this.pageIndex = pageIndex;
    }

    public long getPageSize() {
        if (pageSize == 0) {
            return pageSize = 500;
        }
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public String getDataUpdateTime() {
        return dataUpdateTime;
    }

    public void setDataUpdateTime(String dataUpdateTime) {
        this.dataUpdateTime = dataUpdateTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getKeyUpdateTime() {
        return keyUpdateTime;
    }

    public void setKeyUpdateTime(String keyUpdateTime) {
        this.keyUpdateTime = keyUpdateTime;
    }

    public String getTableSyncDesc() {
        return tableSyncDesc;
    }

    public void setTableSyncDesc(String tableSyncDesc) {
        this.tableSyncDesc = tableSyncDesc;
    }

    public boolean isAsyncAll() {
        return isAsyncAll;
    }

    public void setAsyncAll(boolean asyncAll) {
        isAsyncAll = asyncAll;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public long getSuccessSize() {
        return successSize;
    }

    public void setSuccessSize(long successSize) {
        this.successSize = successSize;
    }

    public long getFailedSize() {
        return failedSize;
    }

    public void setFailedSize(long failedSize) {
        this.failedSize = failedSize;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    @Override
    public String toString() {
        return "TableInfo{" + "id=" + id + ", tableName='" + tableName + '\'' + ", servicePrimaryKey='" + servicePrimaryKey + '\'' + ", pageIndex=" + pageIndex + ", pageSize=" + pageSize + ", dataUpdateTime='" + dataUpdateTime + '\'' + ", updateTime='" + updateTime + '\'' + ", createTime='" + createTime + '\'' + ", keyUpdateTime='" + keyUpdateTime + '\'' + ", tableSyncDesc='" + tableSyncDesc + '\'' + ", isAsyncAll=" + isAsyncAll + ", isSuccess=" + isSuccess + ", totalSize=" + totalSize + ", successSize=" + successSize + ", failedSize=" + failedSize + '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.tableName);
        dest.writeString(this.servicePrimaryKey);
        dest.writeLong(this.pageIndex);
        dest.writeLong(this.pageSize);
        dest.writeString(this.dataUpdateTime);
        dest.writeString(this.updateTime);
        dest.writeString(this.createTime);
        dest.writeString(this.keyUpdateTime);
        dest.writeString(this.tableSyncDesc);
        dest.writeByte(this.isAsyncAll ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isSuccess ? (byte) 1 : (byte) 0);
        dest.writeLong(this.totalSize);
        dest.writeLong(this.successSize);
        dest.writeLong(this.failedSize);
    }

    protected TableInfo(Parcel in) {
        this.id = in.readLong();
        this.tableName = in.readString();
        this.servicePrimaryKey = in.readString();
        this.pageIndex = in.readLong();
        this.pageSize = in.readLong();
        this.dataUpdateTime = in.readString();
        this.updateTime = in.readString();
        this.createTime = in.readString();
        this.keyUpdateTime = in.readString();
        this.tableSyncDesc = in.readString();
        this.isAsyncAll = in.readByte() != 0;
        this.isSuccess = in.readByte() != 0;
        this.totalSize = in.readLong();
        this.successSize = in.readLong();
        this.failedSize = in.readLong();
    }

    public static final Creator<TableInfo> CREATOR = new Creator<TableInfo>() {
        @Override
        public TableInfo createFromParcel(Parcel source) {
            return new TableInfo(source);
        }

        @Override
        public TableInfo[] newArray(int size) {
            return new TableInfo[size];
        }
    };
}
