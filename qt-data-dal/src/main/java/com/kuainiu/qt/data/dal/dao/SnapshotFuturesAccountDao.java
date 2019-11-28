package com.kuainiu.qt.data.dal.dao;

import com.kuainiu.qt.data.dal.entity.SnapshotFuturesAccount;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SnapshotFuturesAccountDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SnapshotFuturesAccount record);

    int insertSelective(SnapshotFuturesAccount record);

    SnapshotFuturesAccount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SnapshotFuturesAccount record);

    int updateByPrimaryKey(SnapshotFuturesAccount record);

    @ResultMap("BaseResultMap")
    @Select("select * from t_snapshot_futures_account where belong_time < #{endBelongTime} and account_code = #{accountCode} order by belong_time desc limit 1")
    SnapshotFuturesAccount getLastBeforeOpenMarket(SnapshotFuturesAccount record);

    void batchInsert(List<SnapshotFuturesAccount> recordList);

    @ResultMap("BaseResultMap")
    @Select("select * from t_snapshot_futures_account where snapshot_code = #{snapshotCode} ")
    List<SnapshotFuturesAccount> findListBySnapshotCode(String snapshotCode);



}