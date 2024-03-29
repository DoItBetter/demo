package com.cx.qt.data.service.http.response;

import com.cx.qt.data.service.http.bean.DCSnapshotHttpBean;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 2019/7/25
 * Time: 7:33 PM
 */
@Data
public class FuturesSnapshotResponse extends BaseDataHttpResponse {

    private Map<String, DCSnapshotHttpBean> data = new HashMap<>();
}
