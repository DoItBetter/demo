/**
 * 
 */
package com.kuainiu.qt.data.common.util.seq;


import org.apache.commons.lang3.StringUtils;

public enum SysSeqRuleEnum {
  
  /** 通用业务调用标志号 */
  NORMAL_SEQ(SysSeqRuleEnum.NORMAL_SEQ_CODE, 1, "通用业务调用标志号"),
  
  /** 查询业务调用标志号 */
  QUERAY_SEQ(SysSeqRuleEnum.QUERY_SEQ_CODE, 2, "查询业务调用标志号"),
  
  /** 批次标志号 */
  BATCH_SEQ(SysSeqRuleEnum.BATCH_SEQ_CODE, 3, "批次标志号"),
  
  /** 汇总标志号 */
  SUMMARY_SEQ(SysSeqRuleEnum.SUMMARY_SEQ_CODE, 4, "汇总标志号"),
  
  /** 汇总明细标志号 */
  SUMMARY_ENTRY_SEQ(SysSeqRuleEnum.SUMMARY_ENTRY_SEQ_CODE, 5, "汇总明细标志号"),
  
  /** 出款单标志号 */
  CASH_OUT_SEQ(SysSeqRuleEnum.CASH_OUT_SEQ_CODE, 6, "出款单标志号"),
  
  /** 用户交易单标志号 */
  SETTING_TRANS_SEQ(SysSeqRuleEnum.SETTING_TRANS_SEQ_CODE, 7, "用户交易单标志号"),
  
  /** 用户交易单标志号 */
  UNIQUE_CODE_SEQ(SysSeqRuleEnum.UNIQUE_CODE_SEQ_CODE, 8, "专用码标志号"),;
  
  public static final String NORMAL_SEQ_CODE = "normal_seq";
  
  public static final String QUERY_SEQ_CODE = "query_seq";
  
  public static final String BATCH_SEQ_CODE = "batch_seq";
  
  public static final String SUMMARY_SEQ_CODE = "summary_seq";
  
  public static final String SUMMARY_ENTRY_SEQ_CODE = "summary_entry_seq";
  
  public static final String CASH_OUT_SEQ_CODE = "cash_out_seq";
  
  public static final String SETTING_TRANS_SEQ_CODE = "setting_trans_seq";
  
  public static final String UNIQUE_CODE_SEQ_CODE = "unique_code_seq_code";
  
  private String code;
  
  /** 默认不为0 */
  private int type;
  
  private String desc;
  
  SysSeqRuleEnum(String code, int type, String desc){
    this.code = code;
    this.type = type;
    this.desc = desc;
  }
  
  /**
   * 通过规则编码查询支持规则
   * 
   * @param code
   * @return 如果找不到或者code为null, 返回null
   */
  public static SysSeqRuleEnum getRuleByCode(String code){
    if (null == code) {
      return null;
    }
    
    for (SysSeqRuleEnum rul : SysSeqRuleEnum.values()) {
      if (StringUtils.equals(rul.code, code)) {
        return rul;
      }
    }
    return null;
  }
  
  public static SysSeqRuleEnum getRuleByType(int type){
    for (SysSeqRuleEnum rul : SysSeqRuleEnum.values()) {
      if (rul.getType() == type) {
        return rul;
      }
    }
    return null;
  }
  
  /**
   * @return the code
   */
  public String getCode(){
    return code;
  }
  
  /**
   * @return the desc
   */
  public String getDesc(){
    return desc;
  }
  
  /**
   * @return the type
   */
  public int getType(){
    return type;
  }
  
}
