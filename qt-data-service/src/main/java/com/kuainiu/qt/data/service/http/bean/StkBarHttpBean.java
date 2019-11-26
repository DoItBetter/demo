package com.kuainiu.qt.data.service.http.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/10/29
 * Time: 7:03 PM
 */
@Data
public class StkBarHttpBean extends BaseHttpBean {

    List<BigDecimal> limit_down = new ArrayList<>();

    List<BigDecimal> limit_up = new ArrayList<>();
}
