package com.cx.qt.data.facade.code;

/**
 * 目前统一使用11位，
 * 前三位为子系统码
 * 中间4位为子系统模块功能码，最后四位为逻辑异常码
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 2019/5/19
 * Time: 10:42 AM
 * @author user
 */
public enum QtDataRspCode {
    /**
     * transaction response: succeed
     */
    SUCCESS("处理成功", "10"),
    /**
     * transaction response: failed
     */
    FAIL("操作失败", "20"),
    /**
     * 业务类异常 2XXX,外部需回滚
     */
    ERR_REPEAT_SUBMIT("重复的交易流水", "00200012001"),

    /**
     * 系统内异常 3xxx
     */
    ERR_SYS_ERROR("交易系统错误", "00200013001"),
    SYS_TIMEOUT("交易系统超时", "00200013002"),
    SYS_ERROR("交易系统错误", "00200013003"),
    ERR_DBERR("db错误", "00200013004"),
    ERR_CHANNEL_RULE_FAIL("渠道路由没命中", "00200013005"),
    ERR_COPY_LIST("Copy list fail", "00200013006"),
    ERR_RESPONSE_REBUILD("出参构建错误", "00200033001"),
    ERR_SYS_RPC("系统间调用RPC异常", "00200013007"),

    /**
     * 系统间问题 ,4xxx
     */
    ERR_PARAM_ERROR("参数错误", "00200014001"),

    ERR_DUBBO_REMOTE_CONNECT_ERROR("DUBBO远程服务不可用（OES系统）", "00200014035"),
    ERR_DUBBO_REMOTE_NO_PROVIDER("DUBBO服务无服务提供者（OES系统）", "00200014036"),
    ERR_DUBBO_REMOTE_CONNECT_TIMEOUT("请求超时(测试环境一般是券商系统不稳定导致的)", "00200014037"),
    ERR_HTTP_CONNECT("HTTP连接异常", "00200014039"),
    ERR_HTTP_RESPONSE_CLASS_NULL("HTTP请求响应class为空", "00200014040"),

    ERR_PARAM_SYS_ID("系统号不存在", "00200014002"),
    ERR_PARAM_REQUEST("请求入参为空", "00200014022"),
    ERR_PARAM_RESPONSE("响应为空", "00200014041"),

    ERR_PARAM_PORTFOLIO_CODE_NULL("投资组合不能为空", "00200014003"),
    ERR_AIDC_FUTURES_SNAPSHOT_QRY("从AIDC查询期货最新价失败", "00200014004"),
    ERR_DC_STK_SNAPSHOT_QRY("从数仓查询股票快照失败", "00200014005"),
    ERR_PARAM_STRATEGY_CODE_NULL("策略编号不能为空", "00200014006"),
    ERR_PARAM_ASSET_NO_NULL("合约代码不能为空", "00200014007"),
    ERR_AIDC_QRY_RM_FAIL("丛AIDC查询RM失败", "00200014008"),
    ERR_PARAM_END_BELONG_TIME_NULL("endBelongTime为空", "00200014009"),
    ERR_PARAM_ERROR_FLAG_NULL("错误标志参数为空", "00200014010"),


    /**
     *  业务类异常 5XXX,外部不用回滚
     */
    ERR_BUSI_ERROR("业务错误", "00200015001"),
    ERR_EMPTY_SER_BEAN("serBean为空", "00200015002"),
    ERR_EMPTY_IN_BEAN("inBean为空", "00200015003"),

