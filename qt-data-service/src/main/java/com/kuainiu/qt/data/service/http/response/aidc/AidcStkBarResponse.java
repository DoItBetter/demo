package com.kuainiu.qt.data.service.http.response.aidc;

import com.kuainiu.qt.data.service.http.bean.StkBarHttpBean;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/10/29
 * Time: 7:01 PM
 */
@Data
public class AidcStkBarResponse extends DCBaseResponse{
    private Map<String, StkBarHttpBean> data = new HashMap<>();
}
