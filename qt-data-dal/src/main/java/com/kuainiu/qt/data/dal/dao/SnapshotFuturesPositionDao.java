package com.kuainiu.qt.data.dal.dao;

import com.kuainiu.qt.data.dal.entity.SnapshotFuturesPosition;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SnapshotFuturesPositionDao {
    int deleteByPrimaryKey(Long id);

    int insert(SnapshotFuturesPosition record);

    int insertSelective(SnapshotFuturesPosition record);

    SnapshotFuturesPosition selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SnapshotFuturesPosition record);

    int updateByPrimaryKey(SnapshotFuturesPosition record);

    void batchInsert(List<SnapshotFuturesPosition> recordList);

    @ResultMap("BaseResultMap")
    @Select("select * from t_snapshot_futures_position where snapshot_code =  #{snapshotCode}")
    List<SnapshotFuturesPosition> getListBySnapshotCode(String snapshotCode);

    @ResultMap("BaseResultMap")
    @Select("select " +
            "position.id, position.snapshot_code, position.asset_no, position.pnl, position.daily_pnl, position.holding_pnl, position.realized_pnl, position.trans_cost,\n" +
            "position.margin, position.frz_margin, position.market_value, position.closable_buy_qty, position.buy_today_qty, position.buy_avg_open_price, position.buy_avg_holding_price, position.closable_sell_qty, position.sell_today_qty, position.sell_avg_open_price, position.sell_avg_holding_price " +
            "from t_snapshot_futures_position as position " +
            "inner join t_snapshot_portfolio as portfolio on position.snapshot_code = portfolio.snapshot_code " +
            "where position.asset_no = #{assetNo} and portfolio.belong_time <= #{endCreateTime} " +
            "order by portfolio.belong_time desc limit 1")
    SnapshotFuturesPosition getLastRecordBeforeToday(SnapshotFuturesPosition record);

    @ResultMap("BaseResultMap")
    @Select("select * from t_snapshot_futures_position" +
            " where " +
            "strategy_code =  #{strategyCode} and asset_no = #{assetNo} and belong_time < #{endBelongTime} " +
            "order by belong_time desc limit 1")
    SnapshotFuturesPosition findOne(SnapshotFuturesPosition record);
}