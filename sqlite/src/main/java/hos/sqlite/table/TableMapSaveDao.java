package hos.sqlite.table;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import java.util.Map;

import hos.sqlite.datebase.SqlBuilder;

/**
 * <p>Title: BaseTable </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/6/18 13:49
 */

public interface TableMapSaveDao extends TableMapDao {


    /**
     * 保存或者更新数据
     * @param values 更新的数据
     * @param where 更新的条件sql
     * @param whereArgs 更新的条件
     * @return true 保存或者更新成功
     */
    default boolean saveOrUpdate(@NonNull final Map<String, Object> values, @NonNull final String where,
                                 @NonNull final String[] whereArgs) {
        final String createSqlForMaster = getCreateSqlForMaster();
        final Map<String, Object> oldMap = queryFirst(where, whereArgs);
        if (oldMap != null && !oldMap.isEmpty()) {
            final SqlBuilder sqlBuilder = new SqlBuilder().mergeMap(oldMap, values, createSqlForMaster);
            beforeUpdate(sqlBuilder.mapValues, oldMap);
            return update(sqlBuilder.mapValues, where, whereArgs) > 0;
        } else {
            final SqlBuilder sqlBuilder = new SqlBuilder().filterMap(values, createSqlForMaster);
            beforeInsert(sqlBuilder.mapValues);
            return insert(sqlBuilder.mapValues) > 0;
        }
    }
    /**
     * 根据主键保存或者更新数据
     * @param map 更新的数据
     * @return true 保存或者更新成功
     */
    default boolean saveOrUpdate(@NonNull final Map<String, Object> map) {
        String primaryValue = String.valueOf(map.get(getPrimaryKey()));
        if (TextUtils.isEmpty(primaryValue)) {
            return false;
        }
        return saveOrUpdate(map, " " + getPrimaryKey() + "=? ", new String[]{primaryValue});
    }

    /**
     * 插入数据之前
     * @param mapValues 插入的数据
     */
    void beforeInsert(@NonNull final Map<String, Object> mapValues);

    /**
     * 更新数据之前
     * @param mapValues 要更新的数据
     * @param oldMap 之前的数据
     */
    default void beforeUpdate(@NonNull final Map<String, Object> mapValues, @NonNull final Map<String, Object> oldMap) {
        mapValues.put(getPrimaryKey(), oldMap.get(getPrimaryKey()));
    }
}
