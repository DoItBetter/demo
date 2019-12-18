/**
 * 
 */
package com.cx.qt.data.common.util.seq;


import com.cx.qt.framework.common.util.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Description: 系统通用标志号管理器
 * 支持的特殊标志号需要在SysSeqRuleEnum中定义：
 * 
 */
@Service
public class SysSeqNoManager {



  private final static short SEQ_LEN = 27;
  
  private final static short BALANCE_SEQ_LEN = 32;

  private static final Logger LOGGER = LoggerFactory.getLogger(SysSeqNoManager.class);

  public static final String ZERO = "0";

  /**
   * 生成调用标志号
   * 
   * @return
   */
  public String getInvokeNo(){
    return "123";
  }

  /**
   * 生成标志号
   * 
   * @param sysSeqRuleEnum 支持的标志号生成规则
   * @return 有效的标志号生成规则返回对应标志号，否则返回通用标志号
   */
  public String getSeqNo(SysSeqRuleEnum sysSeqRuleEnum){

    if (null == sysSeqRuleEnum) {
      LoggerUtils.error(LOGGER, "业务标志生成规则不能为空！");
      return null;
    }

    String seqNo;
    switch (sysSeqRuleEnum) {
      // 查询seq
      case QUERAY_SEQ : {
        seqNo = getQuerySeqNo();
        break;
      }
      
      case UNIQUE_CODE_SEQ : {
        seqNo = getPrefixSeqNo();
        break;
      }
        // 默认seqNo
      case NORMAL_SEQ :
      default : {
        seqNo = getNormalSeqNo();
        break;
      }
    }
    return seqNo;
  }

  /**
   * 通用的seqNo，服务调用以及其他未定义情况可使用
   * 
   * @return
   */
  private String getNormalSeqNo(){

    return "456";
  }

  /**
   * 查询服务的seqNo
   * 
   * @return
   */
  private String getQuerySeqNo(){
    return "";
  }
  
  private String getPrefixSeqNo(){

    return String.valueOf(System.currentTimeMillis()).substring(1);
  }
}
