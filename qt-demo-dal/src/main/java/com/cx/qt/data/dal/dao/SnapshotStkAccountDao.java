package com.cx.qt.data.dal.dao;

import com.cx.qt.data.dal.entity.SnapshotStkAccount;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SnapshotStkAccountDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SnapshotStkAccount record);

    int insertSelective(SnapshotStkAccount record);

    SnapshotStkAccount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SnapshotStkAccount record);

    int updateByPrimaryKey(SnapshotStkAccount record);

    void batchInsert(List<SnapshotStkAccount> stkAccountList);

    @ResultMap("BaseResultMap")
    @Select("select * from t_snapshot_stk_account where snapshot_code = #{snapshotCode} ")
    List<SnapshotStkAccount> getListBySnapshotCode(SnapshotStkAccount record);

    int createAndGetId(SnapshotStkAccount snapshotStkAccount);

    @ResultMap("BaseResultMap")
    @Select("select * from t_snapshot_stk_account where snapshot_code = #{snapshotCode} ")
    List<SnapshotStkAccount> findListBySnapshotCode(String snapshotCode);
}