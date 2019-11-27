package com.kuainiu.qt.data.biz.bean;

import lombok.Data;

import java.util.Date;

@Data
public class StkPositionInBean extends BaseDataInBean {

    private String strategyCode;

    private String assetNo;

    private Date endBelongTime;
}
