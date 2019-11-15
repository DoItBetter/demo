package com.kuainiu.qt.data.biz.bean;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/7/30
 * Time: 8:13 PM
 */
@Data
public class FuturesTransQryGroupBean {
    private Integer total;

    List<FuturesTransOutBean> transList = new ArrayList<>();
}