    ERR_QRY_SNAPSHOT_PORTFOLIO_STD("快照方差查询失败", "00200015004"),
    ERR_PORTFOLIOSNAPSHOT_INFO_QRY_FAIL("快照信息查询失败", "00200015005"),
    ERR_PORTFOLIO_LIST_QRY_FAIL("投资组合列表查询失败", "00200015006"),
    ERR_PORTFOLIO_SS_RECORD("投资组合快照记录失败！", "00200015007"),
    ERR_SNAPSHOT_FUTURES_ACCOUNT_SNAPSHOT_CODE("期货账户快照查询，投资组合代码为空", "00200015008"),
    ERR_FUTURES_ACCOUNT_LIST_QRY("期货账户列表查询失败", "00200015009"),
    ERR_SNAPSHOT_FUTURES_ACCOUNT_SER_BEAN("期货账户快照serBean不能为空", "00200015010"),
    ERR_SNAPSHOT_FUTURES_ACCOUNT_CODE("期货账户编号为空", "00200015011"),
    ERR_QRY_TRANS_PORTFOLIO_FAIL("投资组合查询trans失败！", "00200015012"),
    ERR_AIDC_ASSET_NAME("从AIDC获取的合约名称为空", "002000150013"),
    ERR_PORTFOLIO_QRY_FAIL("投资组合查询失败", "002000150014"),
    ERR_PORTFOLIO_QRY_BY_BELONGTIME_FAIL("某时刻投资组合查询失败", "002000150015"),
    ERR_QRY_TRANS_PORTFOLIO_ALL_FAIL("所有可用投资组合查询失败", "002000150016"),
    ERR_QRY_TRANS_PORTFOLIO_DISTINCT_FAIL("投资组合去重查询失败", "002000150017"),
    ERR_CALC_INFO_RATIO("infomation ratio 计算失败", "002000150018"),


    /**
     *  数据库操作异常，6XXX
     */
    ERR_DB_UPDATE("更新失败", "00200016000"),
    ERR_DB_ADD("数据创建失败", "00200016001"),
    ERR_DB_SNAPSHOT_PORTFOLIO_QRY("投资组合快照查询失败", "00200016002"),
    ERR_DB_SNAPSHOT_FUTURES_ACCOUNT_LIST_QRY("期货快照列表查询失败", "00200016003"),
    ERR_DB_SNAPSHOT_STK_ACCOUNT_ADD("账户余额变动日志列表查询失败", "00200016004"),
    ERR_DB_SNAPSHOT_FUTURES_ACCOUNT_ADD("期货快照添加失败", "00200016005"),
    ERR_DB_SNAPSHOT_FUTURES_ACCOUNT_BATCH_ADD("期货快照批量添加添加失败", "00200016006"),
    ERR_DB_SNAPSHOT_FUTURES_ACCOUNT_QRY("期货快照查询失败", "00200016007"),
    ERR_DB_SNAPSHOT_FUTURES_POSITION_BATCH_ADD("[db]futures仓位批量添加失败", "00200016008"),
    ERR_DB_SNAPSHOT_FUTURES_POSITION_QRY("[db]futures仓位查询失败", "00200016009"),
    ERR_DB_HISTORY_PNL_QRY("历史累计盈亏查询失败", "00200016010"),
    ERR_DB_SNAPSHOT_PORTFOLIO_CASHFLOW_BATCH_ADD("出入金快照批量添加失败", "00200016011"),
    ERR_DB_SNAPSHOT_PORTFOLIO_CASHFLOW_QRY("[db]出入金快照查询失败", "00200016012"),
    ERR_DB_SNAPSHOT_STK_FEE_BATCH_ADD("股票快照费用添加失败", "00200016013"),
    ERR_DB_SNAPSHOT_STK_POSITION_BATCH_ADD("stk仓位快照批量添加失败", "00200016014"),
    ERR_DB_SNAPSHOT_STK_POSITION_QRY("[db]stk仓位快照查询失败", "00200016015"),

    ;

    private String code;

    private String msg;

    private QtDataRspCode(String message, String code){
        this.code = code;
        this.msg = message;
    }

    public String getCode(){
        return code;
    }

    public String getMessage(){
        return msg;
    }

    public static QtDataRspCode getEnum(String value){
        if (null == value) {
            return null;
        }
        QtDataRspCode[] crc = QtDataRspCode.values();
        for (int i = 0; i < crc.length; i++) {
            if (crc[i].getCode().equals(value)) {
                return crc[i];
            }
        }
        return null;
    }

    public static QtDataRspCode getEnumOrDefault(String value){
        if (null == value) {
            return SYS_ERROR;
        }
        QtDataRspCode[] crc = QtDataRspCode.values();
        for (int i = 0; i < crc.length; i++) {
            if (crc[i].getCode().equals(value)) {
                return crc[i];
            }
        }
        return SYS_ERROR;
    }
}

