package com.kuainiu.qt.data.dal.dao;

import com.kuainiu.qt.data.dal.entity.SnapshotStkPosition;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SnapshotStkPositionDao {
    int deleteByPrimaryKey(Long id);

    int insert(SnapshotStkPosition record);

    int insertSelective(SnapshotStkPosition record);

    SnapshotStkPosition selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SnapshotStkPosition record);

    int updateByPrimaryKey(SnapshotStkPosition record);

    void batchInsert(List<SnapshotStkPosition> recordList);

    @ResultMap("BaseResultMap")
    @Select("select * from t_snapshot_stk_position where snapshot_code =  #{snapshotCode}")
    List<SnapshotStkPosition> getListBySnapshotCode(String snapshotCode);

    @ResultMap("BaseResultMap")
    @Select("select * from t_snapshot_stk_position" +
            " where portfolio_code = #{portfolioCode} " +
            " and strategy_code =  #{strategyCode} and asset_no = #{assetNo} " +
            "and belong_time < #{endBelongTime} " +
            "order by belong_time desc limit 1")
    SnapshotStkPosition findOne(SnapshotStkPosition record);

}