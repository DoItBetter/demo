package com.kuainiu.qt.data.service.http.bean;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/8/6
 * Time: 4:24 PM
 */
@Data
public class DCRunDaysHttpBean extends BaseHttpBean{
    private List<String> runDays = new ArrayList<>();
}
