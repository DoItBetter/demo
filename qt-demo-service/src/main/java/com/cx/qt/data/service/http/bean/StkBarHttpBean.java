package com.cx.qt.demo.service.http.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 2019/10/29
 * Time: 7:03 PM
 */
@Data
public class StkBarHttpBean extends BaseHttpBean {

    List<BigDecimal> limit_down = new ArrayList<>();

    List<BigDecimal> limit_up = new ArrayList<>();
}
