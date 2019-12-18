package com.cx.qt.data.dal.dao;

import com.cx.qt.data.dal.entity.SnapshotPortfolioCashflow;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SnapshotPortfolioCashflowDao {
    int deleteByPrimaryKey(Long id);

    int insert(SnapshotPortfolioCashflow record);

    int insertSelective(SnapshotPortfolioCashflow record);

    SnapshotPortfolioCashflow selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SnapshotPortfolioCashflow record);

    int updateByPrimaryKey(SnapshotPortfolioCashflow record);

    void batchInsert(List<SnapshotPortfolioCashflow> recordList);

    @ResultMap("BaseResultMap")
    @Select("select * from t_snapshot_portfolio_cashflow where snapshot_code =  #{snapshotCode}")
    List<SnapshotPortfolioCashflow> getListBySnapshotCode(String snapshotCode);
}