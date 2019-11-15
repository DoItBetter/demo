package com.kuainiu.qt.data.dal.dao;

import com.kuainiu.qt.data.dal.entity.SnapshotStkFee;
import java.util.List;

public interface SnapshotStkFeeDao {
    int deleteByPrimaryKey(Long id);

    int insert(SnapshotStkFee record);

    int insertSelective(SnapshotStkFee record);

    SnapshotStkFee selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SnapshotStkFee record);

    int updateByPrimaryKey(SnapshotStkFee record);

    void batchInsert(List<SnapshotStkFee> stkAccountList);
}