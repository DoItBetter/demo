package com.cx.qt.data.service.http.response.aidc;

import com.cx.qt.data.service.http.bean.DCStkHttpBean;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 2019/9/20
 * Time: 10:11 AM
 */
@Data
public class StkSnapshotResponse extends DCBaseResponse {

    private Map<String, DCStkHttpBean> data = new HashMap<>();
}
