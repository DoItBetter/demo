package com.kuainiu.qt.data.dal.dao;

import com.kuainiu.qt.data.dal.entity.SnapshotPortfolio;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface SnapshotPortfolioDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SnapshotPortfolio record);

    int insertSelective(SnapshotPortfolio record);

    SnapshotPortfolio selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SnapshotPortfolio record);

    int updateByPrimaryKey(SnapshotPortfolio record);

    @ResultMap("BaseResultMap")
    @Select("select * from t_snapshot_portfolio where " +
            "belong_time " +
            "in " +
            "(select max(belong_time) from t_snapshot_portfolio where `portfolio_code` = #{portfolioCode} group by belong_date) " +
            "order by `belong_time` ")
    List<SnapshotPortfolio> findLastRecordPerDayByPFCode(String portfolioCode);

    List<SnapshotPortfolio> findSnapshotPortfolioList(SnapshotPortfolio record);

    @ResultMap("BaseResultMap")
    @Select("<script>select * from t_snapshot_portfolio" +
            " where `portfolio_code` = #{portfolioCode} " +
            "<if test=\"endBelongTime != null\">\n" +
            "      and belong_time &lt;= #{endBelongTime,jdbcType=TIMESTAMP}\n" +
            "    </if>" +
            "order by belong_time desc limit 1</script>")
    SnapshotPortfolio findOneByPortfolioCode(SnapshotPortfolio record);

    @ResultMap("BaseResultMap")
    @Select("select STD(realtime_returns) as std from t_snapshot_portfolio where `portfolio_code` = #{portfolioCode}")
    SnapshotPortfolio getStdByPFCode(String portfolioCode);

    @ResultMap("BaseResultMap")
    @Select("select STD(balance_returns) as std from t_snapshot_portfolio where `portfolio_code` = #{portfolioCode} and balance_returns is not null " +
            "and DATE_FORMAT(belong_time,'%H:%i:%S')>='09:30:00' and DATE_FORMAT(belong_time,'%H:%i:%S')<='15:00:00' "+
            "and belong_time < #{endBelongTime}")
    SnapshotPortfolio getBalanceReturnStdByPortfolioCode(SnapshotPortfolio snapshotPortfolio);

    @ResultMap("BaseResultMap")
    @Select("select * from t_snapshot_portfolio where portfolio_code = #{portfolioCode} and belong_time = #{belongTime} " +
            "and error_flag=#{errorFlag} order by belong_time desc limit 1")
    SnapshotPortfolio getPortfolioByPortfolioCodeAndBelongTime(SnapshotPortfolio snapshotPortfolio);

    List<SnapshotPortfolio> getLastPortfolioByPortfolioCodeAndBelongTime(SnapshotPortfolio snapshotPortfolio);

    @ResultMap("BaseResultMap")
    @Select("select * from t_snapshot_portfolio where `portfolio_code` = #{portfolioCode} and error_flag=#{errorFlag}  order by belong_time desc limit 1")
    SnapshotPortfolio getInfoRatioByPFCode(SnapshotPortfolio snapshotPortfolio);

    @ResultMap("BaseResultMapWithAccount")
    @Select("select * from t_snapshot_portfolio where `portfolio_code` = #{portfolioCode} and belong_time < #{endBelongTime} order by belong_time desc limit 1")
    SnapshotPortfolio getLastBeforeOpenMarket(SnapshotPortfolio snapshotPortfolio);

    @Update("<script>" +
            "update t_snapshot_portfolio set " +
            "<if test=\"baseReturns != null\">\n" +
            "   base_returns = #{baseReturns,jdbcType=DECIMAL},\n" +
            " </if>\n" +
            " <if test=\"baseRealtimeReturns != null\">\n" +
            "   base_realtime_returns = #{baseRealtimeReturns,jdbcType=DECIMAL},\n" +
            " </if> " +
            "<if test=\"balanceReturns != null\">\n" +
            "   balance_returns = #{balanceReturns,jdbcType=DECIMAL},\n" +
            " </if>\n" +
            "   id = #{id}" +
            " where id = #{id}" +
            "</script>")
    int updateReturnsFields(SnapshotPortfolio snapshotPortfolio);

    @Update("<script>" +
            "update t_snapshot_portfolio set " +
            "<if test=\"informationRatio != null\">\n" +
            "   information_ratio = #{informationRatio,jdbcType=DECIMAL},\n" +
            " </if>\n" +
            "<if test=\"errorFlag != null\">\n" +
            "   error_flag = #{errorFlag,jdbcType=VARCHAR},\n" +
            " </if>\n" +
            "   id = #{id}" +
            " where id = #{id}" +
            "</script>")
    int updateInfoRatio(SnapshotPortfolio snapshotPortfolio);
}