package com.kuainiu.qt.data.service.bean;

import lombok.Data;

@Data
public class BaseQryReqSerBean extends BaseReqSerBean {
    private Integer offset;

    private Integer limit;
}
