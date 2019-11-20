package com.kuainiu.qt.data.service.http.response;

import com.kuainiu.qt.data.service.http.bean.DCSnapshotHttpBean;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/7/25
 * Time: 7:33 PM
 */
@Data
public class FuturesSnapshotResponse extends BaseDataHttpResponse {

    private Map<String, DCSnapshotHttpBean> data = new HashMap<>();
}
